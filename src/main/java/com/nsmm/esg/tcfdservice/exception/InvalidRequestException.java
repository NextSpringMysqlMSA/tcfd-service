package com.nsmm.esg.tcfdservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends TcfdException {
    public InvalidRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "INVALID_REQUEST");
    }
}