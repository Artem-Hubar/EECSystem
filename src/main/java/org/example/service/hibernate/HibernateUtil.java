package org.example.service.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<>();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("в.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        Session session = threadLocal.get();
        if (session == null || !session.isOpen()) {
            session = sessionFactory.openSession();
            threadLocal.set(session);

        }
        return session;
    }

    public static void closeSession() {
        Session session = threadLocal.get();
        if (session != null) {
            session.close();
            threadLocal.remove();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

