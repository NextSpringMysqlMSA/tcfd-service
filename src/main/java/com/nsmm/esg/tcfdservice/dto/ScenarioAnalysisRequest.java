package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.ScenarioAnalysis;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScenarioAnalysisRequest {

    private String regions;
    private Double longitude;
    private Double latitude;
    private String warming;
    private String industry;
    private String scenario;
    private Integer baseYear;
    private String climate;
    private Double damage;
    private String format;
    private String responseStrategy;

    public ScenarioAnalysis toEntity(Long memberId) {
        return ScenarioAnalysis.builder()
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
    public static ScenarioAnalysisRequest fromEntity(ScenarioAnalysis entity) {
        return ScenarioAnalysisRequest.builder()
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
