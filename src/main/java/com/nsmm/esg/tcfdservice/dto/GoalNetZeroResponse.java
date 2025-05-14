package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GoalNetZero;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 넷제로 포트폴리오 조회 응답 DTO
 * 넷제로 목표 정보와 관련 산업 및 배출량 데이터를 클라이언트에 전달하기 위한 객체
 */
@Getter
@Builder
public class GoalNetZeroResponse {

    private final Long id;                // 고유 식별자
    private final Long memberId;          // 회원 ID
    private final String industrialSector;  // 산업 부문
    private final int baseYear;           // 기준 연도
    private final int targetYear;         // 목표 연도
    private final String scenario;        // 시나리오 정보

    private final List<GoalNetZeroIndustryResponse> industries;  // 산업별 투자/대출 자산 정보 목록
    private final List<GoalNetZeroEmissionResponse> emissions;   // 연도별 배출량 정보 목록

    private final LocalDateTime createdAt;  // 생성 일시
    private final LocalDateTime updatedAt;  // 수정 일시

    /**
     * 엔티티 객체로부터 응답 DTO를 생성하는 정적 팩토리 메서드
     *
     * @param entity 변환할 넷제로 목표 엔티티
     * @param industryResponses 산업별 정보 응답 DTO 목록
     * @param emissionResponses 배출량 정보 응답 DTO 목록
     * @return 넷제로 목표 응답 DTO
     */
    public static GoalNetZeroResponse fromEntity(GoalNetZero entity,
                                                 List<GoalNetZeroIndustryResponse> industryResponses,
                                                 List<GoalNetZeroEmissionResponse> emissionResponses) {
        return GoalNetZeroResponse.builder()
                .id(entity.getId())
                .memberId(entity.getMemberId())
                .industrialSector(entity.getIndustrialSector())
                .baseYear(entity.getBaseYear())
                .targetYear(entity.getTargetYear())
                .scenario(entity.getScenario())
                .industries(industryResponses)
                .emissions(emissionResponses)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}