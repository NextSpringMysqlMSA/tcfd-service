package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.GoalKpiRequest;


import com.nsmm.esg.tcfdservice.dto.GoalKpiResponse;

import com.nsmm.esg.tcfdservice.service.KpiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tcfd/goal")
public class KpiController {

    private final KpiService kpiService;

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
        return kpiService.getKpiGoals(memberId);
    }

    /**
     * 특정 KPI 목표 조회 (GET)
     */
    @GetMapping("/kpi/{id}")
    public GoalKpiResponse getKpiGoalById(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return kpiService.getKpiGoalById(id, memberId);
    }

    /**
     * KPI 목표 저장
     */
    @PostMapping("/kpi")
    public String createKpiGoal(@RequestBody GoalKpiRequest request, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = kpiService.createKpiGoal(memberId, request);
        return "KPI 목표 저장 완료. ID = " + id;
    }

    /**
     * KPI 목표 수정
     */
    @PutMapping("/kpi/{id}")
    public String updateKpiGoal(@PathVariable Long id, @RequestBody GoalKpiRequest request, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        kpiService.updateKpiGoal(id, memberId, request);
        return "KPI 목표 수정 완료. ID = " + id;
    }

    /**
     * KPI 목표 삭제
     */
    @DeleteMapping("/kpi/{id}")
    public String deleteKpiGoal(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        kpiService.deleteKpiGoal(id, memberId);
        return "KPI 목표 삭제 완료. ID = " + id;
    }


}