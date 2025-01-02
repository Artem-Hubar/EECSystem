package org.example.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import lombok.EqualsAndHashCode;


import java.time.Instant;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
public class Transformer extends Device {


    private final DoubleProperty turnsRatio = new SimpleDoubleProperty();

    public Transformer() {
        super.deviceType = DeviceType.TRANSPORTER;
    }

    public Transformer(String sensorId, Double turnsRatio) {
        super.setSensorId(sensorId);
        this.turnsRatio.set(turnsRatio);
        super.deviceType = DeviceType.TRANSPORTER;
    }


    public double getTurnsRation() {
        return turnsRatio.get();
    }

    public void setTurnsRation(Double turnsRatio) {
        this.turnsRatio.set(turnsRatio);
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
