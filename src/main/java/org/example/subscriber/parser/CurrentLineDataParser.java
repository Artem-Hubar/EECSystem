package org.example.subscriber.parser;

import org.example.entity.CurrentLineSensor;
import org.example.entity.Device;
import org.example.subscriber.DeviceDataParser;

import java.util.Map;

public class CurrentLineDataParser extends DeviceDataParser {
    @Override
    protected void setDeviceSpecificData(Device device, Map<String, String> messageData) {
        double current = Double.parseDouble(messageData.get("current"));
        double voltage = Double.parseDouble(messageData.get("voltage"));
        CurrentLineSensor currentLineSensor = (CurrentLineSensor) device;
        currentLineSensor.setCurrent(current);
        currentLineSensor.setVoltage(voltage);
    }


}
