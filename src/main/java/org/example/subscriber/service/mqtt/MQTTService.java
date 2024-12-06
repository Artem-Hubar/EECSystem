package org.example.subscriber.service.mqtt;

import java.util.Set;

public class MQTTService {
    private MQTTRepository mqttRepository;

    public MQTTService() {
        this.mqttRepository = new MQTTRepository();
    }

    public Set<String> getTopics() {
        try {
            String endpoint = "topics";
            String responseBody = mqttRepository.getResponseBodyByEndPoint(endpoint);
            ResponseTopicUnwraper responseTopicUnwraper = new ResponseTopicUnwraper();
            return responseTopicUnwraper.unwrapResponse(responseBody);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
