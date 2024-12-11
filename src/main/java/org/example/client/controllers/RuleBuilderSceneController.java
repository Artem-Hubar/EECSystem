package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.example.client.entity.ActionUI;
import org.example.client.entity.ConditionUI;
import org.example.rule.RuleBuilder;
import org.example.service.RuleService;


import java.util.List;

public class RuleBuilderSceneController {

    @FXML
    private VBox conditionsContainer;

    @FXML
    private VBox actionsContainer;

    private final List<Object> objects;

    public RuleBuilderSceneController(List<Object> objects) {
        this.objects = objects;
    }

    @FXML
    public void onAddCondition() {
        ConditionUI conditionUI = new ConditionUI(objects, this::onDeleteCondition);
        Node conditionRow = conditionUI.createConditionRow();
        conditionsContainer.getChildren().add(conditionRow);
    }

    @FXML
    public void onDeleteCondition() {
        if (!conditionsContainer.getChildren().isEmpty()) {
            conditionsContainer.getChildren().removeLast();
        }
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

    @FXML
    public void onSaveRule() {
        RuleBuilder ruleBuilder = new RuleBuilder()
                .fromUi(conditionsContainer, actionsContainer);
        RuleService ruleService = new RuleService();
        ruleService.saveRule(ruleBuilder.build());
        System.out.println("Rule successfully added!");
    }
}
