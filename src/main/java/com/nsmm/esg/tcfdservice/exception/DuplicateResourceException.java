package com.nsmm.esg.tcfdservice.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends TcfdException {
    public DuplicateResourceException(String resourceName) {
        super(resourceName + "는 이미 존재합니다.",
                HttpStatus.CONFLICT,
                "DUPLICATE_RESOURCE");
    }
}