package org.example.client.entity;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExpressionRow {


    private final List<Object> objects;

    public ExpressionRow(List<Object> objects) {

        this.objects = objects;
    }
    public HBox createExpressionRow(Pane parentGroup) {
        HBox expressionRow = new HBox(10);
        ComboBox<String> methodSelector = getMethodSelector();
        ComboBox<Object> objectSelector = getObjectSelector(methodSelector);
        ComboBox<String> operatorSelector = getOperatorSelector();
        TextField valueInputField = getValueInputField();
        VBox nestedExpressionGroup = getNestedExpressionGroup();
        ComboBox<String> rightOperandSelector = getRightOperandSelector(valueInputField, nestedExpressionGroup);
        Button deleteButton = getDeleteButton(parentGroup, expressionRow);
        expressionRow.getChildren().addAll(
                deleteButton,
                objectSelector,
                methodSelector,
                operatorSelector,
                rightOperandSelector,
                valueInputField,
                nestedExpressionGroup
        );
        expressionRow.setPadding(new Insets(5));
        return expressionRow;
    }

    private @NotNull ComboBox<String> getMethodSelector() {
        ComboBox<String> methodSelector = new ComboBox<>();
        methodSelector.setPromptText("Select method");
        return methodSelector;
    }

    private @NotNull TextField getValueInputField() {
        TextField valueInputField = new TextField();
        valueInputField.setPromptText("Enter value manually");
        return valueInputField;
    }

    private @NotNull VBox getNestedExpressionGroup() {
        VBox nestedExpressionGroup = new VBox(10);
        nestedExpressionGroup.setPadding(new Insets(5));
        nestedExpressionGroup.setVisible(false);
        return nestedExpressionGroup;
    }

    private @NotNull ComboBox<String> getRightOperandSelector(TextField valueInputField, VBox nestedExpressionGroup) {
        ComboBox<String> rightOperandSelector = new ComboBox<>();
        rightOperandSelector.getItems().addAll("Value", "Expression");
        rightOperandSelector.setPromptText("Select operand type");

        rightOperandSelector.setOnAction(e -> {
            String selectedOperandType = rightOperandSelector.getValue();
            if ("Value".equals(selectedOperandType)) {
                valueInputField.setVisible(true);
                nestedExpressionGroup.setVisible(false);
            } else if ("Expression".equals(selectedOperandType)) {
                valueInputField.setVisible(false);
                nestedExpressionGroup.setVisible(true);
                if (nestedExpressionGroup.getChildren().isEmpty()) {
                    nestedExpressionGroup.getChildren().add(createExpressionRow(nestedExpressionGroup));
                }
            }
        });
        return rightOperandSelector;
    }

    private @NotNull ComboBox<String> getOperatorSelector() {
        ComboBox<String> operatorSelector = new ComboBox<>();
        operatorSelector.getItems().addAll("==", "!=", ">", "<", ">=", "<=", "+", "-", "*", "/");
        operatorSelector.setPromptText("Select operator");
        return operatorSelector;
    }

    private @NotNull ComboBox<Object> getObjectSelector(ComboBox<String> methodSelector) {
        ComboBox<Object> objectSelector = new ComboBox<>();
        objectSelector.getItems().addAll(objects);
        objectSelector.setPromptText("Select object");


        objectSelector.setOnAction(event -> {
            Object selectedObject = objectSelector.getValue();
            if (selectedObject != null) {
                methodSelector.getItems().clear();
                for (var method : selectedObject.getClass().getDeclaredMethods()) {
                    methodSelector.getItems().add(method.getName());
                }
            }
        });
        return objectSelector;
    }

    private @NotNull Button getDeleteButton(Pane parentGroup, HBox expressionRow) {
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> parentGroup.getChildren().remove(expressionRow));
        return deleteButton;
    }
}
