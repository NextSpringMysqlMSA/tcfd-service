package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceMeeting;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 거버넌스 회의 응답 DTO
 * 기후변화 관련 거버넌스 회의 정보를 클라이언트에 제공하기 위한 객체
 */
@Getter
@Builder
public class GovernanceMeetingResponse {

    private final Long id;              // 회의 고유 식별자
    private final String meetingName;   // 회의명 (예: 기후변화 대응 이사회, ESG 위원회 등)
    private final LocalDate meetingDate; // 회의 개최 일자
    private final String agenda;        // 회의 안건 (기후변화 관련 의제)
    private final Long memberId;        // 회원 ID
    private final LocalDateTime createdAt; // 데이터 생성 일시
    private final LocalDateTime updatedAt; // 데이터 수정 일시

    /**
     * 엔티티 객체로부터 응답 DTO를 생성하는 정적 팩토리 메서드
     *
     * @param entity 변환할 거버넌스 회의 엔티티
     * @return 거버넌스 회의 응답 DTO
     */
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