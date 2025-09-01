// src/main/java/com/nsmm/esg.tcfdservice.repository.TcfdGovernanceCommitteeRepository.java
package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceCommittee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TcfdGovernanceCommitteeRepository extends JpaRepository<TcfdGovernanceCommittee, Long> {

    List<TcfdGovernanceCommittee> findByMemberId(Long memberId);

}
