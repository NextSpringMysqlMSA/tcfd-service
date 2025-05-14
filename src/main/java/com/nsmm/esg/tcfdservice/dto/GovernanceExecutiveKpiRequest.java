package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.GovernanceExecutiveKpi;
import lombok.Builder;
import lombok.Getter;

/**
 * 임원 기후변화 KPI 요청 DTO
 * 클라이언트로부터 임원의 기후변화 관련 성과지표(KPI) 정보를 전달받기 위한 객체
 */
@Getter
@Builder
public class GovernanceExecutiveKpiRequest {

    private final String executiveName;   // 임원 이름 (예: CEO, CFO, 기후변화 책임자 등)
    private final String kpiName;         // KPI 이름 (예: 온실가스 감축률, 재생에너지 전환 비율 등)
    private final String targetValue;     // 목표 값 (예: 10%, 100톤, 5건 등)
    private final String achievedValue;   // 달성 값 (예: 8%, 80톤, 3건 등)

    /**
     * 요청 DTO를 엔티티 객체로 변환하는 메서드
     *
     * @param memberId 회원 ID (인증된 사용자의 ID)
     * @return 임원 KPI 엔티티 객체
     */
    public GovernanceExecutiveKpi toEntity(Long memberId) {
        return GovernanceExecutiveKpi.builder()
                .memberId(memberId)
                .executiveName(executiveName)
                .kpiName(kpiName)
                .targetValue(targetValue)
                .achievedValue(achievedValue)
                .build();
    }
}