package org.example.rule.executor;

import org.example.entity.Device;
import org.example.factory.DeviceFactory;
import org.example.rule.entity.Action;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;


public class DefaultActionExecutor implements ActionExecutor {
    @Override
    public void execute(Action action)  {
//        System.out.println("ActionExecute");
        Object targetObject = action.getTargetObject();
        String fieldName = action.getFieldName();
        Object newValue = action.getExpression().evaluate();
        if (newValue instanceof Double number && number > 140){
            System.out.println(action);
            System.out.println("newValue " + number);
        }

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
        if (targetObject instanceof Device device){
            Random random = new Random();
            MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher(String.valueOf(random.nextInt()));
            mqttPublisher.writeData(device);
        }
    }

    private void invoke(Method method, Object targetObject, Object newValue) {
        try {
            Class<?> parameterType = method.getParameterTypes()[0];
            Object convertedValue = convertValueToMethodParameterType(parameterType, newValue);
            method.invoke(targetObject, convertedValue);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Object convertValueToMethodParameterType(Class<?> parameterType, Object newValue) {
        if (newValue == null) {
            return null;
        }

        if (parameterType.equals(String.class)) {
            return newValue.toString();  // Преобразуем в String
        } else if (parameterType.equals(Double.class)) {
            return Double.valueOf(newValue.toString());  // Преобразуем в Double
        } else if (parameterType.equals(Integer.class)) {
            return Integer.valueOf(newValue.toString());  // Преобразуем в Integer
        } else if (parameterType.equals(Boolean.class)) {
            return Boolean.valueOf(newValue.toString());  // Преобразуем в Boolean
        } else {
            return newValue;
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
