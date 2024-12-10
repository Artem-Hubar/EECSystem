package org.example.rule.entity;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class Rule {
    private long id;
    private List<ConditionWithOperator> conditionsWithOperators;
    private List<Action> actions;
    private Instant timeStamp;

    public Rule() {
    }

    public Rule(List<ConditionWithOperator> conditionsWithOperators, List<Action> actions) {
        this.conditionsWithOperators = conditionsWithOperators;
        this.actions = actions;
//        System.out.println("Создано правило с " + conditionsWithOperators.size() + " условиями и " + actions.size() + " действиями.");
    }
}
