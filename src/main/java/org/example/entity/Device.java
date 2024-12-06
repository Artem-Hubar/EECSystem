package org.example.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class   Device {
    private String sensorId;

    public abstract String toInfluxDBLineProtocol();

    public abstract String getMqttPayload();

    public abstract String getMqttTopic();
}
