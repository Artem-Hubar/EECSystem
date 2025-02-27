package org.example.client.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import lombok.Getter;
import lombok.Setter;
import org.example.client.controllers.expression.object.ExpressionObjectController;
import org.example.client.service.ObjectService;
import org.example.client.view.ExpressionView;
import org.example.entity.Device;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExpressionsContainerController {
    private final List<Object> objects;


    @Setter
    private List<ExpressionController> expressionControllers = new ArrayList<>();
    @FXML
    private HBox deviceContainer;
    @FXML
    private ComboBox<Object> deviceChoiceBox = new ComboBox<>();
    @Setter
    private Runnable actionOnDelete;
    private ChoiceBox<String> choiceBox;
    private final ObservableList<Object> objectsObservable = FXCollections.observableArrayList();
    @FXML
    HBox controlElement;



    public ExpressionsContainerController() {
        ObjectService objectService = ObjectService.getInstance();
        this.objects = objectService.getObjects();
    }

    @FXML
    public void initialize() {
        deviceChoiceBox.setItems(objectsObservable);
        objectsObservable.addAll(objects);
        if (expressionControllers != null){
            for (ExpressionController expressionController : expressionControllers) {
                ExpressionView expressionView = new ExpressionView(expressionController);
                Parent expressionPane = expressionView.getView();
                ExpressionObjectController deviceViewController = expressionController.getExpressionObjectController();
                deviceViewController.setActionOnDelete(() -> onDeleteChildExpression(expressionPane, expressionController));
                deviceContainer.getChildren().add(expressionPane);
            }
        }
    }

    @FXML
    public void onAddValue(){
        String textField= "textField";
        addObject(textField);
        Device device = (Device) objects.getFirst();
        device.updateDevice();
    }

    @FXML
    public void onAddObject() {
        Object object = deviceChoiceBox.getValue();
        addObject(object);
    }


    private void addObject(Object object) {
        String selectedOperator = null;
        if (!this.expressionControllers.isEmpty()) {
            selectedOperator = this.expressionControllers.getLast().getChoiceBox().getValue();
        }
        ExpressionView expressionView = new ExpressionView(object, selectedOperator);
        fillContainer(expressionView);
    }

    private void fillContainer(ExpressionView expressionView) {
        Parent expressionPane = expressionView.getView();
        ExpressionController expressionController = expressionView.getExpressionModelView();
        ExpressionObjectController deviceViewController = expressionController.getExpressionObjectController();
        deviceViewController.setActionOnDelete(() -> onDeleteChildExpression(expressionPane, expressionController));
        deviceContainer.getChildren().add(expressionPane);
        expressionControllers.add(expressionController);
    }

    public void onDeleteChildExpression(Parent expressionParent, ExpressionController expressionController) {
        deviceContainer.getChildren().remove(expressionParent);
        expressionControllers.remove(expressionController);
    }


    @FXML
    public void onDeleteThisExpressionsContainer() {
        if (actionOnDelete != null) {
            actionOnDelete.run();
        }
    }

    public void addRemoveButton(){
        Button onDeleteThisExpressionsContainerButton = new Button("Delete");
        onDeleteThisExpressionsContainerButton.setMnemonicParsing(false); // Отключаем обработку мнемоник
        onDeleteThisExpressionsContainerButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-size: 14px;"); // Стили
        onDeleteThisExpressionsContainerButton.setOnAction(_ ->onDeleteThisExpressionsContainer());
        controlElement.getChildren().add(onDeleteThisExpressionsContainerButton);
    }

    public void setChoiceLogic(ChoiceBox<String> choiceBox) {
        this.choiceBox = choiceBox;
    }

}
