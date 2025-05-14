package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalKpi;
import lombok.Builder;
import lombok.Getter;

/**
 * 기후변화 관련 핵심성과지표(KPI) 요청 DTO
 * 클라이언트로부터 기업의 기후변화 대응 목표 및 성과 측정을 위한 KPI 정보를 전달받기 위한 객체
 */
@Getter
@Builder
public class GoalKpiRequest {
    private final String indicator;           // KPI 지표명 (예: 온실가스 배출량, 에너지 사용량, 재생에너지 비율 등)
    private final String detailedIndicator;   // 세부 지표명 (예: Scope 1 배출량, Scope 2 배출량, 전력 사용량 등)
    private final String unit;                // 측정 단위 (예: tCO2e, MWh, %, 백만원 등)
    private final int baseYear;               // 기준 연도 (성과 측정의 시작점)
    private final int goalYear;               // 목표 연도 (목표 달성 기한)
    private final int referenceValue;         // 기준 연도 값 (기준 연도의 실적)
    private final int currentValue;           // 현재 값 (최근 측정된 실적)
    private final int targetValue;            // 목표 값 (목표 연도에 달성하고자 하는 값)

    /**
     * 요청 DTO를 엔티티 객체로 변환하는 메서드
     *
     * @param memberId 회원 ID (인증된 사용자의 ID)
     * @return 목표 KPI 엔티티 객체
     */
    public GoalKpi toEntity(Long memberId) {
        return GoalKpi.builder()
                .memberId(memberId)
                .indicator(indicator)
                .detailedIndicator(detailedIndicator)
                .unit(unit)
                .baseYear(baseYear)
                .goalYear(goalYear)
                .referenceValue(referenceValue)
                .currentValue(currentValue)
                .targetValue(targetValue)
                .build();
    }
}