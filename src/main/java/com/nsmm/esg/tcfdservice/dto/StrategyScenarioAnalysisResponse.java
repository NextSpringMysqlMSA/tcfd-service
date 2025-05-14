package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.StrategyScenarioAnalysis;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StrategyScenarioAnalysisResponse {

    private final Long id;
    private final String regions;
    private final Double longitude;
    private final Double latitude;
    private final String assetType;
    private final String industry;
    private final String scenario;
    private final Integer baseYear;
    private final String climate;
    private final Double assetValue;
    private final Long estimatedDamage;
    private final Long memberId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

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
