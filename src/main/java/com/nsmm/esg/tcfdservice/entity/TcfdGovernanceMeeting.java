package com.nsmm.esg.tcfdservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tcfd_governance_meeting")
public class TcfdGovernanceMeeting implements Identifiable<Long>{

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
    private String meetingName; // 회의 이름

    @Column(nullable = false)
    private LocalDate meetingDate; // 회의 날짜 (추가!)

    @Lob
    private String agenda; // 회의 안건

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
