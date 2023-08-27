package com.githubhelper.RepositoryFetcher.repositoryfetcher.controller;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.exception.ExceptionTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@DisplayName("The github repository fetcher integration test")
@ActiveProfiles("test")
@WireMockTest(httpPort = 8089)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubRepositoryFetcherIntegrationTest {

    @LocalServerPort
    private Integer port;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port + "/api/v1/").build();
    }

    @Test
    @DisplayName("returns a 'Not Found' exception when the user does not exist")
    void GetRepoBranchInfo_WhenUserNotFound_ThenReturnNotFoundException() {
        givenThat(get(urlEqualTo("/users/non-existing-user/repos")).willReturn(aResponse().withStatus(404)));

        client.get()
                .uri("repositories/non-existing-user")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ExceptionTemplate.class);
    }

    @Test
    @DisplayName("returns an empty list when the user does not have any repositories")
    void GetRepoBranchInfo_WhenUserDoesNotHaveAnyRepositories_ThenReturnEmptyList() {
        givenThat(get(urlEqualTo("/users/user-without-repositories/repos")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("[]")));

        client.get()
                .uri("repositories/user-without-repositories")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("[]");
    }

    @Test
    @DisplayName("returns the filled RepoBranchInfo models when the user exists and has repositories")
    void GetRepoBranchInfo_WhenUserExistsAndHasRepositories_ThenReturnListOfRepoBranchInfoModels() {
        String repositoryJson = "[{\"name\":\"repositoryName\",\"owner\":{\"login\":\"login\"}}]";

        String branchJson = "[{\"name\":\"branchName\",\"commit\":{\"sha\":\"sha\"}}]";

        String expectedBody = "[{\"repositoryName\":\"repositoryName\",\"login\":\"login\",\"branches\":" +
                "[{\"name\":\"branchName\",\"commit\":\"sha\"}]}]";

        stubFor(get(urlEqualTo("/users/existing-user-with-repositories/repos"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(repositoryJson)));

        stubFor(get(urlEqualTo("/repos/existing-user-with-repositories/repositoryName/branches"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(branchJson)));

        client.get()
                .uri("repositories/existing-user-with-repositories")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(expectedBody);
    }

    @Test
    @DisplayName("returns a HttpMediaTypeNotAcceptable exception when the 'Accept' in the request header " +
            "is different from 'application/json'")
    void GetRepoBranchInfo_WhenAcceptHeaderIsIncorrect_ThenReturnHttpMediaTypeNotAcceptableException() {
        client.get()
                .uri("repositories/user")
                .accept(MediaType.APPLICATION_XML)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE.value())
                .expectBody(ExceptionTemplate.class);
    }
}