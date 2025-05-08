package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.StrategyScenarioAnalysis;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StrategyScenarioAnalysisRequest {

    private final Long id;

    private final String regions;
    private final Double longitude;
    private final Double latitude;
    private final String assetType;       // 변경: warming → assetType
    private final String industry;
    private final String scenario;
    private final Integer baseYear;
    private final String climate;
    private final Double assetValue;
    private final Double estimatedDamage;


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
                .build(); // 피해액은 백엔드 계산 후 setDamage()로 따로 처리
    }

    public static StrategyScenarioAnalysisRequest fromEntity(StrategyScenarioAnalysis entity) {
        return StrategyScenarioAnalysisRequest.builder()
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
                .build(); // estimatedDamage는 응답용 DTO로 따로 정의할 수 있음
    }
}
