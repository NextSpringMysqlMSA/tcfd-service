package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalKpi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoalKpiRequest {

    private final Long id;
    private final String indicator;
    private final String detailedIndicator;
    private final String unit;
    private final int baseYear;
    private final int goalYear;
    private final int referenceValue;
    private final int currentValue;
    private final int targetValue;

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
        return GoalKpiRequest.builder()
                .id(entity.getId())
                .indicator(entity.getIndicator())
                .detailedIndicator(entity.getDetailedIndicator())
                .unit(entity.getUnit())
                .baseYear(entity.getBaseYear())
                .goalYear(entity.getGoalYear())
                .referenceValue(entity.getReferenceValue())
                .currentValue(entity.getCurrentValue())
                .targetValue(entity.getTargetValue())
                .build();
    }
}
