package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceExecutiveKpi;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GovernanceExecutiveKpiResponse {

    private final Long id;
    private final String executiveName;
    private final String kpiName;
    private final String targetValue;
    private final String achievedValue;
    private final Long memberId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static GovernanceExecutiveKpiResponse fromEntity(GovernanceExecutiveKpi entity) {
        return GovernanceExecutiveKpiResponse.builder()
                .id(entity.getId())
                .executiveName(entity.getExecutiveName())
                .kpiName(entity.getKpiName())
                .targetValue(entity.getTargetValue())
                .achievedValue(entity.getAchievedValue())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
