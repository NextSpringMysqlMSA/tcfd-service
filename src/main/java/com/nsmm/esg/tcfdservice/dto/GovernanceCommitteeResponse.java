package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceCommittee;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 기후변화 관련 위원회 응답 DTO
 * 기업의 기후변화 관련 위원회 구성 및 책임 정보를 클라이언트에 제공하기 위한 객체
 */
@Getter
@Builder
public class GovernanceCommitteeResponse {

    private final Long id;                      // 위원회 구성원 고유 식별자
    private final String committeeName;         // 위원회 이름 (예: ESG위원회, 기후변화대응위원회 등)
    private final String memberName;            // 위원회 구성원 이름
    private final String memberPosition;        // 위원회 구성원 직위 (예: 위원장, 위원, 간사 등)
    private final String memberAffiliation;     // 위원회 구성원 소속 (예: 이사회, 경영진, 외부전문가 등)
    private final String climateResponsibility; // 기후변화 관련 책임 영역 (예: 전략 수립, 리스크 평가, 목표 관리 등)
    private final Long memberId;                // 회원 ID
    private final LocalDateTime createdAt;      // 데이터 생성 일시
    private final LocalDateTime updatedAt;      // 데이터 수정 일시

    /**
     * 엔티티 객체로부터 응답 DTO를 생성하는 정적 팩토리 메서드
     *
     * @param entity 변환할 거버넌스 위원회 엔티티
     * @return 거버넌스 위원회 응답 DTO
     */
    public static GovernanceCommitteeResponse fromEntity(GovernanceCommittee entity) {
        return GovernanceCommitteeResponse.builder()
                .id(entity.getId())
                .committeeName(entity.getCommitteeName())
                .memberName(entity.getMemberName())
                .memberPosition(entity.getMemberPosition())
                .memberAffiliation(entity.getMemberAffiliation())
                .climateResponsibility(entity.getClimateResponsibility())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}