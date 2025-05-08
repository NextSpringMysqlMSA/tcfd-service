package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.StrategyScenarioAnalysisRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "strategy_scenario_analysis")
public class StrategyScenarioAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;                // 작성자 ID (Gateway에서 X-MEMBER-ID로 전달)

    private String regions;              // 행정구역 (서울특별시 등)
    private Double longitude;            // 경도
    private Double latitude;             // 위도
    private String assetType;            // 자산 유형 (예: 공장, 빌딩)
    private String industry;             // 산업 분야
    private String scenario;             // SSP 시나리오 (SSP1-2.6 등)
    private Integer baseYear;            // 기준 연도 (2020, 2025 등)
    private String climate;              // 기후 위협 (태풍, 홍수, 폭염, 가뭄)

    @Column(nullable = false)
    private Double assetValue;           // 자산 가치

    private Double estimatedDamage;               // 백엔드에서 계산되는 예상 피해액

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

    // DTO 기반 수정 메서드
    public void updateFromDto(StrategyScenarioAnalysisRequest request) {
        this.regions = request.getRegions();
        this.longitude = request.getLongitude();
        this.latitude = request.getLatitude();
        this.assetType = request.getAssetType();
        this.industry = request.getIndustry();
        this.scenario = request.getScenario();
        this.baseYear = request.getBaseYear();
        this.climate = request.getClimate();
        this.assetValue = request.getAssetValue();
        // damage는 계산 후 set
    }

    public void setDamage(double damage) {
        this.estimatedDamage = damage;
    }
}

