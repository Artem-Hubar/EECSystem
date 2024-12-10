package org.example.rule;

import junit.framework.TestCase;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Transformer;
import org.example.rule.entity.Action;
import org.example.rule.entity.Condition;
import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Rule;
import org.example.rule.executor.RuleExecutor;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;

public class RuleBuilderTest extends TestCase {
    public void testName(){
        Transformer objA = new Transformer();
        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("testClient");
        objA.setTurnsRation(2.0);
        CurrentLineSensor objB = new CurrentLineSensor();
        objB.setVoltage(110.0);
        objA.setSensorId("device1");
        RuleBuilder builder = new RuleBuilder();

        Condition condition1= new Condition(objB, "voltage", ">", 110.0);
        Condition condition2= new Condition(objB, "voltage", "<", 330.0);
        Condition condition3= new Condition(objB, "voltage", "<", 330.0);
        Condition condition4= new Condition(objB, "voltage", ">", 100.0);
        ConditionWithOperator conditionWithOperator1 = new ConditionWithOperator(condition1, "AND");
        ConditionWithOperator conditionWithOperator2 = new ConditionWithOperator(condition2,  "AND");
        ConditionWithOperator conditionWithOperator3 = new ConditionWithOperator(condition3,  "OR");
        ConditionWithOperator conditionWithOperator4 = new ConditionWithOperator(condition4,  "AND");
        Action action = new Action(mqttPublisher, "writeData", "4");

        Rule rule = builder
                .addCondition(conditionWithOperator1)
                .addCondition(conditionWithOperator2)
                .addCondition(conditionWithOperator3)
                .addCondition(conditionWithOperator4)
                .addAction(action)
                .build();

        RuleExecutor ruleExecutor = new RuleExecutor(rule);
        ruleExecutor.execute();

        System.out.println("Sensor voltage= " + objB.getVoltage());
        System.out.println("Transformer turnsRation= " + objA.getTurnsRation());
    }


    public void testRuleExecutor() {

    }
}
