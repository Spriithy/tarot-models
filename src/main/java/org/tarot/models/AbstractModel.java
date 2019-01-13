package org.tarot.models;

import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * @author Théophile Dano
 *
 */
public abstract class AbstractModel implements Serializable {

    private String id;

    public AbstractModel() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }
}
