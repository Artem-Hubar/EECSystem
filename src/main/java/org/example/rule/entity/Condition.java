package org.example.rule.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;
import org.example.rule.serializer.TargetObjectDeserializer;

import java.lang.reflect.Method;
import java.util.Objects;

@ToString
@Data
public class Condition {
    @JsonDeserialize(using = TargetObjectDeserializer.class)
    private Object targetObject;   // Объект, к которому применяется условие
    private String fieldName;      // Имя поля(которое может быть обьектом) или метода
    private String operator;       // Оператор: ==, !=, >, <, и т.д.
    private Object value;          // Значение для сравнения (число, строка, другой объект)


    public Condition() {
    }

    public Condition(Object targetObject, String fieldName, String operator, Object value) {
        this.targetObject = targetObject;
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    public boolean evaluate() throws Exception {
        Object fieldValue = getFieldValue();
        return compareValues(fieldValue);
    }

    // Извлечение значения поля из объекта
    private Object getFieldValue() throws Exception {
        Method field = targetObject.getClass().getDeclaredMethod(fieldName);
        field.setAccessible(true);
        return field.invoke(targetObject);
    }

    // Сравнение значения поля с заданным значением
    private boolean compareValues(Object fieldValue) {
        value = convertValueToFieldType(fieldValue, value);
        switch (operator) {
            case "==":
                return isEqual(fieldValue, value);
            case "!=":
                return isNotEqual(fieldValue, value);
            case ">":
                return isGreaterThan(fieldValue, value);
            case "<":
                return isLessThan(fieldValue, value);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    private Object convertValueToFieldType(Object fieldValue, Object rawValue) {
        if (fieldValue == null || rawValue == null) {
            return rawValue; // Нет значения, возвращаем как есть
        }

        Class<?> fieldClass = fieldValue.getClass();

        // Преобразуем значение в тип поля
        if (fieldClass.equals(Double.class)) {
            return Double.parseDouble(rawValue.toString()); // Преобразуем в Double
        } else if (fieldClass.equals(Integer.class)) {
            return Integer.parseInt(rawValue.toString()); // Преобразуем в Integer
        } else if (fieldClass.equals(Boolean.class)) {
            return Boolean.parseBoolean(rawValue.toString()); // Преобразуем в Boolean
        } else if (fieldClass.equals(String.class)) {
            return rawValue.toString(); // Строка, возвращаем как есть
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldClass);
        }
    }


    // Логика проверки равенства
    private boolean isEqual(Object fieldValue, Object comparisonValue) {
        return fieldValue.equals(comparisonValue);
    }

    // Логика проверки неравенства
    private boolean isNotEqual(Object fieldValue, Object comparisonValue) {
        return !fieldValue.equals(comparisonValue);
    }

    // Логика проверки "больше"
    private boolean isGreaterThan(Object fieldValue, Object comparisonValue) {
        if (!(fieldValue instanceof Comparable)) {
            throw new IllegalArgumentException("Field value is not comparable: " + fieldValue);
        }
        return ((Comparable<Object>) fieldValue).compareTo(comparisonValue) > 0;
    }

    // Логика проверки "меньше"
    private boolean isLessThan(Object fieldValue, Object comparisonValue) {
        if (!(fieldValue instanceof Comparable)) {
            throw new IllegalArgumentException("Field value is not comparable: " + fieldValue);
        }
        return ((Comparable<Object>) fieldValue).compareTo(comparisonValue) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Condition condition = (Condition) o;
        return Objects.equals(targetObject, condition.targetObject) && Objects.equals(fieldName, condition.fieldName) && Objects.equals(operator, condition.operator) && Objects.equals(value, condition.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetObject, fieldName, operator, value);
    }


}
