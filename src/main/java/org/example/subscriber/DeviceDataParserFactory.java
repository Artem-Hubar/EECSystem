package org.example.subscriber;

import org.example.entity.DeviceType;

import java.util.HashMap;
import java.util.Map;

public class DeviceDataParserFactory {
    private final Map<DeviceType, DeviceDataParser> parsers = new HashMap<>();

    public void registerParser(DeviceType deviceType, DeviceDataParser parser) {
        parsers.put(deviceType, parser);
    }

    public DeviceDataParser getParser(DeviceType deviceType) {
        return parsers.get(deviceType);
    }
}
