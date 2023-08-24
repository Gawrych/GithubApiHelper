package com.githubhelper.RepositoryFetcher.repositoryfetcher.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionTemplate> handleNotFoundException(NotFoundException ex) {
        ExceptionTemplate exception = new ExceptionTemplate(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
}
