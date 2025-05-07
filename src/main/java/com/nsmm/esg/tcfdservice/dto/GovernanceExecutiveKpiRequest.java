package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceExecutiveKpi;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GovernanceExecutiveKpiRequest {
    private final Long id;
    private final String executiveName;
    private final String kpiName;
    private final String targetValue;
    private final String achievedValue;

    public GovernanceExecutiveKpi toEntity(Long memberId) {
        return GovernanceExecutiveKpi.builder()
                .memberId(memberId)
                .executiveName(executiveName)
                .kpiName(kpiName)
                .targetValue(targetValue)
                .achievedValue(achievedValue)
                .build();
    }

    public static GovernanceExecutiveKpiRequest fromEntity(GovernanceExecutiveKpi entity) {
        return GovernanceExecutiveKpiRequest.builder()
                .id(entity.getId())
                .executiveName(entity.getExecutiveName())
                .kpiName(entity.getKpiName())
                .targetValue(entity.getTargetValue())
                .achievedValue(entity.getAchievedValue())
                .build();
    }
}