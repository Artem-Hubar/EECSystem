package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.example.client.SceneManager;
import org.example.client.service.ObjectService;
import org.example.client.view.ExpressionsContainerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ConditionalController {
    private final List<Object> objects;
    List<ExpressionsContainerController> expressionsContainerControllers = new ArrayList<>();
    SceneManager sceneManager;
    @FXML
    VBox vBox;
    @FXML
    VBox conditionalBox;

    Map<ExpressionsContainerView, String> expressionContainers = new HashMap<>();

    public ConditionalController() {
        this.sceneManager = new SceneManager();
        ObjectService objectService = new ObjectService();
        this.objects = objectService.getObjects();
    }

    @FXML
    private void initialize() {
        if (expressionContainers!= null){
            for(Map.Entry<ExpressionsContainerView, String> entry : expressionContainers.entrySet()){
                addExpressionsContainer(entry.getKey(), entry.getValue());
            }
        }else {
            addConditional();
        }
    }

    @FXML
    public void onAddConditional() {
        addConditional();
    }

    private void addConditional() {
        ExpressionsContainerView expressionsContainerView = new ExpressionsContainerView(objects);
        addExpressionsContainer(expressionsContainerView, null);
    }

    public void addExpressionsContainer(ExpressionsContainerView expressionsContainerView, String selectedLogicOperator) {
        ExpressionsContainerController expressionsContainerController = expressionsContainerView.getExpressionsContainerController();
        expressionsContainerController.addRemoveButton();
        Parent expressionScene = expressionsContainerView.getView();
        VBox conditionalItem = new VBox();
        conditionalItem.setId("itisVbox");
        if (!expressionsContainerControllers.isEmpty()) {
            ChoiceBox<String> choiceBox = getChoiceLogicOperand();
            conditionalItem.getChildren().add(choiceBox);
            expressionsContainerController.setChoiceLogic(choiceBox);
            if (selectedLogicOperator != null && !selectedLogicOperator.isEmpty()){
                choiceBox.setValue(selectedLogicOperator);
            }
        }
        conditionalItem.getChildren().add(expressionScene);
        conditionalBox.getChildren().add(conditionalItem);
        expressionsContainerController.setActionOnDelete(() -> onDeleteConditional(conditionalItem, expressionsContainerController));
        expressionsContainerControllers.add(expressionsContainerController);
    }

    public void onDeleteConditional(Parent conditionalPane, ExpressionsContainerController expressionsContainerController) {
        conditionalBox.getChildren().remove(conditionalPane);
        expressionsContainerControllers.remove(expressionsContainerController);
    }


    private ChoiceBox<String> getChoiceLogicOperand() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("AND", "OR");
        choiceBox.setValue("AND");
        return choiceBox;
    }


    public void addExpressionsContainer(ExpressionsContainerController expressionsContainerController) {
        expressionsContainerControllers.add(expressionsContainerController);
        ExpressionsContainerView containerView = new ExpressionsContainerView(expressionsContainerController);
        Parent parent = containerView.getView();
        expressionsContainerController.setActionOnDelete(()->this.onDeleteConditional(parent, expressionsContainerController));
        expressionsContainerController.addRemoveButton();
    }

    public void addExpressionView(ExpressionsContainerView expressionsContainerView, String logicalOperator) {
        expressionContainers.put(expressionsContainerView, logicalOperator);
    }
}
