package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalNetzero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class GoalNetzeroRequest {

    private Long id;
    private String industrialSector;     // 산업군 (예: 제조업, 에너지)
    private double financialAssetValue;  // 금융 자산 가치 (투자액, 대출액)
    private double totalAssetValue;      // 총 자산 가치 (기업가치, 총사업비, 자산가치)
    private String assetType;            // 자산 유형 (기업대출, 상장주식/채권, PF 등)
    private double emissionFactor;       // 산업별 배출계수 (자동 계산)
    private double attributionFactor;    // 귀속 계수 (AF) - 자동 계산됨
    private double baseYearEmission;     // 기준 연도 배출량 (tCO₂e) - 자동 계산됨
    private double targetYearEmission;   // 목표 연도 배출량 (tCO₂e) - 자동 계산됨
    private double reductionRate;        // 평균 감축률 (%) - 자동 계산됨
    private int baseYear = 2025;         // 기준 연도 (2025으로 고정)
    private int targetYear = 2050;       // 목표 연도 (2050으로 고정)
    private String scenario;             // ✅ 시나리오 (예: "IEA Net Zero 2050")

    // 연도별 예상 배출량 (2030, 2040, 2050)
    private Map<Integer, Double> yearlyEmissions = new HashMap<>();

    // ✅ DTO → Entity 변환
    public GoalNetzero toEntity(Long memberId) {
        return GoalNetzero.builder()
                .memberId(memberId)
                .industrialSector(this.industrialSector)
                .financialAssetValue(this.financialAssetValue)
                .totalAssetValue(this.totalAssetValue) // ✅ 총 자산 가치 추가
                .attributionFactor(this.attributionFactor) // 서비스에서 자동 계산됨
                .assetType(this.assetType)
                .emissionFactor(this.emissionFactor) // 서비스에서 자동 설정
                .baseYearEmission(this.baseYearEmission) // 서비스에서 자동 계산됨
                .targetYearEmission(this.targetYearEmission) // 서비스에서 자동 계산됨
                .reductionRate(this.reductionRate) // 서비스에서 자동 계산됨
                .baseYear(2025) // 기준년도 2025로 고정
                .targetYear(2050) // 목표년도 2050으로 고정
                .scenario(this.scenario)
                .yearlyEmissions(this.yearlyEmissions)
                .build();
    }

    // ✅ Entity → DTO 변환
    public static GoalNetzeroRequest fromEntity(GoalNetzero entity) {
        return GoalNetzeroRequest.builder()
                .id(entity.getId())
                .industrialSector(entity.getIndustrialSector())
                .financialAssetValue(entity.getFinancialAssetValue())
                .totalAssetValue(entity.getTotalAssetValue()) // ✅ 총 자산 가치 추가
                .attributionFactor(entity.getAttributionFactor())
                .assetType(entity.getAssetType())
                .emissionFactor(entity.getEmissionFactor())
                .baseYearEmission(entity.getBaseYearEmission())
                .targetYearEmission(entity.getTargetYearEmission())
                .reductionRate(entity.getReductionRate())
                .baseYear(2025) // 기준년도 2025로 고정
                .targetYear(2050) // 목표년도 2050으로 고정
                .scenario(entity.getScenario())
                .yearlyEmissions(entity.getYearlyEmissions())
                .build();
    }
}