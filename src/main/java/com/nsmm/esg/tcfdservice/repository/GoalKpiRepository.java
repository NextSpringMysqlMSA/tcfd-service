package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.GoalKpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalKpiRepository extends JpaRepository<GoalKpi, Long> {
    List<GoalKpi> findByMemberId(Long memberId);


}