package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;

/**
 * singleton class Game
 * it provides access to a Map and a role Controller
 */
public class Game {
    private static Game game;
    private Castle castle = new Castle();
    private Player player;

    private Game() {
    }

    public static Game getInstance() {
        if (Game.game == null) {
            Game.game = new Game();
        }
        return Game.game;
    }

    /**
     * TODO: What does start() provide?
     */
    public void start() throws JsonProcessingException {
        System.out.println("Game started here...");
        String userName = Prompter.getUserInput("Welcome to Zombie Castle Rush! Please enter your name");
        player = new Player(userName);
        showInstructions();
        Prompter.getUserInput("\nPress enter to continue.");
        player.setCurrentPosition(castle.getCastleRooms().get("Castle-Hall"));
        while (true) {
            Prompter.advanceGame(player);
        }
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        System.out.println("Game stopped here.\nSaving status and releasing resources");
        System.exit(0);
    }

    public void showInstructions() {
        System.out.print("\nGame Instructions:");
        System.out.println("\n1. To go somewhere, please type go and one of the available locations displayed");
        System.out.println("2. To attempt a puzzle, please type \"attempt puzzle\"");
        System.out.println("3. To display player's status, please type \"display status\"");
        System.out.println("4. To pick-up or drop an item, please type \"pick-up\", \"drop\" and \"item name\"");
        System.out.println("5. To buy an item from the shop, please type \"buy\" and \"item name\"");
        System.out.println("6. To sell an item to the shop, please type \"sell\" and \"item name\"\n");
    }
}