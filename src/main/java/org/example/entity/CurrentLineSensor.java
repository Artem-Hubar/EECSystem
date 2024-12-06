package org.example.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class CurrentLineSensor extends Device {
    private double voltage;
    private double current;

    @Override
    public String toInfluxDBLineProtocol() {
        String measurement = "current_line_sensor";
        String tags = "device=" + super.getSensorId();  // предполагаем, что есть метод getDeviceName() в родительском классе
        String fields = "voltage=" + voltage + ",current=" + current;
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        return measurement + "," + tags + " " + fields + " " + timestamp;
    }

    @Override
    public String getMqttPayload() {
        return "{\"voltage\":" + voltage + ",\"current\":" + current + "}";
    }


    @Override
    public String getMqttTopic() {
        return "client/current_line_sensor/" + super.getSensorId();
    }

}
