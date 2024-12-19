package org.example.client.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import org.example.client.view.ExpressionView;
import org.example.entity.Device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ExpressionsContainerController {
    private final List<Object> objects;
    private final List<ExpressionController> expressionControllers = new ArrayList<>();
    @FXML
    private HBox deviceContainer;
    @FXML
    private ComboBox<Object> deviceChoiceBox = new ComboBox<>();
    @Setter
    private Runnable actionOnDelete;
    private ChoiceBox<String> choiceBox;


    public ExpressionsContainerController(List<Object> objects) {
        this.objects = objects;
    }

    @FXML
    public void initialize() {
        deviceChoiceBox.getItems().addAll(objects);
    }

    @FXML
    public void onAddObject() {
        Object object = deviceChoiceBox.getValue();
        addDevice(object);
    }

    private void addDevice(Object object) {
        String selectedOperator = null;
        if (!expressionControllers.isEmpty()) {
            selectedOperator = expressionControllers.getLast().getSelectedOperator();
        }
        ExpressionView expressionView = new ExpressionView(object, selectedOperator);
        Parent expressionPane = expressionView.getView();
        ExpressionController expressionController = expressionView.getExpressionModelView();
        DeviceViewController deviceViewController = expressionController.getDeviceViewController();
        deviceViewController.setActionOnDelete(() -> onDeleteChildExpression(expressionPane, expressionController));
        deviceContainer.getChildren().add(expressionPane);
        expressionControllers.add(expressionController);
    }

    public void onDeleteChildExpression(Parent expressionParent, ExpressionController expressionController) {
        deviceContainer.getChildren().remove(expressionParent);
        expressionControllers.remove(expressionController);
        System.out.println(expressionControllers);
    }

    @FXML
    public void onDeleteThisExpressionsContainer() {
        if (actionOnDelete != null) {
            actionOnDelete.run();
        }
    }

    public void setChoiceLogic(ChoiceBox<String> choiceBox) {
        this.choiceBox = choiceBox;
    }

//    @FXML
//    public void onDeleteThisExpressionsContainer() {
//        Node deviceNode = deviceChoiceBox.getParent();
//        Node conditionWithOperator = deviceNode.getParent();
//        Node conditionalBox = conditionWithOperator.getParent();
//        Node vBox = conditionalBox.getParent();
//        Pane pane = (Pane) vBox;
//        pane.getChildren().remove(conditionalBox);
//    }
}
