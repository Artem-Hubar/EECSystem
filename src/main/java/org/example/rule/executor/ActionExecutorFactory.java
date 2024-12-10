package org.example.rule.executor;

import org.example.entity.Device;
import org.example.rule.entity.Action;

public class ActionExecutorFactory {
    public static ActionExecutor getInstance(Action action){
        return new DefaultActionExecutor();
    }
}
