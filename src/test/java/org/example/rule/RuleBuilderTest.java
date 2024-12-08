package org.example.rule;

import junit.framework.TestCase;
import org.example.entity.CurrentLineSensor;
import org.example.entity.Device;
import org.example.entity.Transformer;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;

public class RuleBuilderTest extends TestCase {
    public void testName() throws Exception {
        Transformer objA = new Transformer();
        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("testClient");
        objA.setTurnsRation("2");
        CurrentLineSensor objB = new CurrentLineSensor();
        objB.setVoltage(110.0);
        objA.setSensorId("device1");
        RuleBuilder builder = new RuleBuilder();


        Rule rule = builder.addCondition(objB, "voltage", ">", 110.0, "AND")
                .addCondition(objB, "voltage", "<", 330.0, "AND")
                .addCondition(objB, "voltage", "<", 330.0, "OR")
                .addCondition(objB, "voltage", ">", 100.0, "AND")
                .addAction(mqttPublisher, "writeData", "4").build();

        RuleExecutor ruleExecutor = new RuleExecutor(rule);
        ruleExecutor.execute();

        System.out.println("Sensor voltage= " + objB.getVoltage()); // Вывод: objB.b = 1
        System.out.println("Transformer turnsRation= " + objA.getTurnsRation()); // Вывод: objB.b = 1
    }
}
