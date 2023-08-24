package com.githubhelper.RepositoryFetcher.repositoryfetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Repository;

import java.io.IOException;

public class RepositoryDeserializer extends JsonDeserializer<Repository> {

    @Override
    public Repository deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        return new Repository(
                jsonNode.get("name").asText(),
                jsonNode.get("owner").get("login").asText(),
                jsonNode.get("fork").asBoolean());
    }
}
