package com.nsmm.esg.tcfdservice.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TcfdGovernanceMeetingRequest {

    private Long memberId;          // 사용자 ID
    private String meetingName;     // 회의 제목
    private LocalDate meetingDate;  // 회의 날짜 (yyyy-MM-dd 형식으로 요청)
    private String agenda;          // 주요 안건 및 의결 내용
}
