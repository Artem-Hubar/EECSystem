package org.example.repostiory;

import org.example.entity.Topic;
import org.example.service.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

public class TopicRepository {

    private final EntityManager entityManager;

    public TopicRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Topic topic) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(topic);
        transaction.commit();
        session.close();
    }

    public Topic getById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Topic topic = session.get(Topic.class, id);
        session.close();
        return topic;
    }

    public List<Topic> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Topic> topics = session.createQuery("FROM Topic", Topic.class).list();
        session.close();
        return topics;
    }

    public void update(Topic topic) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(topic);
        transaction.commit();
        session.close();
    }

    public void delete(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Topic topic = session.get(Topic.class, id);
        if (topic != null) {
            session.delete(topic);
        }
        transaction.commit();
        session.close();
    }

    public void processTopic(Topic incomingTopic) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // Получение всех топиков из базы данных
            TypedQuery<Topic> query = entityManager.createQuery("SELECT t FROM Topic t", Topic.class);
            List<Topic> topics = query.getResultList();

            // Текущее время
            ZonedDateTime now = ZonedDateTime.now();

            for (Topic topic : topics) {
                // Если разница во времени больше заданного (например, 1 час)
                if (Duration.between(topic.getData(), now).toSeconds() > 30) {
                    // Удаление старого топика
                    entityManager.remove(topic);
                } else if (topic.getTitle().equals(incomingTopic.getTitle())) {
                    // Если топик совпадает с переданным, обновляем время
                    topic.setData(now.toInstant());
                }
            }

            // Если переданный топик не найден, сохраняем его
            if (topics.stream().noneMatch(t -> t.getTitle().equals(incomingTopic.getTitle()))) {
                incomingTopic.setData(now.toInstant());
                entityManager.persist(incomingTopic);
            }

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
