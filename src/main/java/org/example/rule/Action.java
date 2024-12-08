package org.example.rule;

import lombok.ToString;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;


@ToString
public class Action {
    private Object targetObject;   // Объект, к которому применяется действие
    private String fieldName;      // Поле или метод
    private Object newValue;       // Новое значение или метод для вызова

    public Action(Object targetObject, String fieldName, Object newValue) {
        this.targetObject = targetObject;
        this.fieldName = fieldName;
        this.newValue = newValue;
    }

    public void execute() throws Exception {
        Class<?> clazz = targetObject.getClass();
        try {
            // Попробуем найти поле
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(targetObject, newValue);
        } catch (NoSuchFieldException e) {
            // Если поля нет, попробуем найти метод
            Method method = getDeclaredMethod(clazz, fieldName);
            method.setAccessible(true);
            method.invoke(targetObject, newValue);
        }
    }

    private Method getDeclaredMethod(Class<?> clazz, String fieldName) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method nextMethod : methods) {
            if (nextMethod.getName().equals(fieldName)) {
                return nextMethod; // Возвращаем найденный метод
            }
        }
        throw new NoSuchElementException("Метод с именем '" + fieldName + "' не найден в классе " + clazz.getName());
    }

}
