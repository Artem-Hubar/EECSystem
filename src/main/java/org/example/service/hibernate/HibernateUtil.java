package org.example.service.hibernate;

import org.example.entity.Topic;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            // Создаем Configuration объект для настройки Hibernate
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml"); // Путь к конфигурационному файлу

            // Добавляем класс сущности, чтобы Hibernate знал о нем
            configuration.addAnnotatedClass(Topic.class);

            // Создаем SessionFactory
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("SessionFactory creation failed");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Закрытие SessionFactory
        getSessionFactory().close();
    }
}
