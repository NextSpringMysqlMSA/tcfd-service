package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TcfdGovernanceMeetingRepository extends JpaRepository<TcfdGovernanceMeeting, Long> {
    List<TcfdGovernanceMeeting> findByMemberId(Long memberId);
}
