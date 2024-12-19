package org.example.subscriber;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public abstract class SubscriberBehaviour {
    public abstract void execute(MqttClient client, String topic, MqttMessage message);

}
