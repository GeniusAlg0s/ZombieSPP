package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Entity;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * challenge class provides the challenge description and validation
 * TODO: more methods and attributes
 */

@JsonPropertyOrder({"description, inventory, cleared"})
public class Challenge extends Entity {

    private  boolean cleared;

    public Challenge(String description) {
        super.setDescription(description);
        this.setCleared(false);
    }

    /**
     * @param flag
     * @return
     */
    public  void setCleared(boolean flag) {
       this.cleared = flag;
    }

    public boolean isCleared() {
        return cleared;
    }
}
