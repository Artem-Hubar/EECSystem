package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import lombok.Getter;
import org.example.client.controllers.expression.object.ExpressionObjectController;
import org.example.client.factory.ObjectViewFactory;
import org.example.client.view.expression.object.DeviceView;
import org.example.client.view.expression.object.ObjectView;
import org.example.entity.Device;

@Getter
public class ExpressionController {

    private final String selectedOperator;
    @FXML
    private HBox expressionsBox;
    private ObjectView objectView;
    ChoiceBox<String> choiceBox = new ChoiceBox<>();;
    private final ExpressionObjectController expressionObjectController;

    public Object getObject(){
        return expressionObjectController.getObject();
    }

    public ExpressionController(Object object, String selectedOperator) {
        this.selectedOperator = selectedOperator;
        ObjectViewFactory objectViewFactory = new ObjectViewFactory();
        objectView = objectViewFactory.getObjectView(object);
        expressionObjectController = objectView.getExpressionObjectController();
    }
    @FXML
    public void initialize() {
        expressionsBox.getChildren().add(objectView.getView());
        if (isNotEnd()) {
            choiceBox.getItems().addAll("+", "-", "/", "*", "==", ">", "<", ">=", "<=");
            expressionsBox.getChildren().add(choiceBox);
        }

    }

    private boolean isNotEnd() {
        if (selectedOperator == null) return true;
        if (
                selectedOperator.equals("==") ||
                selectedOperator.equals("<") ||
                selectedOperator.equals(">") ||
                selectedOperator.equals(">=") ||
                selectedOperator.equals("<=")) {
            return false;
        }
        return true;
    }
}
