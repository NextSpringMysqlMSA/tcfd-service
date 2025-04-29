package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TcfdGovernanceMeetingRepository extends JpaRepository<TcfdGovernanceMeeting, Long> {

}
