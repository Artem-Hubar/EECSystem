package org.example.subscriber;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.entity.Topic;
import org.example.subscriber.service.mqtt.MQTTClientFactory;

import java.util.Set;

public class SubscriberThread implements Runnable {

    private String clientId;
    private SubscriberBehaviour subscriberBehaviour;
    private Set<Topic> topics;

    public SubscriberThread(String clientId, SubscriberBehaviour subscriberBehaviour, Set<Topic> topics) {
        this.clientId = clientId;
        this.subscriberBehaviour = subscriberBehaviour;
        this.topics = topics;
    }


    @Override
    public void run() {
        try {
            MqttClient client = MQTTClientFactory.getMqttClient(clientId);
            MQTTClientSubscriber clientSubscriber = new MQTTClientSubscriber(client, subscriberBehaviour);
            clientSubscriber.connectAndSubscribe(topics);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
