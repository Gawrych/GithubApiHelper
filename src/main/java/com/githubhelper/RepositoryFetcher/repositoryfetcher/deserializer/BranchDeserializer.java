package com.githubhelper.RepositoryFetcher.repositoryfetcher.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.githubhelper.RepositoryFetcher.repositoryfetcher.model.Branch;

import java.io.IOException;


public class BranchDeserializer extends JsonDeserializer<Branch> {

    @Override
    public Branch deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        return new Branch(jsonNode.get("name").asText(), jsonNode.get("commit").get("sha").asText());
    }
}
