package com.gameforge.jdev.jpatest;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author sascha
 */
@Model
public class MyModel {

    @Inject
    MyDao dao;

    @PostConstruct
    public void init() {
        dao.deleteAll();
    }

    public String getStatus() {
        return "Starting JPA Tests";
    }

    /**
     * Persisting a freshly created entity object, using persist.
     *
     * This works, the persisted entity has an ID, no flush needed!
     */
    public String persistNewEntity() {
        MyEntity newEntity = new MyEntity("persisted");
        dao.persistEntity(newEntity);
        String output = "newly persisted entity:<br/>";
        output += newEntity;
        output += "<br/>-----------------------";
        return output;
    }

    /**
     * Persisting a freshly created entity object, using merge.
     *
     * This works, the merged entity has an ID too, no flush needed!
     */
    public String mergeNewEntity() {
        MyEntity newEntity = new MyEntity("merged 1");
        MyEntity mergedEntity = dao.mergeEntity(newEntity);
        String output = "newly merged entity:<br/>";
        output += mergedEntity;
        output += "<br/>-----------------------";
        return output;
    }

    /**
     * Persisting a freshly created entity object, using merge.
     *
     * This works too, but we use the wrong object afterwards, resulting in a
     * null id. Merge copies the given entity object to a new object that is
     * returned after saving. Using the old object instead of the newly returned
     * one leaves you with a detached object.
     */
    public String mergeNewEntityAndUseWrongObject() {
        MyEntity newEntity = new MyEntity("merged 2");
        dao.mergeEntity(newEntity);
        String output = "newly merged entity and then using *wrong* object:<br/>";
        output += newEntity;
        output += "<br/>-----------------------";
        return output;
    }

    /**
     * Persisting an existing but detached object, using persist.
     *
     * This breaks, because persist will try to insert the new entity into the
     * db, resulting in a primary key constraint failure.
     */
    public String persistExistingDetachedEntity() {
        MyEntity existingAttachedEntity = dao.findFirst();
        MyEntity existingDetachedEntity = new MyEntity();
        existingDetachedEntity.setText(existingAttachedEntity.getText());
        existingDetachedEntity.setId(existingAttachedEntity.getId());
        String output = "persisted existing and detached entity:<br/>";
        // the next line will produce an exception, see for yourself ;)
        // dao.persistEntity(existingDetachedEntity);
        output += "this will raise a org.eclipse.persistence.exceptions.DatabaseException, because JPA tries to insert an already existing primary key";
        output += "<br/>-----------------------";
        return output;
    }

    /**
     * Persisting an existing and attached object, using persist.
     *
     * This breaks too, because persist will try to insert the entity into the
     * db, resulting in a primary key constraint failure.
     */
    public String persistExistingAttachedEntity() {
        MyEntity existingAttachedEntity = dao.findFirst();
        existingAttachedEntity.setText("changed and persisted");
        // the next line will produce an exception, see for yourself ;)
        //dao.persistEntity(existingAttachedEntity);
        String output = "persisted existing and attached entity:<br/>";
        output += "this will raise a org.eclipse.persistence.exceptions.DatabaseException, because JPA tries to insert an already existing primary key";
        output += "<br/>-----------------------";
        return output;
    }

    /**
     * Persisting an existing but detached object, using merge.
     *
     * This works! Merge will first load the existing entity, then copy the
     * state of the new object, save it to db and then return the loaded, new
     * entity.
     */
    public String mergeExistingDetachedEntity() {
        MyEntity existingAttachedEntity = dao.findFirst();
        MyEntity existingDetachedEntity = new MyEntity();
        existingDetachedEntity.setText("changed and merged");
        existingDetachedEntity.setId(existingAttachedEntity.getId());
        String output = "merged existing and detached entity:<br/>";
        MyEntity mergedEntity = dao.mergeEntity(existingDetachedEntity);
        output += mergedEntity;
        output += "<br/>-----------------------";
        return output;
    }

    /**
     * Persisting an existing and attached object, using merge.
     *
     * This works! Merge will first load the existing entity (it doesnt matter
     * if it's attached or not), then copy the state of the new object, save it
     * to db and then return the loaded, new entity.
     */
    public String mergeExistingAttachedEntity() {
        MyEntity existingAttachedEntity = dao.findFirst();
        existingAttachedEntity.setText("changed and merged");
        MyEntity mergedEntity = dao.mergeEntity(existingAttachedEntity);
        String output = "merged existing and attached entity:<br/>";
        output += mergedEntity;
        output += "<br/>-----------------------";
        return output;
    }

    /**
     * The dao method changes something on an attached object and is not merging
     * or persisting and that is the way it should be. There's no need to call
     * merge() or persist() on an attached object, in fact, it's not only
     * useless, it's dangerous! Merge copies the state of the whole object to
     * the new object it fetches from the database -> that can possibly result
     * in complications if your entity is deeply nested and has a lot of
     * relations.
     */
    public String changeSomethingInAttachedEntityWithoutMergeOrPersist() {
        dao.updateEntityWithoutMergingOrPersisting();
        String output = "now i'm changing something on an attached entity without merging or persisting:<br/>";
        output += "<br/>-----------------------";
        return output;
    }

    /**
     * We can see now that the changes we made to the attached object in the
     * previous method got saved to the database on commit (= on leaving the
     * method).
     *
     */
    public String getThePreviouslyChangedEntity() {
        String output = "this is the entity i changed in the previous method:<br />";
        output += dao.findFirst();
        output += "<br/>-----------------------";
        return output;
    }
}
