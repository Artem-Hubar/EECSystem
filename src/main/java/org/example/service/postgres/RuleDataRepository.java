package org.example.service.postgres;


import org.example.service.hibernate.EntityManagerUtil;
import org.example.service.postgres.entity.RuleData;

import javax.persistence.*;
import java.util.List;
import java.util.function.Consumer;

public class RuleDataRepository {

    public void save(RuleData ruleData) {
        executeInTransaction(em -> em.persist(ruleData));
    }

    public RuleData getById(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        return em.find(RuleData.class, id);
    }

    public List<RuleData> getAll() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        return em.createQuery("SELECT r FROM RuleData r", RuleData.class).getResultList();
    }

    public void update(RuleData ruleData) {
        executeInTransaction(em -> em.merge(ruleData));
    }

    public void delete(Long id) {
        executeInTransaction(em -> {
            RuleData ruleData = em.find(RuleData.class, id);
            if (ruleData != null) {
                em.remove(ruleData);
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
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Transaction failed, rolled back", e);
        } finally {
            EntityManagerUtil.closeEntityManager();
        }
    }
}
