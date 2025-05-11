package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.*;
import com.nsmm.esg.tcfdservice.service.GovernanceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tcfd/governance")
@RequiredArgsConstructor
public class GovernanceController {

    private final GovernanceService governanceService;

    /**
     * 공통적으로 사용하는 X-MEMBER-ID 추출 메서드....
     * - 인증된 사용자 식별을 위해 사용됨
     * @param request HttpServletRequest 객체
     * @return Long 타입의 회원 ID
     */
    private Long extractMemberId(HttpServletRequest request) {
        String memberIdHeader = request.getHeader("X-MEMBER-ID");
        if (memberIdHeader == null || memberIdHeader.isBlank()) {
            throw new IllegalArgumentException("X-MEMBER-ID 헤더가 누락되었습니다.");
        }
        return Long.parseLong(memberIdHeader);
    }

    // ------------------------------ 위원회 ------------------------------

    /**
     * 위원회 목록 조회 (GET)
     */
    @GetMapping("/committee")
    public List<GovernanceCommitteeResponse> getCommittees(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return governanceService.getCommittees(memberId);
    }

    /**
     * 특정 위원회 조회 (GET)
     */
    @GetMapping("/committee/{id}")
    public GovernanceCommitteeResponse getCommitteeById(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return governanceService.getCommitteeById(memberId, id);
    }

    /**
     * 위원회 생성 (POST)
     */
    @PostMapping("/committee")
    public String createCommittee(@RequestBody GovernanceCommitteeRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = governanceService.createCommittee(memberId, request);
        return "위원회 생성 완료. ID = " + id;
    }

    /**
     * 위원회 수정 (PUT)
     */
    @PutMapping("/committee/{id}")
    public String updateCommittee(@PathVariable Long id,
                                  @RequestBody GovernanceCommitteeRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        governanceService.updateCommittee(memberId, id, request);
        return "위원회 수정 완료. ID = " + id;
    }

    /**
     * 위원회 삭제 (DELETE)
     */
    @DeleteMapping("/committee/{id}")
    public String deleteCommittee(@PathVariable Long id,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        governanceService.deleteCommittee(memberId, id);
        return "위원회 삭제 완료. ID = " + id;
    }

    // ------------------------------ 회의 ------------------------------

    /**
     * 회의 목록 조회 (GET)
     */
    @GetMapping("/meeting")
    public List<GovernanceMeetingResponse> getMeetings(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return governanceService.getMeetings(memberId);
    }


    /**
     * 특정 회의 조회 (GET)
     */
    @GetMapping("/meeting/{id}")
    public GovernanceMeetingResponse getMeetingById(@PathVariable Long id,
                                                    HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return governanceService.getMeetingById(memberId, id);
    }

    /**
     * 회의 등록 (POST)
     */
    @PostMapping("/meeting")
    public String createMeeting(@RequestBody GovernanceMeetingRequest request,
                                HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = governanceService.createMeeting(memberId, request);
        return "회의 등록 완료. ID = " + id;
    }

    /**
     * 회의 수정 (PUT)
     */
    @PutMapping("/meeting/{id}")
    public String updateMeeting(@PathVariable Long id,
                                @RequestBody GovernanceMeetingRequest request,
                                HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        governanceService.updateMeeting(memberId, id, request);
        return "회의 수정 완료. ID = " + id;
    }

    /**
     * 회의 삭제 (DELETE)
     */
    @DeleteMapping("/meeting/{id}")
    public String deleteMeeting(@PathVariable Long id,
                                HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        governanceService.deleteMeeting(memberId, id);
        return "회의 삭제 완료. ID = " + id;
    }

    // ------------------------------ 경영진 KPI ------------------------------

    /**
     * 경영진 KPI 목록 조회 (GET)
     */
    @GetMapping("/executive-kpi")
    public List<GovernanceExecutiveKpiResponse> getExecutiveKpis(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return governanceService.getExecutiveKpis(memberId);
    }

    /**
     * 특정 경영진 KPI 조회 (GET)
     */
    @GetMapping("/executive-kpi/{id}")
    public GovernanceExecutiveKpiResponse getExecutiveKpiById(@PathVariable Long id,
                                                             HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return governanceService.getExecutiveKpiById(memberId, id);
    }


    /**
     * 경영진 KPI 등록 (POST)
     */
    @PostMapping("/executive-kpi")
    public String createExecutiveKpi(@RequestBody GovernanceExecutiveKpiRequest request,
                                     HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = governanceService.createExecutiveKpi(memberId, request);
        return "경영진 KPI 등록 완료. ID = " + id;
    }

    /**
     * 경영진 KPI 수정 (PUT)
     */
    @PutMapping("/executive-kpi/{id}")
    public String updateExecutiveKpi(@PathVariable Long id,
                                     @RequestBody GovernanceExecutiveKpiRequest request,
                                     HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        governanceService.updateExecutiveKpi(memberId, id, request);
        return "경영진 KPI 수정 완료. ID = " + id;
    }

    /**
     * 경영진 KPI 삭제 (DELETE)
     */
    @DeleteMapping("/executive-kpi/{id}")
    public String deleteExecutiveKpi(@PathVariable Long id,
                                     HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        governanceService.deleteExecutiveKpi(memberId, id);
        return "경영진 KPI 삭제 완료. ID = " + id;
    }

    // ------------------------------ 환경 교육 ------------------------------

    /**
     * 환경 교육 목록 조회 (GET)
     */
    @GetMapping("/education")
    public List<GovernanceEducationResponse> getEducations(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return governanceService.getEducations(memberId);
    }

    /**
     * 특정 환경 교육 조회 (GET)
     */
    @GetMapping("/education/{id}")
    public GovernanceEducationResponse getEducationById(@PathVariable Long id,
                                                       HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return governanceService.getEducationById(memberId, id);
    }


    /**
     * 환경 교육 등록 (POST)
     */
    @PostMapping("/education")
    public String createEducation(@RequestBody GovernanceEducationRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = governanceService.createEducation(memberId, request);
        return "환경 교육 등록 완료. ID = " + id;
    }

    /**
     * 환경 교육 수정 (PUT)
     */
    @PutMapping("/education/{id}")
    public String updateEducation(@PathVariable Long id,
                                  @RequestBody GovernanceEducationRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        governanceService.updateEducation(memberId, id, request);
        return "환경 교육 수정 완료. ID = " + id;
    }

    /**
     * 환경 교육 삭제 (DELETE)
     */
    @DeleteMapping("/education/{id}")
    public String deleteEducation(@PathVariable Long id,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        governanceService.deleteEducation(memberId, id);
        return "환경 교육 삭제 완료. ID = " + id;
    }
}