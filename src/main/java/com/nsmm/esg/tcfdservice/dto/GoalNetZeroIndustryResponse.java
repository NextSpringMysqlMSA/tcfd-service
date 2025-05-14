package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalNetZeroIndustry;
import lombok.Builder;
import lombok.Getter;

/**
 * 산업별 투자/대출 자산 항목 응답 DTO
 * 특정 산업에 대한 투자 및 배출량 관련 정보를 클라이언트에 제공하기 위한 객체
 */
@Getter
@Builder
public class GoalNetZeroIndustryResponse {

    private final Long id;                  // 고유 식별자
    private final String industry;          // 산업명 (예: 철강 제조)
    private final String assetType;         // 자산 유형 (예: 기업대출, PF 등)
    private final double amount;            // 투자/대출 금액 (Ai)
    private final double totalAssetValue;   // 자산 전체 가치 (AV)
    private final double emissionFactor;    // 배출계수 (EF)
    private final double attributionFactor; // 귀속계수 (AF = Ai / AV)
    private final double baseEmission;      // 기준년도 배출량 (Eb = Ai × EF × AF)

    /**
     * 엔티티 객체로부터 응답 DTO를 생성하는 정적 팩토리 메서드
     *
     * @param entity 변환할 산업별 투자/대출 자산 항목 엔티티
     * @return 산업별 투자/대출 자산 항목 응답 DTO
     */
    public static GoalNetZeroIndustryResponse fromEntity(GoalNetZeroIndustry entity) {
        return GoalNetZeroIndustryResponse.builder()
                .id(entity.getId())
                .industry(entity.getIndustry())
                .assetType(entity.getAssetType())
                .amount(entity.getAmount())
                .totalAssetValue(entity.getTotalAssetValue())
                .emissionFactor(entity.getEmissionFactor())
                .attributionFactor(entity.getAttributionFactor())
                .baseEmission(entity.getBaseEmission())
                .build();
    }
}