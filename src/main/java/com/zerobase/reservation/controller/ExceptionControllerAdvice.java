package com.zerobase.reservation.controller;

import com.zerobase.reservation.exception.CustomException;
import com.zerobase.reservation.exception.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * CustomException handle 클래스
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ExceptionResponse> customRequestException(
        final CustomException exception) {

        log.warn("api Exception : {}", exception.getErrorCode());

        return ResponseEntity.badRequest()
            .body(new ExceptionResponse(exception.getMessage(), exception.getErrorCode()));

    }

    @Getter
    private static class ExceptionResponse {

        private final String message;
        private final ErrorCode code;

        public ExceptionResponse(final String message, final ErrorCode errorCode) {
            this.message = message;
            this.code = errorCode;
        }

        @Override
        public String toString() {
            return String.format("{ %s : %s }", message, code.getDetail());
        }

    }

}
