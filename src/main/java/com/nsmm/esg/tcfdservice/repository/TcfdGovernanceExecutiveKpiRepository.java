package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceExecutiveKpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TcfdGovernanceExecutiveKpiRepository extends JpaRepository<TcfdGovernanceExecutiveKpi, Long> {
}
