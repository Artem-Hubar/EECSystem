package org.example.client.entity;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;

public class ConditionUI {
    private final List<Object> objects;
    private Runnable onDelete;

    public ConditionUI(List<Object> objects, Runnable onDelete) {
        this.objects = objects;
        this.onDelete = onDelete;
    }

    public HBox createConditionRow() {
        ComboBox<Object> objectSelector = new ComboBox<>();
        objectSelector.getItems().addAll(objects);
        objectSelector.setPromptText("Input object");

        ComboBox<String> fieldSelector = new ComboBox<>();
        fieldSelector.setPromptText("Input field");

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
        operatorSelector.setPromptText("Input operator");

        TextField comparisonValue = new TextField();
        comparisonValue.setPromptText("Input value for equal");

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> onDelete.run());

        HBox conditionRow = new HBox(5, objectSelector, fieldSelector, operatorSelector, comparisonValue, deleteButton);
        conditionRow.setPadding(new Insets(5));
        return conditionRow;
    }
}
