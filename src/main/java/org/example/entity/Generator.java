package org.example.entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Objects;

public class Generator extends Device {
    BooleanProperty isWorking = new SimpleBooleanProperty();
    DoubleProperty voltage = new SimpleDoubleProperty();
    DoubleProperty current = new SimpleDoubleProperty();

    public Generator() {
        super.deviceType = DeviceType.GENERATOR;
    }

    public Generator(String id, double voltage, double current, boolean isWorking) {
        this.setSensorId(id);
        this.isWorking.set(isWorking);
        this.voltage.set(voltage);
        this.current.set(current);
        super.deviceType = DeviceType.GENERATOR;
    }
    public double getVoltage() {
        return voltage.get();
    }

    public void setVoltage(double voltage) {
        this.voltage.set(voltage);
    }

    public double getCurrent() {
        return current.get();
    }

    public void setCurrent(double current) {
        this.current.set(current);
    }

    public boolean getIsWorking() {
        return isWorking.get();
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking.set(isWorking);
    }

    @Override
    public String toString() {
        return "Generator{" +
                "id=" + super.getSensorId() +
                " voltage=" + getVoltage() +
                ", current=" + getCurrent() +
                ", isWorking=" + getIsWorking() +
                ", measurement=" + deviceType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Generator generator = (Generator) o;
        return isWorking == generator.isWorking &&
                Double.compare(getVoltage(), generator.getVoltage()) == 0 &&
                Double.compare(getCurrent(), generator.getCurrent()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isWorking, voltage, current);
    }
}
