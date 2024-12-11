package org.example.client.rulebuilder;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.rule.entity.Condition;
import org.example.rule.entity.ConditionWithOperator;

import java.util.ArrayList;
import java.util.List;

public class UiConditionsExtractor {
    private final List<ConditionWithOperator> conditionsWithOperators = new ArrayList<>();
    public List<ConditionWithOperator> extractConditions(VBox conditionsContainer) {
        for (var conditionNode : conditionsContainer.getChildren()) {
            if (conditionNode instanceof HBox conditionRow) {
                ComboBox<Object> objectSelector = (ComboBox<Object>) conditionRow.getChildren().get(0);
                ComboBox<String> fieldSelector = (ComboBox<String>) conditionRow.getChildren().get(1);
                ComboBox<String> operatorSelector = (ComboBox<String>) conditionRow.getChildren().get(2);
                TextField comparisonValue = (TextField) conditionRow.getChildren().get(3);

                Object targetObject = objectSelector.getValue();
                String fieldName = fieldSelector.getValue();
                String operator = operatorSelector.getValue();
                String value = comparisonValue.getText();

                if (targetObject == null || fieldName == null || operator == null || value == null || value.isEmpty()) {
                    throw new IllegalArgumentException("Условие содержит пустые или некорректные данные.");
                }

                Condition condition = new Condition(targetObject, fieldName, operator, value);
                ConditionWithOperator conditionWithOperator = new ConditionWithOperator(condition, "AND");
                conditionsWithOperators.add(conditionWithOperator);
            }
        }
        return conditionsWithOperators;
    }
}
