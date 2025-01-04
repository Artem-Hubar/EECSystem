package org.example.client.controllers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import org.example.client.ruleparser.RuleSceneControllerParser;
import org.example.client.view.RuleContainerSceneView;
import org.example.rule.entity.Rule;
import org.example.service.RuleService;

import java.util.ArrayList;
import java.util.List;

public class RulesSceneController {

    List<Rule> rules;
    List<RuleContainerController> rulesSceneControllers = new ArrayList<>();

    public RulesSceneController() {
        RuleService ruleService = new RuleService();
        rules = ruleService.getAllRule();
    }

    @FXML
    private GridPane ruleGrid;

    @FXML
    private void initialize() {
        RuleSceneControllerParser ruleControllerParser = new RuleSceneControllerParser();
        int rowIndex = 0; // Индекс строки в GridPane


        for (Rule rule : rules) {
            GridPane hBox = getRuleNodeItem(rule, ruleControllerParser);
            ruleGrid.add(hBox, 0, rowIndex);
            GridPane.setHgrow(hBox, Priority.ALWAYS);
            rowIndex++;
        }
    }

    private GridPane getRuleNodeItem(Rule rule, RuleSceneControllerParser ruleControllerParser) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        Label idRule = new Label(String.valueOf(rule.getId()));
        Label descriptionRule = new Label(rule.getDescription());

        RuleContainerController controller = ruleControllerParser.getRuleSceneController(rule);
        rulesSceneControllers.add(controller);
        Node ruleSceneView = new RuleContainerSceneView(controller).getView();

        gridPane.add(idRule, 0, 0);
        gridPane.add(descriptionRule, 1, 0);
        gridPane.add(ruleSceneView, 2, 0);

        gridPane.setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-border-style: solid;");

        idRule.setStyle("-fx-border-bottom-color: white; -fx-border-bottom-width: 1px;");
        descriptionRule.setStyle("-fx-border-bottom-color: white; -fx-border-bottom-width: 1px;");
        ruleSceneView.setStyle("-fx-border-bottom-color: white; -fx-border-bottom-width: 1px;");

        GridPane.setHalignment(idRule, HPos.LEFT);
        GridPane.setHalignment(descriptionRule, HPos.LEFT);
        GridPane.setHgrow(ruleSceneView, Priority.ALWAYS);



        return gridPane;
    }


    private VBox getRuleLabelNode(Rule rule) {
        VBox vBox = new VBox();
        vBox.setSpacing(5); // Отступы между элементами внутри VBox
        Label idRule = new Label();
        Label descriptionRule = new Label();
        idRule.setText(String.valueOf(rule.getId()));
        descriptionRule.setText(rule.getDescription());
        vBox.getChildren().addAll(idRule, descriptionRule);
        return vBox;
    }



}
