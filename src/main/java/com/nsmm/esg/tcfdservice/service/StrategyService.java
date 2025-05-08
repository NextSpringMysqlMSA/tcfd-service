package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.StrategyRiskIdentificationRequest;
import com.nsmm.esg.tcfdservice.dto.StrategyScenarioAnalysisRequest;
import com.nsmm.esg.tcfdservice.entity.StrategyRiskIdentification;
import com.nsmm.esg.tcfdservice.entity.StrategyScenarioAnalysis;
import com.nsmm.esg.tcfdservice.repository.StrategyRiskIdentificationRepository;
import com.nsmm.esg.tcfdservice.repository.StrategyScenarioAnalysisRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StrategyService {

    private final StrategyScenarioAnalysisRepository strategyScenarioAnalysisRepository;
    private final StrategyRiskIdentificationRepository strategyRiskIdentificationRepository;
    private final DamageEstimationService damageEstimationService;


    // 시나리오 분석 전체 조회 - 특정 사용자(memberId)의 시나리오 목록 반환
    public List<StrategyScenarioAnalysisRequest> getScenarios(Long memberId) {
        return strategyScenarioAnalysisRepository.findByMemberId(memberId).stream()
                .map(StrategyScenarioAnalysisRequest::fromEntity)
                .toList();
    }

    // 시나리오 분석 단건 조회
    public StrategyScenarioAnalysisRequest getScenarioById(Long memberId, Long id) {
        StrategyScenarioAnalysis scenario = strategyScenarioAnalysisRepository.findById(id)
                .filter(s -> s.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("해당 시나리오가 존재하지 않거나 권한이 없습니다."));

        return StrategyScenarioAnalysisRequest.fromEntity(scenario);
    }


    // 시나리오 분석 생성 - DTO를 엔티티로 변환 후 저장하고 생성된 ID 반환
    public Long createScenario(Long memberId, StrategyScenarioAnalysisRequest request) {
        // 1. DTO를 JPA Entity로 변환 (기본 필드 설정)
        StrategyScenarioAnalysis entity = request.toEntity(memberId);

        // 2. 만약 기후 유형이 '태풍'이라면 피해액을 계산한다
        //    - NetCDF 파일에서 풍속 값을 추출한 뒤
        //    - (풍속 / 70)^2 × 자산 가치 로 피해액 산출
        if ("태풍".equals(request.getClimate())) {
            double damage = damageEstimationService.calculateTyphoonDamage(
                    request.getScenario(),     // SSP 시나리오 (예: ssp126)
                    request.getBaseYear(),     // 기준 연도 (예: 2025)
                    request.getLatitude(),     // 위도
                    request.getLongitude(),    // 경도
                    request.getAssetValue()    // 자산 가치
            );

            // 계산된 피해액을 엔티티에 반영
            entity.setDamage(damage);
        }

        // 3. 엔티티를 DB에 저장하고 생성된 ID 반환
        return strategyScenarioAnalysisRepository.save(entity).getId();
    }



    // 시나리오 분석 수정 - 소유자 확인 후 엔티티 필드 업데이트
    @Transactional
    public void updateScenario(Long memberId, Long id, StrategyScenarioAnalysisRequest request) {
        // 1. 해당 ID의 시나리오 엔티티를 조회
        StrategyScenarioAnalysis scenario = strategyScenarioAnalysisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 시나리오가 존재하지 않습니다."));

        // 2. 로그인된 사용자(memberId)가 소유자인지 확인 (권한 확인)
        if (!scenario.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 시나리오에 대한 권한이 없습니다.");
        }

        // 3. 전달된 DTO 값으로 기존 엔티티 필드 업데이트
        scenario.updateFromDto(request);

        // 4. 태풍 시나리오일 경우, 피해액을 다시 계산해 반영
        //    - 수정 시 위도/경도/연도/시나리오 변경 가능성이 있으므로 재계산
        if ("태풍".equals(request.getClimate())) {
            double damage = damageEstimationService.calculateTyphoonDamage(
                    request.getScenario(),
                    request.getBaseYear(),
                    request.getLatitude(),
                    request.getLongitude(),
                    request.getAssetValue()
            );

            scenario.setDamage(damage);
        }
    }



    // 시나리오 분석 삭제 - 소유자 확인 후 삭제
    public void deleteScenario(Long memberId, Long id) {
        StrategyScenarioAnalysis scenario = strategyScenarioAnalysisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 시나리오가 존재하지 않습니다."));

        // 사용자 권한 확인
        if (!scenario.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 시나리오에 대한 권한이 없습니다.");
        }

        strategyScenarioAnalysisRepository.delete(scenario);
    }

    //=====================================================risk=======================================================

    // 리스크 식별 전체 조회 - 특정 사용자(memberId)의 리스크 목록 반환
    public List<StrategyRiskIdentificationRequest> getRisks(Long memberId) {
        return strategyRiskIdentificationRepository.findByMemberId(memberId).stream()
                .map(StrategyRiskIdentificationRequest::fromEntity)
                .toList();
    }

    // 리스크 부분 조회
    public StrategyRiskIdentificationRequest getRiskById(Long memberId, Long id) {
        StrategyRiskIdentification risk = strategyRiskIdentificationRepository.findById(id)
                .filter(r -> r.getMemberId().equals(memberId))
                .orElseThrow(() -> new IllegalArgumentException("해당 리스크가 존재하지 않거나 권한이 없습니다."));

        return StrategyRiskIdentificationRequest.fromEntity(risk);
    }

    // 리스크 식별 생성 - DTO를 엔티티로 변환 후 저장하고 생성된 ID 반환
    public Long createRisk(Long memberId, StrategyRiskIdentificationRequest request) {
        return strategyRiskIdentificationRepository.save(request.toEntity(memberId)).getId();
    }

    // 리스크 식별 수정 - 소유자 확인 후 엔티티 필드 업데이트
    @Transactional
    public void updateRisk(Long memberId, Long id, StrategyRiskIdentificationRequest request) {
        StrategyRiskIdentification risk = strategyRiskIdentificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("수정할 리스크가 없거나 권한이 없습니다."));

        // 사용자 권한 확인
        if (!risk.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 리스크에 대한 권한이 없습니다.");
        }

        // DTO 값으로 엔티티 업데이트
        risk.updateFromDto(request);
    }

    // 리스크 식별 삭제 - 소유자 확인 후 삭제
    public void deleteRisk(Long memberId, Long id) {
        StrategyRiskIdentification risk = strategyRiskIdentificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("삭제할 리스크가 없거나 권한이 없습니다."));

        // 사용자 권한 확인
        if (!risk.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 리스크에 대한 권한이 없습니다.");
        }

        strategyRiskIdentificationRepository.delete(risk);
    }
}
