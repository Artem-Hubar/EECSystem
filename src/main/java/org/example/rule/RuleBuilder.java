package org.example.rule;

import org.example.client.controllers.ActionController;
import org.example.client.controllers.ConditionalController;
import org.example.client.ruleparser.UiActionsExtractor;
import org.example.client.ruleparser.UiConditionsExtractor;
import org.example.rule.entity.Action;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleBuilder {
    private List<ConditionWithOperator> conditionsWithOperators = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();

    // Добавление условия с логическим оператором
    public RuleBuilder addCondition(ConditionWithOperator conditionWithOperator) {
        if (conditionsWithOperators.isEmpty() && !"AND".equalsIgnoreCase(conditionWithOperator.getLogicalOperator())) {
            throw new IllegalStateException("Первое условие должно начинаться с оператора 'AND'.");
        }
        conditionsWithOperators.add(conditionWithOperator);
        return this;
    }

    // Добавление действия
    public RuleBuilder addAction(Action action) {
        System.out.println("Добавление действия: объект=" + action.getTargetObject() + ", поле=" + action.getFieldName() + ", значение=" + action.getExpression().evaluate());
        actions.add(action);
        return this;
    }

    // Построение правила
    public Rule build() {
        System.out.println("Построение правила с " + conditionsWithOperators.size() + " условиями и " + actions.size() + " действиями.");
        return new Rule(conditionsWithOperators, actions);
    }

    public RuleBuilder fromUi(ConditionalController conditionalControllers, List<ActionController> actionControllers) {
        List<ConditionWithOperator> conditionsFromUi = getConditionsFromUi(conditionalControllers);
        System.out.println("conditionsFromUi " +conditionsFromUi);
        List<Action> actionsFromUi = getActionsFromUi(actionControllers);
        conditionsFromUi.forEach(this::addCondition);
        actionsFromUi.forEach(this::addAction);
        return this;
    }

    private List<Action> getActionsFromUi(List<ActionController> actionsContainer) {
        System.out.println(actionsContainer);
        UiActionsExtractor uiActionsExtractor = new UiActionsExtractor();
        return uiActionsExtractor.extractActions(actionsContainer);
    }

    private List<ConditionWithOperator> getConditionsFromUi(ConditionalController conditionsContainer) {
        UiConditionsExtractor uiConditionsExtractor = new UiConditionsExtractor();
        return new ArrayList<>(uiConditionsExtractor.extractConditions(conditionsContainer));
    }



}
