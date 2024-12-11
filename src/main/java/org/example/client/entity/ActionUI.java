package org.example.client.entity;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;

public class ActionUI {
    private final List<Object> objects;
    private Runnable onDelete;

    public ActionUI(List<Object> objects, Runnable onDelete) {
        this.objects = objects;
        this.onDelete = onDelete;
    }

    public HBox createActionRow() {
        ComboBox<Object> actionObjectSelector = new ComboBox<>();
        actionObjectSelector.getItems().addAll(objects);
        actionObjectSelector.setPromptText("Choice object");

        ComboBox<String> actionFieldSelector = new ComboBox<>();
        actionFieldSelector.setPromptText("Choice method");

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
        actionValue.setPromptText("Input new value");

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> onDelete.run());

        HBox actionRow = new HBox(5, actionObjectSelector, actionFieldSelector, actionValue, deleteButton);
        actionRow.setPadding(new Insets(5));
        return actionRow;
    }
}
