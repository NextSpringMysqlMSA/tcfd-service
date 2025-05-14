package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.StrategyRiskIdentification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 전략적 기후변화 리스크 식별 응답 DTO
 * 기후변화 관련 리스크 식별 정보를 클라이언트에 제공하기 위한 객체
 */
@Getter
@Builder
public class StrategyRiskIdentificationResponse {

    private final Long id;                   // 고유 식별자
    private final String riskType;           // 리스크 유형 (예: 물리적 리스크, 전환 리스크)
    private final String riskCategory;       // 리스크 세부 분류 (예: 정책 및 법률, 시장, 기술 등)
    private final String riskCause;          // 리스크 원인
    private final String time;               // 리스크 예상 발생 시기 (예: 단기, 중기, 장기)
    private final String impact;             // 리스크 영향
    private final String financialImpact;    // 재무적 영향 (예: 매출 감소, 비용 증가 등)
    private final String businessModelImpact;// 비즈니스 모델 영향
    private final String plans;              // 대응 계획
    private final Long memberId;             // 회원 ID
    private final LocalDateTime createdAt;   // 생성 일시
    private final LocalDateTime updatedAt;   // 수정 일시

    /**
     * 엔티티 객체로부터 응답 DTO를 생성하는 정적 팩토리 메서드
     *
     * @param entity 변환할 리스크 식별 엔티티
     * @return 리스크 식별 응답 DTO
     */
    public static StrategyRiskIdentificationResponse fromEntity(StrategyRiskIdentification entity) {
        return StrategyRiskIdentificationResponse.builder()
                .id(entity.getId())
                .riskType(entity.getRiskType())
                .riskCategory(entity.getRiskCategory())
                .riskCause(entity.getRiskCause())
                .time(entity.getTime())
                .impact(entity.getImpact())
                .financialImpact(entity.getFinancialImpact())
                .businessModelImpact(entity.getBusinessModelImpact())
                .plans(entity.getPlans())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}