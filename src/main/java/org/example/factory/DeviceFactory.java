package org.example.factory;

import org.example.entity.*;


import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceFactory {
    public static Optional<Device> getDevice(DeviceType measurement){
        Device device = null;
        switch (measurement){
            case DeviceType.CURRENT_LINE_SENSOR:
                device = new CurrentLineSensor();
                break;
            case DeviceType.TRANSPORTER:
                device = new Transformer();
                break;
            case GENERATOR:
                device = new Generator();
                break;
            case SWITCH_BOARD:
                device = new SwitchBoard();
                break;
            default:
                Logger.getLogger(DeviceFactory.class.getName()).log(Level.SEVERE, "error sensors factory");
        }
        return Optional.ofNullable(device);
    }
}
