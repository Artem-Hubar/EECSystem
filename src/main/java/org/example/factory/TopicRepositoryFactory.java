package org.example.factory;

import org.example.service.postgres.TopicRepository;

public class TopicRepositoryFactory {
    public static TopicRepository getInstance() {
        return new TopicRepository();
    }
}
