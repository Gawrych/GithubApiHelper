package com.githubhelper.RepositoryFetcher.repositoryfetcher.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record RepoBranchInfo(String repositoryName,
                             @JsonUnwrapped
                             Owner owner,

                             List<Branch> branches) {}
