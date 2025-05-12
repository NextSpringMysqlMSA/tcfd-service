package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.GoalNetzeroRequest;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter // Setter 추가하여 updateFromDto 사용 시 Lombok의 자동 Setter 사용 가능
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "goal_net_zero")
public class GoalNetzero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;               // 작성자 ID (Gateway에서 X-MEMBER-ID로 전달)

    private String industrialSector;     // 산업군 (예: 제조업, 에너지)
    private double baseYearEmission;     // 기준 연도 배출량 (tCO₂e)
    private double targetYearEmission;   // 목표 연도 배출량 (tCO₂e)
    private double reductionRate;        // 감축율 (%)
    private int baseYear;                // 기준 연도
    private int targetYear;              // 목표 연도
    private double financialAssetValue;  // 금융 자산 가치 (투자액, 대출액)
    private double attributionFactor;    // 귀속 계수 (AF)
    private String assetType;            // 자산 유형 (기업대출, 상장주식/채권, PF 등)
    private String scenario;             // 시나리오 (IEA Net Zero 2050, NGFS Orderly Transition)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // 자동 생성 시간 (생성 시)
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 자동 수정 시간 (업데이트 시)
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // DTO 기반 수정 메서드 (NetZero 목표 수정 시 사용)
    public void updateFromDto(GoalNetzeroRequest request) {
        if (request.getIndustrialSector() != null) this.industrialSector = request.getIndustrialSector();
        if (request.getBaseYearEmission() > 0) this.baseYearEmission = request.getBaseYearEmission();
        if (request.getTargetYearEmission() > 0) this.targetYearEmission = request.getTargetYearEmission();
        if (request.getReductionRate() > 0) this.reductionRate = request.getReductionRate();
        if (request.getBaseYear() > 0) this.baseYear = request.getBaseYear();
        if (request.getTargetYear() > 0) this.targetYear = request.getTargetYear();
        if (request.getFinancialAssetValue() > 0) this.financialAssetValue = request.getFinancialAssetValue();
        if (request.getAttributionFactor() > 0) this.attributionFactor = request.getAttributionFactor();
        if (request.getAssetType() != null) this.assetType = request.getAssetType();
        if (request.getScenario() != null) this.scenario = request.getScenario();
    }

    // 목표 배출량 계산 (시나리오 기반)
    public void calculateTargetEmission(String scenario) {
        switch (scenario) {
            case "IEA Net Zero 2050" -> this.targetYearEmission = 1;
            case "NGFS Orderly Transition" -> this.targetYearEmission = this.baseYearEmission * 0.1;
            default -> throw new IllegalArgumentException("지원되지 않는 시나리오: " + scenario);
        }
    }
}
