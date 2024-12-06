package org.example.subscriber;

import java.util.HashMap;
import java.util.Map;

public class DeviceDataHandlerFactory {
    private final Map<String, DeviceDataHandler> handlers = new HashMap<>();

    public void registerHandler(String deviceType, DeviceDataHandler handler) {
        handlers.put(deviceType, handler);
    }

    public DeviceDataHandler getHandler(String deviceType) {
        return handlers.get(deviceType);
    }
}
