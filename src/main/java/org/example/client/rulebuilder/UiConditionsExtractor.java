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
        List<ConditionWithOperator> condition = new ArrayList<>();
        List<ExpressionsContainerController> expressionsContainerControllers = conditionsContainer.getExpressionsContainerControllers();
        for (ExpressionsContainerController expressionContainer : expressionsContainerControllers) {
            ConditionWithOperator conditionWithOperator = getConditionWithOperator(expressionContainer);
            condition.add(conditionWithOperator);
        }
        return condition;
    }

    private @NotNull ConditionWithOperator getConditionWithOperator(ExpressionsContainerController expressionContainer) {
        String logicalOperator = getLogicalOperator(expressionContainer);
        List<Object[]> expressionList = getExpressionList(expressionContainer);
        Expression expression = getExpression(expressionList);
        return new ConditionWithOperator(expression, logicalOperator);
    }

    private @NotNull String getLogicalOperator(ExpressionsContainerController expressionContainer) {
        ConditionLogicOperatorParser logicOperatorParser = new ConditionLogicOperatorParser();
        return logicOperatorParser.parse(expressionContainer);
    }


    private List<Object[]> getExpressionList(ExpressionsContainerController expressionContainer) {
        List<Object[]> expressions = new ArrayList<>();
        List<ExpressionController> expressionControllers = expressionContainer.getExpressionControllers();
        for (ExpressionController expressionController : expressionControllers) {
            Object targetObject = expressionController.getObject();
            ExpressionObjectListParser expressionObjectListParser = new ExpressionObjectListParser();
            Object[] expression = expressionObjectListParser.getExpression(targetObject, expressionController);
            expressions.add(expression);
        }
        return expressions;
    }

    private Expression getExpression(List<Object[]> expressionList) {
        ExpressionParser parser = new ExpressionParser();
        return parser.parseExpressionList(expressionList);
    }
}
