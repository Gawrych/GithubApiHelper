package com.githubhelper.RepositoryFetcher.repositoryfetcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Repository(
        @JsonProperty("name")
        String name,

        @JsonProperty("owner")
        Owner owner,

        @JsonProperty("fork")
        boolean isFork)
{}
