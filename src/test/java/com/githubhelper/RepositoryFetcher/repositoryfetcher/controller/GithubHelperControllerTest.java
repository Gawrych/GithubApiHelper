package com.githubhelper.RepositoryFetcher.repositoryfetcher.controller;

import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.RepoBranchInfo;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.service.GithubHelperRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@DisplayName("The githubHelper controller")
class GithubHelperControllerTest {

    private MockMvc mvc;

    @Mock
    private GithubHelperRepositoryService githubHelperRepositoryService;

    @InjectMocks
    private GithubHelperController githubHelperController;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(githubHelperController).build();
    }

    @Test
    @DisplayName("returns Ok status when service return a list")
    void GetRepoBranchInfo_WhenPassValidListAsAParameter_ThenReturnCorrectResult() throws Exception {
        // Given
        List<RepoBranchInfo> list = new ArrayList<>();

        given(githubHelperRepositoryService.getAllRepositoriesWithBranches(anyString())).willReturn(list);

        // When
        MockHttpServletResponse response = mvc
                .perform(get("/api/v1/repositories/username"))
                .andReturn()
                .getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("throws HttpMediaTypeNotAcceptableException when the 'Accept' in request header is different than 'application/json'")
    void GetRepoBranchInfo_WhenAcceptParamInHeaderIsNotJson_ThenThrowsAnException() throws Exception {
        // When
        Exception exception = mvc
                .perform(get("/api/v1/repositories/username").accept(MediaType.APPLICATION_XML))
                .andReturn()
                .getResolvedException();

        // Then
        then(exception).isInstanceOf(HttpMediaTypeNotAcceptableException.class);
    }
}