package org.example.rule.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;
import org.example.rule.serializer.TargetObjectDeserializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
@ToString
public class Expression {
    @JsonDeserialize(using = TargetObjectDeserializer.class)
    private Object targetObject;
    private String methodName;
    private String operator;
    private Expression leftOperand;
    private Expression rightOperand;
    private boolean isGrouped;

    public Expression() {
    }

    public Expression(Object targetObject) {
        this.targetObject = targetObject;
        this.isGrouped = false;
    }

    public Expression(Object targetObject, String methodName) {
        this.targetObject = targetObject;
        this.methodName = methodName;
        this.isGrouped = false;
    }

    public Expression(Expression groupedExpression) {
        this.leftOperand = groupedExpression;
        this.isGrouped = true;
    }

    public Expression(Expression leftOperand, String operator, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
        this.isGrouped = false;
    }

    public Object evaluate() {
        if (isGrouped) {
            return evaluateGroupedExpression();
        }

        if (isExpressionWithOperand()) {
            Object leftValue = leftOperand.evaluate();
            Object rightValue = rightOperand.evaluate();
            return performOperation(leftValue, rightValue);
        }

        // Если это простое значение или объект
        return getFieldValue();
    }

    private boolean isExpressionWithOperand() {
        return leftOperand != null && rightOperand != null;
    }

    // Оценка для выражений в скобках (группированных выражений)
    private Object evaluateGroupedExpression()  {
        // Оценка выражения внутри скобок
        if (isExpressionWithOperand()) {
            Object leftValue = leftOperand.evaluate();
            Object rightValue = rightOperand.evaluate();
            return performOperation(leftValue, rightValue);
        }
        return getFieldValue();
    }

    // Получение значения поля у объекта
    private Object getFieldValue(){
        if (targetObject == null) {
            throw new IllegalArgumentException("Target object or method name is missing");
        }
        if (methodName == null) {
            return targetObject;
        }
        Method field = null;
        try {
            field = targetObject.getClass().getDeclaredMethod(methodName);
            field.setAccessible(true);
            return field.invoke(targetObject);
        }

        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    // Выполнение арифметической операции между двумя значениями
    private Object performOperation(Object leftValue, Object rightValue) {

        if (leftValue instanceof Number && rightValue instanceof Number) {
            switch (operator) {
                case "+":
                    return ((Number) leftValue).doubleValue() + ((Number) rightValue).doubleValue();
                case "-":
                    return ((Number) leftValue).doubleValue() - ((Number) rightValue).doubleValue();
                case "*":
                    return ((Number) leftValue).doubleValue() * ((Number) rightValue).doubleValue();
                case "/":
                    if (((Number) rightValue).doubleValue() == 0) {
                        throw new ArithmeticException("Cannot divide by zero");
                    }
                    return ((Number) leftValue).doubleValue() / ((Number) rightValue).doubleValue();
                case "==":
                    return leftValue.equals(rightValue);
                case "!=":
                    return !leftValue.equals(rightValue);
                case ">":
                    return ((Comparable<Object>) leftValue).compareTo(rightValue) > 0;
                case "<":
                    return ((Comparable<Object>) leftValue).compareTo(rightValue) < 0;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        } else {
            throw new IllegalArgumentException("Operands must be numbers or objects with comparable fields");
        }
    }
}
