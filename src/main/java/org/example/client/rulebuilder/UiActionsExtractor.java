package org.example.client.rulebuilder;

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
            List<ExpressionsContainerController> expressionsContainerControllers = actionController.getExpressionsContainerControllers();
            for (ExpressionsContainerController expressionsContainerController : expressionsContainerControllers){
                ExpressionExtractor expressionExtractor = new ExpressionExtractor();

            }
        }

        return null;
    }

}
