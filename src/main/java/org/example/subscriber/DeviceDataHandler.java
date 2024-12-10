package org.example.subscriber;

import org.example.entity.Device;
import org.example.factory.DeviceFactory;

import java.util.Optional;

public abstract class DeviceDataHandler {
    Device device;
    public Optional<Device>  handleData(String[] topicParts, String message){
        Optional<Device> device = DeviceFactory.getDevice(topicParts[1]);
        if (device.isPresent()){
            setDeviceData(device.get(), topicParts, message);
        }
        return device;
    }

    protected void setDeviceData(Device device, String[] topicParts, String message) {
        device.setSensorId(topicParts[2]);
    }
}
