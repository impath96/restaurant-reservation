package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.ErrorResponse;
import com.zerobase.reservation.exception.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ErrorResponse handleRestaurantNotFound(RestaurantNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

}
