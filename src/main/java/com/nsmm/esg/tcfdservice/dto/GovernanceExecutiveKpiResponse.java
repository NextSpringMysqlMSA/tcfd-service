package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceExecutiveKpi;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 임원 기후변화 KPI 응답 DTO
 * 임원의 기후변화 관련 성과지표(KPI) 정보를 클라이언트에 제공하기 위한 객체
 */
@Getter
@Builder
public class GovernanceExecutiveKpiResponse {

    private final Long id;                // 고유 식별자
    private final String executiveName;   // 임원 이름 (예: CEO, CFO, 기후변화 책임자 등)
    private final String kpiName;         // KPI 이름 (예: 온실가스 감축률, 재생에너지 전환 비율 등)
    private final String targetValue;     // 목표 값 (예: 10%, 100톤, 5건 등)
    private final String achievedValue;   // 달성 값 (예: 8%, 80톤, 3건 등)
    private final Long memberId;          // 회원 ID
    private final LocalDateTime createdAt; // 데이터 생성 일시
    private final LocalDateTime updatedAt; // 데이터 수정 일시

    /**
     * 엔티티 객체로부터 응답 DTO를 생성하는 정적 팩토리 메서드
     *
     * @param entity 변환할 임원 KPI 엔티티
     * @return 임원 KPI 응답 DTO
     */
    public static GovernanceExecutiveKpiResponse fromEntity(GovernanceExecutiveKpi entity) {
        return GovernanceExecutiveKpiResponse.builder()
                .id(entity.getId())
                .executiveName(entity.getExecutiveName())
                .kpiName(entity.getKpiName())
                .targetValue(entity.getTargetValue())
                .achievedValue(entity.getAchievedValue())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}