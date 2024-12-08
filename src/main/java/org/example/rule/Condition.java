package org.example.rule;

import lombok.ToString;

import java.lang.reflect.Field;

@ToString
public class Condition {
    private Object targetObject;   // Объект, к которому применяется условие
    private String fieldName;      // Имя поля или метода
    private String operator;       // Оператор: ==, !=, >, <, и т.д.
    private Object value;          // Значение для сравнения (число, строка, другой объект)

    public Condition(Object targetObject, String fieldName, String operator, Object value) {
        this.targetObject = targetObject;
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    public boolean evaluate() throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object fieldValue = field.get(targetObject);
        System.out.println(fieldValue + " " + value);
        // Сравниваем значение
        switch (operator) {
            case "==":
                return fieldValue.equals(value);
            case "!=":
                return !fieldValue.equals(value);
            case ">":
                return ((Comparable) fieldValue).compareTo(value) > 0;
            case "<":
                return ((Comparable) fieldValue).compareTo(value) < 0;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}
