package org.example.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import lombok.EqualsAndHashCode;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;


import java.time.Instant;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
public class Transformer extends Device {


    private final DoubleProperty turnsRatio = new SimpleDoubleProperty();

    public Transformer() {
    }

    public Transformer(String sensorId, Double turnsRatio) {
        super.setSensorId(sensorId);
        this.turnsRatio.set(turnsRatio);
    }


    public double getTurnsRation() {
        return turnsRatio.get();
    }

    public DoubleProperty turnsRatioProperty() {
        return turnsRatio;
    }

    public void setTurnsRation(Double turnsRatio) {
        this.turnsRatio.set(turnsRatio);
//        System.out.println(turnsRatio);
//        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("transformer");
//        mqttPublisher.writeData(this);

    }

    @Override
    public String getMqttPayload() {
        return "{\"turnsRation\":\"" + turnsRatio.get() + "\"}";
    }

    @Override
    public String toInfluxDBLineProtocol() {
        String measurement = "transporter";
        String tags = "device=" + super.getSensorId();
        String fields = "turnsRation=" + turnsRatio.get();
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        return measurement + "," + tags + " " + fields + " " + timestamp;
    }

    @Override
    public String getMqttTopic() {
        return "client/transformer/" + super.getSensorId() + "/data";
    }

    @Override
    public String toString() {
        return "Transformer{" +
                "id='" + super.getSensorId() +
                "' turnsRation='" + turnsRatio.get() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transformer that = (Transformer) o;
        return Objects.equals(getTurnsRation(), that.getTurnsRation()) && Objects.equals(getSensorId(), that.getSensorId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(turnsRatio);
    }


}
