package org.example.rule.executor;

import java.util.HashMap;
import java.util.Map;

public class ActionExecutorRegistry {
    private static final Map<Class<?>, ActionExecutor> registry = new HashMap<>();

    public static void registerExecutor(Class<?> clazz, ActionExecutor executor) {
        registry.put(clazz, executor);
    }

    public static ActionExecutor getExecutor(Class<?> clazz) {
        return registry.get(clazz);
    }
}
