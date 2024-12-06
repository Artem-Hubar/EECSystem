package org.example.subscriber.service.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseTopicUnwraper {
    public Set<String> unwrapResponse (String responseBody){
        Map<String, Object> responseBodyUnwrapped = getMessageData(responseBody);
        List<Object> nodes = (List<Object>) responseBodyUnwrapped.get("data");
        return nodes.stream().map(x -> ((Map<String, String>) x).get("topic")).filter(x -> !x.equals("client/#")).collect(Collectors.toSet());
    }

    private Map<String, Object> getMessageData(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> messageData;
        try {
            messageData = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return messageData;
    }
}
