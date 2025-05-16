package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.GovernanceCommittee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GovernanceCommitteeRepository extends JpaRepository<GovernanceCommittee, Long> {

    List<GovernanceCommittee> findByMemberId(Long memberId);


}
