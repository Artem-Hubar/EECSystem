package org.example.entity;

import javafx.beans.property.DoubleProperty;


import java.time.Instant;

public class CurrentLineSensor extends Device {
    private DoubleProperty voltage;
    private DoubleProperty current;

    @Override
    public String toInfluxDBLineProtocol() {
        String measurement = "current_line_sensor";
        String tags = "device=" + super.getSensorId();  // предполагаем, что есть метод getDeviceName() в родительском классе
        String fields = "voltage=" + voltage.get() + ",current=" + current.get();
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

    public double getCurrent() {
        return current.get();
    }

    public DoubleProperty currentProperty() {
        return current;
    }

    public void setCurrent(double current) {
        this.current.set(current);
    }

    public double getVoltage() {
        return voltage.get();
    }

    public DoubleProperty voltageProperty() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage.set(voltage);
    }
}
