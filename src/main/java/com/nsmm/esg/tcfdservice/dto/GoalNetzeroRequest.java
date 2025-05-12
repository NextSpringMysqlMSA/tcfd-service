package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalNetzero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class GoalNetzeroRequest {

    private Long id;
    private String industrialSector;     // 산업군 (예: 제조업, 에너지)
    private double baseYearEmission;     // 기준 연도 배출량 (tCO₂e)
    private double targetYearEmission;   // 목표 연도 배출량 (tCO₂e)
    private double reductionRate;        // 감축율 (%)
    private int baseYear;                // 기준 연도
    private int targetYear;              // 목표 연도
    private double financialAssetValue;  // 금융 자산 가치 (투자액, 대출액)
    private double attributionFactor;    // 귀속 계수 (AF)
    private String assetType;            // 자산 유형 (기업대출, 상장주식/채권, PF 등)
    private String scenario;             // 시나리오 (IEA Net Zero 2050, NGFS Orderly Transition)
    private double activityAmount;       // 활동량 (예: 대출액, 투자액)
    private double assetValue;           // 자산 가치 (예: 총 자산, 기업 가치)

    // DTO → Entity 변환
    public GoalNetzero toEntity(Long memberId) {
        return GoalNetzero.builder()
                .memberId(memberId)
                .industrialSector(this.industrialSector)
                .baseYearEmission(this.baseYearEmission)
                .targetYearEmission(this.targetYearEmission)
                .reductionRate(this.reductionRate)
                .baseYear(this.baseYear)
                .targetYear(this.targetYear)
                .financialAssetValue(this.financialAssetValue)
                .attributionFactor(this.attributionFactor)
                .assetType(this.assetType)
                .scenario(this.scenario)
                .build();
    }

    // Entity → DTO 변환
    public static GoalNetzeroRequest fromEntity(GoalNetzero entity) {
        return GoalNetzeroRequest.builder()
                .id(entity.getId())
                .industrialSector(entity.getIndustrialSector())
                .baseYearEmission(entity.getBaseYearEmission())
                .targetYearEmission(entity.getTargetYearEmission())
                .reductionRate(entity.getReductionRate())
                .baseYear(entity.getBaseYear())
                .targetYear(entity.getTargetYear())
                .financialAssetValue(entity.getFinancialAssetValue())
                .attributionFactor(entity.getAttributionFactor())
                .assetType(entity.getAssetType())
                .scenario(entity.getScenario())
                .build();
    }
}
