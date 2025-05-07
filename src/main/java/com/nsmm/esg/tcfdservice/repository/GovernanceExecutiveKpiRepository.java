package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.GovernanceExecutiveKpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GovernanceExecutiveKpiRepository extends JpaRepository<GovernanceExecutiveKpi, Long> {
    List<GovernanceExecutiveKpi> findByMemberId(Long memberId);
}
