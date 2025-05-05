package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.ScenarioAnalysisRequest;
import com.nsmm.esg.tcfdservice.service.ScenarioAnalysisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tcfd/strategy")
@RequiredArgsConstructor
public class ScenarioAnalysisController {

    private final ScenarioAnalysisService scenarioAnalysisService;

    private Long extractMemberId(HttpServletRequest request) {
        String memberIdHeader = request.getHeader("X-MEMBER-ID");
        if (memberIdHeader == null || memberIdHeader.isBlank()) {
            throw new IllegalArgumentException("X-MEMBER-ID 헤더가 누락되었습니다.");
        }
        return Long.parseLong(memberIdHeader);
    }

    // ✅ 생성
    @PostMapping("/scenario")
    public String createScenario(@RequestBody ScenarioAnalysisRequest request,
                                 HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = scenarioAnalysisService.createScenario(memberId, request);
        return "시나리오 분석 등록 완료. ID = " + id;
    }

    // ✅ 수정
    @PutMapping("/scenario/{id}")
    public String updateScenario(@PathVariable Long id,
                                 @RequestBody ScenarioAnalysisRequest request,
                                 HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        scenarioAnalysisService.updateScenario(memberId, id, request);
        return "시나리오 분석 수정 완료. ID = " + id;
    }

    // ✅ 삭제
    @DeleteMapping("/scenario/{id}")
    public String deleteScenario(@PathVariable Long id,
                                 HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        scenarioAnalysisService.deleteScenario(memberId, id);
        return "시나리오 분석 삭제 완료. ID = " + id;
    }
}
