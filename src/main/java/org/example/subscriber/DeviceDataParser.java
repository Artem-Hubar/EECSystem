package org.example.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Device;
import org.example.entity.DeviceType;
import org.example.factory.DeviceFactory;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class DeviceDataParser {
    public Optional<Device>  parserData(String[] topicParts, String message){
        DeviceType measurement = DeviceType.fromType(topicParts[1]);
        if (measurement != null){
            Optional<Device> device = DeviceFactory.getDevice(measurement);
            device.ifPresent(value -> setDeviceData(value, topicParts, message));
            return device;
        }else {
            throw new NoSuchElementException();
        }
    }

    protected void setDeviceData(Device device, String[] topicParts, String message) {
        device.setSensorId(topicParts[2]);
        Map<String, String> messageData = getMessageData(message);
        setDeviceSpecificData(device, messageData);
    }

    protected abstract void setDeviceSpecificData(Device device, Map<String, String> messageData);

    protected Map<String, String> getMessageData(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> messageData;
        try {
            messageData =  objectMapper.readValue(message, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return messageData;
    }
}
