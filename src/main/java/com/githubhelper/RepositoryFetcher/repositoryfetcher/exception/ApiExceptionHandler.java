package com.githubhelper.RepositoryFetcher.repositoryfetcher.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
@NoArgsConstructor
public class ApiExceptionHandler {

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ExceptionTemplate> handleMediaTypeNotAcceptableException(
            HttpMediaTypeNotAcceptableException ex) {

        ExceptionTemplate exception = new ExceptionTemplate(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(exception, headers, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ExceptionTemplate> handleNotFoundException(WebClientResponseException ex) {
        ExceptionTemplate exception = new ExceptionTemplate(ex.getStatusCode().value(), ex.getMessage());
        return new ResponseEntity<>(exception, ex.getStatusCode());
    }
}
