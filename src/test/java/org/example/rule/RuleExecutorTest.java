package org.example.rule;

import junit.framework.TestCase;
import org.example.entity.Transformer;
import org.example.rule.entity.Action;
import org.example.rule.executor.ActionExecutor;
import org.example.rule.executor.DefaultActionExecutor;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;

public class RuleExecutorTest extends TestCase {
    public void testActionExecutor(){
        Transformer transformer = new Transformer("device1", 2.0);
        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("test");
        Action action = new Action(transformer, "setTurnsRation", 3.0);
        Action action2 = new Action(mqttPublisher, "writeData", transformer);
        ActionExecutor actionExecutor = new DefaultActionExecutor();
        actionExecutor.execute(action);
        System.out.println(transformer);
    }
}
