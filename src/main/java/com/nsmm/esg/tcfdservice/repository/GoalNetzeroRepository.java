package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.GoalNetzero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalNetzeroRepository extends JpaRepository<GoalNetzero, Long> {
    /**
     * 특정 회원의 NetZero 목표 목록 조회
     */
    List<GoalNetzero> findByMemberId(Long memberId);

    /**
     * 특정 회원의 NetZero 목표 중 시나리오 기반 조회
     */
    List<GoalNetzero> findByMemberIdAndScenario(Long memberId, String scenario);
}
