package org.example.service;

import org.example.entity.Device;
import org.example.entity.DeviceType;
import org.example.utlity.ClassMethodService;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class DeviceUpdater {

    private final InflexDBService inflexDBService= new InflexDBService();
    private final ClassMethodService classMethodService = new ClassMethodService();;


    public void updateDevice(Device targetDevice, DeviceType deviceType, String sensorId) {
        Optional<Device> newDeviceOptional = inflexDBService.getDeviceById(deviceType, sensorId);
        if (newDeviceOptional.isPresent()) {
            Device newDevice = newDeviceOptional.get();
            Field[] fields = getFields(newDevice);

            for (Field field : fields) {
                Method getter = getGetterNewDevice(field, newDevice);
                Method setter = getSetterOldDevice(targetDevice, field);

                if (isMethodsNotNull(getter, setter)) {
                    System.out.println("Getter or setter not found for field: " + field.getName());
                    continue;
                }

                Object value = getNewValue(getter, newDevice);
                updateField(targetDevice, field, setter, value);
            }
        } else {
            System.out.println("Can't update device - new device is null");
        }
    }

    private Field [] getFields(Device newDevice) {
        Class<?> clazz = newDevice.getClass();
        return clazz.getDeclaredFields();
    }

    private Method getGetterNewDevice(Field field, Device newDevice) {
        return classMethodService.getGetterMethodByFieldName(field.getName(), newDevice.getClass());
    }

    private Method getSetterOldDevice(Device targetDevice, Field field) {
        return classMethodService.getSetterMethodByField(field.getName(), targetDevice.getClass());
    }

    private Object getNewValue(Method getter, Device newDevice) {
        try {
            return getter.invoke(newDevice);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isMethodsNotNull(Method getter, Method setter) {
        return getter == null || setter == null;
    }

    private void updateField(Device targetDevice, Field field, Method setter, Object value) {
        try {
            setter.invoke(targetDevice, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error updating field: " + field.getName(), e);
        }
    }
}
