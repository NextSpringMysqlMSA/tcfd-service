package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.StrategyRiskIdentificationRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 기후변화 관련 전략적 리스크 식별 엔티티
 * TCFD 전략 영역에서 기업이 식별한 기후변화 관련 리스크 정보를 저장하는 엔티티
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "strategy_risk_identification")
public class StrategyRiskIdentification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // 리스크 식별 고유 식별자

    private Long memberId;                  // 회원 ID (작성자)

    private String riskType;                // 리스크 유형 (예: 물리적 리스크, 전환 리스크)
    private String riskCategory;            // 리스크 카테고리 (예: 정책 및 법률, 기술, 시장, 평판, 급성 물리적, 만성 물리적)
    private String riskCause;               // 리스크 원인 (예: 탄소세 도입, 소비자 선호도 변화, 극단적 기상현상)
    private String time;                    // 시간적 범위 (예: 단기, 중기, 장기)
    private String impact;                  // 영향 (예: 운영 비용 증가, 수익 감소, 자산 가치 하락)
    private String financialImpact;         // 재무적 영향 (예: 비용 증가, 수익 감소, 자산 가치 하락에 대한 구체적 금액 또는 비율)
    private String businessModelImpact;     // 비즈니스 모델 영향 (예: 제품 라인업 변경, 공급망 재구성, 생산 방식 변경 필요성)
    private String plans;                   // 대응 계획 (예: 에너지 효율 개선, 저탄소 기술 투자, 공급망 다변화)

    @CreationTimestamp
    private LocalDateTime createdAt;        // 데이터 생성 일시

    @UpdateTimestamp
    private LocalDateTime updatedAt;        // 데이터 수정 일시

    /**
     * 요청 DTO를 사용하여 엔티티 정보 업데이트
     *
     * @param request 리스크 식별 정보 업데이트 요청 객체
     */
    public void updateFromDto(StrategyRiskIdentificationRequest request) {
        this.riskType = request.getRiskType();
        this.riskCategory = request.getRiskCategory();
        this.riskCause = request.getRiskCause();
        this.time = request.getTime();
        this.impact = request.getImpact();
        this.financialImpact = request.getFinancialImpact();
        this.businessModelImpact = request.getBusinessModelImpact();
        this.plans = request.getPlans();
    }
}