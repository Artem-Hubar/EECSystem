package org.example.subscriber.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Device;
import org.example.subscriber.DeviceDataHandler;

import java.util.Map;


public class CurrentLineDataHandler extends DeviceDataHandler {
    @Override
    protected void setDeviceData(Device device, String[] topicParts, String message) {
        super.setDeviceData(device, topicParts, message);
        CurrentLineSensor currentLineSensor = (CurrentLineSensor) device;
        Map<String, Integer> messageData = getMessageData(message);
        currentLineSensor.setCurrent(messageData.get("current"));
        currentLineSensor.setVoltage(messageData.get("voltage"));
    }

    private Map<String, Integer> getMessageData(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Integer> messageData;
        try {
            messageData =  objectMapper.readValue(message, new TypeReference<Map<String, Integer>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return messageData;
    }
}
