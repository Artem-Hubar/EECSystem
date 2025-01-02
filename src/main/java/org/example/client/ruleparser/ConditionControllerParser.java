package org.example.client.ruleparser;

import org.example.client.controllers.ConditionalController;
import org.example.client.controllers.ExpressionsContainerController;
import org.example.client.view.ExpressionsContainerView;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Expression;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConditionControllerParser {
    public @NotNull ConditionalController extract(List<ConditionWithOperator> conditionWithOperators) {
        ConditionalController controller = new ConditionalController();
        ExpressionContainerControllerParser expressionParser = new ExpressionContainerControllerParser();
        for (ConditionWithOperator conditionWithOperator : conditionWithOperators) {
            Expression expression = conditionWithOperator.getCondition();
            ExpressionsContainerController expressionsContainerController = expressionParser.getExpressionContainer(expression);
            ExpressionsContainerView expressionsContainerView = new ExpressionsContainerView(expressionsContainerController);
            controller.addExpressionView(expressionsContainerView, conditionWithOperator.getLogicalOperator());
        }
        return controller;
    }
}
