package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.StrategyRiskIdentification;
import lombok.Builder;
import lombok.Getter;

/**
 * 전략적 기후변화 리스크 식별 요청 DTO
 * 클라이언트로부터 기후변화 관련 리스크 식별 정보를 전달받기 위한 객체
 */
@Getter
@Builder
public class StrategyRiskIdentificationRequest {

    private final String riskType;            // 리스크 유형 (예: 물리적 리스크, 전환 리스크)
    private final String riskCategory;        // 리스크 세부 분류 (예: 정책 및 법률, 시장, 기술 등)
    private final String riskCause;           // 리스크 원인
    private final String time;                // 리스크 예상 발생 시기 (예: 단기, 중기, 장기)
    private final String impact;              // 리스크 영향
    private final String financialImpact;     // 재무적 영향 (예: 매출 감소, 비용 증가 등)
    private final String businessModelImpact; // 비즈니스 모델 영향
    private final String plans;               // 대응 계획

    /**
     * 요청 DTO를 엔티티 객체로 변환하는 메서드
     *
     * @param memberId 회원 ID (인증된 사용자의 ID)
     * @return 리스크 식별 엔티티 객체
     */
    public StrategyRiskIdentification toEntity(Long memberId) {
        return StrategyRiskIdentification.builder()
                .memberId(memberId)
                .riskType(riskType)
                .riskCategory(riskCategory)
                .riskCause(riskCause)
                .time(time)
                .impact(impact)
                .financialImpact(financialImpact)
                .businessModelImpact(businessModelImpact)
                .plans(plans)
                .build();
    }
}