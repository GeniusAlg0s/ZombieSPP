package com.zombiecastlerush.entity;

import com.zombiecastlerush.building.Room;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private Player player = new Player("Player name # 1");
    private Room r0 = new Room("room#0", "ENTRY room");

    @Before
    public void setUp() throws Exception {
        Room r1 = new Room("room#1", "room # 1");
        r0.addConnectedRooms(r1);
        for (int i = 2; i < 10; i++) {
            Room r = new Room(String.format("room#%d", i), "A room");
            r.addConnectedRooms(r1);
            r1.addConnectedRooms(r);
            r1 = r;
        }
    }

    @Test
    public void testPlayerHasNoRoom() {
        player.setCurrentPosition(null);
        Assert.assertEquals(player.getCurrentPosition(), null);
    }

    @Test
    public void testSetPlayerPosition_returnPlayerCurrentLocationRoom0() {
        player.setCurrentPosition(r0);
        Assert.assertEquals(player.getCurrentPosition().getName(), r0.getName());
    }

    @Test
    public void testRoomConnectionsLinear_eachRoomConnectedToDesiredRoom() {
        // linear rooms room#0 -> room#1 -> ... -> room#9 -> null
        player.setCurrentPosition(r0);
        Room r = player.getCurrentPosition();
        int index = 0;
        // test from room#0 to room#9
        while (!r.getName().equals("room#9")) {
            System.out.printf("Room: %s", r.getName());
            Assert.assertEquals(r.getName(), String.format("room#%d", index));
            if (r.getConnectedRooms().size() == 1)
                r = r.getConnectedRooms().get(0);
            else
                r = r.getConnectedRooms().get(1);
            System.out.printf(" is connected to %s.\n", r.getName());
            index++;
        }
    }

    @Test
    public void testPlayerMoveBetweenRoomsLinear_eachRoomConnectedToDesiredRoom() {
        // linear rooms room#0 -> room#1 -> ... -> room#9 -> null
        player.setCurrentPosition(r0);
        Room r = player.getCurrentPosition();
        int index = 0;
        String roomName = r.getName();
        // testing player's movement from room#0 to room#9
        while (index < 10) {
            Assert.assertEquals(player.getCurrentPosition().getName(), String.format("room#%d", index));
            if (r.getConnectedRooms().size() == 1)
                r = r.getConnectedRooms().get(0);
            else
                r = r.getConnectedRooms().get(1);
            player.moveTo(r.getName());
            index++;
        }
    }

    @Test
    public void testCanMoveToRoom_returnRoom1ThatConnectedToRoom0() {
        player.setCurrentPosition(r0);
        Assert.assertEquals(player.canMoveToRoom("room#1").getName(), "room#1");
    }
}