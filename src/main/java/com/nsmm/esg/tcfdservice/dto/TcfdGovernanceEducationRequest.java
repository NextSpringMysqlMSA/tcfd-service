package com.nsmm.esg.tcfdservice.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TcfdGovernanceEducationRequest {

    private Long memberId;            // 사용자 ID
    private String educationTitle;    // 교육 제목
    private LocalDate educationDate;  // 교육 일자 (yyyy-MM-dd 형식으로 입력)
    private Integer participantCount; // 참석자 수
    private String content;           // 교육 주요 내용
}
