package com.nsmm.esg.tcfdservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 넷제로(탄소중립) 목표 정보를 관리하는 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalNetZero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    private Long memberId; // 회원 ID

    private String industrialSector; // 산업 부문

    private int baseYear; // 기준 연도

    private int targetYear; // 목표 연도

    private String scenario; // 시나리오 정보

    @OneToMany(mappedBy = "netZero", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GoalNetZeroIndustry> industries = new ArrayList<>();

    @OneToMany(mappedBy = "netZero", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GoalNetZeroEmission> emissions = new ArrayList<>();


    @CreationTimestamp
    private LocalDateTime createdAt; // 생성 일시

    @UpdateTimestamp
    private LocalDateTime updatedAt; // 수정 일시

    // 연관관계 편의 메서드
    /**
     * 산업별 목표를 추가하는 편의 메서드
     * @param industry 추가할 산업별 목표 객체
     */
    public void addIndustry(GoalNetZeroIndustry industry) {
        industries.add(industry);
        industry.setNetZero(this);
    }

    /**
     * 배출량 정보를 추가하는 편의 메서드
     * @param emission 추가할 배출량 객체
     */
    public void addEmission(GoalNetZeroEmission emission) {
        emissions.add(emission);
        emission.setNetZero(this);
    }

    /**
     * 넷제로 목표 정보를 업데이트하는 메서드
     * @param industrialSector 산업 부문
     * @param baseYear 기준 연도
     * @param targetYear 목표 연도
     * @param scenario 시나리오 정보
     */
    public void updateInfo(String industrialSector, int baseYear, int targetYear, String scenario) {
        this.industrialSector = industrialSector;
        this.baseYear = baseYear;
        this.targetYear = targetYear;
        this.scenario = scenario;
    }



}