package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.ScenarioAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioAnalysisRepository extends JpaRepository<ScenarioAnalysis, Long> {
    List<ScenarioAnalysis> findByMemberId(Long memberId);

}
