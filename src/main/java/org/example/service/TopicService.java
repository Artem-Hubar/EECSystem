package org.example.service;

import org.example.entity.Topic;
import org.example.factory.TopicRepositoryFactory;
import org.example.service.postgres.TopicRepository;

import java.util.List;

public class TopicService {

    private final TopicRepository topicRepository = TopicRepositoryFactory.getInstance();

    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public Topic getTopic(Integer id) {
        return topicRepository.getById(id);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.getAll();
    }

    public void updateTopic(Topic topic) {
        topicRepository.update(topic);
    }

    public void deleteTopic(Integer id) {
        topicRepository.delete(id);
    }

    public void deleteByTitle(String title){
        topicRepository.deleteByTitle(title);
    }

    public void subscribeTopic(String topic) {
        Topic topicEntity = new Topic();
        topicEntity.setTitle(topic);
        topicRepository.processTopic(topicEntity);
    }
}

