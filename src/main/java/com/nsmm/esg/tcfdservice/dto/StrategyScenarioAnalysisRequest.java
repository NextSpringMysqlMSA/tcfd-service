package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.StrategyScenarioAnalysis;
import lombok.Builder;
import lombok.Getter;

/**
 * 전략 시나리오 분석 요청 DTO
 * 클라이언트로부터 기후변화 시나리오 분석을 위한 데이터를 전달받기 위한 객체
 */
@Getter
@Builder
public class StrategyScenarioAnalysisRequest {

    private final String regions;      // 지역명 (예: 서울, 부산 등)
    private final Double longitude;    // 경도 좌표
    private final Double latitude;     // 위도 좌표
    private final String assetType;    // 자산 유형 (예: 부동산, 설비 등)
    private final String industry;     // 산업 분류
    private final String scenario;     // 기후 시나리오 (예: RCP 2.6, RCP 8.5 등)
    private final Integer baseYear;    // 기준 연도
    private final String climate;      // 기후 영향 유형 (예: 홍수, 가뭄, 해수면 상승 등)
    private final Double assetValue;   // 자산 가치 (단위: 백만원)

    /**
     * 요청 DTO를 엔티티 객체로 변환하는 메서드
     *
     * @param memberId 회원 ID (인증된 사용자의 ID)
     * @return 전략 시나리오 분석 엔티티 객체
     */
    public StrategyScenarioAnalysis toEntity(Long memberId) {
        return StrategyScenarioAnalysis.builder()
                .memberId(memberId)
                .regions(regions)
                .longitude(longitude)
                .latitude(latitude)
                .assetType(assetType)
                .industry(industry)
                .scenario(scenario)
                .baseYear(baseYear)
                .climate(climate)
                .assetValue(assetValue)
                .build();
    }
}