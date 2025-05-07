package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TcfdGovernanceEducationRepository extends JpaRepository<TcfdGovernanceEducation, Long> {
    List<TcfdGovernanceEducation> findByMemberId(Long memberId);
}
