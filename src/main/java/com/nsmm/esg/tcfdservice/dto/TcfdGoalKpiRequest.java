package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.TcfdGoalKpi;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TcfdGoalKpiRequest {

    private String indicator;
    private String detailedIndicator;
    private String unit;
    private int baseYear;
    private int goalYear;
    private int referenceValue;
    private int currentValue;
    private int targetValue;

    // DTO → Entity
    public TcfdGoalKpi toEntity(Long memberId) {
        return TcfdGoalKpi.builder()
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
    public static TcfdGoalKpiRequest fromEntity(TcfdGoalKpi entity) {
        TcfdGoalKpiRequest dto = new TcfdGoalKpiRequest();
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