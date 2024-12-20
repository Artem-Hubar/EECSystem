package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.client.SceneManager;
import org.example.client.entity.ActionUI;
import org.example.client.view.ConditionalView;
import org.example.rule.RuleBuilder;
import org.example.service.RuleService;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RuleBuilderSceneController {

    @FXML
    private VBox conditionsContainer;

    @FXML
    private VBox actionsContainer;

    private final List<Object> objects;

    private ConditionalController conditionControllers;

    @FXML
    Pane toolBarPane;


    public RuleBuilderSceneController(List<Object> objects) {
        this.objects = objects;
    }



    @FXML
    private void initialize() {
//        addToolBar();
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
        ActionUI actionUI = new ActionUI(objects, this::onDeleteAction);
        Node actionRow = actionUI.createActionRow();
        actionsContainer.getChildren().add(actionRow);
    }

    @FXML
    public void onDeleteAction() {
        if (!actionsContainer.getChildren().isEmpty()) {
            actionsContainer.getChildren().removeLast();
        }
    }


    private List<ConditionalController> getConditionalList(){
        List<ConditionalController> conditionalModelViews = new ArrayList<>();
        for (Node node : conditionsContainer.getChildren()){
            System.out.println(node);
            ConditionalController model = (ConditionalController) node.getUserData();
            System.out.println(3);
            if (model != null) { // Защита от null
                System.out.println(4);
                conditionalModelViews.add(model);
            }
        }
        return conditionalModelViews;
    }

    @FXML
    public void onSaveRule() {

        RuleBuilder ruleBuilder = new RuleBuilder()
                .fromUi(conditionControllers, actionsContainer);
        RuleService ruleService = new RuleService();
        ruleService.saveRule(ruleBuilder.build());


        System.out.println(getConditionalList());

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
