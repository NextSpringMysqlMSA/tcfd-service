package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.GoalNetZero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalNetZeroRepository extends JpaRepository<GoalNetZero, Long> {

    List<GoalNetZero> findByMemberId(Long memberId);

    Optional<GoalNetZero> findByIdAndMemberId(Long id, Long memberId);

    boolean existsByMemberIdAndBaseYearAndTargetYearAndScenario(Long memberId, int baseYear, int targetYear, String scenario);
}
