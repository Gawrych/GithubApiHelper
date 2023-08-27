package com.githubhelper.RepositoryFetcher.repositoryfetcher.service;

import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Branch;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.RepoBranchInfo;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GithubServiceImpl implements GithubService {

    @Value("${github.api.repositories}")
    private String repositoriesEndpoint;

    @Value("${github.api.branches}")
    private String branchesEndpoint;

    private final WebClient webClient;

    public GithubServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public List<RepoBranchInfo> getAllRepositoriesWithBranches(String username) {
        List<Repository> repositories = getRepositoriesFromAPI(username);

        return repositories.stream()
                .filter(repository -> !repository.isFork())
                .map(repository -> {
                    List<Branch> branches = getBranchesFromAPI(username, repository.name());
                    return new RepoBranchInfo(repository.name(), repository.owner(), branches);
                }).toList();
    }

    private List<Repository> getRepositoriesFromAPI(String username) {

        return webClient.get()
                .uri(repositoriesEndpoint, username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Repository>>(){})
                .block();
    }

    private List<Branch> getBranchesFromAPI(String username, String repositoryName) {

        return webClient.get()
                .uri(branchesEndpoint, username, repositoryName)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Branch>>(){})
                .block();
    }
}
