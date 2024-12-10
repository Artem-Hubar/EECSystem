package org.example.subscriber.strategy;


import lombok.Getter;
import org.eclipse.paho.client.mqttv3.*;
import org.example.service.TopicService;
import org.example.subscriber.SubscriberBehaviour;

@Getter
public class TopicSubscriber extends SubscriberBehaviour {


    @Override
    public void execute(MqttClient client, String topic, MqttMessage message) {
        TopicService topicService = new TopicService();
        topicService.subscribeTopic(topic);
    }
}
