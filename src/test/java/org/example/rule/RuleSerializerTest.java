package org.example.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Transformer;
import org.example.rule.entity.Action;
import org.example.rule.entity.Condition;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Rule;
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
    public void testSerializeAndDeserializeRule(){
        Rule rule = getRule();
        String serializedRule = ruleSerializer.serializeRule(rule);
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
        for (int actionPos = 0; actionPos<actionsOrig.size();actionPos++){
            Action actionOrig = actionsOrig.get(actionPos);
            Action actionDes = actionsDes.get(actionPos);
            assertEquals(actionOrig, actionDes);
        }
    }

    private static void isConditionsWithOperatorEquals(Rule rule, Rule deserializedRule) {
        List<ConditionWithOperator> conditionsOrig = rule.getConditionsWithOperators();
        List<ConditionWithOperator> conditionsDeserealized = deserializedRule.getConditionsWithOperators();
        for (int condPos = 0 ; condPos< conditionsOrig.size();condPos++){
            ConditionWithOperator conditionWithOpOrig = conditionsOrig.get(condPos);
            ConditionWithOperator conditionWithOpDes = conditionsDeserealized.get(condPos);
            Condition conditionOrig = conditionWithOpOrig.getCondition();
            Condition conditionDes = conditionWithOpDes.getCondition();
            String logicOrig = conditionWithOpOrig.getLogicalOperator();
            String logicDes = conditionWithOpDes.getLogicalOperator();
            assertEquals(conditionOrig, conditionDes);
            assertEquals(logicOrig, logicDes);
        }
    }

    @Test
    public void testSerializeRuleProducesValidJson() throws JsonProcessingException {
        Rule rule = getRule();

        String serializedRule = ruleSerializer.serializeRule(rule);
        assertTrue(serializedRule.startsWith("{"), "Serialized rule should start with a '{'");
        assertTrue(serializedRule.endsWith("}"), "Serialized rule should end with a '}'");
        assertDoesNotThrow(() -> {
            new com.fasterxml.jackson.databind.ObjectMapper().readTree(serializedRule);
        }, "Serialized rule should be valid JSON");
    }

    @Test
    public void testDeserializeInvalidJson() {
        // Arrange
        String invalidJson = "{invalid_json}";

        // Act & Assert
        assertThrows(JsonProcessingException.class, () -> {
            ruleDeserializer.deserialize(invalidJson);
        }, "Deserialization of invalid JSON should throw JsonProcessingException");
    }

    public static @NotNull Rule getRule() {

        CurrentLineSensor sensor = new CurrentLineSensor("device1", 220.0, 10.0);
        CurrentLineSensor sensor2 = new CurrentLineSensor("device", 222.0, 10.0);

        Condition condition = new Condition(sensor, "voltage", ">", 200.0);
        Condition condition2 = new Condition(sensor2, "voltage", ">", 204.0);
        ConditionWithOperator conditionWithOperator = new ConditionWithOperator(condition, "AND");
        ConditionWithOperator conditionWithOperator2 = new ConditionWithOperator(condition2, "AND");

        Transformer transformer = new Transformer("device1", 2.0);
        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("test");
        Action action = new Action(transformer, "turnsRatio", 2.0);
        Action action2 = new Action(mqttPublisher, "writeData", transformer);

        return new Rule(List.of(conditionWithOperator, conditionWithOperator2), List.of(action, action2));
    }
}
