package com.githubhelper.RepositoryFetcher.repositoryfetcher.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@DisplayName("The API exception handler")
class ApiExceptionHandlerTest {

    @Mock
    private HttpMediaTypeNotAcceptableException httpMediaTypeNotAcceptableException;

    @Mock
    private NotFoundException notFoundException;

    @InjectMocks
    private ApiExceptionHandler apiExceptionHandler;

    @Nested
    @DisplayName("correctly catches HttpMediaTypeNotAcceptableException")
    class CatchesHttpMediaTypeNotAcceptableException {

        @Test
        @DisplayName("returning a response entity with a NOT_ACCEPTABLE status code and a message in the body")
        void HandleMediaException_WhenCatchHttpMediaTypeNotAcceptableException_ThenReturnResponseEntityWithMessageAndStatusCodeInTheBody() {
            // Given
            String exceptionMessage = "Exception message";
            ExceptionTemplate expectedBody = new ExceptionTemplate(HttpStatus.NOT_ACCEPTABLE.value(), exceptionMessage);

            given(httpMediaTypeNotAcceptableException.getMessage()).willReturn(exceptionMessage);

            // When
            ResponseEntity<ExceptionTemplate> response = apiExceptionHandler
                    .handleMediaTypeNotAcceptableException(httpMediaTypeNotAcceptableException);

            // Then
            then(response.getBody()).isNotNull();
            then(response.getBody()).isEqualTo(expectedBody);
        }

        @Test
        @DisplayName("returning a response entity with the the content type in the header set to APPLICATION_JSON")
        void HandleMediaException_WhenCatchHttpMediaTypeNotAcceptableException_ThenReturnResponseEntityWithApplicationJsonContentType() {
            // Given
            given(httpMediaTypeNotAcceptableException.getMessage()).willReturn("");

            // When
            ResponseEntity<ExceptionTemplate> response = apiExceptionHandler
                    .handleMediaTypeNotAcceptableException(httpMediaTypeNotAcceptableException);

            // Then
            then(response.getHeaders()).isNotNull();
            then(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        }

        @Test
        @DisplayName("returning a NOT_ACCEPTABLE HTTP status")
        void HandleMediaException_WhenCatchHttpMediaTypeNotAcceptableException_ThenReturnNotAcceptableHttpStatus() {
            // Given
            given(httpMediaTypeNotAcceptableException.getMessage()).willReturn("");

            // When
            ResponseEntity<ExceptionTemplate> response = apiExceptionHandler
                    .handleMediaTypeNotAcceptableException(httpMediaTypeNotAcceptableException);

            // Then
            then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Nested
    @DisplayName("correctly catches NotFoundException")
    class CatchesNotFoundException {

        @Test
        @DisplayName("returning a response entity with a NOT_FOUND status code and a message in the body")
        void HandleNotFoundException_WhenCatchNotFoundException_ThenReturnResponseEntityWithMessageAndStatusCodeInTheBody() {
            // Given
            String exceptionMessage = "Exception message";
            ExceptionTemplate expectedBody = new ExceptionTemplate(HttpStatus.NOT_FOUND.value(), exceptionMessage);

            given(notFoundException.getMessage()).willReturn(exceptionMessage);

            // When
            ResponseEntity<ExceptionTemplate> response = apiExceptionHandler
                    .handleNotFoundException(notFoundException);

            // Then
            then(response.getBody()).isNotNull();
            then(response.getBody()).isEqualTo(expectedBody);
        }

        @Test
        @DisplayName("returning a NOT_FOUND HTTP status")
        void HandleNotFoundException_WhenCatchNotFoundException_ThenReturnNotFoundHttpStatus() {
            // Given
            given(notFoundException.getMessage()).willReturn("");

            // When
            ResponseEntity<ExceptionTemplate> response = apiExceptionHandler
                    .handleNotFoundException(notFoundException);

            // Then
            then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}