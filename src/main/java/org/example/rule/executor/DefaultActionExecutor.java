package org.example.rule.executor;

import org.example.rule.entity.Action;

import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class DefaultActionExecutor implements ActionExecutor {
    @Override
    public void execute(Action action)  {
        Object targetObject = action.getTargetObject();
        String fieldName = action.getFieldName();
        Object newValue = action.getNewValue();

        Class<?> clazz = targetObject.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(targetObject, newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Method method = getDeclaredMethod(clazz, fieldName);
            method.setAccessible(true);
            invoke(method, targetObject, newValue);
        }
    }

    private void invoke(Method method, Object targetObject, Object newValue) {
        try {
            // Получаем тип параметра метода
            Class<?> parameterType = method.getParameterTypes()[0];

            // Преобразуем newValue в нужный тип
            Object convertedValue = convertValueToMethodParameterType(parameterType, newValue);

            // Вызов метода с преобразованным значением
            method.invoke(targetObject, convertedValue);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Object convertValueToMethodParameterType(Class<?> parameterType, Object newValue) {
        if (newValue == null) {
            return null; // Если значение null, возвращаем null
        }

        // Преобразуем значение в нужный тип в зависимости от типа параметра
        if (parameterType.equals(String.class)) {
            return newValue.toString();  // Преобразуем в String
        } else if (parameterType.equals(Double.class)) {
            return Double.valueOf(newValue.toString());  // Преобразуем в Double
        } else if (parameterType.equals(Integer.class)) {
            return Integer.valueOf(newValue.toString());  // Преобразуем в Integer
        } else if (parameterType.equals(Boolean.class)) {
            return Boolean.valueOf(newValue.toString());  // Преобразуем в Boolean
        } else {
            throw new IllegalArgumentException("Unsupported parameter type: " + parameterType);
        }
    }


    private Method getDeclaredMethod(Class<?> clazz, String fieldName) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(fieldName)) {
                return method;
            }
        }
        throw new NoSuchElementException("Метод с именем '" + fieldName + "' не найден в классе " + clazz.getName());
    }
}
