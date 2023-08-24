package com.githubhelper.RepositoryFetcher.repositoryfetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Branch;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Repository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.then;
@ExtendWith(MockitoExtension.class)
@DisplayName("The Repository deserializer")
class RepositoryDeserializerTest {

    @Mock
    private DeserializationContext deserializationContext;

    @InjectMocks
    private RepositoryDeserializer repositoryDeserializer;

    @Test
    @DisplayName("correctly deserializes json to Repository model")
    void Deserialize_WhenPassJsonInJsonParser_ThenDeserializeToRepositoryModel() throws IOException {
        // Given
        String json = "{\"name\":\"AAA\",\"owner\":{\"login\":\"BBB\"},\"fork\":true}";
        JsonParser jsonNode = new ObjectMapper().createParser(json);
        Repository expectedRepository = new ObjectMapper().readValue(json, Repository.class);

        // When
        Repository actualRepository = repositoryDeserializer.deserialize(jsonNode, deserializationContext);

        // Then
        then(actualRepository).isEqualTo(expectedRepository);
    }
}