package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceCommittee;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GovernanceCommitteeResponse {

    private final Long id;
    private final String committeeName;
    private final String memberName;
    private final String memberPosition;
    private final String memberAffiliation;
    private final String climateResponsibility;
    private final Long memberId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static GovernanceCommitteeResponse fromEntity(GovernanceCommittee entity) {
        return GovernanceCommitteeResponse.builder()
                .id(entity.getId())
                .committeeName(entity.getCommitteeName())
                .memberName(entity.getMemberName())
                .memberPosition(entity.getMemberPosition())
                .memberAffiliation(entity.getMemberAffiliation())
                .climateResponsibility(entity.getClimateResponsibility())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
