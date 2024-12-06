package org.example.subscriber.service.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTClientFactory {
    private static final String BROKER = "tcp://localhost:1883";


    public static MqttClient getMqttClient(String clientId) throws MqttException {
        return new MqttClient(BROKER, clientId, new MemoryPersistence());
    }
}
