package org.example.client.ruleparser;

import org.example.client.controllers.ActionController;
import org.example.client.controllers.ExpressionsContainerController;
import org.example.rule.entity.Action;
import org.example.rule.entity.Expression;

import java.util.ArrayList;
import java.util.List;

public class UiActionsExtractor {
    private final List<Action> actions = new ArrayList<>();
    public List<Action> extractActions(List<ActionController> actionControllers) {
        for (ActionController actionController : actionControllers){
            Object targetObject = actionController.getActionObjectSelector().getValue();
            String method = actionController.getActionFieldSelector().getValue();
            ExpressionsContainerController expressionsContainerControllers = actionController.getExpressionsContainerControllers();
            ExpressionExtractor expressionExtractor = new ExpressionExtractor();
            Expression expression = expressionExtractor.extract(expressionsContainerControllers);
            actions.add(new Action(targetObject, method, expression));
        }
        return actions;
    }

}
