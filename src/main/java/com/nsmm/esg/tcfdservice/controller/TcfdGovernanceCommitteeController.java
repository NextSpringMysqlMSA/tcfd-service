// src/main/java/com/nsmm/esg.tcfdservice.controller.TcfdGovernanceCommitteeController.java
package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.CreateTcfdGovernanceCommitteeRequest;
import com.nsmm.esg.tcfdservice.service.TcfdGovernanceCommitteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tcfd/governance")
@RequiredArgsConstructor
public class TcfdGovernanceCommitteeController {

    private final TcfdGovernanceCommitteeService tcfdGovernanceCommitteeService;

    @PostMapping("/committee")
    public String createCommittee(@RequestBody CreateTcfdGovernanceCommitteeRequest request) {
        Long id = tcfdGovernanceCommitteeService.createCommittee(request);
        return "위원회 생성 완료. ID = " + id;
    }
}
