package org.example.service.hibernate;

import org.example.entity.Topic;
import org.example.repostiory.TopicRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class TopicService {

    private final TopicRepository topicRepository = new TopicRepository();

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

