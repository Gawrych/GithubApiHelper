package com.githubhelper.RepositoryFetcher.repositoryfetcher.connector;

import com.githubhelper.RepositoryFetcher.repositoryfetcher.exception.NotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@NoArgsConstructor
public class ApiConnectorImpl implements ApiConnector {

    public String getJsonFromAPI(String url) {

        return WebClient.create(url)
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(
                        WebClientResponseException.NotFound.class,
                        throwable -> {throw new NotFoundException("Not Found from GET " + url);})
                .block();
    }
}
