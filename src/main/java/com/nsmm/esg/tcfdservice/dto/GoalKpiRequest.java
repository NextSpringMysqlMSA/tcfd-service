package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalKpi;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoalKpiRequest {
    private final String indicator;
    private final String detailedIndicator;
    private final String unit;
    private final int baseYear;
    private final int goalYear;
    private final int referenceValue;
    private final int currentValue;
    private final int targetValue;

    public GoalKpi toEntity(Long memberId) {
        return GoalKpi.builder()
                .memberId(memberId)
                .indicator(indicator)
                .detailedIndicator(detailedIndicator)
                .unit(unit)
                .baseYear(baseYear)
                .goalYear(goalYear)
                .referenceValue(referenceValue)
                .currentValue(currentValue)
                .targetValue(targetValue)
                .build();
    }
}
