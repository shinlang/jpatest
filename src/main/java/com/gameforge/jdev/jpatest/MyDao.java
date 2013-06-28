package com.gameforge.jdev.jpatest;

import javax.ejb.Stateless;
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
        Query deleteAllQuery = em.createNativeQuery("DELETE FROM MYENTITY");
        deleteAllQuery.executeUpdate();
    }

    public void persistEntity(MyEntity entity) {
        em.persist(entity);
    }

    public MyEntity mergeEntity(MyEntity entity) {
        return em.merge(entity);
    }

    public MyEntity findFirst() {
        Query findFirstQuery = em.createNativeQuery("SELECT * FROM MYENTITY FETCH FIRST 1 ROWS ONLY", MyEntity.class);
        return (MyEntity) findFirstQuery.getSingleResult();
    }

    public MyEntity find(Long id) {
        return em.find(MyEntity.class, id);
    }

    public void updateEntityWithoutMergingOrPersisting() {
        MyEntity attachedEntity = findFirst();
        attachedEntity.setText("this change has never been persisted or merged!");
    }
}
