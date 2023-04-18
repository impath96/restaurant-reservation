package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.ErrorResponse;
import com.zerobase.reservation.exception.DuplicatedEmailException;
import com.zerobase.reservation.exception.LogInFailException;
import com.zerobase.reservation.exception.RestaurantNotFoundException;
import com.zerobase.reservation.exception.UserNotFoundException;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFound(UserNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedEmailException.class)
    public ErrorResponse handleDuplicatedEmail(DuplicatedEmailException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LogInFailException.class)
    public ErrorResponse handleLogInFail(LogInFailException exception) {
        return new ErrorResponse(exception.getMessage());
    }



}
