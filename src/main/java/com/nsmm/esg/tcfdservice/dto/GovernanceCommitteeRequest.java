package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GovernanceCommitteeRequest {

    private final String committeeName;
    private final String memberName;
    private final String memberPosition;
    private final String memberAffiliation;
    private final String climateResponsibility;

    public GovernanceCommittee toEntity(Long memberId) {
        return GovernanceCommittee.builder()
                .memberId(memberId)
                .committeeName(committeeName)
                .memberName(memberName)
                .memberPosition(memberPosition)
                .memberAffiliation(memberAffiliation)
                .climateResponsibility(climateResponsibility)
                .build();
    }

    public static GovernanceCommitteeRequest fromEntity(GovernanceCommittee entity) {
        return GovernanceCommitteeRequest.builder()
                .committeeName(entity.getCommitteeName())
                .memberName(entity.getMemberName())
                .memberPosition(entity.getMemberPosition())
                .memberAffiliation(entity.getMemberAffiliation())
                .climateResponsibility(entity.getClimateResponsibility())
                .build();
    }
}