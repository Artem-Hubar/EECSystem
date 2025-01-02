package org.example.subscriber;

import org.example.entity.Device;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MQTTPayloadBuilder {
    public String getPayloadBuilder(Device device){
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        Field[] fields = device.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            String fieldName = field.getName();
            String methodName = getMethodName(fieldName);
            Method method;
            Object value;
            try {
                method = device.getClass().getMethod(methodName);
                value = method.invoke(device);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            jsonBuilder.append(String.format("\"%s\":\"%s\"", field.getName(), value));

            if (i < fields.length - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private String getMethodName(String fieldName) {
        String s1 = fieldName.substring(0, 1).toUpperCase();
        String nameCapitalized = s1 + fieldName.substring(1);
        return "get" + nameCapitalized;
    }
}
