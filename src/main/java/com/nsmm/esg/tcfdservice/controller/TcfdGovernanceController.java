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
     * 공통적으로 사용하는 X-MEMBER-ID 추출 로직
     * - 해당 헤더가 존재하지 않거나 빈 값일 경우 예외 발생
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

    /**
     * 위원회 목록 조회
     * @param httpRequest HTTP 요청 객체
     * @return 위원회 요청 DTO 리스트
     */
    @GetMapping("/committee")
    public List<TcfdGovernanceCommitteeRequest> getCommittees(HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        return tcfdGovernanceService.getCommittees(memberId);
    }

    /**
     * 위원회 생성
     * @param request 위원회 요청 DTO
     * @param httpRequest HTTP 요청 객체
     * @return 생성 완료 메시지
     */
    @PostMapping("/committee")
    public String createCommittee(@RequestBody TcfdGovernanceCommitteeRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = tcfdGovernanceService.createCommittee(memberId, request);
        return "위원회 생성 완료. ID = " + id;
    }

    /**
     * 회의 정보 등록
     * @param request 회의 요청 DTO
     * @param httpRequest HTTP 요청 객체
     * @return 생성 완료 메시지
     */
    @PostMapping("/meeting")
    public String createMeeting(@RequestBody TcfdGovernanceMeetingRequest request,
                                HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = tcfdGovernanceService.createMeeting(memberId, request);
        return "회의 등록 완료. ID = " + id;
    }

    /**
     * 경영진 KPI 등록
     * @param request KPI 요청 DTO
     * @param httpRequest HTTP 요청 객체
     * @return 생성 완료 메시지
     */
    @PostMapping("/executive-kpi")
    public String createExecutiveKpi(@RequestBody TcfdGovernanceExecutiveKpiRequest request,
                                     HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = tcfdGovernanceService.createExecutiveKpi(memberId, request);
        return "경영진 KPI 등록 완료. ID = " + id;
    }

    /**
     * 환경 교육 등록
     * @param request 교육 요청 DTO
     * @param httpRequest HTTP 요청 객체
     * @return 생성 완료 메시지
     */
    @PostMapping("/education")
    public String createEducation(@RequestBody TcfdGovernanceEducationRequest request,
                                  HttpServletRequest httpRequest) {
        Long memberId = extractMemberId(httpRequest);
        Long id = tcfdGovernanceService.createEducation(memberId, request);
        return "환경 교육 등록 완료. ID = " + id;
    }
}
