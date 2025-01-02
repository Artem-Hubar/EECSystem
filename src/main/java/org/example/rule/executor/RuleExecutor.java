package org.example.rule.executor;

import org.example.rule.entity.*;

import java.util.List;
import java.util.NoSuchElementException;

public class RuleExecutor {
    private final Rule rule;

    public RuleExecutor(Rule rule) {
        this.rule = rule;
    }

    public void execute() {
        List<Action> actions = rule.getActions();
        if (conditionsMet(0, true)) {
            System.out.println(rule.getDescription());
            for (Action action : actions) {
                performAction(action);
            }
        } else {
        }
    }

    private boolean conditionsMet(int index, boolean accumulatedResult) {
        List<ConditionWithOperator> conditionsWithOperators = rule.getConditionsWithOperators();
        if (index >= conditionsWithOperators.size()) {
            return accumulatedResult;
        }

        ConditionWithOperator current = conditionsWithOperators.get(index);
        boolean conditionResult = evaluateCondition(current.getCondition());

        String logicalOperator = current.getLogicalOperator();
        boolean newResult;


        if ("AND".equalsIgnoreCase(logicalOperator)) {
            newResult = accumulatedResult && conditionResult;
        } else if ("OR".equalsIgnoreCase(logicalOperator)) {
            newResult = accumulatedResult || conditionResult;
        } else {
            throw new IllegalStateException("Неизвестный логический оператор: " + logicalOperator);
        }

        return conditionsMet(index + 1, newResult);
    }

    private boolean evaluateCondition(Expression condition) {
        try {
            Object object = condition.evaluate();
            return (Boolean) object ;
        }catch (Exception e){
            throw new NoSuchElementException(e);
        }

    }

    private void performAction(Action action) {
        ActionExecutor actionExecutor = new DefaultActionExecutor();
        actionExecutor.execute(action);
    }
}
