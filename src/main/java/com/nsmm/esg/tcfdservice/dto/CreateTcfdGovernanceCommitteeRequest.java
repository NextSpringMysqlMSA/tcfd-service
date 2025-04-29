package com.nsmm.esg.tcfdservice.dto;

import lombok.Getter;

@Getter
public class CreateTcfdGovernanceCommitteeRequest {

    private Long memberId;              // 사용자 ID
    private String committeeName;       // 위원회 이름
    private String memberName;           // 구성원 이름
    private String memberPosition;       // 구성원 직책
    private String memberAffiliation;    // 구성원 소속
    private String climateResponsibility; // 기후 관련 역할 및 책임
}
