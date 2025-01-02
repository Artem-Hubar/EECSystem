package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;
import org.example.client.controllers.expression.object.DeviceController;
import org.example.client.controllers.expression.object.ExpressionObjectController;
import org.example.client.controllers.expression.object.TextFieldController;
import org.example.client.factory.ObjectViewFactory;
import org.example.client.view.expression.object.ObjectView;
import org.example.client.view.expression.object.TextFieldView;

@Getter
public class ExpressionController {

    private String lastOperator;
    @FXML
    private HBox expressionsBox;
    private ObjectView objectView;
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    ;
    private ExpressionObjectController expressionObjectController;
    @Setter
    private String selectedOperator;

    public Object getObject() {
        return expressionObjectController.getObject();
    }

    public ExpressionController(Object object) {

        if (object instanceof Number number){
            TextFieldController textFieldController = new TextFieldController();
            textFieldController.setInitValue((Double) number);
            TextFieldView textFieldView = new TextFieldView(textFieldController);
            this.expressionObjectController = textFieldView.getExpressionObjectController();
            this.objectView = textFieldView;
        }else {
            objectInit(object);
        }
    }

    public ExpressionController(Object object, String lastOperator) {
        objectInit(object);
        this.lastOperator = lastOperator;
    }

    private void objectInit(Object object) {
        ObjectViewFactory objectViewFactory = new ObjectViewFactory();
        this.objectView = objectViewFactory.getObjectView(object);
        this.expressionObjectController = objectView.getExpressionObjectController();
    }

    @FXML
    public void initialize() {
        expressionsBox.getChildren().add(objectView.getView());
        if (selectedOperator != null) {
            choiceBox.getItems().addAll("+", "-", "/", "*", "==", ">", "<", ">=", "<=");
            choiceBox.setValue(selectedOperator);
            expressionsBox.getChildren().add(choiceBox);
        } else if(isNotEnd()) {
            choiceBox.getItems().addAll("+", "-", "/", "*", "==", ">", "<", ">=", "<=");
            expressionsBox.getChildren().add(choiceBox);
        }
    }

    public void setDeviceMethod(String methodName) {
        ((DeviceController) expressionObjectController).setMethod(methodName);
    }

    private boolean isNotEnd() {
        if (lastOperator == null) return true;
        if (
                lastOperator.equals("==") ||
                        lastOperator.equals("<") ||
                        lastOperator.equals(">") ||
                        lastOperator.equals(">=") ||
                        lastOperator.equals("<=")) {
            return false;
        }
        return true;
    }
}
