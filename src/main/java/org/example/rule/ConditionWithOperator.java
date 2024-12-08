package org.example.rule;

class ConditionWithOperator {
    private Condition condition;
    private String logicalOperator;

    public ConditionWithOperator(Condition condition, String logicalOperator) {
        this.condition = condition;
        this.logicalOperator = logicalOperator;
    }

    public Condition getCondition() {
        return condition;
    }

    public String getLogicalOperator() {
        return logicalOperator;
    }
}