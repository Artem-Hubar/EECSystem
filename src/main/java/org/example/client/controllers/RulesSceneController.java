package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.example.client.ruleparser.ExpressionContainerControllerParser;

import org.example.client.ruleparser.RuleSceneControllerParser;
import org.example.client.view.ExpressionsContainerView;
import org.example.client.view.RuleSceneView;
import org.example.rule.entity.Expression;
import org.example.rule.entity.Rule;
import org.example.service.RuleService;

import java.util.ArrayList;
import java.util.List;

public class RulesSceneController{
    @FXML
    private VBox ruleContainer;
    List<Rule> rules;
    List<RuleBuilderSceneController> rulesSceneControllers = new ArrayList<>();

    public RulesSceneController() {
        RuleService ruleService = new RuleService();
        rules = ruleService.getAllRule();
    }

    @FXML
    private void initialize(){
        RuleSceneControllerParser ruleControllerParser = new RuleSceneControllerParser();
        for (Rule rule : rules){
            RuleBuilderSceneController controller = ruleControllerParser.getRuleSceneController(rule);
            RuleSceneView ruleSceneView = new RuleSceneView(controller);
            ruleContainer.getChildren().add(ruleSceneView.getView());
            rulesSceneControllers.add(controller);
        }
    }
}
