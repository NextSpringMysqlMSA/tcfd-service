package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.GovernanceMeetingRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 기후변화 관련 거버넌스 회의 엔티티
 * TCFD 거버넌스 영역에서 기업의 이사회 및 경영진이 진행한
 * 기후변화 관련 회의에 대한 정보를 저장하는 엔티티
 * 기후변화 리스크 및 기회 논의 현황을 파악할 수 있는 회의 정보 포함
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "governance_meeting")
public class GovernanceMeeting implements Identifiable<Long>{

    /**
     * Identifiable 인터페이스 구현 메서드
     * 엔티티의 고유 식별자 반환
     *
     * @return 회의 고유 식별자
     */
    @Override
    public Long getId() {
        return this.id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                           // 회의 고유 식별자

    @Column(nullable = false)
    private Long memberId;                     // 회원 ID (작성자)

    @Column(nullable = false, length = 100)
    private String meetingName;                // 회의 이름 (예: ESG위원회 정기회의, 기후변화 대응 전략회의, 이사회 기후리스크 검토)

    @Column(nullable = false)
    private LocalDate meetingDate;             // 회의 개최 날짜 (yyyy-MM-dd 형식)

    @Lob
    private String agenda;                     // 회의 안건 및 내용 (예: 기후변화 리스크 평가 결과 검토, 온실가스 감축 목표 설정, 탄소중립 로드맵 승인)

    @CreationTimestamp
    private LocalDateTime createdAt;           // 데이터 생성 일시

    @UpdateTimestamp
    private LocalDateTime updatedAt;           // 데이터 수정 일시

    /**
     * 요청 DTO를 사용하여 엔티티 정보 업데이트
     *
     * @param request 회의 정보 업데이트 요청 객체
     */
    public void updateFromRequest(GovernanceMeetingRequest request) {
        this.meetingName = request.getMeetingName();
        this.meetingDate = request.getMeetingDate();
        this.agenda = request.getAgenda();
    }
}