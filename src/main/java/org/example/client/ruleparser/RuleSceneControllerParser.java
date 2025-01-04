package org.example.client.ruleparser;

import org.example.client.controllers.ActionController;
import org.example.client.controllers.ConditionalController;
import org.example.client.controllers.RuleContainerController;
import org.example.client.view.ConditionalView;
import org.example.rule.entity.Action;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Rule;

import java.util.List;

public class RuleSceneControllerParser {
    public RuleContainerController getRuleSceneController(Rule rule){
        RuleContainerController ruleBuilderSceneController = new RuleContainerController();
        ConditionalController controller = getConditionController(rule);
        ConditionalView conditionalView = new ConditionalView(controller);
        ActionControllerParser actionControllerParser = new ActionControllerParser();
        List<Action> actions = rule.getActions();
        for (Action action : actions){
            ActionController actionController = actionControllerParser.parse(action);
            ruleBuilderSceneController.onAddActionController(actionController);
        }
        ruleBuilderSceneController.setConditionalView(conditionalView);

        System.out.printf("\n{%s\n%s\n%s\n%s\n}\n", ruleBuilderSceneController, controller, conditionalView, actions);
        return ruleBuilderSceneController;
    }

    private ConditionalController getConditionController(Rule rule) {
        List<ConditionWithOperator> conditions = rule.getConditionsWithOperators();
        ConditionControllerParser conditionControllerParser = new ConditionControllerParser();
        return conditionControllerParser.extract(conditions);
    }
}
