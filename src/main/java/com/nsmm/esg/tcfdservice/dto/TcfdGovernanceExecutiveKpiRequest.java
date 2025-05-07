package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceExecutiveKpi;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TcfdGovernanceExecutiveKpiRequest {

    private final String executiveName;
    private final String kpiName;
    private final String targetValue;
    private final String achievedValue;

    public TcfdGovernanceExecutiveKpi toEntity(Long memberId) {
        return TcfdGovernanceExecutiveKpi.builder()
                .memberId(memberId)
                .executiveName(executiveName)
                .kpiName(kpiName)
                .targetValue(targetValue)
                .achievedValue(achievedValue)
                .build();
    }

    public static TcfdGovernanceExecutiveKpiRequest fromEntity(TcfdGovernanceExecutiveKpi entity) {
        return TcfdGovernanceExecutiveKpiRequest.builder()
                .executiveName(entity.getExecutiveName())
                .kpiName(entity.getKpiName())
                .targetValue(entity.getTargetValue())
                .achievedValue(entity.getAchievedValue())
                .build();
    }
}