package com.example.shopstore.controller.advise;

import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public String notFoundExceptionHandler(Exception ex) {
        return "404";
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public String numberFormatExceptionHandler(Exception ex) {
        return "404";
    }

    @ExceptionHandler(value = {Exception.class})
    public String allExceptionHandler(Exception ex) {
        return "404";
    }

}
