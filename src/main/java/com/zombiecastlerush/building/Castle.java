package com.zombiecastlerush.building;

import java.util.*;

public class Castle {
    //create a map of rooms in castle
    private Map<String, Room> castleRooms = new HashMap<>();


    //Ctor
    public Castle() {
        // create rooms
        Room eastWing = new Room("East-Wing", "Another box is here. Sounds like the moans of a man in agony grow louder as you venture deeper into this room.");
        Room westWing = new Room("West-Wing", "Eerily quiet, only a box awaits you in this chamber.");
        Room castleHall = new Room("Castle-Hall", "It is cold, dark, and empty, save for a dimly lit, white box. ");
        Room drawBridge = new Room("Draw-Bridge", "The bridge is up, and there is no way to the other side. Nothing but the giant, open castle doors looks inviting, but alas, it is the only way forward. A box lays on the ground right before the doorway.. is that a stair case below???.");
        Room combatHall = new Room("Combat-Hall", "Festooned with the arms and armor of warriors past, this room is better lit than the others. In the middle of the room, a single coffin has been left slightly open, its lid closed just enough to obscure the contents from view");
        //new room
        Room graveyard = new Room("Grave-Yard", "The stairs led to a grave, and there a old looking tomb with an opening. \nThere is a weird looking ghost, among the tomb stones. it blocks the stair case....");
        Room tomb = new Room("Tomb","Take a chance win some mullah.. \nIt costs 2 coins to play...don't stay to long the house wins eventually");
        Shop shop = new Shop("Shop", "A strangely silent shopkeeper seems to preside over a collection of wares, oblivious or indifferent to your presence.");

        //add connected rooms to room
        eastWing.addConnectedRooms(castleHall, combatHall);
        castleHall.addConnectedRooms(drawBridge, eastWing, westWing, shop);
        drawBridge.addConnectedRooms(westWing, castleHall, graveyard); //new room
        westWing.addConnectedRooms(castleHall, drawBridge);
        combatHall.addConnectedRooms(eastWing);
        //new room
        graveyard.addConnectedRooms(drawBridge,tomb);
        tomb.addConnectedRooms(graveyard);
        shop.addConnectedRooms(castleHall);

        //add Challenge to room
        eastWing.setChallenge(new Puzzle("East-Wing-Puzzle", "I shave every day, but my beard stays the same. What am I?", "Barber"));
        eastWing.getChallenge().getInventory().addItems(new Item("Knife", "This is a knife", 50.0));
        westWing.setChallenge(new Puzzle("West-Wing-Puzzle", "The person who makes it has no need of it; the person who buys it has no use for it. The person who uses it can neither see nor feel it. What is it?", "Coffin"));
        westWing.getChallenge().getInventory().addItems(new Item("Spoon", "This is a spoon", 50.0));
        westWing.getChallenge().getInventory().addItems(new Item("Coins", "You have solved the riddle, unlocked coins.",150.0));
        castleHall.setChallenge(new Puzzle("Castle-Hall-Puzzle", "What has many teeth, but cannot bite?", "Comb"));
        castleHall.getChallenge().getInventory().addItems(new Item("Fork", "This is a fork", 25.0));
        drawBridge.setChallenge(new Puzzle("Draw-Bridge-Puzzle", "What can travel all around the world without leaving its corner?", "Stamp"));
        drawBridge.getChallenge().getInventory().addItems(new Item("Vase", "This is a vase", 50.0) ,new Item("Sword", "The Sword of Light", 100));
        //new room
        graveyard.setChallenge(new Combat("Life or Death Battle"));
        tomb.setChallenge(new Roulette("spin for coins"));
        combatHall.setChallenge(new Combat("Life or Death Battle"));
        combatHall.setExit(true);



        //add items to Rooms inventory
        shop.getInventory().addItems(
                new Item("Sword", "This is the sword of Destiny, made up of the Valyrian Steel", 100.0),
                new Item("Helmet", "This is the ultimate shield which will be carried by Captain America in the distant future", 50.0),
                new Item("Potion", "Drinking this potion will restore your health", 100.0),
                new Item("Wand", "Magic Wand", 260.0),
                new Item("50 CAL", "This weapon will destroy enemy into pieces and even their close one won't be able to recognize them", 400.0)
        );

        //Add rooms to castleRooms
        castleRooms.put(eastWing.getName(), eastWing);
        castleRooms.put(westWing.getName(), westWing);
        castleRooms.put(castleHall.getName(), castleHall);
        castleRooms.put(drawBridge.getName(), drawBridge);
        castleRooms.put(combatHall.getName(), combatHall);
        castleRooms.put(shop.getName(), shop);
        castleRooms.put(graveyard.getName(),graveyard); //new room
        castleRooms.put(tomb.getName(), tomb);
    }

    //getter
    public Map<String, Room> getCastleRooms() {
        return castleRooms;
    }

    @Override
    public String toString() {
        return castleRooms.keySet().toString();
    }
}