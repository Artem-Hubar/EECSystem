package org.example.service.inflexdb;

import org.example.entity.Device;
import org.example.utlity.ClassMethodService;

import java.lang.reflect.Method;

public class DeviceHandler {

    public static void handleField(Device device, String field, Object value) {
        try {
            ClassMethodService classMethodService = new ClassMethodService();
            String titleSetterMethod = classMethodService.getSetterTitleByField(field);
            Class<? extends Device> deviceClass = device.getClass();
            Method setterMethod = classMethodService.getMethod(deviceClass, titleSetterMethod);
            setterMethod.invoke(device, value);
        } catch (Exception e) {
            System.err.println("Ошибка при обработке поля " + field + ": " + e.getMessage());
        }
    }


}
