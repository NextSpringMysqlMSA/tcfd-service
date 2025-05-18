package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.NetZeroEmissionResponse;
import com.nsmm.esg.tcfdservice.dto.TcfdProgressResponse;
import com.nsmm.esg.tcfdservice.entity.*;
import com.nsmm.esg.tcfdservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InternalProgressService {

    private final GovernanceCommitteeRepository committeeRepository;
    private final GovernanceMeetingRepository meetingRepository;
    private final GovernanceEducationRepository educationRepository;
    private final GovernanceExecutiveKpiRepository executiveKpiRepository;
    private final GoalKpiRepository goalKpiRepository;
    private final GoalNetZeroRepository netZeroRepository;
    private final StrategyRiskIdentificationRepository strategyRiskIdentificationRepository;
    private final StrategyScenarioAnalysisRepository strategyScenarioAnalysisRepository;

    private final GoalNetZeroRepository goalNetZeroRepository;

    public TcfdProgressResponse getProgress(Long memberId) {
        int totalSections = 8;
        int completed = 0;

        if (!committeeRepository.findByMemberId(memberId).isEmpty()) completed++;
        if (!meetingRepository.findByMemberId(memberId).isEmpty()) completed++;
        if (!educationRepository.findByMemberId(memberId).isEmpty()) completed++;
        if (!executiveKpiRepository.findByMemberId(memberId).isEmpty()) completed++;
        if (!goalKpiRepository.findByMemberId(memberId).isEmpty()) completed++;
        if (!netZeroRepository.findByMemberId(memberId).isEmpty()) completed++;
        if (!strategyScenarioAnalysisRepository.findByMemberId(memberId).isEmpty()) completed++;
        if (!strategyRiskIdentificationRepository.findByMemberId(memberId).isEmpty()) completed++;

        int incomplete = totalSections - completed;
        int rate = totalSections == 0 ? 0 : (int) ((completed * 100.0) / totalSections);

        System.out.println("🟢 완료 항목 수: " + completed + " / 총 항목: " + totalSections);
        System.out.println("🔴 미완료 항목 수: " + incomplete);
        System.out.println("📊 완료율: " + rate + "%");

        return TcfdProgressResponse.builder()
                .totalCount(totalSections)
                .completedCount(completed)
                .inCompletedCount(incomplete)
                .completedRate(rate)
                .build();
    }

    public List<NetZeroEmissionResponse> getNetZeroEmissionProgress(Long memberId) {
        List<GoalNetZero> goals = goalNetZeroRepository.findByMemberId(memberId);

        // 목표가 없으면 빈 리스트 반환
        if (goals.isEmpty()) {
            log.info("🌱 NetZero 목표 없음: memberId={}", memberId);
            return Collections.emptyList();
        }

        // 연도별 배출량을 정리하여 반환
        List<NetZeroEmissionResponse> result = new ArrayList<>();
        for (GoalNetZero goal : goals) {
            for (GoalNetZeroEmission emission : goal.getEmissions()) {
                result.add(NetZeroEmissionResponse.builder()
                        .year(emission.getYear())
                        .emission(emission.getEmission())
                        .build());
            }
        }

        log.info("🌱 NetZero 연도별 배출량 {}건 반환 (memberId={})", result.size(), memberId);
        return result;
    }

}