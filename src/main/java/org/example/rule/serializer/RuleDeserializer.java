package org.example.rule.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rule.entity.Rule;

public class RuleDeserializer {

    public Rule deserialize(String ruleSerialized) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(ruleSerialized, Rule.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}