package org.example.subscriber.service.mqtt.publisher;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.subscriber.service.mqtt.MQTTClientFactory;

public class MQTTPublisherFactory {
    public static MQTTPublisher getPublisher(String clientId){
        MqttClient mqttClient = getMqttClient(clientId);
        return new MQTTPublisher(mqttClient);
    }

    private static MqttClient getMqttClient(String clientId){
        MqttClient mqttClient = null;
        try {
            mqttClient= MQTTClientFactory.getMqttClient(clientId);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        return mqttClient;
    }
}
