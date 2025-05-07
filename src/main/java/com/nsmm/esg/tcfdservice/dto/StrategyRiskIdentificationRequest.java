package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.StrategyRiskIdentification;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StrategyRiskIdentificationRequest {

    private final Long id;
    private final String riskType;
    private final String riskCategory;
    private final String riskCause;
    private final String time;
    private final String impact;
    private final String financialImpact;
    private final String businessModelImpact;
    private final String plans;

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

    public static StrategyRiskIdentificationRequest fromEntity(StrategyRiskIdentification entity) {
        return StrategyRiskIdentificationRequest.builder()
                .id(entity.getId())
                .riskType(entity.getRiskType())
                .riskCategory(entity.getRiskCategory())
                .riskCause(entity.getRiskCause())
                .time(entity.getTime())
                .impact(entity.getImpact())
                .financialImpact(entity.getFinancialImpact())
                .businessModelImpact(entity.getBusinessModelImpact())
                .plans(entity.getPlans())
                .build();
    }

}
