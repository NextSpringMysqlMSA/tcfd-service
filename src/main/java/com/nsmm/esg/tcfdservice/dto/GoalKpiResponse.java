package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalKpi;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GoalKpiResponse {
    private final Long id;
    private final String indicator;
    private final String detailedIndicator;
    private final String unit;
    private final int baseYear;
    private final int goalYear;
    private final int referenceValue;
    private final int currentValue;
    private final int targetValue;
    private final Long memberId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static GoalKpiResponse fromEntity(GoalKpi entity) {
        return GoalKpiResponse.builder()
                .id(entity.getId())
                .indicator(entity.getIndicator())
                .detailedIndicator(entity.getDetailedIndicator())
                .unit(entity.getUnit())
                .baseYear(entity.getBaseYear())
                .goalYear(entity.getGoalYear())
                .referenceValue(entity.getReferenceValue())
                .currentValue(entity.getCurrentValue())
                .targetValue(entity.getTargetValue())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
