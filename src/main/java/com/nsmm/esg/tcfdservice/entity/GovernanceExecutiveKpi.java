package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.GovernanceExecutiveKpiRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "governance_executive_kpi")
public class GovernanceExecutiveKpi implements Identifiable<Long>{

    @Override
    public Long getId() {
        return this.id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 100)
    private String executiveName; // 경영진 이름

    @Column(nullable = false, length = 100)
    private String kpiName; // KPI 이름

    private String targetValue; // KPI 목표 값

    private String achievedValue; // KPI 달성 값

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    public void updateFromRequest(GovernanceExecutiveKpiRequest request) {
        this.executiveName = request.getExecutiveName();
        this.kpiName = request.getKpiName();
        this.targetValue = request.getTargetValue();
        this.achievedValue = request.getAchievedValue();
    }

}
