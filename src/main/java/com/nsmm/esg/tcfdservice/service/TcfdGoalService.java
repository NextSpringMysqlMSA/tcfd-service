package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.TcfdGoalKpiRequest;
import com.nsmm.esg.tcfdservice.entity.TcfdGoalKpi;
import com.nsmm.esg.tcfdservice.repository.TcfdGoalKpiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TcfdGoalService {

    private final TcfdGoalKpiRepository tcfdGoalKpiRepository;

    /**
     * KPI 목표 저장
     */
    public Long saveKpiGoal(Long memberId, TcfdGoalKpiRequest request) {
        TcfdGoalKpi entity = request.toEntity(memberId);
        return tcfdGoalKpiRepository.save(entity).getId();
    }

    /**
     * KPI 목표 목록 조회
     */
    public List<TcfdGoalKpiRequest> getKpiGoals(Long memberId) {
        return tcfdGoalKpiRepository.findByMemberId(memberId).stream()
                .map(TcfdGoalKpiRequest::fromEntity)
                .toList();
    }
}