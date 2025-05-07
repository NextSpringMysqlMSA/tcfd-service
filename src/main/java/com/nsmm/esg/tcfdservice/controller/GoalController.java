package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.GoalKpiRequest;
import com.nsmm.esg.tcfdservice.service.GoalService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tcfd/goal")
public class GoalController {

    private final GoalService goalService;

    /**
     * 공통적으로 사용하는 X-MEMBER-ID 추출 메서드
     * - 인증된 사용자 식별을 위해 사용됨
     */
    private Long extractMemberId(HttpServletRequest request) {
        String memberIdHeader = request.getHeader("X-MEMBER-ID");

        // ✅ 헤더가 없으면 기본값 1L 반환
        if (memberIdHeader == null || memberIdHeader.isBlank()) {
            System.out.println("⚠️ X-MEMBER-ID 누락 → 기본값 1L 사용");
            return 1L;
        }

        return Long.parseLong(memberIdHeader);
    }

    /**
     * KPI 목표 저장
     */
    @PostMapping("/KPIGoal")
    public String saveKpiGoal(@RequestBody GoalKpiRequest request, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = goalService.saveKpiGoal(memberId, request);
        return "KPI 목표 저장 완료. ID = " + id;
    }

    /**
     * KPI 목표 목록 조회
     */
    @GetMapping("/KPIGoal")
    public List<GoalKpiRequest> getKpiGoals(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return goalService.getKpiGoals(memberId);
    }
}