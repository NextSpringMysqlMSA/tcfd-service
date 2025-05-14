package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.StrategyScenarioAnalysisRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 기후변화 관련 시나리오 분석 엔티티
 * TCFD 전략 영역에서 기업이 수행한 기후변화 시나리오 분석 정보를 저장하는 엔티티
 * 특정 기후 시나리오에 따른 물리적 자산의 예상 피해액 계산을 위한 정보 포함
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "strategy_scenario_analysis")
public class StrategyScenarioAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                       // 시나리오 분석 고유 식별자

    private Long memberId;                 // 회원 ID (Gateway에서 X-MEMBER-ID로 전달)

    private String regions;                // 자산 위치 행정구역 (예: 서울특별시, 부산광역시, 경기도 등)
    private Double longitude;              // 자산 위치 경도 (예: 126.9780)
    private Double latitude;               // 자산 위치 위도 (예: 37.5665)
    private String assetType;              // 자산 유형 (예: 공장, 물류센터, 사무실 빌딩, 소매점 등)
    private String industry;               // 산업 분야 (예: 제조업, 에너지, 화학, 금융, 유통 등)
    private String scenario;               // 기후변화 시나리오 (예: SSP1-2.6, SSP2-4.5, SSP5-8.5 등)
    private Integer baseYear;              // 분석 기준 연도 (예: 2020, 2025, 2030 등)
    private String climate;                // 분석 대상 기후 위협 요소 (예: 태풍, 홍수, 폭염, 가뭄 등)

    @Column(nullable = false)
    private Double assetValue;             // 분석 대상 자산 가치 (단위: 원, 백만원 등)

    private Long estimatedDamage;          // 기후변화 시나리오에 따른 예상 피해액 (백엔드에서 계산)

    @CreationTimestamp
    private LocalDateTime createdAt;       // 데이터 생성 일시

    @UpdateTimestamp
    private LocalDateTime updatedAt;       // 데이터 수정 일시

    /**
     * 요청 DTO를 사용하여 엔티티 정보 업데이트
     *
     * @param request 시나리오 분석 정보 업데이트 요청 객체
     */
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
        // estimatedDamage는 별도 계산 후 설정됨
    }

    /**
     * 계산된 기후변화로 인한 예상 피해액 설정
     *
     * @param damage 예상 피해액 (단위: 원, 백만원 등)
     */
    public void setDamage(Long damage) {
        this.estimatedDamage = damage;
    }
}