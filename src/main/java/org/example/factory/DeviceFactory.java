package org.example.factory;

import org.example.entity.Device;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Transformer;


import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceFactory {
    public static Optional<Device> getDevice(String type){
        Device device = null;
        switch (type){
            case "currentsensor":
                device = new CurrentLineSensor();
                break;
            case "transformer":
                device = new Transformer();
                break;
            default:
                Logger.getLogger(DeviceFactory.class.getName()).log(Level.SEVERE, "error sensors factory");
        }
        return Optional.ofNullable(device);
    }
}
