package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.GoalKpiRequest;
import com.nsmm.esg.tcfdservice.dto.GoalNetzeroRequest;
import com.nsmm.esg.tcfdservice.entity.GoalKpi;
import com.nsmm.esg.tcfdservice.entity.GoalNetzero;
import com.nsmm.esg.tcfdservice.repository.GoalKpiRepository;
import com.nsmm.esg.tcfdservice.repository.GoalNetzeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalKpiRepository goalKpiRepository;
    private final GoalNetzeroRepository goalNetzeroRepository;

    /**
     * KPI 목표 저장
     */
    public Long createKpiGoal(Long memberId, GoalKpiRequest request) {
        GoalKpi entity = request.toEntity(memberId);
        return goalKpiRepository.save(entity).getId();
    }

    /**
     * KPI 목표 목록 조회
     */
    public List<GoalKpiRequest> getKpiGoals(Long memberId) {
        return goalKpiRepository.findByMemberId(memberId).stream()
                .map(GoalKpiRequest::fromEntity)
                .toList();
    }

    /**
     * 특정 KPI 목표 조회
     */
    public GoalKpiRequest getKpiGoalById(Long id, Long memberId) {
        GoalKpi kpi = goalKpiRepository.findById(id)
                .filter(g -> g.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("조회할 KPI 목표가 존재하지 않거나 권한이 없습니다."));
        return GoalKpiRequest.fromEntity(kpi);
    }


    /**
     * KPI 목표 수정
     */
    public void updateKpiGoal(Long goalId, Long memberId, GoalKpiRequest request) {
        GoalKpi goalKpi = goalKpiRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 KPI 목표가 존재하지 않습니다. ID = " + goalId));
        if (!goalKpi.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 목표에 대한 권한이 없습니다.");
        }
        goalKpi.updateFromDto(request);
    }

    /**
     * KPI 목표 삭제
     */
    public void deleteKpiGoal(Long goalId, Long memberId) {
        if (!goalKpiRepository.existsById(goalId)) {
            throw new IllegalArgumentException("해당 KPI 목표가 존재하지 않습니다. ID = " + goalId);
        }
        goalKpiRepository.deleteById(goalId);
    }
// --------------------------- NetZero 목표 생성 ---------------------------

/**
 * NetZero 목표 저장
 */
public Long createNetzeroGoal(Long memberId, GoalNetzeroRequest request, String scenario) {
    // NetZero 금융배출량 계산
    double baseEmission = calculateNetzeroEmission(
            request.getActivityAmount(),  // DTO의 활동량 사용
            request.getIndustrialSector(),
            request.getAssetType(),
            request.getAssetValue()       // DTO의 자산 가치 사용
    );

    // 엔티티 생성
    GoalNetzero entity = GoalNetzero.builder()
            .memberId(memberId)
            .industrialSector(request.getIndustrialSector())
            .baseYear(request.getBaseYear())
            .targetYear(request.getTargetYear())
            .baseYearEmission(baseEmission)
            .targetYearEmission(0) // 초기화, 아래에서 설정
            .reductionRate(request.getReductionRate())
            .financialAssetValue(request.getFinancialAssetValue())
            .attributionFactor(calculateAttributionFactor(
                    request.getActivityAmount(),  // DTO의 활동량 사용
                    request.getAssetValue(),      // DTO의 자산 가치 사용
                    request.getAssetType()
            ))
            .assetType(request.getAssetType())
            .scenario(scenario)
            .build();

    // 시나리오 기반 목표 배출량 설정
    entity.calculateTargetEmission(scenario);

    // 저장 및 ID 반환
    return goalNetzeroRepository.save(entity).getId();
}

/// NetZeri 목표 목록 조회
    public List<GoalNetzeroRequest> getNetzeroGoals(Long memberId) {
        return goalNetzeroRepository.findByMemberId(memberId).stream()
                .map(GoalNetzeroRequest::fromEntity)
                .toList();
    }

/// NetZero 목표 조회 (GET)
public GoalNetzeroRequest getNetzeroGoalById(Long id, Long memberId) {
    GoalNetzero netzero = goalNetzeroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 NetZero 목표가 존재하지 않습니다. ID = " + id));

    if (!netzero.getMemberId().equals(memberId)) {
        throw new IllegalArgumentException("해당 목표에 대한 권한이 없습니다.");
    }

    return GoalNetzeroRequest.fromEntity(netzero);
}

    // NetZero 목표 수정
    public void updateNetzeroGoal(Long goalId, Long memberId, GoalNetzeroRequest request, String scenario) {
        GoalNetzero netzero = goalNetzeroRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 NetZero 목표가 존재하지 않습니다. ID = " + goalId));

        if (!netzero.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 목표에 대한 권한이 없습니다.");
        }

        // DTO 기반 수정
        netzero.updateFromDto(request);

        // 시나리오 기반 목표 배출량 재계산
        netzero.calculateTargetEmission(scenario);

        // 수정된 엔티티 저장
        goalNetzeroRepository.save(netzero);
    }

/**
 * NetZero 목표 삭제
 */
public void deleteNetzeroGoal(Long goalId, Long memberId) {
    if (!goalNetzeroRepository.existsById(goalId)) {
        throw new IllegalArgumentException("해당 NetZero 목표가 존재하지 않습니다. ID = " + goalId);
    }
    goalNetzeroRepository.deleteById(goalId);
}

// --------------------------- NetZero 계산 메서드 ---------------------------

/**
 * NetZero 금융배출량 계산
 */
private double calculateNetzeroEmission(double activityAmount, String industry, String assetType, double assetValue) {
    double emissionFactor = getEmissionFactor(industry);
    double attributionFactor = calculateAttributionFactor(activityAmount, assetValue, assetType);
    return activityAmount * emissionFactor * attributionFactor;
}

/**
 * 배출계수 (Emission Factor) 자동 조회
 */
private double getEmissionFactor(String industry) {
    return switch (industry.toLowerCase()) {
        case "전력 및 에너지" -> 4.0;
        case "화석연료 생산" -> 10.8;
        case "철강 제조" -> 3.0;
        case "시멘트 제조" -> 7.0;
        case "정유 및 화학" -> 4.0;
        case "운송" -> 1.5;
        case "전자" -> 0.4;
        case "농업/임업" -> 1.7;
        case "상업용 부동산" -> 0.5;
        case "주택담보대출" -> 0.1;
        default -> throw new IllegalArgumentException("지원되지 않는 산업: " + industry);
    };
}

/**
 * 귀속 계수 (Attribution Factor, AF) 자동 계산
 */
private double calculateAttributionFactor(double activityAmount, double assetValue, String assetType) {
    return switch (assetType.toLowerCase()) {
        case "기업대출" -> activityAmount / assetValue;
        case "상장주식/채권" -> activityAmount / assetValue;
        case "부동산담보대출" -> activityAmount / assetValue;
        case "pf" -> activityAmount / assetValue;
        default -> throw new IllegalArgumentException("지원되지 않는 자산 유형: " + assetType);
    };
}

}