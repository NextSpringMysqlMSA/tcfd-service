package com.nsmm.esg.tcfdservice.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 연도별 배출량 엔티티
 * - GoalNetZero와 N:1 연관
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalNetZeroEmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관된 넷제로 목표
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "net_zero_id")
    private GoalNetZero netZero;

    // 연도 (예: 2025, 2030, 2040, 2050)
    private int year;

    // 해당 연도의 배출량 E(y)
    private double emission;

    @SuppressWarnings("unused")
    public void setNetZero(GoalNetZero netZero) {
        this.netZero = netZero;
    }

}
