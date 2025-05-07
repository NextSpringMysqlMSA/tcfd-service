package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.RiskIdentification;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RiskIdentificationRequest {

    private final Long id;
    private final String riskType;
    private final String riskCategory;
    private final String riskCause;
    private final String time;
    private final String impact;
    private final String financialImpact;
    private final String businessModelImpact;
    private final String plans;

    public RiskIdentification toEntity(Long memberId) {
        return RiskIdentification.builder()
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

    public static RiskIdentificationRequest fromEntity(RiskIdentification entity) {
        return RiskIdentificationRequest.builder()
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
