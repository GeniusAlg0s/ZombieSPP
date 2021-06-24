package com.zombiecastlerush.entity;

import com.zombiecastlerush.building.Challenge;
import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.util.Parser;

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
     * TODO: move to a room by room name/description
     *
     * @param roomName
     */
    public boolean moveTo(String roomName) {
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
            System.out.printf(Parser.GREEN + "Player %s moved from the %s to the %s\n", this.getName(), previous, this.getCurrentPosition().getName() + Parser.ANSI_RESET);
            return true;
        } else if (targetRoom == null) {
            System.out.printf(Parser.RED + "Player %s's current room %s doesn't connect to room: %s %n", this.getName(), this.getCurrentPosition().getName(), roomName + Parser.ANSI_RESET);
            return false;
        } else {
            System.out.println(Parser.RED + currRoomChallenge.getDescription() + " must be cleared before you can move to " + targetRoom + Parser.ANSI_RESET);
            return false;
        }
    }

    /**
     * decide next valid movement with target room name
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