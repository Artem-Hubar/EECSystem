package org.example.rule;

import java.util.ArrayList;
import java.util.List;

public class RuleBuilder {
    private List<ConditionWithOperator> conditionsWithOperators = new ArrayList<>(); // Список условий с операторами
    private List<Action> actions = new ArrayList<>();                                // Список действий

    // Добавление условия с логическим оператором
    public RuleBuilder addCondition(Object object, String field, String operator, Object value, String logicalOperator) {
        if (conditionsWithOperators.isEmpty() && !"AND".equalsIgnoreCase(logicalOperator)) {
            throw new IllegalStateException("Первое условие должно начинаться с оператора 'AND'.");
        }

        System.out.println("Добавление условия: объект=" + object + ", поле=" + field +
                ", оператор=" + operator + ", значение=" + value +
                ", логический оператор=" + logicalOperator);

        conditionsWithOperators.add(new ConditionWithOperator(new Condition(object, field, operator, value), logicalOperator));
        return this;
    }

    // Добавление действия
    public RuleBuilder addAction(Object object, String field, Object value) {
        System.out.println("Добавление действия: объект=" + object + ", поле=" + field + ", значение=" + value);
        actions.add(new Action(object, field, value));
        return this;
    }

    // Построение правила
    public Rule build() {
        System.out.println("Построение правила с " + conditionsWithOperators.size() + " условиями и " + actions.size() + " действиями.");
        return new Rule(conditionsWithOperators, actions);
    }
}
