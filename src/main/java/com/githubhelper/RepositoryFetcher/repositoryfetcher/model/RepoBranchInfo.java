package com.githubhelper.RepositoryFetcher.repositoryfetcher.model;

import java.util.List;

public record RepoBranchInfo(String repositoryName, String ownerLogin, List<Branch> branches) {}
