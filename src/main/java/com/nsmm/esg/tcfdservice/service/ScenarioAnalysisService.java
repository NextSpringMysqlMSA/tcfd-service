package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.ScenarioAnalysisRequest;
import com.nsmm.esg.tcfdservice.entity.ScenarioAnalysis;
import com.nsmm.esg.tcfdservice.repository.ScenarioAnalysisRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioAnalysisService {

    private final ScenarioAnalysisRepository scenarioAnalysisRepository;

    // 시나리오 호출
    public List<ScenarioAnalysisRequest> getScenarios(Long memberId) {
        return scenarioAnalysisRepository.findByMemberId(memberId).stream()
                .map(ScenarioAnalysisRequest::fromEntity)
                .toList();
    }
    //----------------------------------------------------------------------------------------------------------------

    // 생성
    public Long createScenario(Long memberId, ScenarioAnalysisRequest request) {
        return scenarioAnalysisRepository.save(request.toEntity(memberId)).getId();
    }
    //----------------------------------------------------------------------------------------------------------------

    // 수정
    @Transactional
    public void updateScenario(Long memberId, Long id, ScenarioAnalysisRequest request) {
        ScenarioAnalysis scenario = scenarioAnalysisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 시나리오가 존재하지 않습니다."));

        if (!scenario.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 시나리오에 대한 권한이 없습니다.");
        }

        scenario.updateFromDto(request);
    }
    //----------------------------------------------------------------------------------------------------------------

    // 삭제
    public void deleteScenario(Long memberId, Long id) {
        ScenarioAnalysis scenario = scenarioAnalysisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 시나리오가 존재하지 않습니다."));

        if (!scenario.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 시나리오에 대한 권한이 없습니다.");
        }

        scenarioAnalysisRepository.delete(scenario);
    }
    //----------------------------------------------------------------------------------------------------------------
}
