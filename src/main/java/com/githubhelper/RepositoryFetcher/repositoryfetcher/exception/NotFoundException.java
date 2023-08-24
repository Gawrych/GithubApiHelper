package com.githubhelper.RepositoryFetcher.repositoryfetcher.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String errorMessage;

    public NotFoundException(String message) {
        super(message);
        this.errorMessage = message;
    }
}
