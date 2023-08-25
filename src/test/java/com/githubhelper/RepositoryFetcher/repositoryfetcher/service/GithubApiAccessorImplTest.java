package com.githubhelper.RepositoryFetcher.repositoryfetcher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.connector.ApiConnector;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Branch;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Repository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("The github API accessor")
class GithubApiAccessorImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ApiConnector apiConnector;

    @Captor
    private ArgumentCaptor<String> captor;

    @InjectMocks
    private GithubApiAccessorImpl githubApiAccessor;

    @Test
    @DisplayName("creates url by concatenating endpoint and username")
    void GetAllRepositories_WhenPassUsername_ThenConcatUrl() throws JsonProcessingException {
        // Given
        String repositoryEndpoint = "http://localhost:8080/%s";
        String username = "AAA";
        ReflectionTestUtils.setField(githubApiAccessor, "repositoriesEndpoint", repositoryEndpoint);

        given(apiConnector.getJsonFromAPI(anyString())).willReturn("");
        given(objectMapper.readValue(anyString(), eq(Repository[].class))).willReturn(new Repository[0]);

        // When
        githubApiAccessor.getAllRepositories(username);

        // Then
        verify(apiConnector).getJsonFromAPI(captor.capture());
        then(captor.getValue()).isEqualTo(String.format(repositoryEndpoint, username));
    }

    @Test
    @DisplayName("creates url by concatenating endpoint, username and repository name")
    void GetAllBranches_WhenPassUsernameAndRepositoryName_ThenConcatUrl() throws JsonProcessingException {
        // Given
        String branchEndpoint = "http://localhost:8080/%s/%s";
        String username = "AAA";
        String repositoryName = "BBB";
        ReflectionTestUtils.setField(githubApiAccessor, "branchesEndpoint", branchEndpoint);

        given(apiConnector.getJsonFromAPI(anyString())).willReturn("");
        given(objectMapper.readValue(anyString(), eq(Branch[].class))).willReturn(new Branch[0]);

        // When
        githubApiAccessor.getAllBranches(username, repositoryName);

        // Then
        verify(apiConnector).getJsonFromAPI(captor.capture());
        then(captor.getValue()).isEqualTo(String.format(branchEndpoint, username, repositoryName));
    }
}