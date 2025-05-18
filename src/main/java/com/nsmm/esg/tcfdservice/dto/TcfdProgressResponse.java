package com.nsmm.esg.tcfdservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TcfdProgressResponse {
    private final int totalCount;
    private final int completedCount;
    private final int inCompletedCount;
    private final int completedRate;
}