package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.GoalKpiRequest;
import com.nsmm.esg.tcfdservice.dto.GoalKpiResponse;
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
    public Long createKpiGoal(Long memberId, GoalKpiRequest request) {
        GoalKpi entity = request.toEntity(memberId);
        return goalKpiRepository.save(entity).getId();
    }

    /**
     * KPI 목표 목록 조회
     */
    public List<GoalKpiResponse> getKpiGoals(Long memberId) {
        return goalKpiRepository.findByMemberId(memberId).stream()
                .map(GoalKpiResponse::fromEntity)
                .toList();
    }

    /**
     * 특정 KPI 목표 조회
     */
    public GoalKpiResponse getKpiGoalById(Long id, Long memberId) {
        GoalKpi kpi = goalKpiRepository.findById(id)
                .filter(g -> g.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("조회할 KPI 목표가 존재하지 않거나 권한이 없습니다."));
        return GoalKpiResponse.fromEntity(kpi);
    }


    /**
     * KPI 목표 수정
     */
    public void updateKpiGoal(Long goalId, Long memberId, GoalKpiRequest request) {
        GoalKpi goalKpi = goalKpiRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 KPI 목표가 존재하지 않습니다. ID = " + goalId));
        if(!goalKpi.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 목표에 대한 권한이 없습니다.");
        }
        goalKpi.updateFromDto(request);
    }

    /**
     * KPI 목표 삭제
     */
    public void deleteKpiGoal(Long goalId, Long memberId) {
        if (!goalKpiRepository.existsById(goalId)) {
            throw new IllegalArgumentException("해당 KPI 목표가 존재하지 않습니다. ID = " + goalId);
        }
        goalKpiRepository.deleteById(goalId);
    }
}