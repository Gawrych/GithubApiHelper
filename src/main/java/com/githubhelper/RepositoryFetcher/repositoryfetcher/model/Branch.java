package com.githubhelper.RepositoryFetcher.repositoryfetcher.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.deserializer.BranchDeserializer;

@JsonDeserialize(using = BranchDeserializer.class)
public record Branch(String name, String sha) {}
