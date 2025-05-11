package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceMeeting;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class GovernanceMeetingResponse {

    private final Long id;
    private final String meetingName;
    private final LocalDate meetingDate;
    private final String agenda;
    private final Long memberId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static GovernanceMeetingResponse fromEntity(GovernanceMeeting entity) {
        return GovernanceMeetingResponse.builder()
                .id(entity.getId())
                .meetingName(entity.getMeetingName())
                .meetingDate(entity.getMeetingDate())
                .agenda(entity.getAgenda())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
