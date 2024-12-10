package org.example.subscriber;

import junit.framework.TestCase;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.entity.Topic;
import org.example.service.TopicService;
import org.example.subscriber.service.mqtt.MQTTClientFactory;
import org.example.subscriber.handler.CurrentLineDataHandler;
import org.example.subscriber.handler.TransformerDataHandler;
import org.example.subscriber.strategy.DeviceDataListener;
import org.example.subscriber.strategy.TopicSubscriber;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Thread.sleep;


public class MQTTSubscriberTest extends TestCase {
    private void testBehaviour(SubscriberBehaviour subscriberBehaviour, String clientId, Set<Topic> topics) {
        try {
            MqttClient client = MQTTClientFactory.getMqttClient(clientId);
            MQTTClientSubscriber clientSubscriber = new MQTTClientSubscriber(client, subscriberBehaviour);
            clientSubscriber.connectAndSubscribe(topics);
        } catch (MqttException e) {
            e.printStackTrace();
        }
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

    public void testTopicListener() {
        topicListener();
        while (true) {

        }
    }

    public void testDeviceDataListener() {
        deviceDataListener();
        while (true) {
        }
    }

    public void testSubscriberThread() {
        topicListener();
        deviceDataListener();
        while (true) {
        }
    }
}
