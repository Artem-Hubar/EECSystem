package org.example.client.ruleparser;

import org.example.client.controllers.ExpressionsContainerController;
import org.example.client.controllers.parser.ConditionLogicOperatorParser;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Expression;
import org.jetbrains.annotations.NotNull;

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
