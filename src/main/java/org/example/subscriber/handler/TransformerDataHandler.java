package org.example.subscriber.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Device;
import org.example.entity.Transformer;
import org.example.subscriber.DeviceDataHandler;

import java.util.HashMap;
import java.util.Map;

public class TransformerDataHandler extends DeviceDataHandler {

    @Override
    protected void setDeviceData(Device device, String[] topicParts, String message) {
        super.setDeviceData(device, topicParts, message);
        Transformer transporter = (Transformer) device;
        Map<String, String> messageData = getMessageData(message);
        transporter.setTurnsRation(Double.valueOf(messageData.get("turnsRation")));
    }

    private Map<String, String> getMessageData(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> messageData = new HashMap<>();
        try {
            messageData =  objectMapper.readValue(message, new TypeReference<Map<String, String>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return messageData;
    }
}
