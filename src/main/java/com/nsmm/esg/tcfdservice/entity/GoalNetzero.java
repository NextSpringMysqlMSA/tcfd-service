package com.nsmm.esg.tcfdservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "goal_net_zero")
public class GoalNetzero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private String industrialSector;
    private double financialAssetValue;
    private double totalAssetValue;
    private double attributionFactor;
    private String assetType;
    private double emissionFactor;
    private double baseYearEmission;
    private double targetYearEmission;
    private double reductionRate;
    private int baseYear;
    private int targetYear;
    private String scenario;

    @ElementCollection
    @CollectionTable(name = "goal_netzero_yearly_emissions", joinColumns = @JoinColumn(name = "goal_id"))
    @MapKeyColumn(name = "year")
    @Column(name = "emission")
    private Map<Integer, Double> yearlyEmissions = new HashMap<>();

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

    // ✅ DTO 기반 업데이트 메서드 (자동 수정)
    public void updateFromDto(double financialAssetValue, double totalAssetValue, double emissionFactor,
                              double baseYearEmission, double targetYearEmission, Map<Integer, Double> yearlyEmissions) {
        this.financialAssetValue = financialAssetValue;
        this.totalAssetValue = totalAssetValue;
        this.emissionFactor = emissionFactor;
        this.baseYearEmission = baseYearEmission;
        this.targetYearEmission = targetYearEmission;
        this.yearlyEmissions = yearlyEmissions;
        this.reductionRate = calculateReductionRate(baseYearEmission, targetYearEmission);
    }

    // ✅ 평균 감축률 자동 계산 메서드
    private double calculateReductionRate(double baseYearEmission, double targetYearEmission) {
        if (baseYearEmission == 0) return 0;
        return (baseYearEmission - targetYearEmission) / baseYearEmission * 100.0;
    }
}