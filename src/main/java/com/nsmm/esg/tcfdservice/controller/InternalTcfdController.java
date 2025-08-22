package com.nsmm.esg.tcfdservice.controller;

import com.nsmm.esg.tcfdservice.dto.NetZeroEmissionResponse;
import com.nsmm.esg.tcfdservice.dto.TcfdProgressResponse;
import com.nsmm.esg.tcfdservice.service.InternalProgressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internal/tcfd")
@RequiredArgsConstructor
@Slf4j
public class InternalTcfdController {

    private final InternalProgressService internalProgressService;

    @GetMapping("/progress")
    public TcfdProgressResponse getProgress(@RequestHeader("X-MEMBER-ID") Long memberId) {
        log.info("memberId = {}", memberId);
        return internalProgressService.getProgress(memberId);
    }

    @GetMapping("/progress/netzero")
    public List<NetZeroEmissionResponse> getNetZeroEmissionProgress(@RequestHeader("X-MEMBER-ID") Long memberId) {
        log.info("[NetZero] memberId = {}", memberId);
        return internalProgressService.getNetZeroEmissionProgress(memberId);
    }

}