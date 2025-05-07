package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.StrategyRiskIdentificationRequest;
import com.nsmm.esg.tcfdservice.entity.StrategyRiskIdentification;
import com.nsmm.esg.tcfdservice.repository.StrategyRiskIdentificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StrategyRiskIdentificationService {

    private final StrategyRiskIdentificationRepository strategyRiskIdentificationRepository;

    // 리스크 호출
    public List<StrategyRiskIdentificationRequest> getRisks(Long memberId) {
        return strategyRiskIdentificationRepository.findById(memberId).stream()
                .map(StrategyRiskIdentificationRequest::fromEntity)
                .toList();
    }
    //----------------------------------------------------------------------------------------------------------------

    // 리스크 생성
    public Long createRisk(Long memberId, StrategyRiskIdentificationRequest request) {
        return strategyRiskIdentificationRepository.save(request.toEntity(memberId)).getId();
    }
    //----------------------------------------------------------------------------------------------------------------

    // 리스크 수정
    @Transactional
    public void updateRisk(Long memberId, Long id, StrategyRiskIdentificationRequest request) {
        StrategyRiskIdentification risk = strategyRiskIdentificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("수정할 리스크가 없거나 권한이 없습니다."));

        if (!risk.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 리스크에 대한 권한이 없습니다.");
        }

        risk.updateFromDto(request);
    }
    //----------------------------------------------------------------------------------------------------------------

    // 리스크 삭제
    public void deleteRisk(Long memberId, Long id) {
        StrategyRiskIdentification risk = strategyRiskIdentificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("삭제할 리스크가 없거나 권한이 없습니다."));

        if (!risk.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 리스크에 대한 권한이 없습니다.");
        }
        strategyRiskIdentificationRepository.delete(risk);
    }
    //----------------------------------------------------------------------------------------------------------------



}
