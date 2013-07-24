package com.gameforge.jdev.jpatest;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sascha
 */
@Stateless
public class MyDao {

    @PersistenceContext(unitName = "pu")
    EntityManager em;

    public void deleteAll() {
        Query deleteAllQuery = em.createNativeQuery("DELETE FROM myentity");
        deleteAllQuery.executeUpdate();
        deleteAllQuery = em.createNativeQuery("DELETE FROM mycomplexentity");
        deleteAllQuery.executeUpdate();
    }

    public void persistEntity(MyEntity entity) {
        em.persist(entity);
    }

    public MyEntity mergeEntity(MyEntity entity) {
        return em.merge(entity);
    }

    public MyEntity findFirst() {
        Query findFirstQuery = em.createNativeQuery("SELECT * FROM myentity LIMIT 1", MyEntity.class);
        return (MyEntity) findFirstQuery.getSingleResult();
    }

    public MyEntity find(Long id) {
        return em.find(MyEntity.class, id);
    }

    public void updateEntityWithoutMergingOrPersisting() {
        MyEntity attachedEntity = findFirst();
        attachedEntity.setText("this change has never been persisted or merged!");
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void persistComplexEntity(MyComplexEntity entity) {
        em.persist(entity);
    } 
    
    public MyComplexEntity mergeComplexEntity(MyComplexEntity entity) {
        return em.merge(entity);
    }
    
    public MyComplexEntity getLatestMyComplexEntity() {
        Query findFirstQuery = em.createNativeQuery("SELECT * FROM mycomplexentity LIMIT 1", MyComplexEntity.class); 
        return (MyComplexEntity) findFirstQuery.getSingleResult();
    }
    
    public void refresh(MyComplexEntity entity) {
        em.refresh(entity);
    }
}
