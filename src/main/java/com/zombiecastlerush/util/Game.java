package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * singleton class Game
 * it provides access to a Map and a role Controller
 */
public class Game {
    private static Game game;
    static Castle castle = new Castle();
    static Player player;

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
        UserInterface ui = UserInterface.getInstance();
        ui.startUI();
        showInstructions();

        while (true) {
        }
    }

    /**
     * TODO: what does stop() provide?
     */
    public void stop() {
        String tyForPlaying = "Thank you for playing Zombie Castle Rush!";
        System.out.println(tyForPlaying);
        Frame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, tyForPlaying);
        System.exit(0);
    }

    public void showInstructions() {
        System.out.println("\nGame Instructions:");
        System.out.printf(Parser.GREEN+"%2s %8s %47s %n", "", "Action   ", "       Command to Type"+Parser.ANSI_RESET);
        System.out.printf("%2s %8s %45s %n", "", "----------------------------", "         --------------------------------------------------");
        System.out.printf("%2s %-30s %1s %-10s %n", " 1.", "Go somewhere","|    ", "\"go\" and one of the available locations displayed");
        System.out.printf("%2s %-30s %1s %-10s %n", " 2.", "attempt a puzzle","|    ", "\"attempt puzzle\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 3.", "display player's status","|    ", "\"display status\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 4.", "pick-up or drop an item","|    ", "\"pick-up\", \"drop\" and \"item name\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 5.", "buy an item from the shop","|    ", "\"buy\" and \"item name\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 6.", "sell an item to the shop","|    ", "\"sell\" and \"item name\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 7.", "fight a monster","|    ", "\"fight\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 8.", "display instructions","|    ", "\"help\"");
        System.out.printf("%2s %-30s %1s %-1s %n", " 9.", "quit the game","|    ", "\"quit\"");

        Prompter.getUserInput("\nPress enter to continue...");
        Prompter.clearScreen();
    }

    public String printInstructions() {
        StringBuffer instructions = new StringBuffer();
        ArrayList <String> instructionList = new ArrayList<>();
        instructionList.add("\nGame Instructions:\n");
        instructionList.add("Action                                           Command to Type");
        instructionList.add("---------------------      |    -------------------------------------");
        instructionList.add(" 1. Go somewhere                    |    \"go\" and one of the available locations displayed");
        instructionList.add(" 2. Attempt a puzzle                |    \"attempt puzzle\"");
        instructionList.add(" 3. Display player's status       |    \"display status\"");
        instructionList.add(" 4. Pick-up or drop an item     |    \"pick-up\", \"drop\" and \"item name\"");
        instructionList.add(" 5. Buy an item from the shop |    \"buy\" and \"item name\"");
        instructionList.add(" 6. Sell an item to the shop     |    \"sell\" and \"item name\"");
        instructionList.add(" 7. Fight a monster                  |    \"fight\"");
        instructionList.add(" 8. Display instructions           |    \"help\"");
        instructionList.add(" 9. Quit the game                    |    \"quit\"");

        instructionList.forEach(instruction -> instructions.append(instruction + "\n"));
        return instructions.toString();
    }
}