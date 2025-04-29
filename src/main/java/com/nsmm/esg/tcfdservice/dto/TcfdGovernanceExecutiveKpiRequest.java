package com.nsmm.esg.tcfdservice.dto;

import lombok.Getter;

@Getter
public class TcfdGovernanceExecutiveKpiRequest {
    private Long memberId;          // 사용자 ID
    private String executiveName;   // 경영진 이름
    private String kpiName;          // KPI 이름
    private String targetValue;      // 목표값
    private String achievedValue;    // 달성값
}
