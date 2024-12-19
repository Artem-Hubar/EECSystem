package org.example;

import org.example.entity.Topic;
import org.example.service.TopicService;
import org.example.subscriber.DeviceDataHandlerFactory;
import org.example.subscriber.SubscriberBehaviour;
import org.example.subscriber.SubscriberThread;
import org.example.subscriber.handler.CurrentLineDataHandler;
import org.example.subscriber.handler.TransformerDataHandler;
import org.example.subscriber.strategy.DeviceDataListener;
import org.example.subscriber.strategy.TopicSubscriber;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class SubscribersThread
{
    public static void main(String[] args) {
        SubscribersThread subscribersThread = new SubscribersThread();
        subscribersThread.topicListener();
        subscribersThread.deviceDataListener();
        while (true) {}
    }

    private void deviceDataListener() {
        DeviceDataHandlerFactory handlerFactory = new DeviceDataHandlerFactory();
        handlerFactory.registerHandler("transformer", new TransformerDataHandler());
        handlerFactory.registerHandler("currentsensor", new CurrentLineDataHandler());
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
        DeviceDataListener subscriberBehaviour = new DeviceDataListener(handlerFactory, topics);
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
}
