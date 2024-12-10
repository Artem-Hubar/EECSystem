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
            method.invoke(targetObject, newValue);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
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
