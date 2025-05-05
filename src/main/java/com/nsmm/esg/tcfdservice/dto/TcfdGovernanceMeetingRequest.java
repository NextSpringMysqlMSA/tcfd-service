package com.nsmm.esg.tcfdservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceMeeting;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
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

    // Entity → DTO 변환 메서드
    public static TcfdGovernanceMeetingRequest fromEntity(TcfdGovernanceMeeting entity) {
        TcfdGovernanceMeetingRequest dto = new TcfdGovernanceMeetingRequest();
        dto.meetingName = entity.getMeetingName();
        dto.meetingDate = entity.getMeetingDate();
        dto.agenda = entity.getAgenda();
        return dto;
    }
}
