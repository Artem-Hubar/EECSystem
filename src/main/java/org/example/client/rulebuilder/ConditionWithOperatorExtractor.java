package org.example.client.rulebuilder;

import org.example.client.controllers.ExpressionController;
import org.example.client.controllers.ExpressionsContainerController;
import org.example.client.controllers.parser.ConditionLogicOperatorParser;
import org.example.client.controllers.parser.ExpressionObjectListParser;
import org.example.client.controllers.parser.ExpressionParser;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Expression;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConditionWithOperatorExtractor {

    public @NotNull ConditionWithOperator extract(ExpressionsContainerController expressionContainer) {
        String logicalOperator = getLogicalOperator(expressionContainer);
        Expression expression = getExpression(expressionContainer);
        return new ConditionWithOperator(expression, logicalOperator);
    }

    private Expression getExpression(ExpressionsContainerController expressionContainer) {
        ExpressionExtractor expressionExtractor = new ExpressionExtractor();
        return expressionExtractor.extract(expressionContainer);
    }

    private @NotNull String getLogicalOperator(ExpressionsContainerController expressionContainer) {
        ConditionLogicOperatorParser logicOperatorParser = new ConditionLogicOperatorParser();
        return logicOperatorParser.parse(expressionContainer);
    }



}
