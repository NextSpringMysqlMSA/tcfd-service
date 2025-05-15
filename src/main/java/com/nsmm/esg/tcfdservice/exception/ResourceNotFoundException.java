package com.nsmm.esg.tcfdservice.exception;

import org.springframework.http.HttpStatus;

/**
 * 요청한 리소스를 찾을 수 없을 때 발생하는 예외
 */
public class ResourceNotFoundException extends TcfdException {

    // 단일 메시지로 처리할 때 사용
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }

    // 리소스명과 ID를 받아 자동 메시지 구성할 때 사용
    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " ID " + id + " 를 찾을 수 없습니다.",
                HttpStatus.NOT_FOUND,
                "RESOURCE_NOT_FOUND");
    }
}