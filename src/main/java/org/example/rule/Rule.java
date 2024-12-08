package org.example.rule;

import lombok.Data;

import java.util.List;

@Data
public class Rule {
    private List<ConditionWithOperator> conditionsWithOperators;
    private List<Action> actions;

    public Rule(List<ConditionWithOperator> conditionsWithOperators, List<Action> actions) {
        this.conditionsWithOperators = conditionsWithOperators;
        this.actions = actions;
//        System.out.println("Создано правило с " + conditionsWithOperators.size() + " условиями и " + actions.size() + " действиями.");
    }
}
