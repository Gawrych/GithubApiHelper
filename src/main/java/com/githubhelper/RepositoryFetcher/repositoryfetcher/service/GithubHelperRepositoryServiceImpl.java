package com.githubhelper.RepositoryFetcher.repositoryfetcher.service;

import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Branch;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Repository;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.RepoBranchInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubHelperRepositoryServiceImpl implements GithubHelperRepositoryService {

    private final GithubApiAccessor githubApiAccessor;

    @Override
    public List<RepoBranchInfo> getAllRepositoriesWithBranches(String username) {
        List<Repository> repositories = githubApiAccessor.getAllRepositories(username);

        return repositories.stream()
                .filter(repository -> !repository.isFork())
                .map(repository -> {
                    List<Branch> branches = githubApiAccessor.getAllBranches(username, repository.name());
                    return new RepoBranchInfo(repository.name(), repository.ownerLogin(), branches);
                }).toList();
    }
}
