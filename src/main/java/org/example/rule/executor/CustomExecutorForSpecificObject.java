package org.example.rule.executor;

import org.example.rule.entity.Action;

public class CustomExecutorForSpecificObject implements ActionExecutor {
    @Override
    public void execute(Action action){
        // Ваше уникальное поведение для конкретного типа объекта
        System.out.println("Executing custom action for specific object: " + action.getTargetObject());
    }
}
