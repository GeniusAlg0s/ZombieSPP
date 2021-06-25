package com.zombiecastlerush.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zombiecastlerush.building.Item;
import com.zombiecastlerush.building.Room;

/**
 * base class for all roles
*/

@JsonPropertyOrder({"name", "health", "inventory", "currentRoom"})
public class Role extends Entity {
    private final int MAX_HEALTH = 100;
    private final int MIN_HEALTH = 0;
    private Room currentRoom;

    private int health; // range from 0-100

    // cannot have a Role without name
    private Role() {
        this.health = MAX_HEALTH;
        this.currentRoom = null;
    }

    public Role(String name) {
        this();
        this.setName(name);
    }

    public Role(String name, Room currentRoom) {
        this(name);
        this.currentRoom = currentRoom;
    }

    /**
     * increase this role's health points
     * health range 0 - 100
     *
     * @param points how many points are increased
     */
    public void increaseHealth(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Invalid negative health points");
        }
        this.setHealth(Math.min((points + this.getHealth()), MAX_HEALTH));
    }

    /**
     * decrease this role's health points
     *
     * @param points how many points are decreased
     */
    public void decreaseHealth(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Invalid negative health points");
        }
        this.setHealth(Math.max((this.getHealth() - points), MIN_HEALTH));
    }

    @JsonGetter("currentRoom")
    public Room getCurrentPosition() {
        return this.currentRoom;
    }

    /**
     * change current room where this Role is located
     *
     * @param room room reference
     */
    @JsonSetter("currentRoom")
    public void setCurrentPosition(Room room) {
        this.currentRoom = room;
    }

    public int getHealth() {
        return this.health;
    }

    /**
     * validate health range 0 - 100
     *
     * @param points input health points
     */
    public void setHealth(int points) {
        if (points > MAX_HEALTH || points < MIN_HEALTH) {
            throw new IllegalArgumentException(String.format("Invalid health range; health range from %d to %d\n", MIN_HEALTH, MAX_HEALTH));
        } else {
            this.health = points;
        }
    }

    // Inventory methods
    public Item pickUp(Item item) {
        for (Item existingItem : this.getCurrentPosition().getInventory().getItems()) {
            System.out.println(existingItem.getName());
            if (item.equals(existingItem)) {
                this.getInventory().addItems(item);
                this.getCurrentPosition().getInventory().deleteItems(item);
                return item;
            }
        }
        return null;
    }

    public Item drop(Item item) {
        for (Item existingItem : this.getInventory().getItems()) {
            if (item.equals(existingItem)) {
                this.getCurrentPosition().getInventory().addItems(item);
                this.getInventory().deleteItems(item);
                return item;
            }
        }
        return null;
    }

    public void dropAll() {
        for (Item item : this.getInventory().getItems()) {
            this.getCurrentPosition().getInventory().addItems(item);
        }
        this.getInventory().deleteAllItems();
    }

    /**
     * display Role's status in Json format {"name", "currentRoom", "health", "inventory"}
     *
     * @return String Json format status {"name", "currentRoom", "health", "inventory"}
     * @throws JsonProcessingException
     */
    public String displayStatus() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }
}

