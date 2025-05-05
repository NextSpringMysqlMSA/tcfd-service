package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.RiskIdentification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskIdentificationRepository extends JpaRepository<RiskIdentification, Long> {

}
