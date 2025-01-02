package org.example.client.ruleparser;

import org.example.client.controllers.ActionController;
import org.example.client.controllers.ExpressionsContainerController;
import org.example.client.view.ActionView;
import org.example.client.view.ExpressionsContainerView;
import org.example.rule.entity.Action;
import org.example.rule.entity.Expression;

public class ActionControllerParser {
    public ActionController parse(Action action) {
        ActionController actionController = new ActionController();
        actionController.setSelectedObject(action.getTargetObject());
        actionController.setSelectedField(action.getFieldName());
        Expression expression = action.getExpression();
        ExpressionContainerControllerParser expressionParser = new ExpressionContainerControllerParser();
        ExpressionsContainerController expressionsContainerController = expressionParser.getExpressionContainer(expression);
        ExpressionsContainerView expressionsContainerView = new ExpressionsContainerView(expressionsContainerController);
        actionController.setExpressionsContainerView(expressionsContainerView);
        return actionController;
    }

}
