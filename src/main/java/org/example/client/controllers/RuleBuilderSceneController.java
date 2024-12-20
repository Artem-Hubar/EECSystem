package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import org.example.client.SceneManager;
import org.example.client.view.ActionView;
import org.example.client.view.ConditionalView;
import org.example.rule.RuleBuilder;
import org.example.service.RuleService;


import java.util.ArrayList;
import java.util.List;

public class RuleBuilderSceneController {
    @FXML
    private VBox conditionsContainer;
    @FXML
    private VBox actionsContainer;
    private final List<Object> objects;
    private ConditionalController conditionControllers;
    private final List<ActionController> actionControllers = new ArrayList<>();

    public RuleBuilderSceneController(List<Object> objects) {
        this.objects = objects;
    }

    @FXML
    private void initialize() {
        addCondition();
    }

    @FXML
    public void addCondition() {
        SceneManager sceneManager = new SceneManager();
        ConditionalView conditionalView = new ConditionalView(objects);
        Parent conditionalScene = conditionalView.getView();
        ConditionalController conditionalModelView = conditionalView.getConditionalView();
        conditionsContainer.getChildren().add(conditionalScene);
        conditionControllers =conditionalModelView ;
    }

    @FXML
    public void onAddAction() {
        ActionView actionView = new ActionView(objects);
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



    @FXML
    public void onSaveRule() {

        RuleBuilder ruleBuilder = new RuleBuilder()
                .fromUi(conditionControllers, actionControllers);
        RuleService ruleService = new RuleService();
        ruleService.saveRule(ruleBuilder.build());



        System.out.println("Rule successfully added!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Rule successfully added!");
        alert.showAndWait();

        clearConditional();
        actionsContainer.getChildren().clear();
    }

    private void clearConditional() {
        conditionControllers.getExpressionsContainerControllers().forEach(ExpressionsContainerController::onDeleteThisExpressionsContainer);
    }
}
