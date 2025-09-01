package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceCommitteeRequest;
import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceEducationRequest;
import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceExecutiveKpiRequest;
import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceMeetingRequest;
import com.nsmm.esg.tcfdservice.service.TcfdGovernanceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tcfd/governance")
@RequiredArgsConstructor
public class TcfdGovernanceController {

    private final TcfdGovernanceService tcfdGovernanceService;

    /**
     * 공통적으로 사용하는 X-MEMBER-ID 추출 메서드
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
    public List<TcfdGovernanceCommitteeRequest> getCommittees(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return tcfdGovernanceService.getCommittees(memberId);
    }

    /**
     * 특정 위원회 조회 (GET)
     */
    @GetMapping("/committee/{id}")
    public TcfdGovernanceCommitteeRequest getCommitteeById(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return tcfdGovernanceService.getCommitteeById(memberId, id);
    }

    /**
     * 위원회 생성 (POST)
     */
    @PostMapping("/committee")
    public String createCommittee(@RequestBody TcfdGovernanceCommitteeRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = tcfdGovernanceService.createCommittee(memberId, request);
        return "위원회 생성 완료. ID = " + id;
    }

    /**
     * 위원회 수정 (PUT)
     */
    @PutMapping("/committee/{id}")
    public String updateCommittee(@PathVariable Long id,
                                  @RequestBody TcfdGovernanceCommitteeRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        tcfdGovernanceService.updateCommittee(memberId, id, request);
        return "위원회 수정 완료. ID = " + id;
    }

    /**
     * 위원회 삭제 (DELETE)
     */
    @DeleteMapping("/committee/{id}")
    public String deleteCommittee(@PathVariable Long id,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        tcfdGovernanceService.deleteCommittee(memberId, id);
        return "위원회 삭제 완료. ID = " + id;
    }

    // ------------------------------ 회의 ------------------------------

    /**
     * 회의 목록 조회 (GET)
     */
    @GetMapping("/meeting")
    public List<TcfdGovernanceMeetingRequest> getMeetings(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return tcfdGovernanceService.getMeetings(memberId);
    }

    /**
     * 회의 등록 (POST)
     */
    @PostMapping("/meeting")
    public String createMeeting(@RequestBody TcfdGovernanceMeetingRequest request,
                                HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = tcfdGovernanceService.createMeeting(memberId, request);
        return "회의 등록 완료. ID = " + id;
    }

    /**
     * 회의 수정 (PUT)
     */
    @PutMapping("/meeting/{id}")
    public String updateMeeting(@PathVariable Long id,
                                @RequestBody TcfdGovernanceMeetingRequest request,
                                HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        tcfdGovernanceService.updateMeeting(memberId, id, request);
        return "회의 수정 완료. ID = " + id;
    }

    /**
     * 회의 삭제 (DELETE)
     */
    @DeleteMapping("/meeting/{id}")
    public String deleteMeeting(@PathVariable Long id,
                                HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        tcfdGovernanceService.deleteMeeting(memberId, id);
        return "회의 삭제 완료. ID = " + id;
    }

    // ------------------------------ 경영진 KPI ------------------------------

    /**
     * 경영진 KPI 목록 조회 (GET)
     */
    @GetMapping("/executive-kpi")
    public List<TcfdGovernanceExecutiveKpiRequest> getExecutiveKpis(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return tcfdGovernanceService.getExecutiveKpis(memberId);
    }

    /**
     * 경영진 KPI 등록 (POST)
     */
    @PostMapping("/executive-kpi")
    public String createExecutiveKpi(@RequestBody TcfdGovernanceExecutiveKpiRequest request,
                                     HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = tcfdGovernanceService.createExecutiveKpi(memberId, request);
        return "경영진 KPI 등록 완료. ID = " + id;
    }

    /**
     * 경영진 KPI 수정 (PUT)
     */
    @PutMapping("/executive-kpi/{id}")
    public String updateExecutiveKpi(@PathVariable Long id,
                                     @RequestBody TcfdGovernanceExecutiveKpiRequest request,
                                     HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        tcfdGovernanceService.updateExecutiveKpi(memberId, id, request);
        return "경영진 KPI 수정 완료. ID = " + id;
    }

    /**
     * 경영진 KPI 삭제 (DELETE)
     */
    @DeleteMapping("/executive-kpi/{id}")
    public String deleteExecutiveKpi(@PathVariable Long id,
                                     HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        tcfdGovernanceService.deleteExecutiveKpi(memberId, id);
        return "경영진 KPI 삭제 완료. ID = " + id;
    }

    // ------------------------------ 환경 교육 ------------------------------

    /**
     * 환경 교육 목록 조회 (GET)
     */
    @GetMapping("/education")
    public List<TcfdGovernanceEducationRequest> getEducations(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return tcfdGovernanceService.getEducations(memberId);
    }

    /**
     * 환경 교육 등록 (POST)
     */
    @PostMapping("/education")
    public String createEducation(@RequestBody TcfdGovernanceEducationRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = tcfdGovernanceService.createEducation(memberId, request);
        return "환경 교육 등록 완료. ID = " + id;
    }

    /**
     * 환경 교육 수정 (PUT)
     */
    @PutMapping("/education/{id}")
    public String updateEducation(@PathVariable Long id,
                                  @RequestBody TcfdGovernanceEducationRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        tcfdGovernanceService.updateEducation(memberId, id, request);
        return "환경 교육 수정 완료. ID = " + id;
    }

    /**
     * 환경 교육 삭제 (DELETE)
     */
    @DeleteMapping("/education/{id}")
    public String deleteEducation(@PathVariable Long id,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        tcfdGovernanceService.deleteEducation(memberId, id);
        return "환경 교육 삭제 완료. ID = " + id;
    }
}