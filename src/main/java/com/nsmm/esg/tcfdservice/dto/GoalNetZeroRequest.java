package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalNetZero;
import com.nsmm.esg.tcfdservice.entity.GoalNetZeroIndustry;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 넷제로 포트폴리오 생성 및 수정 요청 DTO
 */
@Getter
@Builder
public class GoalNetZeroRequest {

    // 산업군 (예: 금융업, 제조업 등)
    private final String industrialSector;

    // 기준연도 (예: 2020)
    private final int baseYear;

    // 목표연도 (예: 2050)
    private final int targetYear;

    // 분석 시나리오 (예: SSP1-2.6)
    private final String scenario;

    // 산업별 자산 항목 리스트
    private final List<IndustryAsset> assets;

    /**
     * GoalNetZero 엔티티로 변환
     *
     * @param memberId 작성자 ID
     * @return 변환된 GoalNetZero 엔티티
     */
    public GoalNetZero toEntity(Long memberId) {
        GoalNetZero goal = GoalNetZero.builder()
                .memberId(memberId)
                .industrialSector(industrialSector)
                .baseYear(baseYear)
                .targetYear(targetYear)
                .scenario(scenario)
                .build();

        // 각 자산 항목을 GoalNetZeroIndustry로 변환 후 추가 (연관관계 포함)
        for (IndustryAsset asset : assets) {
            GoalNetZeroIndustry industry = GoalNetZeroIndustry.builder()
                    .industry(asset.getIndustry())
                    .assetType(asset.getAssetType())
                    .amount(asset.getAmount())
                    .totalAssetValue(asset.getTotalAssetValue())
                    .emissionFactor(asset.getEmissionFactor())
                    .build();

            goal.addIndustry(industry); // 양방향 연관관계 설정
        }

        return goal;
    }

    /**
     * 산업별 자산 항목 DTO
     */
    @Getter
    @Builder
    public static class IndustryAsset {
        private final String industry;        // 산업명 (예: 철강, 건설)
        private final String assetType;       // 자산 유형 (예: PF, 기업대출)
        private final double amount;          // 해당 자산 금액 (Ai)
        private final double totalAssetValue; // 산업 내 총 자산 금액 (AV)
        private final double emissionFactor;  // 배출계수 (EF)
    }
}
