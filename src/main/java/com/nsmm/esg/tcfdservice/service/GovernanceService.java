package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.*;
import com.nsmm.esg.tcfdservice.entity.*;
import com.nsmm.esg.tcfdservice.repository.*;
import com.nsmm.esg.tcfdservice.entity.Identifiable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GovernanceService {

    private final GovernanceCommitteeRepository committeeRepository;
    private final GovernanceMeetingRepository meetingRepository;
    private final GovernanceExecutiveKpiRepository kpiRepository;
    private final GovernanceEducationRepository educationRepository;

    /**
     * 위원회 생성
     */
    public Long createCommittee(Long memberId, GovernanceCommitteeRequest request) {
        return saveEntityAndReturnId(request.toEntity(memberId), committeeRepository);
    }

    /**
     * 위원회 목록 조회
     */
    public List<GovernanceCommitteeRequest> getCommittees(Long memberId) {
        return committeeRepository.findByMemberId(memberId).stream()
                .map(GovernanceCommitteeRequest::fromEntity)
                .toList();
    }

    /**
     * 특정 위원회 조회
     */
    public GovernanceCommitteeRequest getCommitteeById(Long memberId, Long committeeId) {
        // 특정 committeeId에 해당하는 위원회 정보를 조회
        GovernanceCommittee committee = committeeRepository.findById(committeeId)
                .filter(c -> c.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("수정할 위원회가 존재하지 않거나 권한이 없습니다."));

        // 엔티티를 요청 객체로 변환하여 반환
        return GovernanceCommitteeRequest.fromEntity(committee);
    }

    /**
     * 위원회 수정
     */
    @Transactional
    public void updateCommittee(Long memberId, Long id, GovernanceCommitteeRequest request) {
        GovernanceCommittee committee = committeeRepository.findById(id)
                .filter(c -> c.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("수정할 위원회가 존재하지 않거나 권한이 없습니다."));

        committee.updateFromRequest(request);
    }

    /**
     * 위원회 삭제
     */
    public void deleteCommittee(Long memberId, Long id) {
        GovernanceCommittee committee = committeeRepository.findById(id)
                .filter(c -> c.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("삭제할 위원회가 존재하지 않거나 권한이 없습니다."));

        committeeRepository.delete(committee);
    }
    //----------------------------------------------------------------------------------------------------------------

    /**
     * 회의 생성
     */
    public Long createMeeting(Long memberId, GovernanceMeetingRequest request) {
        return saveEntityAndReturnId(request.toEntity(memberId), meetingRepository);
    }

    /**
     * 회의 목록 조회
     */
    public List<GovernanceMeetingRequest> getMeetings(Long memberId) {
        return meetingRepository.findByMemberId(memberId).stream()
                .map(GovernanceMeetingRequest::fromEntity)
                .toList();
    }

    /**
     * 특정 회의 조회
     */
    public GovernanceMeetingRequest getMeetingById(Long memberId, Long meetingId) {
        GovernanceMeeting meeting = meetingRepository.findById(meetingId)
                .filter(m -> m.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("조회할 회의가 존재하지 않거나 권한이 없습니다."));
        return GovernanceMeetingRequest.fromEntity(meeting);
    }

    /**
     * 회의 수정
     */
    @Transactional
    public void updateMeeting(Long memberId, Long id, GovernanceMeetingRequest request) {
        GovernanceMeeting meeting = meetingRepository.findById(id)
                .filter(m -> m.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("수정할 회의가 존재하지 않거나 권한이 없습니다."));

        meeting.updateFromRequest(request);
    }

    /**
     * 회의 삭제
     */
    public void deleteMeeting(Long memberId, Long id) {
        GovernanceMeeting meeting = meetingRepository.findById(id)
                .filter(m -> m.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("삭제할 회의가 존재하지 않거나 권한이 없습니다."));

        meetingRepository.delete(meeting);
    }
    //----------------------------------------------------------------------------------------------------------------

    /**
     * 경영진 KPI 생성
     */
    public Long createExecutiveKpi(Long memberId, GovernanceExecutiveKpiRequest request) {
        return saveEntityAndReturnId(request.toEntity(memberId), kpiRepository);
    }

    /**
     * 경영진 KPI 목록 조회
     */
    public List<GovernanceExecutiveKpiRequest> getExecutiveKpis(Long memberId) {
        return kpiRepository.findByMemberId(memberId).stream()
                .map(GovernanceExecutiveKpiRequest::fromEntity)
                .toList();
    }

    /**
     * 특정 경영진 KPI 조회
     */
    public GovernanceExecutiveKpiRequest getExecutiveKpiById(Long memberId, Long kpiId) {
        GovernanceExecutiveKpi kpi = kpiRepository.findById(kpiId)
                .filter(k -> k.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("조회할 KPI가 존재하지 않거나 권한이 없습니다."));
        return GovernanceExecutiveKpiRequest.fromEntity(kpi);
    }


    /**
     * 경영진 KPI 수정
     */
    @Transactional
    public void updateExecutiveKpi(Long memberId, Long id, GovernanceExecutiveKpiRequest request) {
        GovernanceExecutiveKpi kpi = kpiRepository.findById(id)
                .filter(k -> k.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("수정할 KPI가 존재하지 않거나 권한이 없습니다."));

        kpi.updateFromRequest(request);
    }

    /**
     * 경영진 KPI 삭제
     */
    public void deleteExecutiveKpi(Long memberId, Long id) {
        GovernanceExecutiveKpi kpi = kpiRepository.findById(id)
                .filter(k -> k.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("삭제할 KPI가 존재하지 않거나 권한이 없습니다."));

        kpiRepository.delete(kpi);
    }
    //----------------------------------------------------------------------------------------------------------------

    /**
     * 환경 교육 생성
     */
    public Long createEducation(Long memberId, GovernanceEducationRequest request) {
        return saveEntityAndReturnId(request.toEntity(memberId), educationRepository);
    }

    /**
     * 환경 교육 목록 조회
     */
    public List<GovernanceEducationRequest> getEducations(Long memberId) {
        return educationRepository.findByMemberId(memberId).stream()
                .map(GovernanceEducationRequest::fromEntity)
                .toList();
    }

    /**
     * 특정 환경 교육 조회
     */
    public GovernanceEducationRequest getEducationById(Long memberId, Long educationId) {
        GovernanceEducation education = educationRepository.findById(educationId)
                .filter(e -> e.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("조회할 교육이 존재하지 않거나 권한이 없습니다."));
        return GovernanceEducationRequest.fromEntity(education);
    }


    /**
     * 환경 교육 수정
     */
    @Transactional
    public void updateEducation(Long memberId, Long id, GovernanceEducationRequest request) {
        GovernanceEducation education = educationRepository.findById(id)
                .filter(e -> e.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("수정할 교육이 존재하지 않거나 권한이 없습니다."));

        education.updateFromRequest(request);
    }

    /**
     * 환경 교육 삭제
     */
    public void deleteEducation(Long memberId, Long id) {
        GovernanceEducation education = educationRepository.findById(id)
                .filter(e -> e.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("삭제할 교육이 존재하지 않거나 권한이 없습니다."));

        educationRepository.delete(education);
    }
    //----------------------------------------------------------------------------------------------------------------

    /**
     * 공통 저장 로직
     * @param entity 저장할 엔티티 (ID getter를 포함해야 함)
     * @param repository JPA Repository
     * @return 저장된 엔티티의 ID
     */
    private <T extends Identifiable<ID>, ID> ID saveEntityAndReturnId(T entity, JpaRepository<T, ID> repository) {
        return repository.save(entity).getId();
    }
}
