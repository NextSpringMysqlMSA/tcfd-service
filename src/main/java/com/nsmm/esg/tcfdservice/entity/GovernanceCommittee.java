package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.GovernanceCommitteeRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 기후변화 관련 거버넌스 위원회 엔티티
 * TCFD 거버넌스 영역에서 기업의 기후변화 관련 이사회 및 경영진 감독 구조에 대한 정보를 저장하는 엔티티
 * 기후변화 감독 및 관리를 담당하는 위원회와 구성원에 대한 상세 정보 포함
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "governance_committee")
public class GovernanceCommittee implements Identifiable<Long> {

    /**
     * Identifiable 인터페이스 구현 메서드
     * 엔티티의 고유 식별자 반환
     *
     * @return 위원회 고유 식별자
     */
    @Override
    public Long getId() {
        return this.id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                           // 위원회 구성원 고유 식별자

    @Column(nullable = false)
    private Long memberId;                     // 회원 ID (작성자)

    @Column(nullable = false, length = 100)
    private String committeeName;              // 위원회 이름 (예: ESG위원회, 지속가능경영위원회, 기후변화대응위원회)

    @Column(nullable = false, length = 100)
    private String memberName;                 // 구성원 이름 (예: 홍길동, John Doe)

    @Column(nullable = false, length = 100)
    private String memberPosition;             // 구성원 직책 (예: 위원장, 위원, 이사, CEO, CSO, CFO)

    @Column(nullable = false, length = 100)
    private String memberAffiliation;          // 구성원 소속 (예: 이사회, 경영진, 지속가능경영팀, 외부 전문가)

    @Lob
    private String climateResponsibility;      // 기후 관련 역할 및 책임 설명 (예: 기후변화 리스크 평가 및 감독, 탄소중립 목표 설정 및 이행 검토)

    @CreationTimestamp
    private LocalDateTime createdAt;           // 데이터 생성 일시

    @UpdateTimestamp
    private LocalDateTime updatedAt;           // 데이터 수정 일시

    /**
     * 요청 DTO를 사용하여 엔티티 정보 업데이트
     *
     * @param request 위원회 구성원 정보 업데이트 요청 객체
     */
    public void updateFromRequest(GovernanceCommitteeRequest request) {
        this.committeeName = request.getCommitteeName();
        this.memberName = request.getMemberName();
        this.memberPosition = request.getMemberPosition();
        this.memberAffiliation = request.getMemberAffiliation();
        this.climateResponsibility = request.getClimateResponsibility();
    }
}