package com.nsmm.esg.tcfdservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tcfd_governance_executive_kpi")
public class TcfdGovernanceExecutiveKpi implements Identifiable<Long>{

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

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
