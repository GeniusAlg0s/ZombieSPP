package com.zombiecastlerush.util;

import com.zombiecastlerush.building.Challenge;
import com.zombiecastlerush.building.Item;
import com.zombiecastlerush.building.Room;
import com.zombiecastlerush.building.Shop;
import com.zombiecastlerush.entity.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PrompterTest {
   static Room room1;
    Player p1;
    Shop s1 = new Shop("S1", "The shop");

    @Before
    public void setUp() throws Exception {
        room1 = new Room("R1", "test1");
        p1 = new Player("P1", room1);
        s1.getInventory().addItems(new Item("sword", "Killer sword", 100.0));
    }

    @Test
    public void testSceneContextmenu_whenEmptyRoom_playerHasNothing() {
        List<String> expected_testactionList = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET));
        List<String> actual_list = Prompter.sceneContextmenu(room1, p1);
        assertEquals(expected_testactionList, actual_list);
    }

    @Test
    public void testSceneContextmenu_whenRoomHasAnItemPlayerHasNothing() {
        room1.getInventory().addItems(new Item("Stick", "Long Stick", 10.0));
        List<String> expected_testactionList = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET,
                Parser.GREEN + "pick-up" + Parser.ANSI_RESET));
        List<String> actual_list = Prompter.sceneContextmenu(room1, p1);
        assertEquals(expected_testactionList, actual_list);
    }

    @Test
    public void testSceneContextmenu_whenRoomHasNothingPlayerHasItem() {
        p1.getInventory().addItems(new Item("Stick", "Long Stick", 10.0));
        List<String> expected_testactionList = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET,
                Parser.GREEN + "drop" + Parser.ANSI_RESET));
        List<String> actual_list = Prompter.sceneContextmenu(room1, p1);
        assertEquals(expected_testactionList, actual_list);
    }

    @Test
    public void testSceneContextmenu_whenRoomHasSomething_PlayerHasSomething() {
        room1.getInventory().addItems(new Item("Stick", "Long Stick", 10.0));
        p1.getInventory().addItems(new Item("Lamp", "Bright lamp", 10.0));
        List<String> expected_testactionList = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET,
                Parser.GREEN + "pick-up" + Parser.ANSI_RESET,
                Parser.GREEN + "drop" + Parser.ANSI_RESET));
        List<String> actual_list = Prompter.sceneContextmenu(room1, p1);
        assertEquals(expected_testactionList, actual_list);
    }

    @Test
    public void testSceneContextmenu_whenRoomHasAnItemPlayerHasSomething() {
        room1.getInventory().addItems(new Item("Stick", "Long Stick", 10.0));
        p1.getInventory().addItems(new Item("Lamp", "Bright lamp", 10.0));
        List<String> expected_testactionList = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET,
                Parser.GREEN + "pick-up" + Parser.ANSI_RESET,
                Parser.GREEN + "drop" + Parser.ANSI_RESET));
        List<String> actual_list = Prompter.sceneContextmenu(room1, p1);
        assertEquals(expected_testactionList, actual_list);
    }

    @Test
    public void testSceneContextmenu_whenRoomisShop_playerhasNothing() {
        List<String> expected_testactionList = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET,
                Parser.GREEN + "buy" + Parser.ANSI_RESET));
        List<String> actual_list = Prompter.sceneContextmenu(s1, p1);
        assertEquals(expected_testactionList, actual_list);
    }

    @Test
    public void testSceneContextmenu_whenRoomisShop_playerhasSomething() {
        p1.getInventory().addItems(new Item("MRE", "Best Food", 10.0));
        List<String> expected_testactionList = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET,
                Parser.GREEN + "buy" + Parser.ANSI_RESET,
                Parser.GREEN + "sell" + Parser.ANSI_RESET));
        List<String> actual_list = Prompter.sceneContextmenu(s1, p1);
        assertEquals(expected_testactionList, actual_list);
    }

    @Test
    public void testSceneContextmenu_whenRoomisCombatHall() {
        room1.setName("Combat-Hall");
        room1.setChallenge(new Challenge("Monster"));
        List<String> expected_testactionList = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET,
                Parser.GREEN + "fight" + Parser.ANSI_RESET));
        List<String> actual_list = Prompter.sceneContextmenu(room1, p1);
        assertEquals(expected_testactionList, actual_list);

        //when monster is cleared
        room1.getChallenge().setCleared(true);
        expected_testactionList = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET));
        actual_list = Prompter.sceneContextmenu(room1, p1);
        assertEquals(expected_testactionList, actual_list);
    }
}