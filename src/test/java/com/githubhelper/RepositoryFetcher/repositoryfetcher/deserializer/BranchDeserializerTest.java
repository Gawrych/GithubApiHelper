package com.githubhelper.RepositoryFetcher.repositoryfetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Branch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("The Branch deserializer")
class BranchDeserializerTest {

    @Mock
    private DeserializationContext deserializationContext;

    @InjectMocks
    private BranchDeserializer branchDeserializer;

    @Test
    @DisplayName("correctly deserializes json to Branch model")
    void Deserialize_WhenPassJsonInJsonParser_ThenDeserializeToBranchModel() throws IOException {
        // Given
        String json = "{\"name\":\"AAA\",\"commit\":{\"sha\":\"BBB\"}}";
        JsonParser jsonNode = new ObjectMapper().createParser(json);
        Branch expectedBranch = new ObjectMapper().readValue(json, Branch.class);

        // When
        Branch actualBranch = branchDeserializer.deserialize(jsonNode, deserializationContext);

        // Then
        then(actualBranch).isEqualTo(expectedBranch);
    }
}