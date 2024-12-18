package org.example.client.rulebuilder;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.client.controllers.ConditionalController;
import org.example.client.controllers.ExpressionController;
import org.example.client.controllers.ExpressionsContainerController;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UiConditionsExtractor {
    private final List<ConditionWithOperator> conditionsWithOperators = new ArrayList<>();

    public Collection<? extends ConditionWithOperator> extractConditions(ConditionalController conditionsContainer) {
        List<ConditionWithOperator> condition = new ArrayList<>();
        List<ExpressionsContainerController> expressionsContainerControllers = conditionsContainer.getExpressionsContainerControllers();
        for (ExpressionsContainerController expressionContainer : expressionsContainerControllers){
            String logicalOperator;
            if (getLogicalOperator(expressionContainer).isPresent()){
                logicalOperator = getLogicalOperator(expressionContainer).get();
            }else {
                logicalOperator = "AND";
            }
            List<Object[]> expressionList = getExpressionList(expressionContainer);
            Expression expression = parseExpression(expressionList);
            ConditionWithOperator conditionWithOperator = new ConditionWithOperator(expression, logicalOperator);
            condition.add(conditionWithOperator);
        }
        return condition;
    }

    private Optional<String> getLogicalOperator(ExpressionsContainerController containerController) {
        ChoiceBox<String> choiceBox = containerController.getChoiceBox();
        if (choiceBox != null){
            return Optional.of(choiceBox.getValue()) ;
        }
        return Optional.empty();
    }

    private List<Object[]> getExpressionList(ExpressionsContainerController expressionContainer) {
        List<Object[]> expressions = new ArrayList<>();
        List<ExpressionController> expressionControllers = expressionContainer.getExpressionControllers();
        for (ExpressionController expressionController :expressionControllers){
            Object targetObject = expressionController.getDevice();
            String method = getMethod(expressionController);
            String operand =expressionController.getSelectedOperator();
            Object[] expression = {targetObject, method, operand};
            expressions.add(expression);
        }
        return expressions;
    }

    private String getMethod(ExpressionController expressionController) {
        return expressionController
                .getDeviceViewController()
                .getMethodBox()
                .getValue();
    }


    public Expression parseExpression(List<Object[]> expressionList) {

        Expression rootExpression = new Expression();

        rootExpression.setGrouped(true);


        for (Object[] expressionData : expressionList) {

            Object targetObject = expressionData[0];
            String methodName = (String) expressionData[1];
            String operator = (String) expressionData[2];


            Expression currentExpression = new Expression();
            currentExpression.setTargetObject(targetObject);
            currentExpression.setMethodName(methodName);
            currentExpression.setOperator(operator);


            addExpressionToTree(rootExpression, currentExpression);
        }

        return rootExpression;
    }

    private void addExpressionToTree(Expression root, Expression current) {

        if (root.getLeftOperand() == null) {
            root.setLeftOperand(current);
        } else {

            if (root.getRightOperand() == null) {
                root.setRightOperand(current);
            } else {
                Expression newGroup = new Expression();
                newGroup.setGrouped(true);
                newGroup.setLeftOperand(root.getRightOperand());
                newGroup.setRightOperand(current);
                root.setRightOperand(newGroup);
            }
        }
    }





    private ConditionWithOperator extractConditionWithOperator(HBox conditionRow) {
        // Получаем элементы строки
        ComboBox<Object> objectSelector = (ComboBox<Object>) conditionRow.getChildren().get(0);
        ComboBox<String> methodSelector = (ComboBox<String>) conditionRow.getChildren().get(1);
        ComboBox<String> operatorSelector = (ComboBox<String>) conditionRow.getChildren().get(2);
        ComboBox<String> rightOperandSelector = (ComboBox<String>) conditionRow.getChildren().get(3);
        TextField comparisonValueField = (TextField) conditionRow.getChildren().get(4);
        VBox nestedExpressionGroup = (VBox) conditionRow.getChildren().get(5);
        ComboBox<String> logicalOperatorSelector = (ComboBox<String>) conditionRow.getChildren().get(6);

        // Извлекаем значения из элементов
        Object targetObject = objectSelector.getValue();
        String methodName = methodSelector.getValue();
        String operator = operatorSelector.getValue();
        String logicalOperator = logicalOperatorSelector.getValue(); // Например, "AND" или "OR"

        // Проверяем, что правый операнд корректно заполнен
        Expression rightOperand;
        if ("Value".equals(rightOperandSelector.getValue())) {
            String valueText = comparisonValueField.getText();
            validateConditionInputs(targetObject, methodName, operator, valueText);
            rightOperand = new Expression(parseValue(valueText));
        } else if ("Expression".equals(rightOperandSelector.getValue())) {
            // Если выбран вложенный операнд, рекурсивно извлекаем вложенное выражение
            rightOperand = extractNestedExpressions(nestedExpressionGroup);
        } else {
            throw new IllegalArgumentException("Правый операнд не выбран.");
        }

        // Создаем Expression для текущей строки
        Expression leftOperand = new Expression(targetObject, methodName);
        Expression conditionExpression = new Expression(leftOperand, operator, rightOperand);

        // Создаем ConditionWithOperator
        return new ConditionWithOperator(conditionExpression, logicalOperator != null ? logicalOperator : "AND");
    }

    private Expression extractNestedExpressions(VBox nestedGroup) {
        if (nestedGroup == null || nestedGroup.getChildren().isEmpty()) {
            throw new IllegalArgumentException("Вложенное выражение не может быть пустым.");
        }

        Node firstNode = nestedGroup.getChildren().get(0);
        if (firstNode instanceof HBox nestedRow) {
            return extractExpression(nestedRow);
        }

        throw new IllegalStateException("Вложенное выражение имеет некорректную структуру.");
    }

    private Expression extractExpression(HBox conditionRow) {
        // Логика для создания Expression из строки (аналогичная описанной ранее)
        return extractConditionWithOperator(conditionRow).getCondition();
    }

    private void validateConditionInputs(Object targetObject, String fieldName, String operator, String value) {
        if (targetObject == null) {
            throw new IllegalArgumentException("Выберите объект для условия.");
        }
        if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("Поле условия не может быть пустым.");
        }
        if (operator == null || operator.isEmpty()) {
            throw new IllegalArgumentException("Оператор условия не может быть пустым.");
        }
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Значение для сравнения не может быть пустым.");
        }
    }

    private Object parseValue(String value) {
        try {
            if (value.contains(".")) {
                return Double.parseDouble(value);
            }
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return value; // Если это не число, возвращаем как строку
        }
    }


}
