package com.githubhelper.RepositoryFetcher.repositoryfetcher.service;

import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Branch;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Repository;

import java.util.List;

public interface GithubApiAccessor {

    List<Repository> getAllRepositories(String username);

    List<Branch> getAllBranches(String username, String repositoryName);
}
