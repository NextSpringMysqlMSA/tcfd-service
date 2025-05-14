package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.GovernanceEducationRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 기후변화 관련 거버넌스 교육 엔티티
 * TCFD 거버넌스 영역에서 기업의 이사회, 경영진 및 임직원을 대상으로 진행된
 * 기후변화 관련 교육 및 역량 강화 활동에 대한 정보를 저장하는 엔티티
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "governance_education")
public class GovernanceEducation implements Identifiable<Long>{

    /**
     * Identifiable 인터페이스 구현 메서드
     * 엔티티의 고유 식별자 반환
     *
     * @return 교육 고유 식별자
     */
    @Override
    public Long getId() {
        return this.id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                           // 교육 고유 식별자

    @Column(nullable = false)
    private Long memberId;                     // 회원 ID (작성자)

    @Column(nullable = false, length = 100)
    private String educationTitle;             // 교육 제목 (예: 기후변화 리스크와 기회 이해, TCFD 보고 프레임워크 교육)

    @Column(nullable = false)
    private LocalDate educationDate;           // 교육 실시 일자 (yyyy-MM-dd 형식)

    @Column(nullable = false)
    private Integer participantCount;          // 교육 참석자 수 (인원)

    @Lob
    private String content;                    // 교육 내용 상세 설명 (교육 목적, 내용, 강사 정보, 교육 효과 등)

    @CreationTimestamp
    private LocalDateTime createdAt;           // 데이터 생성 일시

    @UpdateTimestamp
    private LocalDateTime updatedAt;           // 데이터 수정 일시

    /**
     * 요청 DTO를 사용하여 엔티티 정보 업데이트
     *
     * @param request 교육 정보 업데이트 요청 객체
     */
    public void updateFromRequest(GovernanceEducationRequest request) {
        this.educationTitle = request.getEducationTitle();
        this.educationDate = request.getEducationDate();
        this.participantCount = request.getParticipantCount();
        this.content = request.getContent();
    }
}