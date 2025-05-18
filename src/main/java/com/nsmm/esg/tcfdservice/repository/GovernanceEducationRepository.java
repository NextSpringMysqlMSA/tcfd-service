package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.GovernanceEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GovernanceEducationRepository extends JpaRepository<GovernanceEducation, Long> {
    List<GovernanceEducation> findByMemberId(Long memberId);

}
