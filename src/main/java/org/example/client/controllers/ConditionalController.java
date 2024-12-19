package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.example.client.SceneManager;
import org.example.client.view.ExpressionsContainerView;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ConditionalController {
    private final List<Object> objects;
    List<ExpressionsContainerController> expressionsContainerControllers = new ArrayList<>();
    SceneManager sceneManager;
    @FXML
    VBox vBox;
    @FXML
    VBox conditionalBox;

    public ConditionalController(List<Object> objects) {
        this.sceneManager = new SceneManager();
        this.objects = objects;
    }

    @FXML
    private void initialize(){
        addConditional();
    }

    @FXML
    public void onAddConditional(){
        addConditional();
    }

    private void addConditional() {
        ExpressionsContainerView expressionsContainerView = new ExpressionsContainerView(objects);
        Parent expressionScene = expressionsContainerView.getView();
        ExpressionsContainerController expressionsContainerController = expressionsContainerView.getExpressionsContainerController();
        VBox conditionalItem = new VBox();
        conditionalItem.setId("itisVbox");
        if (!expressionsContainerControllers.isEmpty()){
            ChoiceBox<String> choiceBox = getChoiceLogicOperand();
            conditionalItem.getChildren().add(choiceBox);
            expressionsContainerController.setChoiceLogic(choiceBox);
        }
        conditionalItem.getChildren().add(expressionScene);
        conditionalBox.getChildren().add(conditionalItem);
        expressionsContainerController.setActionOnDelete(()->onDeleteConditional(conditionalItem, expressionsContainerController));
        expressionsContainerControllers.add(expressionsContainerController);
    }

    private void onDeleteConditional(Parent conditionalPane, ExpressionsContainerController expressionsContainerController){
        conditionalBox.getChildren().remove(conditionalPane);
        expressionsContainerControllers.remove(expressionsContainerController);
    }


    private ChoiceBox<String> getChoiceLogicOperand() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("AND", "OR");
        choiceBox.setValue("AND");
        return choiceBox;
    }




}
