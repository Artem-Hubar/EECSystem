package org.example.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Objects;

public class SwitchBoard extends Device {
    DoubleProperty current = new SimpleDoubleProperty();

    public SwitchBoard() {
        deviceType = DeviceType.SWITCH_BOARD;

    }

    public SwitchBoard(String id, double current) {
        super.setSensorId(id);
        this.current.set(current);
        deviceType = DeviceType.SWITCH_BOARD;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwitchBoard that = (SwitchBoard) o;
        return Objects.equals(current, that.current);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(current);
    }

    @Override
    public String toString() {
        return "SwitchBoard{" +
                "id=" + super.getSensorId() +
                "current=" + current +
                '}';
    }
}
