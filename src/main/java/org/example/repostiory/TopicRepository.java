package org.example.repostiory;

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

        return em.createQuery("SELECT t FROM Topic t", Topic.class).getResultList();
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
            // Создаем запрос для поиска сущности по title
            TypedQuery<Topic> query = em.createQuery(
                    "SELECT t FROM Topic t WHERE t.title = :title", Topic.class
            );
            query.setParameter("title", title);

            // Получаем результат
            List<Topic> topics = query.getResultList();

            // Удаляем все найденные сущности
            for (Topic topic : topics) {
                em.remove(topic);
            }
        });
    }
    public void processTopic(Topic incomingTopic) {
        executeInTransaction(em -> {
            TypedQuery<Topic> query = em.createQuery("SELECT t FROM Topic t", Topic.class);
            List<Topic> topics = query.getResultList();

            ZonedDateTime now = ZonedDateTime.now();

            for (Topic topic : topics) {
                if (Duration.between(topic.getData(), now).toSeconds() > 30) {
                    em.remove(topic);
                } else if (topic.getTitle().equals(incomingTopic.getTitle())) {
                    topic.setData(now.toInstant());
                    em.merge(topic);
                }
            }

            if (topics.stream().noneMatch(t -> t.getTitle().equals(incomingTopic.getTitle()))) {
                incomingTopic.setData(now.toInstant());
                em.persist(incomingTopic);
            }
        });
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

