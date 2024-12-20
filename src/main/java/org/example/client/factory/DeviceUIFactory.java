package org.example.client.factory;

import org.example.client.entity.CurrentSensorUI;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Device;

public class DeviceUIFactory {
    public Device getDeviceUi(Device device){
        if (device instanceof CurrentLineSensor currentLineSensor){
            return new CurrentSensorUI(currentLineSensor);
        }
        else {
            return device;
        }
    }
}
