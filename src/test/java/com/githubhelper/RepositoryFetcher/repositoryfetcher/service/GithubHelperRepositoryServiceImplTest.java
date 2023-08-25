package com.githubhelper.RepositoryFetcher.repositoryfetcher.service;

import com.githubhelper.RepositoryFetcher.repositoryfetcher.exception.ApiExceptionHandler;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.exception.ExceptionTemplate;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.exception.NotFoundException;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Branch;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.RepoBranchInfo;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("The githubHelper repository service")
class GithubHelperRepositoryServiceImplTest {

    @Mock
    private GithubApiAccessor githubApiAccessor;

    @InjectMocks
    private GithubHelperRepositoryServiceImpl githubHelperRepositoryService;

    @Test
    @DisplayName("returns a list with merged repositories and their corresponding branches")
    void GetAllRepositoriesWithBranches_WhenGetListsFromAccessor_ThenReturnMergedListWithModel() {
        // Given
        List<Repository> repositories = new ArrayList<>(List.of(
                new Repository("AAA", "BBB", false)));
        given(githubApiAccessor.getAllRepositories(anyString())).willReturn(repositories);

        List<Branch> branches = new ArrayList<>(List.of(
                new Branch("CCC", "SHA123"),
                new Branch("DDD", "SHA123")));
        given(githubApiAccessor.getAllBranches(anyString(), anyString())).willReturn(branches);

        RepoBranchInfo expectedInfo =
                new RepoBranchInfo(repositories.get(0).name(), repositories.get(0).ownerLogin(), branches);

        // When
        List<RepoBranchInfo> result = githubHelperRepositoryService.getAllRepositoriesWithBranches("");

        // Then
        then(result.get(0)).isNotNull();
        then(result.get(0)).isEqualTo(expectedInfo);
    }

    @Test
    @DisplayName("returns a list without forks")
    void GetAllRepositoriesWithBranches_WhenRepositoryOnListIsAFork_ThenFilterItOut() {
        // Given
        List<Repository> repositories = new ArrayList<>(List.of(
                new Repository("AAA", "BBB", false),
                new Repository("GGG", "UUU", true)));
        given(githubApiAccessor.getAllRepositories(anyString())).willReturn(repositories);

        List<Branch> branches = new ArrayList<>(List.of(
                new Branch("CCC", "SHA123"),
                new Branch("DDD", "SHA123")));
        given(githubApiAccessor.getAllBranches(anyString(), anyString())).willReturn(branches);

        // When
        List<RepoBranchInfo> result = githubHelperRepositoryService.getAllRepositoriesWithBranches("");

        // Then
        then(result.size()).isEqualTo(1);
        then(result.get(0)).isNotNull();
    }
}