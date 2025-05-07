package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceEducation;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class GovernanceEducationRequest {

    private final String educationTitle;
    private final LocalDate educationDate;
    private final Integer participantCount;
    private final String content;

    public GovernanceEducation toEntity(Long memberId) {
        return GovernanceEducation.builder()
                .memberId(memberId)
                .educationTitle(educationTitle)
                .educationDate(educationDate)
                .participantCount(participantCount)
                .content(content)
                .build();
    }

    public static GovernanceEducationRequest fromEntity(GovernanceEducation entity) {
        return GovernanceEducationRequest.builder()
                .educationTitle(entity.getEducationTitle())
                .educationDate(entity.getEducationDate())
                .participantCount(entity.getParticipantCount())
                .content(entity.getContent())
                .build();
    }
}