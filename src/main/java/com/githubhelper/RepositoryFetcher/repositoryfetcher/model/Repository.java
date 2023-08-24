package com.githubhelper.RepositoryFetcher.repositoryfetcher.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.deserializer.RepositoryDeserializer;

@JsonDeserialize(using = RepositoryDeserializer.class)
public record Repository(String name, String ownerLogin, boolean isFork) {}
