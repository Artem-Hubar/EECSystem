package org.example.subscriber.strategy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.entity.Topic;
import org.example.service.hibernate.TopicService;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UpdatedSubscriberTopic {
    public void update(MqttClient client, Set<Topic> initTopics) throws MqttException {
        TopicService topicService = new TopicService();
        Set<Topic> topics = new HashSet<>(topicService.getAllTopics());
        Set<String> initTopicsString = initTopics.stream().map(x -> x.getTitle()).collect(Collectors.toSet());
        ZonedDateTime now = ZonedDateTime.now();
        proceedTopics(client, initTopics, topics, now, initTopicsString);
    }

    private void proceedTopics(MqttClient client, Set<Topic> initTopics, Set<Topic> topics, ZonedDateTime now, Set<String> initTopicsString) throws MqttException {
        for (Topic topic : topics) {
            String topicTitle = topic.getTitle();
            if (isTopicTooOld(now, topic)) {
                client.unsubscribe(topicTitle);
                removeInitTopicItem(initTopics, topic, topicTitle);
            }

            if (!initTopicsString.contains(topicTitle)) {
                client.subscribe(topicTitle);
                System.out.println("Subscribed new topic: " + topicTitle);
                addInitTopicItem(initTopics, topic);
            }
        }
    }

    private void addInitTopicItem(Set<Topic> initTopics, Topic topic) {
        initTopics.add(topic);
    }

    private void removeInitTopicItem(Set<Topic> initTopics, Topic topic, String topicTitle) {
        Topic topicFromInit = initTopics.stream()
                .filter(x -> x.getTitle().equals(topic.getTitle()))
                .toList().getFirst();
        initTopics.remove(topicFromInit);
        System.out.println("Unsubscribed topic: " + topicTitle);
    }

    private boolean isTopicTooOld(ZonedDateTime now, Topic topic) {
        return Duration.between(topic.getData(), now).toSeconds() > 30;
    }
}
