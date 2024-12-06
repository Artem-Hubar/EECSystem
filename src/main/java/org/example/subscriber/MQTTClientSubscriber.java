package org.example.subscriber;

import lombok.Getter;
import org.eclipse.paho.client.mqttv3.*;
import org.example.entity.Topic;

import java.util.*;


@Getter
public class MQTTClientSubscriber {
    private final MqttClient client;
    private final SubscriberBehaviour subscriberBehaviour;

    public MQTTClientSubscriber(MqttClient client, SubscriberBehaviour subscriberBehaviour) throws MqttException {
        this.client = client;
        this.subscriberBehaviour = subscriberBehaviour;
        setClientCallback(client);
    }


    private void setClientCallback(MqttClient client) {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
                System.err.println("Connection error: " + cause.getCause());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message){
                subscriberBehaviour.execute(client, topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }




    public void connectAndSubscribe(Set<Topic> topics) throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setKeepAliveInterval(60);
        options.setAutomaticReconnect(true);
        client.connect(options);
        for (Topic topic: topics){
            client.subscribe(topic.getTitle());
            System.out.println("Subscribe topic: " + topic);
        }
    }


}
