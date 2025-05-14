package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.GoalKpiRequest;

import com.nsmm.esg.tcfdservice.dto.GoalNetZeroRequest;

import com.nsmm.esg.tcfdservice.dto.GoalKpiResponse;

import com.nsmm.esg.tcfdservice.service.GoalService;
import com.nsmm.esg.tcfdservice.service.NetzeroService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tcfd/goal")
public class GoalController {

    private final GoalService goalService;
    private final NetzeroService netzeroService;

    /**
     * 공통적으로 사용하는 X-MEMBER-ID 추출 메서드
     * - 인증된 사용자 식별을 위해 사용됨
     */
    private Long extractMemberId(HttpServletRequest request) {
        String memberIdHeader = request.getHeader("X-MEMBER-ID");

        // 헤더가 없으면 기본값 1L 반환
        if (memberIdHeader == null || memberIdHeader.isBlank()) {
            System.out.println("⚠️ X-MEMBER-ID 누락 → 기본값 1L 사용");
            return 1L;
        }

        return Long.parseLong(memberIdHeader);
    }

    // ------------------------- KPI 목표 API -------------------------

    /**
     * KPI 목표 목록 조회
     */
    @GetMapping("/kpi")
    public List<GoalKpiResponse> getKpiGoals(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return goalService.getKpiGoals(memberId);
    }

    /**
     * 특정 KPI 목표 조회 (GET)
     */
    @GetMapping("/kpi/{id}")
    public GoalKpiResponse getKpiGoalById(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return goalService.getKpiGoalById(id, memberId);
    }

    /**
     * KPI 목표 저장
     */
    @PostMapping("/kpi")
    public String createKpiGoal(@RequestBody GoalKpiRequest request, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = goalService.createKpiGoal(memberId, request);
        return "✅ KPI 목표 저장 완료. ID = " + id;
    }

    /**
     * KPI 목표 수정
     */
    @PutMapping("/kpi/{id}")
    public String updateKpiGoal(@PathVariable Long id, @RequestBody GoalKpiRequest request, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        goalService.updateKpiGoal(id, memberId, request);
        return "✅ KPI 목표 수정 완료. ID = " + id;
    }

    /**
     * KPI 목표 삭제
     */
    @DeleteMapping("/kpi/{id}")
    public String deleteKpiGoal(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        goalService.deleteKpiGoal(id, memberId);
        return "✅ KPI 목표 삭제 완료. ID = " + id;
    }

    // ------------------------- NetZero 목표 API -------------------------
    @GetMapping("/netzero")
    public List<GoalNetZeroRequest> getNetZeroGoals(HttpServletRequest request) {
        Long memberId = extractMemberId(request);
        return netzeroService.getNetZeroGoals(memberId);
    }

    @GetMapping("/netzero/{id}")
    public GoalNetZeroRequest getNetZeroGoalById(@PathVariable Long id, HttpServletRequest request) {
        Long memberId = extractMemberId(request);
        return netzeroService.getNetZeroGoalById(id, memberId);
    }

    @PostMapping("/netzero")
    public String createNetZeroGoal(@RequestBody GoalNetZeroRequest request, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = netzeroService.createNetZeroGoal(memberId, request);
        return "✅ NetZero 목표 저장 완료. ID = " + id;
    }

    @PutMapping("/netzero/{id}")
    public String updateNetZeroGoal(@PathVariable Long id, @RequestBody GoalNetZeroRequest request, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        netzeroService.updateNetZeroGoal(id, memberId, request);
        return "✅ NetZero 목표 수정 완료. ID = " + id;
    }

    @DeleteMapping("/netzero/{id}")
    public String deleteNetZeroGoal(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        netzeroService.deleteNetZeroGoal(id, memberId);
        return "✅ NetZero 목표 삭제 완료. ID = " + id;
    }

    // ✅ NetZero 기준년도 배출량 자동 계산 (2025)
    @GetMapping("/netzero/calculate/base-emission")
    public double calculateBaseYearEmission(
            @RequestParam double financialAssetValue,
            @RequestParam String industrialSector,
            @RequestParam double totalAssetValue) {
        return netzeroService.calculateBaseYearEmission(financialAssetValue, industrialSector, totalAssetValue);
    }

    // ✅ NetZero 중간 목표 배출량 조회 (2030, 2040, 2050)
    @GetMapping("/netzero/mid-target/{id}")
    public Map<Integer, Double> getMidTargetEmissions(@PathVariable Long id, HttpServletRequest request) {
        Long memberId = extractMemberId(request);
        return netzeroService.getMidTargetEmissions(id, memberId);
    }

    // ✅ NetZero 목표 계산값 확인 API (2030, 2040, 2050)
    @GetMapping("/netzero/calculate/{id}")
    public Map<String, Double> getNetZeroCalculation(@PathVariable Long id, HttpServletRequest request) {
        Long memberId = extractMemberId(request);
        return netzeroService.calculateNetZeroValues(id, memberId);
    }

    // ✅ NetZero 연도별 배출량 조회 (2030, 2040, 2050)
    @GetMapping("/netzero/emissions/{id}")
    public Map<Integer, Double> getNetZeroYearlyEmissions(@PathVariable Long id, HttpServletRequest request) {
        Long memberId = extractMemberId(request);
        return netzeroService.calculateYearlyEmissions(
                netzeroService.getNetZeroGoalById(id, memberId).getBaseYearEmission(),
                2025
        );
    }
}