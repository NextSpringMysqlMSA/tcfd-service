package com.nsmm.esg.tcfdservice.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends TcfdException {
    public UnauthorizedAccessException(String message) {
        super(message, HttpStatus.FORBIDDEN, "UNAUTHORIZED_ACCESS");
    }
}
