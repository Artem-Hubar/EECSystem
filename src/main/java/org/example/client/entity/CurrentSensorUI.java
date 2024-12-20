package org.example.client.entity;

import org.example.entity.CurrentLineSensor;

public class CurrentSensorUI extends CurrentLineSensor{
    public CurrentSensorUI(CurrentLineSensor currentLineSensor) {
        super(currentLineSensor.getSensorId(), currentLineSensor.getVoltage(), currentLineSensor.getCurrent());
    }

    @Override
    public void setCurrent(double current) {
        super.setCurrent(current);
    }

    @Override
    public void setVoltage(double voltage) {
        super.setVoltage(voltage);
    }

    @Override
    public double getVoltage() {
        return super.getVoltage();
    }

    @Override
    public double getCurrent() {
        return super.getCurrent();
    }
}
