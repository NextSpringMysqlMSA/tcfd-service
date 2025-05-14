package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceEducation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 기후변화 교육 요청 DTO
 * 클라이언트로부터 기업의 기후변화 관련 교육 정보를 전달받기 위한 객체
 */
@Getter
@Builder
public class GovernanceEducationRequest {

    private final String educationTitle;    // 교육 제목 (예: 탄소중립 이해, 기후변화 리스크와 기회 등)
    private final LocalDate educationDate;  // 교육 실시 일자
    private final Integer participantCount; // 교육 참가자 수
    private final String content;           // 교육 내용 (교육 프로그램 세부 내용, 강사 정보 등)

    /**
     * 요청 DTO를 엔티티 객체로 변환하는 메서드
     *
     * @param memberId 회원 ID (인증된 사용자의 ID)
     * @return 거버넌스 교육 엔티티 객체
     */
    public GovernanceEducation toEntity(Long memberId) {
        return GovernanceEducation.builder()
                .memberId(memberId)
                .educationTitle(educationTitle)
                .educationDate(educationDate)
                .participantCount(participantCount)
                .content(content)
                .build();
    }
}