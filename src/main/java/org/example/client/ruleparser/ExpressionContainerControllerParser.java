package org.example.client.ruleparser;

import org.example.client.controllers.ExpressionController;
import org.example.client.controllers.ExpressionsContainerController;
import org.example.rule.entity.Expression;

import java.util.ArrayList;
import java.util.List;

public class ExpressionContainerControllerParser {
    public ExpressionsContainerController getExpressionContainer(Expression expression){
        ExpressionReverseParser expressionReverseParser = new ExpressionReverseParser();
        List<Object[]> expressions = expressionReverseParser.reverseParseExpression(expression);
        ArrayList<ExpressionController> expressionControllers = new ArrayList<>();
        for(Object[] expressionObject : expressions){
            ObjectExpressionControllerParser objectExpressionControllerParser = new ObjectExpressionControllerParser();
            ExpressionController expressionController = objectExpressionControllerParser.getExpressionController(expressionObject);
            expressionControllers.add(expressionController);
        }
        ExpressionsContainerController expressionsContainerController = new ExpressionsContainerController();
        expressionsContainerController.setExpressionControllers(expressionControllers);
        return expressionsContainerController;
    }
}
