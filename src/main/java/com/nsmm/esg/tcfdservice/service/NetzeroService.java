package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.GoalNetzeroRequest;
import com.nsmm.esg.tcfdservice.entity.GoalNetzero;
import com.nsmm.esg.tcfdservice.repository.GoalNetzeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NetzeroService {

    private final GoalNetzeroRepository goalNetzeroRepository;

    // ✅ 산업별 배출계수 설정 (고정)
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

    // ✅ NetZero 목표 목록 조회
    public List<GoalNetzeroRequest> getNetZeroGoals(Long memberId) {
        return goalNetzeroRepository.findByMemberId(memberId).stream()
                .map(GoalNetzeroRequest::fromEntity)
                .collect(Collectors.toList());
    }

    // ✅ NetZero 목표 단건 조회
    public GoalNetzeroRequest getNetZeroGoalById(Long id, Long memberId) {
        GoalNetzero goal = goalNetzeroRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 NetZero 목표가 존재하지 않거나 권한이 없습니다."));
        return GoalNetzeroRequest.fromEntity(goal);
    }

    // ✅ NetZero 목표 삭제
    @Transactional
    public void deleteNetZeroGoal(Long id, Long memberId) {
        GoalNetzero goal = goalNetzeroRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 NetZero 목표가 존재하지 않거나 권한이 없습니다."));
        goalNetzeroRepository.delete(goal);
    }

    // ✅ NetZero 목표 저장
    @Transactional
    public Long createNetZeroGoal(Long memberId, GoalNetzeroRequest request) {
        double emissionFactor = getEmissionFactorByIndustry(request.getIndustrialSector());
        double baseYearEmission = calculateBaseYearEmission(
                request.getFinancialAssetValue(),
                request.getIndustrialSector(),
                request.getTotalAssetValue()
        );

        GoalNetzero goal = request.toEntity(memberId);
        goal.setEmissionFactor(emissionFactor);
        goal.setBaseYearEmission(baseYearEmission);
        goal.setTargetYearEmission(calculateTargetYearEmission(baseYearEmission, 2050, 2025));
        goal.setYearlyEmissions(calculateYearlyEmissions(baseYearEmission, 2025));

        return goalNetzeroRepository.save(goal).getId();
    }

    // ✅ NetZero 목표 수정
    @Transactional
    public void updateNetZeroGoal(Long id, Long memberId, GoalNetzeroRequest request) {
        GoalNetzero goal = goalNetzeroRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 NetZero 목표가 존재하지 않거나 권한이 없습니다."));

        double emissionFactor = getEmissionFactorByIndustry(request.getIndustrialSector());
        double baseYearEmission = calculateBaseYearEmission(
                request.getFinancialAssetValue(),
                request.getIndustrialSector(),
                request.getTotalAssetValue()
        );

        double targetYearEmission = calculateTargetYearEmission(baseYearEmission, 2050, 2025);
        Map<Integer, Double> yearlyEmissions = calculateYearlyEmissions(baseYearEmission, 2025);

        // ✅ 수정 메서드 호출
        goal.updateFromDto(
                request.getFinancialAssetValue(),
                request.getTotalAssetValue(),
                emissionFactor,
                baseYearEmission,
                targetYearEmission,
                yearlyEmissions
        );

        goalNetzeroRepository.save(goal);
    }

    // ✅ NetZero 중간 목표 배출량 조회 (2030, 2040, 2050)
    public Map<Integer, Double> getMidTargetEmissions(Long id, Long memberId) {
        GoalNetzero goal = goalNetzeroRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 NetZero 목표가 존재하지 않거나 권한이 없습니다."));
        return calculateYearlyEmissions(goal.getBaseYearEmission(), 2025);
    }

    // ✅ NetZero 목표 계산값 확인 (2030, 2040, 2050)
    public Map<String, Double> calculateNetZeroValues(Long id, Long memberId) {
        GoalNetzero goal = goalNetzeroRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 NetZero 목표가 존재하지 않거나 권한이 없습니다."));
        double baseYearEmission = goal.getBaseYearEmission();
        Map<Integer, Double> yearlyEmissions = calculateYearlyEmissions(baseYearEmission, 2025);

        Map<String, Double> result = new HashMap<>();
        result.put("Base Year Emission", baseYearEmission);
        result.put("2030 Emission", yearlyEmissions.get(2030));
        result.put("2040 Emission", yearlyEmissions.get(2040));
        result.put("2050 Emission", yearlyEmissions.get(2050));
        return result;
    }

    // ✅ NetZero 연도별 배출량 계산 (2030, 2040, 2050)
    public Map<Integer, Double> calculateYearlyEmissions(double baseYearEmission, int baseYear) {
        Map<Integer, Double> yearlyEmissions = new HashMap<>();
        for (int year : new int[]{2030, 2040, 2050}) {
            double reduction = baseYearEmission * Math.pow(0.5, (year - baseYear) / (2050.0 - baseYear));
            yearlyEmissions.put(year, year == 2050 ? 1.0 : reduction);
        }
        return yearlyEmissions;
    }

    // ✅ 기준년도 배출량 계산 (2025)
    public double calculateBaseYearEmission(double financialAssetValue, String industrialSector, double totalAssetValue) {
        double emissionFactor = getEmissionFactorByIndustry(industrialSector);
        double attributionFactor = financialAssetValue / totalAssetValue;
        return financialAssetValue * emissionFactor * attributionFactor;
    }

    // ✅ 목표년도 배출량 계산
    public double calculateTargetYearEmission(double baseYearEmission, int targetYear, int baseYear) {
        double averageReductionRate = 1 - Math.pow(1 / baseYearEmission, 1.0 / (targetYear - baseYear));
        double reductionFactor = Math.pow(1 - averageReductionRate, targetYear - baseYear);
        return baseYearEmission * reductionFactor;
    }

    // ✅ 산업별 배출계수 가져오기
    private double getEmissionFactorByIndustry(String industrialSector) {
        return INDUSTRY_EMISSION_FACTORS.getOrDefault(industrialSector, 0.0);
    }
}