package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.RiskIdentificationRequest;
import com.nsmm.esg.tcfdservice.entity.RiskIdentification;
import com.nsmm.esg.tcfdservice.repository.RiskIdentificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskIdentificationService {

    private final RiskIdentificationRepository riskRepository;

    // 리스크 호출
    public List<RiskIdentificationRequest> getRisks(Long memberId) {
        return riskRepository.findById(memberId).stream()
                .map(RiskIdentificationRequest::fromEntity)
                .toList();
    }
    //----------------------------------------------------------------------------------------------------------------

    // 리스크 생성
    public Long createRisk(Long memberId, RiskIdentificationRequest request) {
        return riskRepository.save(request.toEntity(memberId)).getId();
    }
    //----------------------------------------------------------------------------------------------------------------

    // 리스크 수정
    @Transactional
    public void updateRisk(Long memberId, Long id, RiskIdentificationRequest request) {
        RiskIdentification risk = riskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("수정할 리스크가 없거나 권한이 없습니다."));

        if (!risk.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 리스크에 대한 권한이 없습니다.");
        }

        risk.updateFromDto(request);
    }
    //----------------------------------------------------------------------------------------------------------------

    // 리스크 삭제
    public void deleteRisk(Long memberId, Long id) {
        RiskIdentification risk = riskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("삭제할 리스크가 없거나 권한이 없습니다."));

        if (!risk.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("해당 리스크에 대한 권한이 없습니다.");
        }
        riskRepository.delete(risk);
    }
    //----------------------------------------------------------------------------------------------------------------



}
