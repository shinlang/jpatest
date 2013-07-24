/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameforge.jdev.jpatest;

import javax.persistence.*;

/**
 *
 * @author sascha
 */
@Embeddable
public class MyComplexEntityId {

    @Basic(optional=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public MyComplexEntityId(long id) {
        this.id = id;
    }

    public MyComplexEntityId() {
        this.id = 0;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MyComplexEntityId other = (MyComplexEntityId) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "MyComplexEntityId{" + "id=" + id + '}';
    }
    
}
