package org.example.subscriber.parser;


import org.example.entity.Device;
import org.example.entity.SwitchBoard;
import org.example.subscriber.DeviceDataParser;

import java.util.Map;

public class SwitchBoardParser extends DeviceDataParser {


    @Override
    protected void setDeviceSpecificData(Device device, Map<String, String> messageData) {
        double current = Double.parseDouble(messageData.get("current"));
        SwitchBoard switchBoard = (SwitchBoard) device;
        switchBoard.setCurrent(current);
    }
}
