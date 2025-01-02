package org.example.rule.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;
import org.example.rule.serializer.TargetObjectDeserializer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;


@ToString
@Data
public class Action {
    @JsonDeserialize(using = TargetObjectDeserializer.class)
    private Object targetObject;
    private String fieldName;
    private Expression expression;


    public Action() {
    }

    public Action(Object targetObject, String fieldName, Expression expression) {
        this.targetObject = targetObject;
        this.fieldName = fieldName;
        this.expression = expression;
    }

}
