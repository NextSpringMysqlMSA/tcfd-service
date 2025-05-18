package com.nsmm.esg.tcfdservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NetZeroEmissionResponse {
    private int year;
    private double emission;
}
