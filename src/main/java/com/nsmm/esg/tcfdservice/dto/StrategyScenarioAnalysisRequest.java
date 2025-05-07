package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.StrategyScenarioAnalysis;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class  StrategyScenarioAnalysisRequest {

    private final Long id;
    private final String regions;
    private final Double longitude;
    private final Double latitude;
    private final String warming;
    private final String industry;
    private final String scenario;
    private final Integer baseYear;
    private final String climate;
    private final Double damage;
    private final String format;
    private final String responseStrategy;

    public StrategyScenarioAnalysis toEntity(Long memberId) {
        return StrategyScenarioAnalysis.builder()
                .memberId(memberId)
                .regions(regions)
                .longitude(longitude)
                .latitude(latitude)
                .warming(warming)
                .industry(industry)
                .scenario(scenario)
                .baseYear(baseYear)
                .climate(climate)
                .damage(damage)
                .format(format)
                .responseStrategy(responseStrategy)
                .build();
    }
    public static StrategyScenarioAnalysisRequest fromEntity(StrategyScenarioAnalysis entity) {
        return StrategyScenarioAnalysisRequest.builder()
                .id(entity.getId())
                .regions(entity.getRegions())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .warming(entity.getWarming())
                .industry(entity.getIndustry())
                .scenario(entity.getScenario())
                .baseYear(entity.getBaseYear())
                .climate(entity.getClimate())
                .damage(entity.getDamage())
                .format(entity.getFormat())
                .responseStrategy(entity.getResponseStrategy())
                .build();
    }


}
