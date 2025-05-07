package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.GovernanceEducationRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "governance_education")
public class GovernanceEducation implements Identifiable<Long>{

    @Override
    public Long getId() {
        return this.id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 100)
    private String educationTitle; // 교육 제목

    @Column(nullable = false)
    private LocalDate educationDate; // 교육 일자 (날짜만)

    @Column(nullable = false)
    private Integer participantCount; // 참석자 수

    @Lob
    private String content; // 교육 내용

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateFromRequest(GovernanceEducationRequest request) {
        this.educationTitle = request.getEducationTitle();
        this.educationDate = request.getEducationDate();
        this.participantCount = request.getParticipantCount();
        this.content = request.getContent();
    }


}
