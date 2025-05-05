package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.RiskIdentification;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RiskIdentificationRequest {

    private final String riskType;
    private final String riskCategory;
    private final String riskCause;
    private final String time;
    private final String impact;
    private final String financialImpact;
    private final String businessModelImpact;
    private final String plans;

    // DTO → Entity 변환
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
}
