package org.example.client.entity;

import org.example.entity.CurrentLineSensor;

public class CurrentSensorUI extends CurrentLineSensor {

    public CurrentSensorUI(CurrentLineSensor currentLineSensor) {
        super(currentLineSensor.getSensorId(), currentLineSensor.getVoltage(), currentLineSensor.getCurrent());
    }

    @Override
    public String toString() {
        return "CurrentSensor\n"+ getSensorId();
    }
}
