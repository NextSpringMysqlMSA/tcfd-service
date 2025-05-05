package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.RiskIdentificationRequest;
import com.nsmm.esg.tcfdservice.service.RiskIdentificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tcfd/strategy")
@RequiredArgsConstructor
public class RiskIdentificationController {

    private final RiskIdentificationService riskIdentificationService;

    private Long extractMemberId(HttpServletRequest request) {
        String memberIdHeader = request.getHeader("X-MEMBER-ID");
        if (memberIdHeader == null || memberIdHeader.isBlank()) {
            throw new IllegalArgumentException("X-MEMBER-ID 헤더가 누락되었습니다.");
        }
        return Long.parseLong(memberIdHeader);
    }

    @PostMapping("/risk")
    public String createRisk(@RequestBody RiskIdentificationRequest request,
                             HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = riskIdentificationService.createRisk(memberId, request);
        return "리스크 등록 완료. ID = " + id;
    }

    @PutMapping("/risk/{id}")
    public String updateRisk(@PathVariable Long id,
                             @RequestBody RiskIdentificationRequest request,
                             HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        riskIdentificationService.updateRisk(memberId, id, request);
        return "리스크 수정 완료. ID = " + id;
    }

    @DeleteMapping("/risk/{id}")
    public String deleteRisk(@PathVariable Long id,
                             HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        riskIdentificationService.deleteRisk(memberId, id);
        return "리스크 삭제 완료. ID = " + id;
    }
}
