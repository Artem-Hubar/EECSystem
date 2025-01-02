package org.example.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


import java.time.Instant;
import java.util.Objects;

public class CurrentLineSensor extends Device {
    private final DoubleProperty voltage = new SimpleDoubleProperty();
    private final DoubleProperty current = new SimpleDoubleProperty();

    public CurrentLineSensor() {
        deviceType = DeviceType.CURRENT_LINE_SENSOR;
    }

    public CurrentLineSensor(String deviceId, Double voltage, Double current) {
        setSensorId(deviceId);
        setCurrent(current);
        this.voltage.set(voltage);
        deviceType = DeviceType.CURRENT_LINE_SENSOR;
    }

    public double getCurrent() {
        return current.get();
    }

    public void setCurrent(double current) {
        this.current.set(current);
    }

    public double getVoltage() {
        return voltage.get();
    }

    public void setVoltage(double voltage) {
        this.voltage.set(voltage);
    }

    @Override
    public String toString() {
        return "CurrentLineSensor{" +
                "idDevice=" + getSensorId() +
                "current=" + getCurrent() +
                ", voltage=" + getVoltage() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentLineSensor that = (CurrentLineSensor) o;
        return Objects.equals(getVoltage(), that.getVoltage()) &&
                Objects.equals(getCurrent(), that.getCurrent()) &&
                Objects.equals(getSensorId(), that.getSensorId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(voltage, current);
    }
}
