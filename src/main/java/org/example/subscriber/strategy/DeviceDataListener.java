package org.example.subscriber.strategy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.entity.Device;
import org.example.entity.Topic;
import org.example.service.InflexDBService;
import org.example.subscriber.DeviceDataHandler;
import org.example.subscriber.DeviceDataHandlerFactory;
import org.example.subscriber.SubscriberBehaviour;

import java.util.Optional;
import java.util.Set;

public class DeviceDataListener extends SubscriberBehaviour {

    private final DeviceDataHandlerFactory handlerFactory;
    private final Set<Topic> initTopics;


    public DeviceDataListener(DeviceDataHandlerFactory handlerFactory, Set<Topic> initTopics) {
        this.handlerFactory = handlerFactory;
        this.initTopics = initTopics;
    }

    @Override
    public void execute(MqttClient client, String topic, MqttMessage message) {
        String[] topicParts = topic.split("/");
        System.out.println(topic);

        if (isTopicNotEmpty(topicParts)) {
            proceedData(topic, message, topicParts);
            updateSubscribedTopic(client);
        }
    }

    private void proceedData(String topic, MqttMessage message, String[] topicParts) {
        String deviceType = topicParts[1];
        DeviceDataHandler handler = handlerFactory.getHandler(deviceType);
        Optional<Device> device = handleData(topic, message, handler, deviceType);
        if (device.isPresent()) {
            writeToDataBase(device);
        }

    }

    private void updateSubscribedTopic(MqttClient client) {
        try {
            UpdatedSubscribedTopic updatedSubscriberTopic = new UpdatedSubscribedTopic();
            updatedSubscriberTopic.update(client, initTopics);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToDataBase(Optional<Device> device) {
        Device deviceEntity = device.get();
        InflexDBService inflexDBService = new InflexDBService();
        inflexDBService.writeData(deviceEntity);
    }

    private Optional<Device> handleData(String topic, MqttMessage message, DeviceDataHandler handler, String deviceType) {
        Optional<Device> device = Optional.empty();
        if (isHandlerNotNull(handler)) {
            String[] topicParts = topic.split("/");
            device = handler.handleData(topicParts, new String(message.getPayload()));
        } else {
            System.err.println("DeviceDataHandler '" + deviceType + "' not found.");
        }
        return device;
    }

    private boolean isTopicNotEmpty(String[] topicParts) {
        return topicParts.length > 1;
    }

    private boolean isHandlerNotNull(DeviceDataHandler handler) {
        return handler != null;
    }

}
