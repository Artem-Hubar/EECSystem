package org.example.subscriber.service.mqtt.publisher;

import lombok.ToString;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.entity.Device;

import java.util.Objects;


public class MQTTPublisher {
    @Override
    public String toString() {
        return String.format("MQTTPublisher{clientId='%s'}",getClientId());
    }

    MqttClient client;

    public MQTTPublisher(MqttClient client) {
        this.client = client;
    }

    public String getClientId() {
        return client.getClientId();
    }

    public <T extends Device> void writeData(T device){
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            client.connect(options);
            String payload = device.getMqttPayload(); // Пример данных для передачи в JSON формате
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(2); // Уровень качества обслуживания (QoS), 2 - это "exactly once"
            message.setRetained(true);
            String topic = device.getMqttTopic();
            client.publish(topic, message);
            System.out.println("Message published to topic " + topic);
            client.disconnect();
            System.out.println("Disconnected from broker");

        } catch (MqttException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MQTTPublisher that = (MQTTPublisher) o;
        return Objects.equals(client.getClientId(), that.client.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(client);
    }
}
