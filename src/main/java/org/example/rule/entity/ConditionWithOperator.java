package org.example.rule.entity;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Data
@ToString
public class ConditionWithOperator {
    private Expression condition;
    private String logicalOperator;

    public ConditionWithOperator() {
    }

    public ConditionWithOperator(Expression condition, String logicalOperator) {
        this.condition = condition;
        this.logicalOperator = logicalOperator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionWithOperator that = (ConditionWithOperator) o;
        return Objects.equals(condition, that.condition) && Objects.equals(logicalOperator, that.logicalOperator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, logicalOperator);
    }
}