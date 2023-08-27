package com.githubhelper.RepositoryFetcher.repositoryfetcher.service;

import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.RepoBranchInfo;

import java.util.List;

public interface GithubService {

    List<RepoBranchInfo> getAllRepositoriesWithBranches(String user);
}
