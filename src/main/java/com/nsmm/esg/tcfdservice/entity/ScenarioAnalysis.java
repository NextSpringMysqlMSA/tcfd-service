package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.ScenarioAnalysisRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "scenario_analysis")
public class ScenarioAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;                // 작성자 ID

    private String regions;
    private Double longitude;
    private Double latitude;
    private String warming;
    private String industry;
    private String scenario;
    private Integer baseYear;
    private String climate;
    private Double damage;
    private String format;
    private String responseStrategy;

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
    public void updateFromDto(ScenarioAnalysisRequest request) {
        this.regions = request.getRegions();
        this.longitude = request.getLongitude();
        this.latitude = request.getLatitude();
        this.warming = request.getWarming();
        this.industry = request.getIndustry();
        this.scenario = request.getScenario();
        this.baseYear = request.getBaseYear();
        this.climate = request.getClimate();
        this.damage = request.getDamage();
        this.format = request.getFormat();
        this.responseStrategy = request.getResponseStrategy();
    }
}
