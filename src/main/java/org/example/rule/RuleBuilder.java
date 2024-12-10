package org.example.rule;

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
        System.out.println("Добавление действия: объект=" + action.getTargetObject() + ", поле=" + action.getFieldName() + ", значение=" + action.getNewValue());
        actions.add(action);
        return this;
    }

    // Построение правила
    public Rule build() {
        System.out.println("Построение правила с " + conditionsWithOperators.size() + " условиями и " + actions.size() + " действиями.");
        return new Rule(conditionsWithOperators, actions);
    }
}
