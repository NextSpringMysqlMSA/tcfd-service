package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.*;
import com.nsmm.esg.tcfdservice.entity.*;
import com.nsmm.esg.tcfdservice.repository.*;
import com.nsmm.esg.tcfdservice.entity.Identifiable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TcfdGovernanceService {

    private final TcfdGovernanceCommitteeRepository committeeRepository;
    private final TcfdGovernanceMeetingRepository meetingRepository;
    private final TcfdGovernanceExecutiveKpiRepository kpiRepository;
    private final TcfdGovernanceEducationRepository educationRepository;

    // 위원회 생성
    public Long createCommittee(Long memberId, TcfdGovernanceCommitteeRequest request) {
        return saveEntityAndReturnId(request.toEntity(memberId), committeeRepository);
    }

    // 회의 생성
    public Long createMeeting(Long memberId, TcfdGovernanceMeetingRequest request) {
        return saveEntityAndReturnId(request.toEntity(memberId), meetingRepository);
    }

    // KPI 생성
    public Long createExecutiveKpi(Long memberId, TcfdGovernanceExecutiveKpiRequest request) {
        return saveEntityAndReturnId(request.toEntity(memberId), kpiRepository);
    }

    // 교육 생성
    public Long createEducation(Long memberId, TcfdGovernanceEducationRequest request) {
        return saveEntityAndReturnId(request.toEntity(memberId), educationRepository);
    }

    // 위원회 목록 조회
    public List<TcfdGovernanceCommitteeRequest> getCommittees(Long memberId) {
        return committeeRepository.findByMemberId(memberId).stream()
                .map(TcfdGovernanceCommitteeRequest::fromEntity)
                .toList();
    }

    // 회의 목록 조회
    public List<TcfdGovernanceMeetingRequest> getMeetings(Long memberId) {
        return meetingRepository.findByMemberId(memberId).stream()
                .map(TcfdGovernanceMeetingRequest::fromEntity)
                .toList();
    }

    // KPI 목록 조회
    public List<TcfdGovernanceExecutiveKpiRequest> getExecutiveKpis(Long memberId) {
        return kpiRepository.findByMemberId(memberId).stream()
                .map(TcfdGovernanceExecutiveKpiRequest::fromEntity)
                .toList();
    }

    // 교육 목록 조회
    public List<TcfdGovernanceEducationRequest> getEducations(Long memberId) {
        return educationRepository.findByMemberId(memberId).stream()
                .map(TcfdGovernanceEducationRequest::fromEntity)
                .toList();
    }

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
