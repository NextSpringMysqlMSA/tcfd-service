package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.GoalKpiRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 기후변화 관련 핵심성과지표(KPI) 엔티티
 * TCFD 지표 및 목표 영역에서 기업이 설정한 기후변화 대응 목표와 성과를 측정하기 위한 KPI 정보를 저장하는 엔티티
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "goal_kpi")
public class GoalKpi implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                           // KPI 고유 식별자

    @Column(nullable = false)
    private Long memberId;                     // 회원 ID (작성자)

    @Column(nullable = false, length = 100)
    private String indicator;                  // KPI 지표명 (예: 온실가스 배출량, 에너지 사용량, 재생에너지 비율 등)

    @Column(nullable = false, length = 100)
    private String detailedIndicator;          // 세부 지표명 (예: Scope 1 배출량, Scope 2 배출량, 전력 사용량 등)

    @Column(nullable = false, length = 50)
    private String unit;                       // 측정 단위 (예: tCO2e, MWh, %, 백만원 등)

    @Column(nullable = false)
    private int baseYear;                      // 기준 연도 (성과 측정의 시작점)

    @Column(nullable = false)
    private int goalYear;                      // 목표 연도 (목표 달성 기한)

    @Column(nullable = false)
    private int referenceValue;                // 기준 연도 값 (기준 연도의 실적)

    @Column(nullable = false)
    private int currentValue;                  // 현재 값 (최근 측정된 실적)

    @Column(nullable = false)
    private int targetValue;                   // 목표 값 (목표 연도에 달성하고자 하는 값)

    @CreationTimestamp
    private LocalDateTime createdAt;           // 데이터 생성 일시

    @UpdateTimestamp
    private LocalDateTime updatedAt;           // 데이터 수정 일시

    /**
     * Identifiable 인터페이스 구현 메서드
     * 엔티티의 고유 식별자 반환
     *
     * @return KPI 고유 식별자
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * 요청 DTO를 사용하여 엔티티 정보 업데이트
     *
     * @param request KPI 정보 업데이트 요청 객체
     */
    public void updateFromDto(GoalKpiRequest request) {
        this.indicator = request.getIndicator();
        this.detailedIndicator = request.getDetailedIndicator();
        this.unit = request.getUnit();
        this.baseYear = request.getBaseYear();
        this.goalYear = request.getGoalYear();
        this.referenceValue = request.getReferenceValue();
        this.currentValue = request.getCurrentValue();
        this.targetValue = request.getTargetValue();
    }
}