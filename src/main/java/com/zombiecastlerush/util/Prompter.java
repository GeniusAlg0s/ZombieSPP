package com.zombiecastlerush.util;

import com.zombiecastlerush.building.*;
import com.zombiecastlerush.entity.Enemy;
import com.zombiecastlerush.entity.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.entity.Role;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * static class and methods
 * interacting between users and this game
 * TODO: deploy APIs that supports the web game version
 */
public class Prompter {
    public static String getUserInput(String displayMessage) {
        System.out.printf(displayMessage + "\n>");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    static void advanceGame(String action, Player player) {
        UserInterface ui = UserInterface.getInstance();
        Room currentRoom = player.getCurrentPosition();
        switch (action) {
            case "go":
                List<Room> rooms = player.getCurrentPosition().getConnectedRooms();
                ui.goMenu(rooms);
                break;
            case "pick-up":
                ui.puMenu(currentRoom.getInventory().getItems());
                break;
            case "buy":
                ui.buyMenu(currentRoom.getInventory().getItems());
                break;
            case "battle":
                ui.battle();
                break;
            case "attempt puzzle":
                ui.attemptPuzzle(player.getCurrentPosition());
                ui.mainButtons();
                break;
            case "drop":
                ui.dropMenu(player.getInventory().getItems());
                break;
            case "sell":
                ui.sellMenu(player.getInventory().getItems());
                break;
            case "cancel":
                ui.goMain();
                break;
            case "quit":
                System.exit(0);
        }
    }

    static void advanceGame(Player player) throws JsonProcessingException {
        displayCurrentScene(player);
        Room currentRoom = player.getCurrentPosition();
        String userInput = Prompter.getUserInput("Enter \"help\" if you need help with the commands");
        List<String> userInputList = Parser.parse(userInput);
        clearScreen();

        if (userInputList != null) {
            String action = userInputList.get(0);
            switch (userInputList.size()) {

                case 2:
                    switch (action) {
                        case "go":
                            CoinGod.chance(player);
                            player.moveTo(userInputList.get(1));
                            break;
                        case "attempt":
                            if (userInputList.get(1).equals("puzzle")) {
                                if (currentRoom.getChallenge() != null && currentRoom.getChallenge() instanceof Puzzle && !currentRoom.getChallenge().isCleared()) {
                                    getUserInput("\nYou touch your hands to the box and cannot let go. You feel that the box demands you answer its question. You do not know how or why you are compelled, but you are.\nPress Enter to solve the Puzzle.");
                                    solvePuzzle(currentRoom, null);
                                } else
                                    System.out.println("There is no puzzle in the room");
                            }
                            break;
                        case "display":
                            if (userInputList.get(1).equalsIgnoreCase("status"))
                                System.out.println(player.displayStatus());
                            break;
                        case "pick-up":
                            if (!(currentRoom instanceof Shop)) {
                                for (Item item : currentRoom.getInventory().getItems()) {
                                    if (item.getName().equalsIgnoreCase(userInputList.get(1))) {
                                        player.pickUp(item);
                                        break;
                                    }
                                }
                            } else {
                                System.out.println(Parser.RED + "You can't do that here." + Parser.ANSI_RESET);
                            }
                            break;
                        case "drop":
                            for (Item item : player.getInventory().getItems()) {
                                if (item.getName().equalsIgnoreCase(userInputList.get(1))) {
                                    player.drop(item);
                                    break;
                                }
                            }
                        case "buy":
                            if (currentRoom instanceof Shop) {
                                for (Item item : currentRoom.getInventory().getItems()) {
                                    if (item.getName().equalsIgnoreCase(userInputList.get(1))) {
                                        ((Shop) currentRoom).sellItemToPlayer(player, item);
                                        break;
                                    }
                                }
                            }
                            break;
                        case "sell":
                            if (currentRoom instanceof Shop) {
                                for (Item item : player.getInventory().getItems()) {
                                    if (item.getName().equalsIgnoreCase(userInputList.get(1))) {
                                        ((Shop) currentRoom).buyItemFromPlayer(player, item);
                                        break;
                                    }
                                }
                            }
                            break;
                    }

                case 1:
                    switch (action) {
                        case "fight":
                            if (!currentRoom.getChallenge().isCleared() && userInputList.get(0).equals("fight")) {
                                if (currentRoom.getChallenge() != null && currentRoom.getChallenge() instanceof Combat && !currentRoom.getChallenge().isCleared()) {
                                    getUserInput("\nPrepare for COMBAT... press enter to continue");
//                                    combat(player, new Enemy("Zombie"));
                                } else {
                                    System.out.println("There is no Monster in the room");
                                    break;
                                }
                            } else {
                                System.out.println("There is no Monster in the room");
                                break;
                            }
                            break;
                        case "spin":
                            if(currentRoom.getName().equals("Tomb")){
                                getUserInput("\nLets get money... press enter to continue");
                                playSlot(player);
                                break;
                            }
                        case "quit":
                            Game.getInstance().stop();
                            break;
                    }
                    break;
            }
        } else {
            //sout invalid input
            System.out.println("invalid input");
            Game.getInstance().showInstructions();
        }
    }
    static String playSlot(Player player){
        StringBuilder gambleString = new StringBuilder();
        Room currentRoom = player.getCurrentPosition();
        Roulette roulette = (Roulette) currentRoom.getChallenge();
        roulette.setCleared(true);
        if(player.getAcctBalance()<=0){
            gambleString.append("*********\nOUT OF CASH\n*********");
            return gambleString.toString();
        }
        if(player.getAcctBalance()<=2){
            player.setAcctBalance(0);
            gambleString.append("Place your bet! New balance: "+ player.getAcctBalance() + "\n");
        }else {
            player.setAcctBalance(player.getAcctBalance() - 2);
            gambleString.append("Place your bet! New balance: " + player.getAcctBalance() + "\n");
        }
        int [] currentSlot = Roulette.spin();
        gambleString.append("Last Spin : "+ Arrays.toString(currentSlot) + "\n");
        gambleString.append(Roulette.checkMatch(currentSlot,player));
//        advanceGame(player);
        return gambleString.toString();
    }

    static String solvePuzzle(Room room, String userInput) {
        StringBuilder puzzleString = new StringBuilder();
        Puzzle puzzle = (Puzzle) room.getChallenge();
        String triesLeft = "Here is your puzzle....Remember you only have " + (3 - puzzle.getAttempts()) + " tries!";
        System.out.println(triesLeft);
//        puzzle.attemptPuzzle(getUserInput(puzzle.getQuestion()));
        puzzle.attemptPuzzle(userInput);
        if (puzzle.getAttempts() < 3 && !puzzle.isCleared())
//            solvePuzzle(room, userInput);
            UserInterface.attemptPuzzle(room);
        else if (puzzle.isCleared()) {
            String rightAnswer = "Right answer! You can now move to the available rooms: " + room.getConnectedRooms().toString();
            puzzleString.append(rightAnswer + "\n\n");
            if (puzzle.getInventory().getItems().size() > 0) {
                String unlocked = "You solved " + puzzle.getDescription() + "! Inside the box you find: " + puzzle.getInventory().toString() + "\n";
                puzzleString.append(unlocked);
                puzzle.getInventory().transferItem(
                        puzzle.getInventory(),
                        room.getInventory(),
                        puzzle.getInventory().getItems().toArray(new Item[0])
                );
            }
        } else {
            System.out.println(Parser.RED + "Wrong Answer!! You have had your chances...You failed...Game Over!!!" + Parser.ANSI_RESET);
            Game.getInstance().stop();
        }
        return puzzleString.toString();
    }

    public static void combat(Role player, Role enemy, String combatChoice) {
        StringBuilder combatText = new StringBuilder();
        var cleared = player.getCurrentPosition().getChallenge().isCleared();
        if (!cleared) {
//            Combat.combat(player, enemy);
            while (player.getHealth() > 0 && enemy.getHealth() > 0) {
                //creat temporary list to check for fight synonyms while in combat
                List<String> fightTemp = new ArrayList<>();
                List<String> runTemp = new ArrayList<>();
                fightTemp.addAll(Parser.FIGHT_LIST);
                runTemp.addAll(Parser.RUN_LIST);

                String msg = "what would you like to do, \"fight\" or \"run\"?";
//                String combatChoice = Prompter.getUserInput(msg).toLowerCase();

                //checks if combat choice is synonym for fight or run
                if (fightTemp.contains(combatChoice)) {
                    combatChoice = "fight";
                } else if (runTemp.contains(combatChoice)) {
                    combatChoice = "run";
                } else {
                    combatChoice = combatChoice;
                }
                if (combatChoice.equals("fight")) {
                    Combat.combat(player, enemy);
                } else if (combatChoice.equals("run")) {
                    player.setCurrentPosition(player.getCurrentPosition().getConnectedRooms().get(0));
                    System.out.println("That is a weak move. But you have escaped death for now.");
                    player.decreaseHealth(15);
                    System.out.println("Since you escaped, you took a damage of 15 points on your health.");
                    break;
//                  Combat.enemyAttack(player,enemy);
                }
            }
            if (enemy.getHealth() <= 0 || player.getHealth() <= 0) {
                Room currentPosition = player.getCurrentPosition();
                if (player.getHealth() <= 0) {
//                    Prompter.getUserInput("You are dead. Press Enter to continue.");
                    Frame frame = new JFrame();
                }
                currentPosition.getChallenge().setCleared(true);
                if (enemy.getHealth() <= 0) {
                    if (currentPosition.isExit()) {
                        Frame frame = new JFrame();
                    }
                }
            }
        } else {
            System.out.println("Room has no Enemy");
        }
    }

    public static String displayCurrentScene(Player player) {
        StringBuilder roomString = new StringBuilder();
        Room currentRoom = player.getCurrentPosition();
        List<Room> availableRooms = currentRoom.getConnectedRooms();
        int numItemsInRoom = currentRoom.getInventory().getItems().size();
        String itemClueText = "pick-up:";
        if (currentRoom instanceof Shop) {
            itemClueText = "buy or sell:";
        }
        String numItemsString = numItemsInRoom > 0 ? numItemsInRoom + " item(s) in here which you can " + itemClueText : "";
//        roomString.append(numItemsString + "\n");

        String roomIn = "You are in the " + currentRoom + ". " + currentRoom.getDescription() + "\n";
        System.out.println(roomIn);
        roomString.append(roomIn + "\n");

        if (currentRoom.getChallenge() != null && !currentRoom.getChallenge().isCleared()) {
            Challenge currRoomChallenge = currentRoom.getChallenge();
            if (currRoomChallenge instanceof Puzzle) {
                String ifPuzz = "The box pulses with power. You know not how, but it has a riddle for you, and it will not let you leave until you have solved it. Perhaps you should attempt puzzle.";
                System.out.println(ifPuzz);
                roomString.append(ifPuzz + "\n");
            } else if (currRoomChallenge instanceof Combat && currentRoom.getName().equals("Combat-Hall")) {
                String ifCombat = "A rotting hand reaches and knocks the lid to the ground with a resounding crash. A monster rises from the coffin and fixes its lifeless, pitiless gaze upon you. It's time to fight.";
                System.out.println(ifCombat);
                roomString.append(ifCombat);
            } else if (currRoomChallenge instanceof Combat && currentRoom.getName().equals("Grave-Yard")) {
                String ifGrave = "The ghost goes underground. A posed corps digs up from underground and fixes its lifeless, pitiless gaze upon you. It's time to fight.";
                System.out.println(ifGrave);
                roomString.append(ifGrave);
            }else if (currentRoom.getName().equalsIgnoreCase("Tomb")){
                String gamble = "\nYou can keep playing spin or you can go to one of the following locations: " + availableRooms;
                roomString.append(gamble);
            }
        } else {

            String roomInventory = currentRoom.getInventory().toString();
            if (currentRoom instanceof Shop) {
                roomInventory = ((Shop) currentRoom).toStringShopInventory() + "\nYou have $" + player.getAcctBalance();
            }
            System.out.println(numItemsString + " " + roomInventory);
            roomString.append(numItemsString + " " + roomInventory + "\n\n");

            if (player.getInventory().getItems().size() > 0) {
                String dropOrSellText = (currentRoom instanceof Shop) ? "sell" : "drop";
                String youHave = "You have the following items that you can " + dropOrSellText + ": " + player.getInventory().toString();
                System.out.println(youHave);
                roomString.append(youHave + "\n");
            }
            String canGo = "\nYou can go to one of the following locations " + availableRooms;
            System.out.println(canGo);
            roomString.append(canGo);
        }
        String actions = "\nActions applicable: " + sceneContextmenu(currentRoom, player) + "  ";
        System.out.println(actions);
        //roomString.append(actions);

        return roomString.toString();
    }

    public static List<String> sceneContextmenu(Room room, Player player) {
        List<String> actionApplicable = new ArrayList<>();
        actionApplicable.add("go");

        if (room.getChallenge() instanceof Puzzle && !room.getChallenge().isCleared()) {
            actionApplicable.add("attempt puzzle");
            actionApplicable.remove("go");
        }

        if (room instanceof Shop) {
            actionApplicable.add("buy");
            if (player.getInventory().getItems().size() > 0)
                actionApplicable.add("sell");
        }
        if (!(room instanceof Shop) && room.getInventory().getItems().size() > 0) {
            actionApplicable.add("pick-up");
        }
        if (!(room instanceof Shop) && player.getInventory().getItems().size() > 0) {
            actionApplicable.add("drop");
        }
        if (room.getName().equalsIgnoreCase("Combat-Hall") && !room.getChallenge().isCleared()) {
            actionApplicable.add("battle");
            actionApplicable.remove("go");
        }
        if (room.getName().equalsIgnoreCase("Grave-Yard") && !room.getChallenge().isCleared()) {
            actionApplicable.add("battle");
            actionApplicable.remove("go");
        }
        return actionApplicable;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}