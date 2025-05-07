package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.TcfdGoalKpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TcfdGoalKpiRepository extends JpaRepository<TcfdGoalKpi, Long> {
    List<TcfdGoalKpi> findByMemberId(Long memberId);
}