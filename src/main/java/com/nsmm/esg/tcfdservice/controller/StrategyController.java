package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.StrategyRiskIdentificationRequest;
import com.nsmm.esg.tcfdservice.dto.StrategyScenarioAnalysisRequest;
import com.nsmm.esg.tcfdservice.service.StrategyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tcfd/strategy")
@RequiredArgsConstructor
public class StrategyController {
    private final StrategyService strategyService;


    private Long extractMemberId(HttpServletRequest request) {
        String memberIdHeader = request.getHeader("X-MEMBER-ID");
        if (memberIdHeader == null || memberIdHeader.isBlank()) {
            throw new IllegalArgumentException("X-MEMBER-ID 헤더가 누락되었습니다.");
        }
        return Long.parseLong(memberIdHeader);
    }
    //----------------------------------------------------------------------------------------------------------------

    // 읽기
    @GetMapping("/scenario")
    public List<StrategyScenarioAnalysisRequest> getScenarios(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return strategyService.getScenarios(memberId);
    }
    //----------------------------------------------------------------------------------------------------------------

    // 생성
    @PostMapping("/scenario")
    public String createScenario(@RequestBody StrategyScenarioAnalysisRequest request,
                                 HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = strategyService.createScenario(memberId, request);
        return "시나리오 분석 등록 완료. ID = " + id;
    }
    //----------------------------------------------------------------------------------------------------------------

    // 수정
    @PutMapping("/scenario/{id}")
    public String updateScenario(@PathVariable Long id,
                                 @RequestBody StrategyScenarioAnalysisRequest request,
                                 HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        strategyService.updateScenario(memberId, id, request);
        return "시나리오 분석 수정 완료. ID = " + id;
    }
    //----------------------------------------------------------------------------------------------------------------

    // 삭제
    @DeleteMapping("/scenario/{id}")
    public String deleteScenario(@PathVariable Long id,
                                 HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        strategyService.deleteScenario(memberId, id);
        return "시나리오 분석 삭제 완료. ID = " + id;
    }
    //----------------------------------------------------------------------------------------------------------------

    // 전체 조회
    @GetMapping("/risk")
    public List<StrategyRiskIdentificationRequest> getRisks(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return strategyService.getRisks(memberId);
    }
    //----------------------------------------------------------------------------------------------------------------


    @PostMapping("/risk")
    public String createRisk(@RequestBody StrategyRiskIdentificationRequest request,
                             HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = strategyService.createRisk(memberId, request);
        return "리스크 등록 완료. ID = " + id;
    }
    //----------------------------------------------------------------------------------------------------------------

    @PutMapping("/risk/{id}")
    public String updateRisk(@PathVariable Long id,
                             @RequestBody StrategyRiskIdentificationRequest request,
                             HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        strategyService.updateRisk(memberId, id, request);
        return "리스크 수정 완료. ID = " + id;
    }
    //----------------------------------------------------------------------------------------------------------------

    @DeleteMapping("/risk/{id}")
    public String deleteRisk(@PathVariable Long id,
                             HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        strategyService.deleteRisk(memberId, id);
        return "리스크 삭제 완료. ID = " + id;
    }
    //----------------------------------------------------------------------------------------------------------------
}
