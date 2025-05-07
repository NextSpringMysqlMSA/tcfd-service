package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.GoalKpiRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "goal_kpi")
public class GoalKpi implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 100)
    private String indicator;

    @Column(nullable = false, length = 100)
    private String detailedIndicator;

    @Column(nullable = false, length = 50)
    private String unit;

    @Column(nullable = false)
    private int baseYear;

    @Column(nullable = false)
    private int goalYear;

    @Column(nullable = false)
    private int referenceValue;

    @Column(nullable = false)
    private int currentValue;

    @Column(nullable = false)
    private int targetValue;

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

    @Override
    public Long getId() {
        return this.id;
    }

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