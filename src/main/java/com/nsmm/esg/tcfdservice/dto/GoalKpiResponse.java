package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalKpi;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 기후변화 관련 핵심성과지표(KPI) 응답 DTO
 * 기업의 기후변화 대응 목표 및 성과 측정을 위한 KPI 정보를 클라이언트에 제공하기 위한 객체
 */
@Getter
@Builder
public class GoalKpiResponse {
    private final Long id;                    // KPI 고유 식별자
    private final String indicator;           // KPI 지표명 (예: 온실가스 배출량, 에너지 사용량, 재생에너지 비율 등)
    private final String detailedIndicator;   // 세부 지표명 (예: Scope 1 배출량, Scope 2 배출량, 전력 사용량 등)
    private final String unit;                // 측정 단위 (예: tCO2e, MWh, %, 백만원 등)
    private final int baseYear;               // 기준 연도 (성과 측정의 시작점)
    private final int goalYear;               // 목표 연도 (목표 달성 기한)
    private final int referenceValue;         // 기준 연도 값 (기준 연도의 실적)
    private final int currentValue;           // 현재 값 (최근 측정된 실적)
    private final int targetValue;            // 목표 값 (목표 연도에 달성하고자 하는 값)
    private final Long memberId;              // 회원 ID
    private final LocalDateTime createdAt;    // 데이터 생성 일시
    private final LocalDateTime updatedAt;    // 데이터 수정 일시

    /**
     * 엔티티 객체로부터 응답 DTO를 생성하는 정적 팩토리 메서드
     *
     * @param entity 변환할 목표 KPI 엔티티
     * @return 목표 KPI 응답 DTO
     */
    public static GoalKpiResponse fromEntity(GoalKpi entity) {
        return GoalKpiResponse.builder()
                .id(entity.getId())
                .indicator(entity.getIndicator())
                .detailedIndicator(entity.getDetailedIndicator())
                .unit(entity.getUnit())
                .baseYear(entity.getBaseYear())
                .goalYear(entity.getGoalYear())
                .referenceValue(entity.getReferenceValue())
                .currentValue(entity.getCurrentValue())
                .targetValue(entity.getTargetValue())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}