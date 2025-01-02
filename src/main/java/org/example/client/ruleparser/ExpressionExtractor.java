package org.example.client.ruleparser;

import org.example.client.controllers.ExpressionController;
import org.example.client.controllers.ExpressionsContainerController;
import org.example.client.controllers.parser.ExpressionObjectListParser;
import org.example.client.controllers.parser.ExpressionParser;
import org.example.rule.entity.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionExtractor {
    public Expression extract(ExpressionsContainerController expressionContainer){
        List<Object[]> expressionList = getExpressionList(expressionContainer);
        for (Object[] objects : expressionList){
            System.out.println(Arrays.toString(objects));
//            System.out.printf("%s %s %s\n", objects[0], objects[1], objects[2]);
        }

        return getExpression(expressionList);
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
