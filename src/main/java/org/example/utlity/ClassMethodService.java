package org.example.utlity;

import org.example.entity.Device;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class ClassMethodService {
    public @NotNull String getSetterTitleByField(String field) {
        return "set" + capitalize(field);
    }

    public String capitalize(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public Method getMethod(Class<? extends Device> deviceClass, String titleMethod) {
        Method method = null;
        Method[] methods = deviceClass.getDeclaredMethods();
        for (Method methodItem : methods) {
            if (methodItem.getName().equals(titleMethod)) {
                method = methodItem;
            }
        }
        return method;
    }

    public String getGetterTitleByField(String fieldName) {
        String s1 = fieldName.substring(0, 1).toUpperCase();
        String nameCapitalized = s1 + fieldName.substring(1);
        return "get" + nameCapitalized;
    }

    public Method getGetterMethodByFieldName(String fieldName, Class<? extends Device> deviceClass){
        String getterTitle = getGetterTitleByField(fieldName);
        return getMethod(deviceClass, getterTitle);
    }

    public Method getSetterMethodByField(String fieldName, Class<? extends Device> deviceClass){
        String setterTitle = getSetterTitleByField(fieldName);
        return getMethod(deviceClass, setterTitle);
    }
}
