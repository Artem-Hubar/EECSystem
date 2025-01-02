package org.example.subscriber.parser;

import org.example.entity.Device;
import org.example.entity.Generator;
import org.example.subscriber.DeviceDataParser;

import java.util.Map;

public class GeneratorDataParser extends DeviceDataParser {

    @Override
    protected void setDeviceSpecificData(Device device, Map<String, String> messageData) {
        double voltage = Double.parseDouble(messageData.get("voltage"));
        double current = Double.parseDouble(messageData.get("current"));
        boolean isWorking = Boolean.parseBoolean(messageData.get("isWorking"));
        Generator generator = (Generator) device;
        generator.setVoltage(voltage);
        generator.setCurrent(current);
        generator.setIsWorking(isWorking);
    }
}
