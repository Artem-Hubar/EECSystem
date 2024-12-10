package org.example;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.rule.entity.Action;
import org.example.rule.entity.Condition;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Rule;
import org.example.service.RuleService;

import java.util.ArrayList;
import java.util.List;

public class SecondSceneConstructor {
    private final List<Object> objects;

    public SecondSceneConstructor(List<Object> objects) {
        this.objects = objects;
    }

    public Parent createScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Конструктор правил");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox conditionsContainer = new VBox(5);
        conditionsContainer.setPadding(new Insets(5));
        conditionsContainer.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-padding: 10px;");

        VBox actionsContainer = new VBox(5);
        actionsContainer.setPadding(new Insets(5));
        actionsContainer.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-padding: 10px;");

        Button addConditionButton = new Button("Добавить условие");
        addConditionButton.setOnAction(e -> conditionsContainer.getChildren().add(createConditionRow(conditionsContainer)));

        Button addActionButton = new Button("Добавить действие");
        addActionButton.setOnAction(e -> actionsContainer.getChildren().add(createActionRow(actionsContainer)));

        Button addRuleButton = new Button("Добавить правило");
        addRuleButton.setOnAction(e -> saveRule(conditionsContainer, actionsContainer));

        root.getChildren().addAll(title, new Label("Условия:"), conditionsContainer, addConditionButton,
                new Label("Действия:"), actionsContainer, addActionButton, addRuleButton);

        return root;
    }

    private HBox createConditionRow(VBox container) {
        ComboBox<Object> objectSelector = new ComboBox<>();
        objectSelector.getItems().addAll(objects);
        objectSelector.setPromptText("Выберите объект");

        ComboBox<String> fieldSelector = new ComboBox<>();
        fieldSelector.setPromptText("Выберите поле");

        objectSelector.setOnAction(e -> {
            Object selectedObject = objectSelector.getValue();
            if (selectedObject != null) {
                fieldSelector.getItems().clear();
                for (var field : selectedObject.getClass().getDeclaredMethods()) {
                    fieldSelector.getItems().add(field.getName());
                }
            }
        });

        ComboBox<String> operatorSelector = new ComboBox<>();
        operatorSelector.getItems().addAll("==", "!=", ">", "<", ">=", "<=");
        operatorSelector.setPromptText("Выберите оператор");

        TextField comparisonValue = new TextField();
        comparisonValue.setPromptText("Введите значение для сравнения");

        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(e -> container.getChildren().remove(deleteButton.getParent()));

        HBox conditionRow = new HBox(5, objectSelector, fieldSelector, operatorSelector, comparisonValue, deleteButton);
        conditionRow.setPadding(new Insets(5));
        return conditionRow;
    }

    private HBox createActionRow(VBox container) {
        ComboBox<Object> actionObjectSelector = new ComboBox<>();
        actionObjectSelector.getItems().addAll(objects);
        actionObjectSelector.setPromptText("Выберите объект");

        ComboBox<String> actionFieldSelector = new ComboBox<>();
        actionFieldSelector.setPromptText("Выберите поле для действия");

        actionObjectSelector.setOnAction(e -> {
            Object selectedObject = actionObjectSelector.getValue();
            if (selectedObject != null) {
                actionFieldSelector.getItems().clear();
                for (var field : selectedObject.getClass().getDeclaredMethods()) {
                    actionFieldSelector.getItems().add(field.getName());
                }
            }
        });

        TextField actionValue = new TextField();
        actionValue.setPromptText("Введите новое значение");

        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(e -> container.getChildren().remove(deleteButton.getParent()));

        HBox actionRow = new HBox(5, actionObjectSelector, actionFieldSelector, actionValue, deleteButton);
        actionRow.setPadding(new Insets(5));
        return actionRow;
    }

    private void saveRule(VBox conditionsContainer, VBox actionsContainer) {
        List<ConditionWithOperator> conditions = new ArrayList<>();
        for (var conditionNode : conditionsContainer.getChildren()) {
            HBox conditionRow = (HBox) conditionNode;

            // Извлечение значений из компонентов
            ComboBox<Object> objectSelector = (ComboBox<Object>) conditionRow.getChildren().get(0);
            ComboBox<String> fieldSelector = (ComboBox<String>) conditionRow.getChildren().get(1);
            ComboBox<String> operatorSelector = (ComboBox<String>) conditionRow.getChildren().get(2);
            TextField comparisonValue = (TextField) conditionRow.getChildren().get(3);

            // Проверка на null и пустые значения
            Object targetObject = objectSelector.getValue();
            String fieldName = fieldSelector.getValue();
            String operator = operatorSelector.getValue();
            String value = comparisonValue.getText();

            if (targetObject == null || fieldName == null || operator == null || value == null || value.isEmpty()) {
                System.out.println("Ошибка: Условие содержит пустые или недопустимые значения.");
                continue; // Пропустить некорректное условие
            }

            // Создание условия
            Condition condition = new Condition(targetObject, fieldName, operator, value);
            ConditionWithOperator conditionWithOperator = new ConditionWithOperator(condition, "AND");
            conditions.add(conditionWithOperator);
        }

        List<Action> actions = new ArrayList<>();
        for (var actionNode : actionsContainer.getChildren()) {
            HBox actionRow = (HBox) actionNode;

            // Извлечение значений из компонентов
            ComboBox<Object> actionObjectSelector = (ComboBox<Object>) actionRow.getChildren().get(0);
            ComboBox<String> actionFieldSelector = (ComboBox<String>) actionRow.getChildren().get(1);
            TextField actionValue = (TextField) actionRow.getChildren().get(2);

            // Проверка на null и пустые значения
            Object targetObj = actionObjectSelector.getValue();
            String actionFieldName = actionFieldSelector.getValue();
            String newValue = actionValue.getText();

            if (targetObj == null || actionFieldName == null || newValue == null || newValue.isEmpty()) {
                System.out.println("Ошибка: Действие содержит пустые или недопустимые значения.");
                continue; // Пропустить некорректное действие
            }

            // Создание действия
            Action action = new Action(targetObj, actionFieldName, newValue);
            actions.add(action);
        }

        if (!conditions.isEmpty() && !actions.isEmpty()) {
            Rule rule = new Rule(conditions, actions);
            RuleService ruleService = new RuleService();
            ruleService.saveRule(rule);
            System.out.println("Правило сохранено: " + rule);
        } else {
            System.out.println("Ошибка: добавьте хотя бы одно корректное условие и действие.");
        }
    }

}
