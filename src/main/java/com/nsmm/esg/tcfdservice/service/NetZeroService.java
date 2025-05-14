package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.GoalNetZeroEmissionResponse;
import com.nsmm.esg.tcfdservice.dto.GoalNetZeroIndustryResponse;
import com.nsmm.esg.tcfdservice.dto.GoalNetZeroRequest;
import com.nsmm.esg.tcfdservice.dto.GoalNetZeroResponse;
import com.nsmm.esg.tcfdservice.entity.GoalNetZero;
import com.nsmm.esg.tcfdservice.entity.GoalNetZeroEmission;
import com.nsmm.esg.tcfdservice.entity.GoalNetZeroIndustry;
import com.nsmm.esg.tcfdservice.repository.GoalNetZeroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NetZeroService {

    private final GoalNetZeroRepository goalNetZeroRepository;

    // 산업별 기본 배출계수 (EF)
    private static final Map<String, Double> INDUSTRY_EMISSION_FACTORS = Map.of(
            "전력 및 에너지", 4.0,
            "화석연료 생산", 10.8,
            "철강 제조", 3.0,
            "시멘트 제조", 7.0,
            "정유 및 화학", 4.0,
            "운송", 1.5,
            "전자", 0.4,
            "농업/임업", 1.7,
            "상업용 부동산", 0.5,
            "주택담보대출", 0.1
    );

    private static final List<Integer> TARGET_YEARS = List.of(2025, 2030, 2040, 2050);

    /**
     * 넷제로 목표 생성
     */
    @Transactional
    public GoalNetZeroResponse createNetZeroGoal(Long memberId, GoalNetZeroRequest request) {
        GoalNetZero goal = buildGoal(memberId, request);
        GoalNetZero saved = goalNetZeroRepository.save(goal);
        return toResponse(saved);
    }

    /**
     * 넷제로 목표 조회 (사용자 전체)
     */
    public List<GoalNetZeroResponse> getNetZeroGoals(Long memberId) {
        return goalNetZeroRepository.findByMemberId(memberId).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * 넷제로 목표 단건 조회
     */
    public GoalNetZeroResponse getNetZeroGoalById(Long id, Long memberId) {
        GoalNetZero goal = goalNetZeroRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 목표가 존재하지 않거나 권한이 없습니다."));
        return toResponse(goal);
    }

    /**
     * 넷제로 목표 수정
     */
    @Transactional
    public GoalNetZeroResponse updateNetZeroGoal(Long id, Long memberId, GoalNetZeroRequest request) {
        GoalNetZero goal = goalNetZeroRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 목표가 존재하지 않거나 권한이 없습니다."));

        goal.updateInfo(request.getIndustrialSector(), request.getBaseYear(), request.getTargetYear(), request.getScenario());
        goal.getIndustries().clear();
        goal.getEmissions().clear();

        GoalNetZero updated = buildGoal(memberId, request);
        updated.getIndustries().forEach(goal::addIndustry);
        updated.getEmissions().forEach(goal::addEmission);

        return toResponse(goal);
    }

    /**
     * 넷제로 목표 삭제
     */
    @Transactional
    public void deleteNetZeroGoal(Long id, Long memberId) {
        GoalNetZero goal = goalNetZeroRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 목표가 존재하지 않거나 권한이 없습니다."));
        goalNetZeroRepository.delete(goal);
    }

    /**
     * GoalNetZero 엔티티 생성 + 산업/배출량 데이터 세팅
     */
    private GoalNetZero buildGoal(Long memberId, GoalNetZeroRequest request) {
        List<GoalNetZeroRequest.IndustryAsset> assets = request.getAssets();
        if (assets == null || assets.isEmpty()) {
            throw new IllegalArgumentException("최소 1개의 자산 항목이 필요합니다.");
        }

        // 산업 항목 포함 GoalNetZero 생성 (DTO의 toEntity 활용)
        GoalNetZero goal = request.toEntity(memberId);

        double totalBaseEmission = 0.0;

        // 산업별 배출량 정보 업데이트 (EF, AF, Eb 계산)
        for (GoalNetZeroIndustry industry : goal.getIndustries()) {
            double ef = resolveEmissionFactor(industry.getIndustry(), industry.getEmissionFactor());
            double af = industry.getAmount() / industry.getTotalAssetValue();
            double baseEmission = industry.getAmount() * ef * af;

            industry.setEmissionFactor(ef);
            industry.setAttributionFactor(af);
            industry.setBaseEmission(baseEmission);
            totalBaseEmission += baseEmission;

            log.info("📌 산업={}, EF={}, AF={}, Ai={}, AV={}, Eb={}",
                    industry.getIndustry(), ef, af, industry.getAmount(), industry.getTotalAssetValue(), baseEmission);
        }

        // 평균 감축률 계산
        double reductionRate = calculateAverageReductionRate(
                totalBaseEmission, 1.0, request.getBaseYear(), request.getTargetYear());
        int roundedReductionRate = (int) Math.round(reductionRate * 100);
        log.info("✅ 총Eb={}, 평균감축률={}%", Math.round(totalBaseEmission), roundedReductionRate);

        // 연도별 배출량 계산 및 등록
        for (int year : TARGET_YEARS) {
            double factor = Math.pow(1 - reductionRate, year - request.getBaseYear());
            double emission = (year == request.getBaseYear()) ? totalBaseEmission
                    : (year == request.getTargetYear()) ? 1
                    : calculateYearlyEmission(totalBaseEmission, reductionRate, request.getBaseYear(), year);

            GoalNetZeroEmission e = GoalNetZeroEmission.builder()
                    .year(year)
                    .emission(Math.round(emission))
                    .build();

            goal.addEmission(e);

            log.info("📊 E({}) = {}, f(y) = {}", year, Math.round(emission), String.format("%.6f", factor));
        }

        return goal;
    }


    /**
     * 산업별 배출계수 계산
     */
    private double resolveEmissionFactor(String industry, double providedEf) {
        if (providedEf != 0) return providedEf;
        double ef = INDUSTRY_EMISSION_FACTORS.getOrDefault(industry, 0.0);
        if (ef == 0.0) throw new IllegalArgumentException("배출계수가 정의되지 않은 산업입니다: " + industry);
        return ef;
    }

    /**
     * 평균 감축률 계산
     */
    private double calculateAverageReductionRate(double baseEmission, double targetEmission, int baseYear, int targetYear) {
        return 1 - Math.pow(targetEmission / baseEmission, 1.0 / (targetYear - baseYear));
    }

    /**
     * 연도별 배출량 계산
     */
    private double calculateYearlyEmission(double baseEmission, double reductionRate, int baseYear, int year) {
        return baseEmission * Math.pow(1 - reductionRate, year - baseYear);
    }

    /**
     * GoalNetZero → DTO 변환
     */
    private GoalNetZeroResponse toResponse(GoalNetZero goal) {
        return GoalNetZeroResponse.fromEntity(
                goal,
                goal.getIndustries().stream().map(GoalNetZeroIndustryResponse::fromEntity).toList(),
                goal.getEmissions().stream().map(GoalNetZeroEmissionResponse::fromEntity).toList()
        );
    }
}
