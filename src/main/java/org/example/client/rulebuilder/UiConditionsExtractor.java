package org.example.client.rulebuilder;

import org.example.client.controllers.ConditionalController;
import org.example.client.controllers.ExpressionController;
import org.example.client.controllers.ExpressionsContainerController;
import org.example.client.controllers.parser.ConditionLogicOperatorParser;
import org.example.client.controllers.parser.ExpressionObjectListParser;
import org.example.client.controllers.parser.ExpressionParser;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Expression;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UiConditionsExtractor {
    private final List<ConditionWithOperator> conditionsWithOperators = new ArrayList<>();

    public Collection<? extends ConditionWithOperator> extractConditions(ConditionalController conditionsContainer) {
        List<ExpressionsContainerController> expressionsContainerControllers = conditionsContainer.getExpressionsContainerControllers();
        return extracted(expressionsContainerControllers);
    }

    public List<ConditionWithOperator> extracted(List<ExpressionsContainerController>  expressionsContainerController) {
        List<ConditionWithOperator> condition = new ArrayList<>();
        for (ExpressionsContainerController expressionContainer : expressionsContainerController) {
            ConditionWithOperatorExtractor conditionExtractor = new ConditionWithOperatorExtractor();
            ConditionWithOperator conditionWithOperator = conditionExtractor.extract(expressionContainer);
            condition.add(conditionWithOperator);
        }
        return condition;
    }
}
