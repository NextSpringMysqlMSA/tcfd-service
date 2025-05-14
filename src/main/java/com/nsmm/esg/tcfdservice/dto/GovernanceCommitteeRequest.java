package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceCommittee;
import lombok.Builder;
import lombok.Getter;

/**
 * 기후변화 관련 위원회 요청 DTO
 * 클라이언트로부터 기업의 기후변화 관련 위원회 구성 및 책임 정보를 전달받기 위한 객체
 */
@Getter
@Builder
public class GovernanceCommitteeRequest {

    private final String committeeName;         // 위원회 이름 (예: ESG위원회, 기후변화대응위원회 등)
    private final String memberName;            // 위원회 구성원 이름
    private final String memberPosition;        // 위원회 구성원 직위 (예: 위원장, 위원, 간사 등)
    private final String memberAffiliation;     // 위원회 구성원 소속 (예: 이사회, 경영진, 외부전문가 등)
    private final String climateResponsibility; // 기후변화 관련 책임 영역 (예: 전략 수립, 리스크 평가, 목표 관리 등)

    /**
     * 요청 DTO를 엔티티 객체로 변환하는 메서드
     *
     * @param memberId 회원 ID (인증된 사용자의 ID)
     * @return 거버넌스 위원회 엔티티 객체
     */
    public GovernanceCommittee toEntity(Long memberId) {
        return GovernanceCommittee.builder()
                .memberId(memberId)
                .committeeName(committeeName)
                .memberName(memberName)
                .memberPosition(memberPosition)
                .memberAffiliation(memberAffiliation)
                .climateResponsibility(climateResponsibility)
                .build();
    }
}