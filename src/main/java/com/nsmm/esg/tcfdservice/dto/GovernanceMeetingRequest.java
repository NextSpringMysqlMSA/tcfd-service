package com.nsmm.esg.tcfdservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nsmm.esg.tcfdservice.entity.GovernanceMeeting;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 거버넌스 회의 요청 DTO
 * 클라이언트로부터 기후변화 관련 거버넌스 회의 정보를 전달받기 위한 객체
 */
@Getter
@Builder
public class GovernanceMeetingRequest {

    private final String meetingName;   // 회의명 (예: 기후변화 대응 이사회, ESG 위원회 등)

    /**
     * 회의 개최 일자
     * yyyy-MM-dd 형식으로 입력 받음 (예: 2024-05-15)
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate meetingDate;

    private final String agenda;        // 회의 안건 (기후변화 관련 의제)

    /**
     * 요청 DTO를 엔티티 객체로 변환하는 메서드
     *
     * @param memberId 회원 ID (인증된 사용자의 ID)
     * @return 거버넌스 회의 엔티티 객체
     */
    public GovernanceMeeting toEntity(Long memberId) {
        return GovernanceMeeting.builder()
                .memberId(memberId)
                .meetingName(meetingName)
                .meetingDate(meetingDate)
                .agenda(agenda)
                .build();
    }
}