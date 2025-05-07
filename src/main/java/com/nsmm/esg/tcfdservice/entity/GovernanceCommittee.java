package com.nsmm.esg.tcfdservice.entity;

import com.nsmm.esg.tcfdservice.dto.GovernanceCommitteeRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "governance_committee")
public class GovernanceCommittee implements Identifiable<Long> {

    @Override
    public Long getId() {
        return this.id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId; // 사용자 구분

    @Column(nullable = false, length = 100)
    private String committeeName; // 위원회 이름

    @Column(nullable = false, length = 100)
    private String memberName; // 구성원 이름

    @Column(nullable = false, length = 100)
    private String memberPosition; // 구성원 직책

    @Column(nullable = false, length = 100)
    private String memberAffiliation; // 구성원 소속

    @Lob
    private String climateResponsibility; // 기후 관련 역할 및 책임 설명

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

    public void updateFromRequest(GovernanceCommitteeRequest request) {
        this.committeeName = request.getCommitteeName();
        this.memberName = request.getMemberName();
        this.memberPosition = request.getMemberPosition();
        this.memberAffiliation = request.getMemberAffiliation();
        this.climateResponsibility = request.getClimateResponsibility();
    }

}

