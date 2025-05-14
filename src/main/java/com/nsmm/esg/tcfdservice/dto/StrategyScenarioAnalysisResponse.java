package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.StrategyScenarioAnalysis;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 전략 시나리오 분석 응답 DTO
 * 기후변화 시나리오에 따른 자산 영향 분석 정보를 클라이언트에 제공하기 위한 객체
 */
@Getter
@Builder
public class StrategyScenarioAnalysisResponse {

    private final Long id;              // 고유 식별자
    private final String regions;       // 지역명 (예: 서울, 부산 등)
    private final Double longitude;     // 경도 좌표
    private final Double latitude;      // 위도 좌표
    private final String assetType;     // 자산 유형 (예: 부동산, 설비 등)
    private final String industry;      // 산업 분류
    private final String scenario;      // 기후 시나리오 (예: RCP 2.6, RCP 8.5 등)
    private final Integer baseYear;     // 기준 연도
    private final String climate;       // 기후 영향 유형 (예: 홍수, 가뭄, 해수면 상승 등)
    private final Double assetValue;    // 자산 가치 (단위: 백만원)
    private final Long estimatedDamage; // 예상 손실액 (단위: 백만원)
    private final Long memberId;        // 회원 ID
    private final LocalDateTime createdAt; // 생성 일시
    private final LocalDateTime updatedAt; // 수정 일시

    /**
     * 엔티티 객체로부터 응답 DTO를 생성하는 정적 팩토리 메서드
     *
     * @param entity 변환할 전략 시나리오 분석 엔티티
     * @return 전략 시나리오 분석 응답 DTO
     */
    public static StrategyScenarioAnalysisResponse fromEntity(StrategyScenarioAnalysis entity) {
        return StrategyScenarioAnalysisResponse.builder()
                .id(entity.getId())
                .regions(entity.getRegions())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .assetType(entity.getAssetType())
                .industry(entity.getIndustry())
                .scenario(entity.getScenario())
                .baseYear(entity.getBaseYear())
                .climate(entity.getClimate())
                .assetValue(entity.getAssetValue())
                .estimatedDamage(entity.getEstimatedDamage())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}