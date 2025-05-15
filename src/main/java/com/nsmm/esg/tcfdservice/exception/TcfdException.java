
package com.nsmm.esg.tcfdservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class TcfdException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;

    protected TcfdException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}