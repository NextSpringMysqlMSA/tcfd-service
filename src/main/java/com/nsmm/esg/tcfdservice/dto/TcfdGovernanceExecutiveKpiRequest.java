package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceExecutiveKpi;
import lombok.Getter;

@Getter
public class TcfdGovernanceExecutiveKpiRequest {

    private String executiveName;   // 경영진 이름
    private String kpiName;         // KPI 이름
    private String targetValue;     // 목표값
    private String achievedValue;   // 달성값

    // DTO → Entity 변환 메서드
    public TcfdGovernanceExecutiveKpi toEntity(Long memberId) {
        return TcfdGovernanceExecutiveKpi.builder()
                .memberId(memberId)
                .executiveName(this.executiveName)
                .kpiName(this.kpiName)
                .targetValue(this.targetValue)
                .achievedValue(this.achievedValue)
                .build();
    }
}
