package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import org.example.client.view.RuleContainerSceneView;
import org.example.rule.RuleBuilder;
import org.example.service.RuleService;

import java.util.List;


public class RuleBuilderSceneController {
    @FXML
    private VBox ruleContainer;

    RuleContainerController ruleContainerController;
    RuleContainerSceneView ruleContainerSceneView;

    public RuleBuilderSceneController() {

    }

    @FXML
    public void initialize(){
        this.ruleContainerSceneView =  new RuleContainerSceneView();
        this.ruleContainerController = ruleContainerSceneView.getController();
        Parent parent = ruleContainerSceneView.getView();
        this.ruleContainer.getChildren().add(parent);
    }

    @FXML
    public void onSaveRule() {
        ConditionalController conditionControllers= ruleContainerController.getConditionControllers();
        List<ActionController> actionControllers = ruleContainerController.getActionControllers();
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

    }
}