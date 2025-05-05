package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.ScenarioAnalysis;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScenarioAnalysisRequest {

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
}
