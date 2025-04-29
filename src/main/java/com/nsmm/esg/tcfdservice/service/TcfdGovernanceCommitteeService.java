// src/main/java/com/nsmm/esg.tcfdservice.service.TcfdGovernanceCommitteeService.java
package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.CreateTcfdGovernanceCommitteeRequest;
import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceCommittee;
import com.nsmm.esg.tcfdservice.repository.TcfdGovernanceCommitteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TcfdGovernanceCommitteeService {

    private final TcfdGovernanceCommitteeRepository tcfdGovernanceCommitteeRepository;

    public Long createCommittee(CreateTcfdGovernanceCommitteeRequest request) {
        TcfdGovernanceCommittee committee = TcfdGovernanceCommittee.builder()
                .memberId(request.getMemberId())
                .committeeName(request.getCommitteeName())
                .memberName(request.getMemberName())
                .memberPosition(request.getMemberPosition())
                .memberAffiliation(request.getMemberAffiliation())
                .climateResponsibility(request.getClimateResponsibility())
                .build();

        return tcfdGovernanceCommitteeRepository.save(committee).getId();
    }
}
