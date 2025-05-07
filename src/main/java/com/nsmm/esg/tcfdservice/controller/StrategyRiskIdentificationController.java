package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.StrategyRiskIdentificationRequest;
import com.nsmm.esg.tcfdservice.service.StrategyRiskIdentificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tcfd/strategy")
@RequiredArgsConstructor
public class StrategyRiskIdentificationController {

    private final StrategyRiskIdentificationService strategyRiskIdentificationService;

    private Long extractMemberId(HttpServletRequest request) {
        String memberIdHeader = request.getHeader("X-MEMBER-ID");
        if (memberIdHeader == null || memberIdHeader.isBlank()) {
            throw new IllegalArgumentException("X-MEMBER-ID 헤더가 누락되었습니다.");
        }
        return Long.parseLong(memberIdHeader);
    }
    //----------------------------------------------------------------------------------------------------------------

    // 전체 조회
    @GetMapping("/risk")
    public List<StrategyRiskIdentificationRequest> getRisks(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return strategyRiskIdentificationService.getRisks(memberId);
    }
    //----------------------------------------------------------------------------------------------------------------


    @PostMapping("/risk")
    public String createRisk(@RequestBody StrategyRiskIdentificationRequest request,
                             HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = strategyRiskIdentificationService.createRisk(memberId, request);
        return "리스크 등록 완료. ID = " + id;
    }
    //----------------------------------------------------------------------------------------------------------------

    @PutMapping("/risk/{id}")
    public String updateRisk(@PathVariable Long id,
                             @RequestBody StrategyRiskIdentificationRequest request,
                             HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        strategyRiskIdentificationService.updateRisk(memberId, id, request);
        return "리스크 수정 완료. ID = " + id;
    }
    //----------------------------------------------------------------------------------------------------------------

    @DeleteMapping("/risk/{id}")
    public String deleteRisk(@PathVariable Long id,
                             HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        strategyRiskIdentificationService.deleteRisk(memberId, id);
        return "리스크 삭제 완료. ID = " + id;
    }
    //----------------------------------------------------------------------------------------------------------------
}
