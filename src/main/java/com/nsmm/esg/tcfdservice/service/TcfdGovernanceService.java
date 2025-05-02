// src/main/java/com/nsmm/esg.tcfdservice.service.TcfdGovernanceCommitteeService.java
package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceCommitteeRequest;
import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceEducationRequest;
import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceExecutiveKpiRequest;
import com.nsmm.esg.tcfdservice.dto.TcfdGovernanceMeetingRequest;
import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceCommittee;
import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceEducation;
import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceExecutiveKpi;
import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceMeeting;
import com.nsmm.esg.tcfdservice.repository.TcfdGovernanceCommitteeRepository;
import com.nsmm.esg.tcfdservice.repository.TcfdGovernanceEducationRepository;
import com.nsmm.esg.tcfdservice.repository.TcfdGovernanceExecutiveKpiRepository;
import com.nsmm.esg.tcfdservice.repository.TcfdGovernanceMeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TcfdGovernanceService {

    private final TcfdGovernanceCommitteeRepository committeeRepository;
    private final TcfdGovernanceMeetingRepository meetingRepository;
    private final TcfdGovernanceExecutiveKpiRepository kpiRepository;
    private final TcfdGovernanceEducationRepository educationRepository;

    public Long createCommittee(Long memberId, TcfdGovernanceCommitteeRequest request) {
        TcfdGovernanceCommittee committee = TcfdGovernanceCommittee.builder()
                .memberId(memberId)  //  X-MEMBER-ID에서 받은 값을 세팅
                .committeeName(request.getCommitteeName())
                .memberName(request.getMemberName())
                .memberPosition(request.getMemberPosition())
                .memberAffiliation(request.getMemberAffiliation())
                .climateResponsibility(request.getClimateResponsibility())
                .build();

        return committeeRepository.save(committee).getId();
    }


    public Long createMeeting(TcfdGovernanceMeetingRequest request) {
        TcfdGovernanceMeeting meeting = TcfdGovernanceMeeting.builder()
                .memberId(request.getMemberId())
                .meetingName(request.getMeetingName())
                .meetingDate(request.getMeetingDate())
                .agenda(request.getAgenda())
                .build();
        return meetingRepository.save(meeting).getId();
    }

    public Long createExecutiveKpi(TcfdGovernanceExecutiveKpiRequest request) {
        TcfdGovernanceExecutiveKpi executiveKpi = TcfdGovernanceExecutiveKpi.builder()
                .memberId(request.getMemberId())
                .executiveName(request.getExecutiveName())
                .kpiName(request.getKpiName())
                .targetValue(request.getTargetValue())
                .achievedValue(request.getAchievedValue())
                .build();
        return kpiRepository.save(executiveKpi).getId();
    }

    public Long createEducation(TcfdGovernanceEducationRequest request) {
        TcfdGovernanceEducation education = TcfdGovernanceEducation.builder()
                .memberId(request.getMemberId())
                .educationTitle(request.getEducationTitle())
                .educationDate(request.getEducationDate())
                .participantCount(request.getParticipantCount())
                .content(request.getContent())
                .build();
        return educationRepository.save(education).getId();

    }
}
