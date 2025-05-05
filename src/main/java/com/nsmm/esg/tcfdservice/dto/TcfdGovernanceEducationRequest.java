package com.nsmm.esg.tcfdservice.dto;

import com.nsmm.esg.tcfdservice.entity.TcfdGovernanceEducation;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TcfdGovernanceEducationRequest {

    private String educationTitle;    // 교육 제목
    private LocalDate educationDate;  // 교육 일자 (yyyy-MM-dd 형식으로 입력)
    private Integer participantCount; // 참석자 수
    private String content;           // 교육 주요 내용

    // DTO → Entity 변환 메서드
    public TcfdGovernanceEducation toEntity(Long memberId) {
        return TcfdGovernanceEducation.builder()
                .memberId(memberId)
                .educationTitle(this.educationTitle)
                .educationDate(this.educationDate)
                .participantCount(this.participantCount)
                .content(this.content)
                .build();
    }
    // Entity → DTO 변환 메서드
    public static TcfdGovernanceEducationRequest fromEntity(TcfdGovernanceEducation entity) {
        TcfdGovernanceEducationRequest dto = new TcfdGovernanceEducationRequest();
        dto.educationTitle = entity.getEducationTitle();
        dto.educationDate = entity.getEducationDate();
        dto.participantCount = entity.getParticipantCount();
        dto.content = entity.getContent();
        return dto;
    }
}
