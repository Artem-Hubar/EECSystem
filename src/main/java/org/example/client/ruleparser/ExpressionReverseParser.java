package org.example.client.ruleparser;

import org.example.rule.entity.Expression;

import java.util.ArrayList;
import java.util.List;

public class ExpressionReverseParser {
    public List<Object[]> reverseParseExpression(Expression expression) {
        List<Object[]> expressionList = new ArrayList<>();
        parseExpression(expression, null, expressionList);
        return expressionList;
    }

    private void parseExpression(Expression expression, String operator, List<Object[]> expressionList) {
        if (expression.getLeftOperand() == null && expression.getRightOperand() == null) {
            // Терминальный узел
            Object targetObject = expression.getTargetObject();
            String method = expression.getMethodName(); // Метод (если есть)
            expressionList.add(new Object[]{targetObject, method, operator});
        } else {
            if (expression.getLeftOperand() != null) {
                parseExpression(expression.getLeftOperand(), expression.getOperator(), expressionList);
            }

            if (expression.getRightOperand() != null) {
                parseExpression(expression.getRightOperand(), operator, expressionList);
            }
        }
    }


}
