package org.example.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class Transformer extends Device{
    private String turnsRation;

    @Override
    public String toInfluxDBLineProtocol() {
        String measurement = "transporter";
        String tags = "device=" + super.getSensorId();
        String fields = "turnsRation=" + turnsRation;
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        return measurement + "," + tags + " " + fields + " " + timestamp;
    }

    @Override
    public String getMqttPayload() {
        return "{\"turnsRation\":\"" + turnsRation + "\"}";
    }

    @Override
    public String getMqttTopic() {
        return "client/transformer/" + super.getSensorId()+"/data";
    }


}
