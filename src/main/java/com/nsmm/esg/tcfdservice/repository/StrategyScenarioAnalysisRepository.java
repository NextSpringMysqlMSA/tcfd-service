package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.StrategyScenarioAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategyScenarioAnalysisRepository extends JpaRepository<StrategyScenarioAnalysis, Long> {
    List<StrategyScenarioAnalysis> findByMemberId(Long memberId);


}
