package org.example.client.rulebuilder;

import org.example.rule.entity.Action;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

public class UiActionsExtractor {
    private final List<Action> actions = new ArrayList<>();

    public List<Action> extractActions(VBox actionsContainer) {
        for (var actionNode : actionsContainer.getChildren()) {
            if (actionNode instanceof HBox actionRow) {

                ComboBox<Object> actionObjectSelector = (ComboBox<Object>) actionRow.getChildren().get(0);
                ComboBox<String> actionFieldSelector = (ComboBox<String>) actionRow.getChildren().get(1);
                TextField actionValue = (TextField) actionRow.getChildren().get(2);

                Object targetObject = actionObjectSelector.getValue();
                String fieldName = actionFieldSelector.getValue();
                String newValue = actionValue.getText();

                if (targetObject == null || fieldName == null || newValue == null || newValue.isEmpty()) {
                    throw new IllegalArgumentException("Действие содержит пустые или некорректные данные.");
                }

                Action action = new Action(targetObject, fieldName, newValue);
                actions.add(action);
            }
        }
        return actions;
    }
}
