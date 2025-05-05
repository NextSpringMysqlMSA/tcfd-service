package com.nsmm.esg.tcfdservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsmm.esg.tcfdservice.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TcfdGovernanceCommitteeRequest {

    private final String committeeName;
    private final String memberName;
    private final String memberPosition;
    private final String memberAffiliation;
    private final String climateResponsibility;

    public TcfdGovernanceCommittee toEntity(Long memberId) {
        return TcfdGovernanceCommittee.builder()
                .memberId(memberId)
                .committeeName(committeeName)
                .memberName(memberName)
                .memberPosition(memberPosition)
                .memberAffiliation(memberAffiliation)
                .climateResponsibility(climateResponsibility)
                .build();
    }

    public static TcfdGovernanceCommitteeRequest fromEntity(TcfdGovernanceCommittee entity) {
        return TcfdGovernanceCommitteeRequest.builder()
                .committeeName(entity.getCommitteeName())
                .memberName(entity.getMemberName())
                .memberPosition(entity.getMemberPosition())
                .memberAffiliation(entity.getMemberAffiliation())
                .climateResponsibility(entity.getClimateResponsibility())
                .build();
    }
}