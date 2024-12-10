package org.example.rule.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rule.entity.Rule;

public class RuleSerializer {
    public String serializeRule(Rule rule){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(rule);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
