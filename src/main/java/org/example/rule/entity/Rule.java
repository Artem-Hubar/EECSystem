package org.example.rule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Rule {
    @JsonIgnore
    private long id;
    private List<ConditionWithOperator> conditionsWithOperators;
    private List<Action> actions;
    @JsonIgnore
    private LocalDateTime timeStamp;

    public Rule() {
    }

    public Rule(List<ConditionWithOperator> conditionsWithOperators, List<Action> actions) {
        this.conditionsWithOperators = conditionsWithOperators;
        this.actions = actions;
//        System.out.println("Создано правило с " + conditionsWithOperators.size() + " условиями и " + actions.size() + " действиями.");
    }
}