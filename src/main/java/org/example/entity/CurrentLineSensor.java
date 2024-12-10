package org.example.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.ToString;


import java.time.Instant;
import java.util.Objects;

public class CurrentLineSensor extends Device {
    private final DoubleProperty voltage = new SimpleDoubleProperty();
    private final DoubleProperty current = new SimpleDoubleProperty();

    public CurrentLineSensor() {
    }

    public CurrentLineSensor(String deviceId, Double voltage, Double current) {
        setSensorId(deviceId);
        this.current.set(current);
        this.voltage.set(voltage);
    }

    @Override
    public String toInfluxDBLineProtocol() {
        String measurement = "current_line_sensor";
        String tags = "device=" + super.getSensorId();  // предполагаем, что есть метод getDeviceName() в родительском классе
        String fields = "voltage=" + getVoltage() + ",current=" + getCurrent();
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        return measurement + "," + tags + " " + fields + " " + timestamp;
    }

    @Override
    public String getMqttPayload() {
        return "{\"voltage\":" + getVoltage() + ",\"current\":" + getCurrent() + "}";
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentLineSensor that = (CurrentLineSensor) o;
        return Objects.equals(getVoltage(), that.getVoltage()) && Objects.equals(getCurrent(), that.getCurrent());
    }

    @Override
    public String toString() {
        return "CurrentLineSensor{" +
                "idDevice=" + getSensorId() +
                "current=" + current +
                ", voltage=" + voltage +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(voltage, current);
    }
}
