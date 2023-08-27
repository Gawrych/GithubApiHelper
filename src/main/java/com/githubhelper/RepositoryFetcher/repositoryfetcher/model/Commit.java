package com.githubhelper.RepositoryFetcher.repositoryfetcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public record Commit(

        @JsonValue
        @JsonProperty("sha")
        String sha) {}