// src/main/java/com/nsmm/esg.tcfdservice.controller.TcfdGovernanceCommitteeController.java
package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceCommitteeRequest;
import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceEducationRequest;
import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceExecutiveKpiRequest;
import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceMeetingRequest;
import com.nsmm.esg.tcfdservice.service.TcfdGovernanceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tcfd/governance")
@RequiredArgsConstructor
public class TcfdGovernanceController {

    private final TcfdGovernanceService tcfdGovernanceService;

    @PostMapping("/committee")
    public String createCommittee(@RequestBody TcfdGovernanceCommitteeRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = Long.parseLong(httpRequest.getHeader("X-MEMBER-ID"));

        Long id = tcfdGovernanceService.createCommittee(memberId, request);

        return "위원회 생성 완료. ID = " + id;
    }


    @PostMapping("/meeting")
    public String createMeeting(@RequestBody TcfdGovernanceMeetingRequest request) {
        Long id = tcfdGovernanceService.createMeeting(request);
        return "회의 등록 완료. ID = " + id;
    }

    @PostMapping("/executive-kpi")
    public String createExecutiveKpi(@RequestBody TcfdGovernanceExecutiveKpiRequest request) {
        Long id = tcfdGovernanceService.createExecutiveKpi(request);
        return "경영진 KPI 등록 완료. ID = " + id;
    }

    @PostMapping("/education")
    public String createEducation(@RequestBody TcfdGovernanceEducationRequest request) {
        Long id = tcfdGovernanceService.createEducation(request);
        return "환경 교육 등록 완료. ID = " + id;
    }
}
