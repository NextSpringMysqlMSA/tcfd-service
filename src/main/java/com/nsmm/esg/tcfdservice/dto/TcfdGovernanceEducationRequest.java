package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceEducation;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class TcfdGovernanceEducationRequest {

    private final Long id;
    private final String educationTitle;
    private final LocalDate educationDate;
    private final Integer participantCount;
    private final String content;

    public TcfdGovernanceEducation toEntity(Long memberId) {
        return TcfdGovernanceEducation.builder()
                .memberId(memberId)
                .educationTitle(educationTitle)
                .educationDate(educationDate)
                .participantCount(participantCount)
                .content(content)
                .build();
    }

    public static TcfdGovernanceEducationRequest fromEntity(TcfdGovernanceEducation entity) {
        return TcfdGovernanceEducationRequest.builder()
                .id(entity.getId())
                .educationTitle(entity.getEducationTitle())
                .educationDate(entity.getEducationDate())
                .participantCount(entity.getParticipantCount())
                .content(entity.getContent())
                .build();
    }
}