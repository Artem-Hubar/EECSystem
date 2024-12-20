package org.example.client.rulebuilder;

import org.example.rule.entity.Expression;

import java.util.List;
import java.util.Stack;

public class ExpressionParser {

    public Expression parseExpressionList(List<Object[]> expressionList) {
        // Создаем список выражений для каждого объекта
        Expression[] expressions = new Expression[expressionList.size()];
        for (int i = 0; i < expressionList.size(); i++) {
            Object[] entry = expressionList.get(i);
            expressions[i] = new Expression(entry[0], (String) entry[1]);
        }

        // Строим вложенное выражение
        Expression result = expressions[0];
        for (int i = 1; i < expressions.length; i++) {
            String operator = (String) expressionList.get(i - 1)[2];
            if (operator != null) {
                result = new Expression(result, operator, expressions[i]);
            }
        }

        return result;
    }
}