package org.example.subscriber.strategy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.entity.Device;
import org.example.entity.DeviceType;
import org.example.entity.Topic;
import org.example.service.InflexDBService;
import org.example.subscriber.DeviceDataParser;
import org.example.subscriber.DeviceDataParserFactory;
import org.example.subscriber.SubscriberBehaviour;

import java.util.Optional;
import java.util.Set;

public class DeviceDataListener extends SubscriberBehaviour {

    private final DeviceDataParserFactory parserFactory;
    private final Set<Topic> initTopics;


    public DeviceDataListener(DeviceDataParserFactory parserFactory, Set<Topic> initTopics) {
        this.parserFactory = parserFactory;
        this.initTopics = initTopics;
    }

    @Override
    public void execute(MqttClient client, String topic, MqttMessage message) {
        String[] topicParts = topic.split("/");

        if (isTopicNotEmpty(topicParts)) {
            proceedData(topic, message, topicParts);
//            updateSubscribedTopic(client);
        }
    }

    private void proceedData(String topic, MqttMessage message, String[] topicParts) {
        DeviceType deviceType = DeviceType.fromType(topicParts[1]);
        DeviceDataParser parser = parserFactory.getParser(deviceType);
        Optional<Device> device = handleData(topic, message, parser, deviceType);
        if (device.isPresent()) {
            writeToDataBase(device.get());
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

    private void writeToDataBase(Device device) {
        InflexDBService inflexDBService = new InflexDBService();
        inflexDBService.writeData(device);
    }

    private Optional<Device> handleData(String topic, MqttMessage message, DeviceDataParser parser, DeviceType deviceType) {
        Optional<Device> device = Optional.empty();
        if (isParserNotNull(parser)) {
//            System.out.println(message);
            String[] topicParts = topic.split("/");
            device = parser.parserData(topicParts, new String(message.getPayload()));
        } else {
            System.err.println("DeviceDataParser '" + deviceType + "' not found.");
        }
        return device;
    }

    private boolean isTopicNotEmpty(String[] topicParts) {
        return topicParts.length > 1;
    }

    private boolean isParserNotNull(DeviceDataParser parser) {
        return parser != null;
    }
}
