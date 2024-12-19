package org.example.client.entity;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExpressionGroup {
    private final List<Object> objects;
    private final Runnable onDelete;

    public ExpressionGroup(List<Object> objects, Runnable onDelete) {
        this.objects = objects;
        this.onDelete = onDelete;
    }

    public VBox createExpressionGroup() {
        VBox expressionGroup = new VBox(10);
        expressionGroup.setPadding(new Insets(10));
        ExpressionRow expressionRow = new ExpressionRow(objects);
        expressionGroup.getChildren().add(expressionRow.createExpressionRow(expressionGroup));
        Button addExpressionButton = getAddExpressionButton(expressionRow, expressionGroup);
        expressionGroup.getChildren().add(addExpressionButton);
        return expressionGroup;
    }

    private @NotNull Button getAddExpressionButton(ExpressionRow expressionRow, VBox expressionGroup) {
        Button addExpressionButton = new Button("Add Expression");
        addExpressionButton.setOnAction(event -> {
            HBox newExpressionRow = expressionRow.createExpressionRow(expressionGroup);
            expressionGroup.getChildren().add(expressionGroup.getChildren().size() - 1, newExpressionRow);
        });
        return addExpressionButton;
    }
}
