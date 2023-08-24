package com.githubhelper.RepositoryFetcher.repositoryfetcher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.connector.ApiConnector;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Branch;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GithubApiAccessorImpl implements GithubApiAccessor {

    @Value("${github.api.repositories}")
    private String repositoriesEndpoint;

    @Value("${github.api.branches}")
    private String branchesEndpoint;

    private final ObjectMapper objectMapper;

    private final ApiConnector apiConnector;

    @Override
    public List<Repository> getAllRepositories(String username) {
        String repos = apiConnector.getJsonFromAPI(String.format(repositoriesEndpoint, username));

        return deserializeJsonToModel(repos, Repository[].class);
    }

    @Override
    public List<Branch> getAllBranches(String username, String repositoryName) {
        String branches = apiConnector.getJsonFromAPI(String.format(branchesEndpoint, username, repositoryName));

        return deserializeJsonToModel(branches, Branch[].class);
    }

    private <T> List<T> deserializeJsonToModel(String json, Class<T[]> type) {
        try {
            return Arrays.stream(objectMapper.readValue(json, type)).toList();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
