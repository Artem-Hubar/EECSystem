package org.example.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Transformer;
import org.example.rule.entity.*;
import org.example.rule.serializer.RuleDeserializer;
import org.example.rule.serializer.RuleSerializer;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RuleSerializerTest {

    private final RuleSerializer ruleSerializer = new RuleSerializer();
    private final RuleDeserializer ruleDeserializer = new RuleDeserializer();

    @Test
    public void testSerializeAndDeserializeRule() throws Exception {
        Rule rule = getRule();

        String serializedRule = ruleSerializer.serializeRule(rule);
        System.out.println(rule);
        System.out.println(serializedRule);
        Rule deserializedRule = ruleDeserializer.deserialize(serializedRule);
        System.out.println(serializedRule);

        isRuleEqual(rule, deserializedRule);
    }

    public static void isRuleEqual(Rule rule, Rule deserializedRule) {
        idRuleIdEqual(rule, deserializedRule);
        isConditionsWithOperatorEquals(rule, deserializedRule);
        isActionsEquals(rule, deserializedRule);
        isTimeStampEqual(rule, deserializedRule);
    }

    private static void isTimeStampEqual(Rule rule, Rule deserializedRule) {
        LocalDateTime timeStampOrig = rule.getTimeStamp();
        LocalDateTime timeStampDes = deserializedRule.getTimeStamp();
        assertEquals(timeStampOrig, timeStampDes);
    }

    private static void idRuleIdEqual(Rule rule, Rule deserializedRule) {
        Long idOrig = rule.getId();
        Long idDes = deserializedRule.getId();
        assertEquals(idOrig, idDes);
    }

    private static void isActionsEquals(Rule rule, Rule deserializedRule) {
        List<Action> actionsOrig = rule.getActions();
        List<Action> actionsDes = deserializedRule.getActions();
        for (int actionPos = 0; actionPos < actionsOrig.size(); actionPos++) {
            Action actionOrig = actionsOrig.get(actionPos);
            Action actionDes = actionsDes.get(actionPos);
            assertEquals(actionOrig, actionDes);
        }
    }

    // Метод для проверки выражений внутри условий
    private static void isConditionsWithOperatorEquals(Rule rule, Rule deserializedRule) {
        List<ConditionWithOperator> conditionsOrig = rule.getConditionsWithOperators();
        List<ConditionWithOperator> conditionsDeserialized = deserializedRule.getConditionsWithOperators();
        for (int condPos = 0; condPos < conditionsOrig.size(); condPos++) {
            ConditionWithOperator conditionWithOpOrig = conditionsOrig.get(condPos);
            ConditionWithOperator conditionWithOpDes = conditionsDeserialized.get(condPos);
            Expression conditionOrig = conditionWithOpOrig.getCondition();
            Expression conditionDes = conditionWithOpDes.getCondition();
            String logicOrig = conditionWithOpOrig.getLogicalOperator();
            String logicDes = conditionWithOpDes.getLogicalOperator();
            assertEquals(conditionOrig, conditionDes);
            assertEquals(logicOrig, logicDes);
        }
    }


    public static Rule getRule() {
        // Создаем объекты для выражений
        CurrentLineSensor sensor = new CurrentLineSensor("device1", 220.0, 10.0);
        CurrentLineSensor sensor2 = new CurrentLineSensor("device", 222.0, 10.0);

        // Используем Expression для выражений с операциями
        Expression expr1 = new Expression(new Expression(sensor, "getVoltage"), ">", new Expression(200.0));
        Expression expr2 = new Expression(new Expression(sensor2, "getVoltage"), ">", new Expression(204.0));

        System.out.println(expr1.evaluate());

        // Создаем условия с использованием выражений
        ConditionWithOperator condition1 = new ConditionWithOperator(expr1, "AND");
        ConditionWithOperator condition2 = new ConditionWithOperator(expr2, "OR");

        // Создаем действия для правила
        Transformer transformer = new Transformer("device1", 2.0);
        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("test");
        Action action = new Action(transformer, "turnsRatio", new Expression(2.0));
        Action action2 = new Action(mqttPublisher, "writeData", new Expression(transformer));

        // Возвращаем правило с выражениями и действиями
        return new Rule(List.of(condition1, condition2), List.of(action, action2));
    }
}