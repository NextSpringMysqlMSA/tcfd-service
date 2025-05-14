package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.GovernanceExecutiveKpiRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 기후변화 관련 경영진 성과지표(KPI) 엔티티
 * TCFD 거버넌스 영역에서 기업 경영진의 기후변화 관련 성과 평가 및 보상 체계에 대한 정보를 저장하는 엔티티
 * 경영진의 책임성 강화를 위한 기후변화 관련 KPI 설정 및 달성 현황 정보 포함
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "governance_executive_kpi")
public class GovernanceExecutiveKpi implements Identifiable<Long>{

    /**
     * Identifiable 인터페이스 구현 메서드
     * 엔티티의 고유 식별자 반환
     *
     * @return 경영진 KPI 고유 식별자
     */
    @Override
    public Long getId() {
        return this.id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                           // 경영진 KPI 고유 식별자

    @Column(nullable = false)
    private Long memberId;                     // 회원 ID (작성자)

    @Column(nullable = false, length = 100)
    private String executiveName;              // 경영진 이름 (예: 홍길동, CEO, CFO, CSO)

    @Column(nullable = false, length = 100)
    private String kpiName;                    // KPI 이름 (예: 온실가스 감축률, 재생에너지 도입률, CDP 평가 등급)

    private String targetValue;                // KPI 목표 값 (예: 10% 감축, A 등급 달성, 30% 전환)

    private String achievedValue;              // KPI 달성 값 (실제 달성한 성과)

    @CreationTimestamp
    private LocalDateTime createdAt;           // 데이터 생성 일시

    @UpdateTimestamp
    private LocalDateTime updatedAt;           // 데이터 수정 일시

    /**
     * 요청 DTO를 사용하여 엔티티 정보 업데이트
     *
     * @param request 경영진 KPI 정보 업데이트 요청 객체
     */
    public void updateFromRequest(GovernanceExecutiveKpiRequest request) {
        this.executiveName = request.getExecutiveName();
        this.kpiName = request.getKpiName();
        this.targetValue = request.getTargetValue();
        this.achievedValue = request.getAchievedValue();
    }
}