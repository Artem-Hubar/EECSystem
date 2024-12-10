package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;



public abstract class   Device {
    private final StringProperty sensorId = new SimpleStringProperty();


    @JsonIgnore
    public abstract String toInfluxDBLineProtocol();
    @JsonIgnore
    public abstract String getMqttPayload();
    @JsonIgnore
    public abstract String getMqttTopic();

    public String getSensorId() {
        return sensorId.get();
    }

    public void setSensorId(String sensorId) {
        this.sensorId.set(sensorId);
    }
}
