package com.mtv.data.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionEndpointHandler {

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleFeignException(FeignException fe) {

        log.error("Feign Error {}", fe.getMessage(), fe);

        return "Internal Server Error: " + fe.getMessage();

    }

}
