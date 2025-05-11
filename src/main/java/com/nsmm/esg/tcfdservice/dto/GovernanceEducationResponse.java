package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceEducation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class GovernanceEducationResponse {

    private final Long id;
    private final String educationTitle;
    private final LocalDate educationDate;
    private final Integer participantCount;
    private final String content;
    private final Long memberId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static GovernanceEducationResponse fromEntity(GovernanceEducation entity) {
        return GovernanceEducationResponse.builder()
                .id(entity.getId())
                .educationTitle(entity.getEducationTitle())
                .educationDate(entity.getEducationDate())
                .participantCount(entity.getParticipantCount())
                .content(entity.getContent())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
