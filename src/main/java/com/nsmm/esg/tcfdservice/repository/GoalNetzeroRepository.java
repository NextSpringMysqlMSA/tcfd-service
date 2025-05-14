package com.nsmm.esg.tcfdservice.repository;

import com.nsmm.esg.tcfdservice.entity.GoalNetZero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalNetzeroRepository extends JpaRepository<GoalNetZero, Long> {

    /**
     * 특정 회원의 NetZero 목표 목록 조회
     */
    List<GoalNetZero> findByMemberId(Long memberId);

    /**
     * 특정 회원의 NetZero 목표 중 시나리오 기반 조회
     */
    @Query("SELECT g FROM GoalNetZero g WHERE g.memberId = :memberId AND g.scenario = :scenario")
    List<GoalNetZero> findByMemberIdAndScenario(@Param("memberId") Long memberId, @Param("scenario") String scenario);

    /**
     * 특정 목표 ID로 NetZero 목표 조회 (회원 ID로 권한 확인)
     */
    @Query("SELECT g FROM GoalNetZero g WHERE g.id = :id AND g.memberId = :memberId")
    Optional<GoalNetZero> findByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    /**
     * 특정 회원의 NetZero 목표 중 연도 범위에 따른 데이터 조회 (그래프 생성에 활용)
     */
    @Query("SELECT g FROM GoalNetZero g WHERE g.memberId = :memberId AND g.baseYear <= :endYear AND g.targetYear >= :startYear")
    List<GoalNetZero> findByMemberIdAndYearRange(
            @Param("memberId") Long memberId,
            @Param("startYear") int startYear,
            @Param("endYear") int endYear
    );

    /**
     * 특정 목표 ID의 기본값 및 목표값만 조회 (성능 최적화)
     */
    @Query("SELECT g.baseYearEmission, g.targetYearEmission FROM GoalNetZero g WHERE g.id = :id")
    Optional<Object[]> findEmissionValuesById(@Param("id") Long id);
}