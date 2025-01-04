package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.example.client.SceneManager;
import org.example.client.view.ActionView;
import org.example.client.view.ConditionalView;
import org.example.rule.RuleBuilder;
import org.example.service.RuleService;


import java.util.ArrayList;
import java.util.List;

public class RuleContainerController {
    @FXML
    private VBox conditionsContainer;
    @FXML
    private VBox actionsContainer;

    @Getter
    private ConditionalController conditionControllers;
    @Getter
    private final List<ActionController> actionControllers = new ArrayList<>();
    @Setter
    private ConditionalView conditionalView;
    private final List<ActionView> actionViews = new ArrayList<>();

    public RuleContainerController() {

    }

    @FXML
    private void initialize() {
        if (conditionalView != null){
            addConditionalView(conditionalView);
        }else {
            addCondition();
        }
        if (actionViews !=null){
            for (ActionView actionView : actionViews){
                addAction(actionView);
            }
        }
    }

    @FXML
    public void addCondition() {
        SceneManager sceneManager = new SceneManager();
        ConditionalView conditionalView = new ConditionalView();
        addConditionalView(conditionalView);
    }

    private void addConditionalView(ConditionalView conditionalView) {
        Parent conditionalScene = conditionalView.getView();
        ConditionalController conditionalModelView = conditionalView.getConditionalView();
        conditionsContainer.getChildren().add(conditionalScene);
        conditionControllers = conditionalModelView ;
    }


    @FXML
    public void onAddAction() {
        ActionView actionView = new ActionView();
        addAction(actionView);
    }

    private void addAction(ActionView actionView) {
        Parent actionScene = actionView.getView();
        ActionController actionController = actionView.getController();
        actionsContainer.getChildren().add(actionScene);
        actionControllers.add(actionController);
        actionController.setOnDelete(()->onDeleteAction(actionScene, actionController));
    }

    @FXML
    public void onDeleteAction(Parent expressionParent, ActionController actionController) {
        actionsContainer.getChildren().remove(expressionParent);
        actionControllers.remove(actionController);
    }

    private void clearConditional() {
        conditionControllers.getExpressionsContainerControllers().forEach(ExpressionsContainerController::onDeleteThisExpressionsContainer);
    }


    public void onAddActionController(ActionController actionController) {
        ActionView actionView = new ActionView(actionController);
        actionViews.add(actionView);
    }
}
