package com.nsmm.esg.tcfdservice.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 산업별 투자/대출 자산 항목 엔티티
 * - GoalNetZero와 N:1 연관
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalNetZeroIndustry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관된 포트폴리오 목표
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "net_zero_id")
    private GoalNetZero netZero;


    // 산업명 (예: 철강 제조)
    private String industry;

    // 자산 유형 (예: 기업대출, PF 등)
    private String assetType;

    // 투자/대출 금액 (Ai)
    private double amount;

    // 자산 전체 가치 (AV)
    private double totalAssetValue;

    // 배출계수 (EF)
    private double emissionFactor;

    // 귀속계수 (AF = Ai / AV)
    private double attributionFactor;

    // 기준년도 배출량 (Eb = Ai × EF × AF)
    private double baseEmission;

    @SuppressWarnings("unused")
    public void setNetZero(GoalNetZero netZero) {
        this.netZero = netZero;
    }

    @SuppressWarnings("unused")
    public void setEmissionFactor(double ef) {
        this.emissionFactor = ef;
    }

    @SuppressWarnings("unused")
    public void setAttributionFactor(double af) {
        this.attributionFactor = af;
    }

    @SuppressWarnings("unused")
    public void setBaseEmission(double eb) {
        this.baseEmission = eb;
    }

}
