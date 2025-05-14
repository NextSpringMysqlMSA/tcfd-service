package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.GoalNetZeroRequest;
import com.nsmm.esg.tcfdservice.dto.GoalNetZeroResponse;
import com.nsmm.esg.tcfdservice.service.NetZeroService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 넷제로 목표 API 컨트롤러
 * - 포트폴리오 생성, 조회, 수정, 삭제
 */
@RestController
@RequestMapping("/api/v1/tcfd")
@RequiredArgsConstructor
public class NetZeroController {

    private final NetZeroService netZeroService;

    /**
     * 넷제로 목표 전체 조회 (사용자별)
     */
    @GetMapping("/netzero")
    public List<GoalNetZeroResponse> getNetZeroGoals(HttpServletRequest request) {
        Long memberId = extractMemberId(request);
        return netZeroService.getNetZeroGoals(memberId);
    }

    /**
     * 넷제로 목표 단건 조회
     */
    @GetMapping("/netzero/{id}")
    public GoalNetZeroResponse getNetZeroGoalById(@PathVariable Long id, HttpServletRequest request) {
        Long memberId = extractMemberId(request);
        return netZeroService.getNetZeroGoalById(id, memberId);
    }

    /**
     * 넷제로 목표 생성
     * - 사용자 ID는 Gateway에서 JWT 디코딩 후 헤더에 담아 전달됨
     */
    @PostMapping("/netzero")
    public GoalNetZeroResponse createNetZeroGoal(@RequestBody GoalNetZeroRequest request,
                                                 HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return netZeroService.createNetZeroGoal(memberId, request);
    }

    /**
     * 넷제로 목표 수정
     */
    @PutMapping("/netzero/{id}")
    public GoalNetZeroResponse updateNetZeroGoal(@PathVariable Long id,
                                                 @RequestBody GoalNetZeroRequest request,
                                                 HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return netZeroService.updateNetZeroGoal(id, memberId, request);
    }

    /**
     * 넷제로 목표 삭제
     */
    @DeleteMapping("/netzero/{id}")
    public String deleteNetZeroGoal(@PathVariable Long id, HttpServletRequest request) {
        Long memberId = extractMemberId(request);
        netZeroService.deleteNetZeroGoal(id, memberId);
        return "NetZero 목표 삭제 완료. ID = " + id;
    }

    /**
     * Gateway에서 전달된 사용자 ID 추출 (JWT 디코딩 결과)
     */
    private Long extractMemberId(HttpServletRequest request) {
        String header = request.getHeader("X-MEMBER-ID");
        if (header == null || header.isBlank()) {
            throw new IllegalArgumentException("X-MEMBER-ID 헤더가 누락되었습니다.");
        }
        return Long.parseLong(header);
    }
}
