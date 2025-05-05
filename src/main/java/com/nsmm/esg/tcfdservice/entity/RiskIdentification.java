package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.RiskIdentificationRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "risk_identification")
public class RiskIdentification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;                // 작성자 ID (추천)

    private String riskType;
    private String riskCategory;
    private String riskCause;
    private String time;
    private String impact;
    private String financialImpact;
    private String businessModelImpact;
    private String plans;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean deleted = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        this.deleted = true;
    }

    public void updateFromDto(RiskIdentificationRequest request) {
        this.riskType = request.getRiskType();
        this.riskCategory = request.getRiskCategory();
        this.riskCause = request.getRiskCause();
        this.time = request.getTime();
        this.impact = request.getImpact();
        this.financialImpact = request.getFinancialImpact();
        this.businessModelImpact = request.getBusinessModelImpact();
        this.plans = request.getPlans();
    }

}
