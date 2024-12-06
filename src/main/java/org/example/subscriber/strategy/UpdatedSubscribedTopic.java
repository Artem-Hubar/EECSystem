package org.example.subscriber.strategy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.entity.Topic;
import org.example.service.hibernate.TopicService;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UpdatedSubscribedTopic {
    TopicService topicService ;
    public UpdatedSubscribedTopic() {
        this.topicService = new TopicService();
    }

    public void update(MqttClient client, Set<Topic> initTopics) throws MqttException {
        Set<Topic> newTopics = new HashSet<>(topicService.getAllTopics());
        Set<String> oldTopicsString = getTopicsString(initTopics);
        proceedTopics(client, initTopics, newTopics, oldTopicsString);
    }

    private @NotNull Set<String> getTopicsString(Set<Topic> initTopics) {
        return initTopics.stream()
                .map(Topic::getTitle)
                .collect(Collectors.toSet());
    }

    private void proceedTopics(MqttClient client, Set<Topic> initTopics, Set<Topic> newTopics, Set<String> oldTopicsString) throws MqttException {
        for (Topic topic : newTopics) {
            String topicTitle = topic.getTitle();
            if (isTopicTooOld(topic)) {
                client.unsubscribe(topicTitle);
                removeInitTopicItem(initTopics, topic, topicTitle);
            }

            if (!oldTopicsString.contains(topicTitle)) {
                client.subscribe(topicTitle);
                System.out.println("Subscribed new topic: " + topicTitle);
                addInitTopicItem(initTopics, topic);
            }
        }
    }
    private void removeInitTopicItem(Set<Topic> initTopics, Topic topic, String topicTitle) {
        if (!initTopics.isEmpty()){
            Optional<Topic> topicFromInit = initTopics.stream()
                    .filter(x -> x.getTitle().equals(topic.getTitle()))
                    .findFirst();

            topicFromInit.ifPresent(initTopics::remove);
            System.out.println("Unsubscribed topic: " + topicTitle);
            waitNewTopics(initTopics);
        }
    }

    private void waitNewTopics(Set<Topic> initTopics) {
        while (initTopics.isEmpty()){
            Set<Topic> newTopics = new HashSet<>(getAllNewTopic());
            if (!newTopics.isEmpty()){
                initTopics = newTopics;
            }
        }
    }

    private List<Topic> getAllNewTopic() {
        return topicService.getAllTopics().stream()
                .filter(this::isNewTopic).toList();
    }

    private boolean isTopicTooOld(Topic topic) {
        ZonedDateTime now = ZonedDateTime.now();
        return Duration.between(topic.getData(), now).toSeconds() > 30;
    }

    private boolean isNewTopic(Topic topic){
        return !isTopicTooOld(topic);
    }

    private void addInitTopicItem(Set<Topic> initTopics, Topic topic) {
        initTopics.add(topic);
    }

}
