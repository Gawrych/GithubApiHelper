package com.githubhelper.RepositoryFetcher.repositoryfetcher.connector;

import com.githubhelper.RepositoryFetcher.repositoryfetcher.exception.NotFoundException;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;


@ExtendWith(MockitoExtension.class)
@DisplayName("The API connector")
class ApiConnectorImplTest {

    private static MockWebServer server;
    
    private String url;

    private ApiConnectorImpl apiConnector;

    @BeforeAll
    static void setupServer() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @BeforeEach
    void initialize() {
        url = String.format("http://localhost:%s", server.getPort());
        apiConnector = new ApiConnectorImpl();
    }

    @AfterAll
    static void shutdownServer() throws IOException {
        server.shutdown();
    }

    @Test
    @DisplayName("retrieves the content from the provided URL as a string")
    void GetJsonFromAPI_WhenPassUrlParameter_ThenRetrieveContentAsString() {
        // Given
        String expectedJson = "[{\"name\": \"Bob\"}]";

        server.enqueue(new MockResponse()
                .setBody(expectedJson)
                .addHeader("Content-Type", "application/json"));

        // When
        String actualJson = apiConnector.getJsonFromAPI(url);

        // Then
        then(actualJson).isEqualTo(expectedJson);
    }

    @Nested
    @DisplayName("sends the correct request")
    class SendsCorrectRequest {

        @Test
        @DisplayName("when the request URL is equal to the given URL")
        void GetJsonFromAPI_WhenRequestURLIsEqualToTheGivenURL_ThenRequestIsCorrect() throws InterruptedException {
            // Given
            server.enqueue(new MockResponse());

            // When
            apiConnector.getJsonFromAPI(url);

            // Then
            RecordedRequest request = server.takeRequest();
            then(request.getRequestUrl()).isEqualTo(HttpUrl.get(url));
        }

        @Test
        @DisplayName("when the request method is GET")
        void GetJsonFromAPI_WhenTheRequestMethodIsGET_ThenRequestIsCorrect() throws InterruptedException {
            // Given
            server.enqueue(new MockResponse());

            // When
            apiConnector.getJsonFromAPI(url);

            // Then
            RecordedRequest request = server.takeRequest();
            then(request.getMethod()).isEqualTo(RequestMethod.GET.toString());
        }

        @Test
        @DisplayName("when the 'Accept' in request header is set to 'application/json'")
        void GetJsonFromAPI_WhenRequestAcceptIsApplicationJson_ThenRequestIsCorrect() throws InterruptedException {
            // Given
            server.enqueue(new MockResponse());

            // When
            apiConnector.getJsonFromAPI(url);

            // Then
            RecordedRequest request = server.takeRequest();
            then(request.getHeader("Accept")).isEqualTo(MediaType.APPLICATION_JSON.toString());
        }
    }

    @Test
    @DisplayName("throws NotFoundException when response code from URL is 404")
    void GetJsonFromAPI_WhenResponseCodeIs404_ThenThrowsNotFoundException() {
        // Given
        server.enqueue(new MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value()));

        // When
        final Throwable throwable = catchThrowable(() -> apiConnector.getJsonFromAPI(url));

        // Then
        then(throwable).isInstanceOf(NotFoundException.class);
    }
}