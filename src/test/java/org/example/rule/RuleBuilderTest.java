package org.example.rule;

import junit.framework.TestCase;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Transformer;
import org.example.rule.entity.*;
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
        Expression condition1Expr = new Expression(
                new Expression(objB, "getVoltage"),
                ">",
                new Expression(110.0)
        );
        Expression condition2Expr = new Expression(
                new Expression(objB, "getVoltage"),
                "<",
                new Expression(330.0)
        );
        Expression condition3Expr = new Expression(
                new Expression(objB, "getVoltage"),
                "<",
                new Expression(330.0)
        );
        Expression condition4Expr = new Expression(
                new Expression(objB, "getVoltage"),
                ">",
                new Expression(100.0)
        );

        ConditionWithOperator conditionWithOperator1 = new ConditionWithOperator(condition1Expr, "AND");
        ConditionWithOperator conditionWithOperator2 = new ConditionWithOperator(condition2Expr, "AND");
        ConditionWithOperator conditionWithOperator3 = new ConditionWithOperator(condition3Expr, "OR");
        ConditionWithOperator conditionWithOperator4 = new ConditionWithOperator(condition4Expr, "AND");

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
