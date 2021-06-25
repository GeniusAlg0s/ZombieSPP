package com.zombiecastlerush.entity;

import com.zombiecastlerush.building.Challenge;
import com.zombiecastlerush.building.Room;

import java.util.List;

public class Player extends Role {
    private double acctBalance = 50.0;

    public Player(String name) {
        super(name);
    }

    public Player(String name, Room room) {
        super(name, room);
    }

    //getter and setter
    public double getAcctBalance() {
        return acctBalance;
    }

    public void setAcctBalance(double acctBalance) {
        this.acctBalance = acctBalance;
    }

    /**
     * @param roomName
     */
    public String moveTo(String roomName) {
        StringBuilder moveString = new StringBuilder();
        Room targetRoom = this.canMoveToRoom(roomName);
        Challenge currRoomChallenge = this.getCurrentPosition().getChallenge();
        boolean roomChallengeflag;

        //if the room has no challenge
        if (currRoomChallenge == null)
            roomChallengeflag = true;
        else
            roomChallengeflag = currRoomChallenge.isCleared();

        if (targetRoom != null && roomChallengeflag) {
            String previous = this.getCurrentPosition().getName();
            this.setCurrentPosition(targetRoom);
            String tgtRoom= String.format("Player %s moved from the %s to the %s\n", this.getName(), previous, this.getCurrentPosition().getName());
            System.out.println(tgtRoom);
            moveString.append(tgtRoom);
            return moveString.toString();
        } else if (targetRoom == null) {
            String tgtRoom = String.format("Player %s's current room %s doesn't connect to room: %s %n", this.getName(), this.getCurrentPosition().getName(), roomName);
            System.out.println(tgtRoom);
            moveString.append(tgtRoom);
            return moveString.toString();
        } else {
            String tgtRoom = currRoomChallenge.getDescription() + " must be cleared before you can move to " + targetRoom;
            System.out.println(tgtRoom);
            moveString.append(tgtRoom);
            return moveString.toString();
        }
    }

    /**
     *
     * @param roomName
     * @return Room reference if input room name is valid for my next movement
     */
    public Room canMoveToRoom(String roomName) {
        List<Room> validRooms = this.whichRoomNext();
        if (validRooms != null) {
            for (Room r : validRooms) {
                if (r.getName().equalsIgnoreCase(roomName))
                    return r; // TODO: is it possible to have rooms with same name? (Xander asking)
            }
        }
        return null;
    }


    /**
     * helper method that provide me a list which room can i go to
     *
     * @return
     */
    private List<Room> whichRoomNext() {
        return (this.getCurrentPosition() == null) ? null : this.getCurrentPosition().getConnectedRooms();
    }
}