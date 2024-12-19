package org.example.rule.executor;

import org.example.rule.entity.Action;
import org.example.rule.entity.Condition;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Rule;

import java.util.List;
import java.util.NoSuchElementException;

public class RuleExecutor {
    private final Rule rule;

    public RuleExecutor(Rule rule) {
        this.rule = rule;
    }

    public void execute() {
        List<Action> actions = rule.getActions();
        System.out.println("Выполнение правила...");
        if (conditionsMet(0, true)) {
            System.out.println("Все условия выполнены. Выполняем действия.");
            for (Action action : actions) {
                performAction(action);
            }
        } else {
            System.out.println("Условия не выполнены. Действия не будут выполнены.");
        }
    }

    private boolean conditionsMet(int index, boolean accumulatedResult) {
//        List<ConditionWithOperator> conditionsWithOperators = rule.getConditionsWithOperators();
//        if (index >= conditionsWithOperators.size()) {
//            return accumulatedResult;
//        }
//
//        ConditionWithOperator current = conditionsWithOperators.get(index);
//        boolean conditionResult = evaluateCondition(current.getCondition());
//        System.out.println("Результат условия " + current.getCondition() + ": " + conditionResult);
//
//        String logicalOperator = current.getLogicalOperator();
//        boolean newResult;
//
//
//        if ("AND".equalsIgnoreCase(logicalOperator)) {
//            newResult = accumulatedResult && conditionResult;
//        } else if ("OR".equalsIgnoreCase(logicalOperator)) {
//            newResult = accumulatedResult || conditionResult;
//        } else {
//            throw new IllegalStateException("Неизвестный логический оператор: " + logicalOperator);
//        }
//        System.out.println("accumulated: " + accumulatedResult + " conditionalResult "+conditionResult + " newResult "+newResult);
//
//        return conditionsMet(index + 1, newResult);
        return false;
    }

    private boolean evaluateCondition(Condition condition) {
        System.out.println("Проверка условия: " + condition);
        try {
            return condition.evaluate();
        }catch (Exception e){
            System.out.println(3);
            throw new NoSuchElementException(e);
        }

    }

    private void performAction(Action action) {
        ActionExecutor actionExecutor = new DefaultActionExecutor();
        actionExecutor.execute(action);
    }
}
