package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.StrategyRiskIdentification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StrategyRiskIdentificationResponse {

    private final Long id;
    private final String riskType;
    private final String riskCategory;
    private final String riskCause;
    private final String time;
    private final String impact;
    private final String financialImpact;
    private final String businessModelImpact;
    private final String plans;
    private final Long memberId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

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
