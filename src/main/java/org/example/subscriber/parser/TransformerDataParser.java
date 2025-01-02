package org.example.subscriber.parser;

import org.example.entity.Device;
import org.example.entity.Transformer;
import org.example.subscriber.DeviceDataParser;

import java.util.Map;

public class TransformerDataParser extends DeviceDataParser {

    @Override
    protected void setDeviceSpecificData(Device device, Map<String, String> messageData) {
        Transformer transporter = (Transformer) device;
        transporter.setTurnsRation(Double.valueOf(messageData.get("turnsRation")));
    }
}
