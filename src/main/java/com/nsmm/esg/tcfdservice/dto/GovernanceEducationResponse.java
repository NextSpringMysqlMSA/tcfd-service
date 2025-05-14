package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceEducation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 기후변화 교육 응답 DTO
 * 기업의 기후변화 관련 교육 정보를 클라이언트에 제공하기 위한 객체
 */
@Getter
@Builder
public class GovernanceEducationResponse {

    private final Long id;                 // 교육 고유 식별자
    private final String educationTitle;   // 교육 제목 (예: 탄소중립 이해, 기후변화 리스크와 기회 등)
    private final LocalDate educationDate; // 교육 실시 일자
    private final Integer participantCount; // 교육 참가자 수
    private final String content;          // 교육 내용 (교육 프로그램 세부 내용, 강사 정보 등)
    private final Long memberId;           // 회원 ID
    private final LocalDateTime createdAt;  // 데이터 생성 일시
    private final LocalDateTime updatedAt;  // 데이터 수정 일시

    /**
     * 엔티티 객체로부터 응답 DTO를 생성하는 정적 팩토리 메서드
     *
     * @param entity 변환할 거버넌스 교육 엔티티
     * @return 거버넌스 교육 응답 DTO
     */
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