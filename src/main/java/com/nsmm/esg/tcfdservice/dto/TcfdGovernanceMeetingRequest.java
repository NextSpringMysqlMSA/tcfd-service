package com.nsmm.esg.tcfdservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceMeeting;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TcfdGovernanceMeetingRequest {

    private String meetingName;     // 회의 제목

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;  // 회의 날짜

    private String agenda;          // 주요 안건 및 의결 내용

    // DTO → Entity 변환 메서드
    public TcfdGovernanceMeeting toEntity(Long memberId) {
        return TcfdGovernanceMeeting.builder()
                .memberId(memberId)
                .meetingName(this.meetingName)
                .meetingDate(this.meetingDate)
                .agenda(this.agenda)
                .build();
    }
}
