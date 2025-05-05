package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceCommittee;
import lombok.Data;

@Data
public class TcfdGovernanceCommitteeRequest {
    private String committeeName;
    private String memberName;
    private String memberPosition;
    private String memberAffiliation;
    private String climateResponsibility;

    // Entity → DTO
    public static TcfdGovernanceCommitteeRequest fromEntity(TcfdGovernanceCommittee entity) {
        TcfdGovernanceCommitteeRequest dto = new TcfdGovernanceCommitteeRequest();
        dto.setCommitteeName(entity.getCommitteeName());
        dto.setMemberName(entity.getMemberName());
        dto.setMemberPosition(entity.getMemberPosition());
        dto.setMemberAffiliation(entity.getMemberAffiliation());
        dto.setClimateResponsibility(entity.getClimateResponsibility());
        return dto;
    }

    // DTO → Entity (리팩토링을 위한 추가 메서드)
    public TcfdGovernanceCommittee toEntity(Long memberId) {
        return TcfdGovernanceCommittee.builder()
                .memberId(memberId)
                .committeeName(this.committeeName)
                .memberName(this.memberName)
                .memberPosition(this.memberPosition)
                .memberAffiliation(this.memberAffiliation)
                .climateResponsibility(this.climateResponsibility)
                .build();
    }
}
