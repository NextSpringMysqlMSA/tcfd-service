package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalKpi;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoalKpiRequest {

    private String indicator;
    private String detailedIndicator;
    private String unit;
    private int baseYear;
    private int goalYear;
    private int referenceValue;
    private int currentValue;
    private int targetValue;

    // DTO → Entity
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

    // Entity → DTO
    public static GoalKpiRequest fromEntity(GoalKpi entity) {
        GoalKpiRequest dto = new GoalKpiRequest();
        dto.indicator = entity.getIndicator();
        dto.detailedIndicator = entity.getDetailedIndicator();
        dto.unit = entity.getUnit();
        dto.baseYear = entity.getBaseYear();
        dto.goalYear = entity.getGoalYear();
        dto.referenceValue = entity.getReferenceValue();
        dto.currentValue = entity.getCurrentValue();
        dto.targetValue = entity.getTargetValue();
        return dto;
    }
}