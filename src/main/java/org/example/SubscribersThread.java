package org.example;

import org.example.entity.DeviceType;
import org.example.entity.Topic;
import org.example.rule.entity.Rule;
import org.example.rule.executor.RuleExecutorThread;
import org.example.service.RuleService;
import org.example.service.TopicService;
import org.example.subscriber.DeviceDataParserFactory;
import org.example.subscriber.SubscriberBehaviour;
import org.example.subscriber.SubscriberThread;
import org.example.subscriber.parser.CurrentLineDataParser;
import org.example.subscriber.parser.SwitchBoardParser;
import org.example.subscriber.parser.TransformerDataParser;
import org.example.subscriber.parser.GeneratorDataParser;
import org.example.subscriber.strategy.DeviceDataListener;
import org.example.subscriber.strategy.TopicSubscriber;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubscribersThread
{
    public static void main(String[] args) {
        SubscribersThread subscribersThread = new SubscribersThread();
        subscribersThread.topicListener();
        subscribersThread.deviceDataListener();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        subscribersThread.ruleExecutor();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void deviceDataListener() {
        DeviceDataParserFactory parserFactory = new DeviceDataParserFactory();
        parserFactory.registerParser(DeviceType.TRANSPORTER, new TransformerDataParser());
        parserFactory.registerParser(DeviceType.CURRENT_LINE_SENSOR, new CurrentLineDataParser());
        parserFactory.registerParser(DeviceType.GENERATOR, new GeneratorDataParser());
        parserFactory.registerParser(DeviceType.SWITCH_BOARD, new SwitchBoardParser());
        TopicService topicService = new TopicService();
        Set<Topic> topics = new HashSet<>(topicService.getAllTopics());
        while (topics.isEmpty()){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            topics = new HashSet<>(topicService.getAllTopics());
        }
        DeviceDataListener subscriberBehaviour = new DeviceDataListener(parserFactory, topics);
        String idClient = "DeviceSubscriberClient";

        Thread thread = new Thread(new SubscriberThread(idClient, subscriberBehaviour, topics));
        thread.start();
    }

    private void topicListener() {
        Set<Topic> topics = new HashSet<>(Set.of(new Topic("client/#", ZonedDateTime.now().toInstant())));
        SubscriberBehaviour subscriberBehaviour = new TopicSubscriber();
        String idClient = "DeviceEnvironmentListener";
        Thread thread = new Thread(new SubscriberThread(idClient, subscriberBehaviour, topics));
        thread.start();
    }

    private  void ruleExecutor(){
        RuleService ruleService = new RuleService();
        List<Rule> ruleList= ruleService.getAllRule();
//        System.out.println(ruleList);
        for (Rule rule : ruleList){
            Thread thread = new RuleExecutorThread(rule);
            thread.start();
        }
    }
}
