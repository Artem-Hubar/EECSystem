package org.example.rule;

import junit.framework.TestCase;
import org.example.entity.Transformer;
import org.example.rule.entity.Action;
import org.example.rule.entity.Expression;
import org.example.rule.entity.Rule;
import org.example.rule.executor.ActionExecutor;
import org.example.rule.executor.DefaultActionExecutor;
import org.example.rule.executor.RuleExecutorThread;
import org.example.service.RuleService;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisher;
import org.example.subscriber.service.mqtt.publisher.MQTTPublisherFactory;

import java.util.List;

public class RuleExecutorTest extends TestCase {
    public void testActionExecutor(){
        Transformer transformer = new Transformer("device1", 2.0);
        MQTTPublisher mqttPublisher = MQTTPublisherFactory.getPublisher("test");
        Action action = new Action(transformer, "setTurnsRation", new Expression(3.0));
        Action action2 = new Action(mqttPublisher, "writeData", new Expression(transformer));
        ActionExecutor actionExecutor = new DefaultActionExecutor();
        actionExecutor.execute(action);
        System.out.println(transformer);
    }

    public void testRuleExecutorThread(){
        RuleService ruleService = new RuleService();
        List<Rule> ruleList= ruleService.getAllRule();
        System.out.println(ruleList);
        for (Rule rule : ruleList){
            Thread thread = new RuleExecutorThread(rule);
            thread.start();
        }
        while (true){}

    }
}
