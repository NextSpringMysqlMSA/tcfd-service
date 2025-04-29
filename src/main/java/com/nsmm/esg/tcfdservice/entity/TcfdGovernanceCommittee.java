// src/main/java/com/nsmm/esg.tcfdservice.entity.TcfdGovernanceCommittee.java
package com.nsmm.esg.tcfdservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tcfd_governance_committee")
public class TcfdGovernanceCommittee {

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

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
