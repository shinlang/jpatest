/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameforge.jdev.jpatest;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author sascha
 */
@Entity
public class MyComplexEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private MyComplexEntityId id;
    private String text;

    public MyComplexEntity() {
    }

    public MyComplexEntity(String text) {
        this.text = text;
    }
    
    public MyComplexEntityId getId() {
        return id;
    }

    public void setId(MyComplexEntityId id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MyComplexEntity{" + "id=" + id + ", text=" + text + '}';
    }
    
}
