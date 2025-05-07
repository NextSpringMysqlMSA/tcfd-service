package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.GoalKpiRequest;
import com.nsmm.esg.tcfdservice.entity.GoalKpi;
import com.nsmm.esg.tcfdservice.repository.GoalKpiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalKpiRepository goalKpiRepository;

    /**
     * KPI 목표 저장
     */
    public Long saveKpiGoal(Long memberId, GoalKpiRequest request) {
        GoalKpi entity = request.toEntity(memberId);
        return goalKpiRepository.save(entity).getId();
    }

    /**
     * KPI 목표 목록 조회
     */
    public List<GoalKpiRequest> getKpiGoals(Long memberId) {
        return goalKpiRepository.findByMemberId(memberId).stream()
                .map(GoalKpiRequest::fromEntity)
                .toList();
    }
}