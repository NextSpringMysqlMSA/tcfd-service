package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalNetZeroEmission;
import lombok.Builder;
import lombok.Getter;

/**
 * 넷제로 포트폴리오 연도별 배출량 응답 DTO
 * 특정 연도의 온실가스 배출량 정보를 클라이언트에 제공하기 위한 객체
 */
@Getter
@Builder
public class GoalNetZeroEmissionResponse {

    private final int year;      // 연도 (기준년도부터 목표년도까지)
    private final double emission; // 해당 연도의 온실가스 배출량 (tCO2e)

    /**
     * 엔티티 객체로부터 응답 DTO를 생성하는 정적 팩토리 메서드
     *
     * @param entity 변환할 배출량 정보 엔티티
     * @return 연도별 배출량 정보 응답 DTO
     */
    public static GoalNetZeroEmissionResponse fromEntity(GoalNetZeroEmission entity) {
        return GoalNetZeroEmissionResponse.builder()
                .year(entity.getYear())
                .emission(entity.getEmission())
                .build();
    }
}