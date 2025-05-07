package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.StrategyScenarioAnalysisRequest;
import com.nsmm.esg.tcfdservice.entity.StrategyScenarioAnalysis;
import com.nsmm.esg.tcfdservice.repository.StrategyScenarioAnalysisRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StrategyScenarioAnalysisService {

    private final StrategyScenarioAnalysisRepository strategyScenarioAnalysisRepository;

    // 시나리오 호출
    public List<StrategyScenarioAnalysisRequest> getScenarios(Long memberId) {
        return strategyScenarioAnalysisRepository.findByMemberId(memberId).stream()
                .map(StrategyScenarioAnalysisRequest::fromEntity)
                .toList();
    }
    //----------------------------------------------------------------------------------------------------------------

    // 생성
    public Long createScenario(Long memberId, StrategyScenarioAnalysisRequest request) {
        return strategyScenarioAnalysisRepository.save(request.toEntity(memberId)).getId();
    }
    //----------------------------------------------------------------------------------------------------------------

    // 수정
    @Transactional
    public void updateScenario(Long memberId, Long id, StrategyScenarioAnalysisRequest request) {
        StrategyScenarioAnalysis scenario = strategyScenarioAnalysisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 시나리오가 존재하지 않습니다."));

        if (!scenario.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 시나리오에 대한 권한이 없습니다.");
        }

        scenario.updateFromDto(request);
    }
    //----------------------------------------------------------------------------------------------------------------

    // 삭제
    public void deleteScenario(Long memberId, Long id) {
        StrategyScenarioAnalysis scenario = strategyScenarioAnalysisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 시나리오가 존재하지 않습니다."));

        if (!scenario.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 시나리오에 대한 권한이 없습니다.");
        }

        strategyScenarioAnalysisRepository.delete(scenario);
    }
    //----------------------------------------------------------------------------------------------------------------
}
