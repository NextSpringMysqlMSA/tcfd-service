package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.StrategyScenarioAnalysis;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StrategyScenarioAnalysisRequest {

    private final String regions;
    private final Double longitude;
    private final Double latitude;
    private final String assetType;
    private final String industry;
    private final String scenario;
    private final Integer baseYear;
    private final String climate;
    private final Double assetValue;

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
