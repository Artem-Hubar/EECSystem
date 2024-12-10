package org.example.service.postgres;

import org.example.entity.Topic;
import org.example.service.hibernate.EntityManagerUtil;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public class TopicRepository {

    public void save(Topic topic) {
        executeInTransaction(em -> em.persist(topic));
    }

    public Topic getById(Integer id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        return em.find(Topic.class, id);
    }

    public List<Topic> getAll() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        return getAllTopicQuery(em)
                .getResultList();
    }

    private TypedQuery<Topic> getAllTopicQuery(EntityManager em) {
        return em.createQuery("SELECT t FROM Topic t", Topic.class);
    }

    public void update(Topic topic) {
        executeInTransaction(em -> em.merge(topic));
    }

    public void delete(Integer id) {
        executeInTransaction(em -> {
            Topic topic = em.find(Topic.class, id);
            if (topic != null) {
                em.remove(topic);
            }
        });
    }

    public void deleteByTitle(String title) {
        executeInTransaction(em -> {
            TypedQuery<Topic> query = getTopicByTitleQuery(em);
            query.setParameter("title", title);
            List<Topic> topics = query.getResultList();

            for (Topic topic : topics) {
                em.remove(topic);
            }
        });
    }

    private TypedQuery<Topic> getTopicByTitleQuery(EntityManager em) {
        return em.createQuery(
                "SELECT t FROM Topic t WHERE t.title = :title", Topic.class
        );
    }

    public void processTopic(Topic incomingTopic) {
        executeInTransaction(em -> {
            TypedQuery<Topic> query = getAllTopicQuery(em);
            List<Topic> topics = query.getResultList();

            ZonedDateTime now = ZonedDateTime.now();
            proceedUpdateTopicList(incomingTopic, em, topics, now);

            if (isTopicNotExist(incomingTopic, topics)) {
                incomingTopic.setData(now.toInstant());
                em.persist(incomingTopic);
            }
        });
    }

    private void proceedUpdateTopicList(Topic incomingTopic, EntityManager em, List<Topic> topics, ZonedDateTime now) {
        for (Topic topic : topics) {
            if (isTooOld(topic, now)) {
                em.remove(topic);
            } else if (isTopicsEquels(incomingTopic, topic)) {
                topic.setData(now.toInstant());
                em.merge(topic);
            }
        }
    }

    private boolean isTopicNotExist(Topic incomingTopic, List<Topic> topics) {
        return topics.stream().noneMatch(t -> isTopicsEquels(incomingTopic, t));
    }

    private boolean isTopicsEquels(Topic incomingTopic, Topic topic) {
        return topic.getTitle().equals(incomingTopic.getTitle());
    }

    private boolean isTooOld(Topic topic, ZonedDateTime now) {
        return Duration.between(topic.getData(), now).toSeconds() > 30;
    }

    private void executeInTransaction(Consumer<EntityManager> action) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            action.accept(em);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction failed, rolled back", e);
        } finally {
            EntityManagerUtil.closeEntityManager();
        }
    }
}

