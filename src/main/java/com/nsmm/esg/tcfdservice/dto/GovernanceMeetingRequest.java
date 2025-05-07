package com.nsmm.esg.tcfdservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsmm.esg.tcfdservice.entity.GovernanceMeeting;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GovernanceMeetingRequest {

    private final Long id;
    private final String meetingName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate meetingDate;

    private final String agenda;

    public GovernanceMeeting toEntity(Long memberId) {
        return com.nsmm.esg.tcfdservice.entity.GovernanceMeeting.builder()
                .memberId(memberId)
                .meetingName(meetingName)
                .meetingDate(meetingDate)
                .agenda(agenda)
                .build();
    }

    public static GovernanceMeetingRequest fromEntity(GovernanceMeeting entity) {
        return GovernanceMeetingRequest.builder()
                .id(entity.getId())
                .meetingName(entity.getMeetingName())
                .meetingDate(entity.getMeetingDate())
                .agenda(entity.getAgenda())
                .build();
    }
}
