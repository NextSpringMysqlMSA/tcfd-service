package com.nsmm.esg.tcfdservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceMeeting;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TcfdGovernanceMeetingRequest {

    private final Long id;
    private final String meetingName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate meetingDate;

    private final String agenda;

    public TcfdGovernanceMeeting toEntity(Long memberId) {
        return TcfdGovernanceMeeting.builder()
                .memberId(memberId)
                .meetingName(meetingName)
                .meetingDate(meetingDate)
                .agenda(agenda)
                .build();
    }

    public static TcfdGovernanceMeetingRequest fromEntity(TcfdGovernanceMeeting entity) {
        return TcfdGovernanceMeetingRequest.builder()
                .id(entity.getId())
                .meetingName(entity.getMeetingName())
                .meetingDate(entity.getMeetingDate())
                .agenda(entity.getAgenda())
                .build();
    }
}
