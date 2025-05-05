package com.nsmm.esg.tcfdservice.service;

import com.nsmm.esg.tcfdservice.dto.RiskIdentificationRequest;
import com.nsmm.esg.tcfdservice.entity.RiskIdentification;
import com.nsmm.esg.tcfdservice.repository.RiskIdentificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiskIdentificationService {

    private final RiskIdentificationRepository riskIdentificationRepository;

    @Transactional
    public Long createRisk(Long memberId, RiskIdentificationRequest request) {
        RiskIdentification risk = request.toEntity(memberId);
        riskIdentificationRepository.save(risk);
        return risk.getId();
    }

    @Transactional
    public void updateRisk(Long memberId, Long id, RiskIdentificationRequest request) {
        RiskIdentification risk = riskIdentificationRepository.findById(id)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리스크 ID입니다."));
        if (!risk.getMemberId().equals(memberId)) {
            throw new SecurityException("리스크 수정 권한이 없습니다.");
        }

        // 업데이트 로직 (setter 사용 없이 update 메서드 추천)
        risk.updateFromDto(request);
    }

    @Transactional
    public void deleteRisk(Long memberId, Long id) {
        RiskIdentification risk = riskIdentificationRepository.findById(id)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리스크 ID입니다."));
        if (!risk.getMemberId().equals(memberId)) {
            throw new SecurityException("리스크 삭제 권한이 없습니다.");
        }

        riskIdentificationRepository.delete(risk);
    }
}
