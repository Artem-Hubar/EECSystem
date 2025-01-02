package org.example.entity;

import lombok.Getter;

import java.util.function.Supplier;

public enum DeviceType {
    TRANSPORTER("transformer", Transformer::new),
    CURRENT_LINE_SENSOR("currentsensor", CurrentLineSensor::new),
    GENERATOR("generator", Generator::new),
    SWITCH_BOARD("switchboard", SwitchBoard::new);
    @Getter
    private final String measurement;
    private final Supplier<Device> deviceCreator;

    DeviceType(String measurement, Supplier<Device> deviceCreator) {
        this.measurement = measurement;
        this.deviceCreator = deviceCreator;
    }

    public Device createDevice() {
        return deviceCreator.get();
    }

    // Метод для поиска типа устройства по measurement
    public static DeviceType fromType(String typeString) {
        for (DeviceType type : values()) {
            if (type.measurement.equals(typeString)) {
                return type;
            }
        }
        return null;
    }
}