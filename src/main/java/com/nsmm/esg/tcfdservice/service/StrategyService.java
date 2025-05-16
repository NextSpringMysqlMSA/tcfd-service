package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.StrategyRiskIdentificationRequest;
import com.nsmm.esg.tcfdservice.dto.StrategyRiskIdentificationResponse;
import com.nsmm.esg.tcfdservice.dto.StrategyScenarioAnalysisRequest;
import com.nsmm.esg.tcfdservice.dto.StrategyScenarioAnalysisResponse;
import com.nsmm.esg.tcfdservice.entity.StrategyRiskIdentification;
import com.nsmm.esg.tcfdservice.entity.StrategyScenarioAnalysis;
import com.nsmm.esg.tcfdservice.exception.ResourceNotFoundException;
import com.nsmm.esg.tcfdservice.exception.DuplicateResourceException;
import com.nsmm.esg.tcfdservice.exception.UnauthorizedAccessException;
import com.nsmm.esg.tcfdservice.repository.StrategyRiskIdentificationRepository;
import com.nsmm.esg.tcfdservice.repository.StrategyScenarioAnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StrategyService {

    private final StrategyScenarioAnalysisRepository strategyScenarioAnalysisRepository;
    private final StrategyRiskIdentificationRepository strategyRiskIdentificationRepository;
    private final DamageEstimationService damageEstimationService;


    // 시나리오 분석 전체 조회 - 특정 사용자(memberId)의 시나리오 목록 반환
    public List<StrategyScenarioAnalysisResponse> getScenarios(Long memberId) {
        return strategyScenarioAnalysisRepository.findByMemberId(memberId).stream()
                .map(StrategyScenarioAnalysisResponse::fromEntity)
                .toList();
    }

    // 시나리오 분석 단건 조회
    public StrategyScenarioAnalysisResponse getScenarioById(Long memberId, Long id) {
        StrategyScenarioAnalysis scenario = strategyScenarioAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scenario", id));

        if (!scenario.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException("해당 시나리오에 대한 권한이 없습니다.");
        }

        return StrategyScenarioAnalysisResponse.fromEntity(scenario);
    }

    private static final Map<String, Double> REGION_PRECIPITATION_MAP = Map.of(
            "서울특별시", 1417.9,
            "부산광역시", 1576.7,
            "대구광역시", 1080.8,
            "인천광역시", 1207.4,
            "광주광역시", 1380.6,
            "대전광역시", 1351.2,
            "울산광역시", 1292.6,
            "세종특별자치시", 1204.9,
            "제주특별자치도", 1502.3
    );

    public Long createScenario(Long memberId, StrategyScenarioAnalysisRequest request) {
        if (strategyScenarioAnalysisRepository.existsByMemberIdAndScenarioAndBaseYear(memberId, request.getScenario(), request.getBaseYear())) {
            throw new DuplicateResourceException("시나리오 분석 항목");
        }
        StrategyScenarioAnalysis entity = request.toEntity(memberId);
        applyEstimatedDamage(entity, request);
        return strategyScenarioAnalysisRepository.save(entity).getId();
    }

    @Transactional
    public void updateScenario(Long memberId, Long id, StrategyScenarioAnalysisRequest request) {
        StrategyScenarioAnalysis scenario = strategyScenarioAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scenario", id));

        if (!scenario.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException("해당 시나리오에 대한 권한이 없습니다.");
        }

        scenario.updateFromDto(request);
        applyEstimatedDamage(scenario, request);
    }

    private void applyEstimatedDamage(StrategyScenarioAnalysis entity, StrategyScenarioAnalysisRequest request) {
        Long damage = switch (request.getClimate()) {
            case "태풍" -> damageEstimationService.calculateTyphoonDamage(
                    request.getScenario(), request.getBaseYear(),
                    request.getLatitude(), request.getLongitude(), request.getAssetValue()
            );
            case "홍수" -> damageEstimationService.calculateFloodDamage(
                    request.getScenario(), request.getBaseYear(),
                    request.getLatitude(), request.getLongitude(), request.getAssetValue()
            );
            case "폭염" -> damageEstimationService.calculateHeatwaveDamage(
                    request.getScenario(), request.getBaseYear(),
                    request.getLatitude(), request.getLongitude(), request.getAssetValue()
            );
            case "가뭄" -> damageEstimationService.calculateDroughtDamage(
                    request.getScenario(), request.getBaseYear(),
                    request.getLatitude(), request.getLongitude(), request.getAssetValue(),
                    REGION_PRECIPITATION_MAP.getOrDefault(request.getRegions(), 1200.0)
            );
            default -> null;
        };

        if (damage != null) {
            entity.setDamage(damage);
        }
    }


    // 시나리오 분석 삭제 - 소유자 확인 후 삭제
    public void deleteScenario(Long memberId, Long id) {
        StrategyScenarioAnalysis scenario = strategyScenarioAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scenario", id));

        // 사용자 권한 확인
        if (!scenario.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException("해당 시나리오에 대한 권한이 없습니다.");
        }

        strategyScenarioAnalysisRepository.delete(scenario);
    }

    //=====================================================risk=======================================================

    // 리스크 식별 전체 조회 - 특정 사용자(memberId)의 리스크 목록 반환
    public List<StrategyRiskIdentificationResponse> getRisks(Long memberId) {
        return strategyRiskIdentificationRepository.findByMemberId(memberId).stream()
                .map(StrategyRiskIdentificationResponse::fromEntity)
                .toList();
    }

    // 리스크 부분 조회
    public StrategyRiskIdentificationResponse getRiskById(Long memberId, Long id) {
        StrategyRiskIdentification risk = strategyRiskIdentificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk", id));

        if (!risk.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException("해당 리스크에 대한 권한이 없습니다.");
        }

        return StrategyRiskIdentificationResponse.fromEntity(risk);
    }

    // 리스크 식별 생성 - DTO를 엔티티로 변환 후 저장하고 생성된 ID 반환
    public Long createRisk(Long memberId, StrategyRiskIdentificationRequest request) {
        return strategyRiskIdentificationRepository.save(request.toEntity(memberId)).getId();
    }

    // 리스크 식별 수정 - 소유자 확인 후 엔티티 필드 업데이트
    @Transactional
    public void updateRisk(Long memberId, Long id, StrategyRiskIdentificationRequest request) {
        StrategyRiskIdentification risk = strategyRiskIdentificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk", id));

        // 사용자 권한 확인
        if (!risk.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException("해당 리스크에 대한 권한이 없습니다.");
        }

        // DTO 값으로 엔티티 업데이트
        risk.updateFromDto(request);
    }

    // 리스크 식별 삭제 - 소유자 확인 후 삭제
    public void deleteRisk(Long memberId, Long id) {
        StrategyRiskIdentification risk = strategyRiskIdentificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk", id));

        // 사용자 권한 확인
        if (!risk.getMemberId().equals(memberId)) {
            throw new UnauthorizedAccessException("해당 리스크에 대한 권한이 없습니다.");
        }

        strategyRiskIdentificationRepository.delete(risk);
    }
}
