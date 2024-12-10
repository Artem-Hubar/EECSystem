package org.example.rule.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;
import org.example.rule.serializer.TargetObjectDeserializer;

import java.lang.reflect.Field;
import java.util.Objects;

@ToString
@Data
public class Condition {
    @JsonDeserialize(using = TargetObjectDeserializer.class)
    private Object targetObject;   // Объект, к которому применяется условие
    private String fieldName;      // Имя поля(которое может быть обьектом) или метода
    private String operator;       // Оператор: ==, !=, >, <, и т.д.
    private Object value;          // Значение для сравнения (число, строка, другой объект)

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

    public Condition() {
    }

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
