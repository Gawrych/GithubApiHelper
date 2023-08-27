package com.githubhelper.RepositoryFetcher.repositoryfetcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Branch(
        @JsonProperty("name")
        String name,

        @JsonProperty("commit")
        Commit commit)
{}
