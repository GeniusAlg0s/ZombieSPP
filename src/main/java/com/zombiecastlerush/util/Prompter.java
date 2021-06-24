package com.zombiecastlerush.util;

import com.zombiecastlerush.building.*;
import com.zombiecastlerush.entity.Enemy;
import com.zombiecastlerush.entity.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.entity.Role;

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

    private static String path = "src/main/resources/sound/longSound.wav";
    private static MusicPlayer mp = new MusicPlayer(path);

    public static String getUserInput(String displayMessage) {
        System.out.printf(displayMessage + "\n>");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    static void advanceGame(Player player) throws JsonProcessingException {
        mp.soundLoop();
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
                                    solvePuzzle(currentRoom);
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
                                    combat(player, new Enemy("Zombie"));
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
                            if (currentRoom.getName().equals("Tomb")) {
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
    /*
    *these will allow the user to start and stop back ground music
    * to used in gui not the console version as it will
    * be attached to JButtons later
     */
    static void stopMusic() {
    mp.stop();
    }
    static void startMusic(){
        mp.soundLoop();
    }
    static void playSlot(Player player) throws JsonProcessingException {
        Room currentRoom = player.getCurrentPosition();
        Roulette roulette = (Roulette) currentRoom.getChallenge();
        roulette.setCleared(true);
        if (player.getAcctBalance() <= 0) {
            System.out.println("*********\nOUT OF CASH\n*********");
            return;
        }
        if (player.getAcctBalance() <= 2) {
            player.setAcctBalance(0);
            System.out.println("new balance: " + player.getAcctBalance());
        } else {
            player.setAcctBalance(player.getAcctBalance() - 2);
            System.out.println("new balance: " + player.getAcctBalance());
        }
        int[] currentSlot = Roulette.spin();
        System.out.println("Last Spin : " + Arrays.toString(currentSlot));
        Roulette.checkMatch(currentSlot, player);
        advanceGame(player);

    }

    static void solvePuzzle(Room room) {
        Puzzle puzzle = (Puzzle) room.getChallenge();
        System.out.println(Parser.YELLOW + "Here is your puzzle....Remember you only have " + (3 - puzzle.getAttempts()) + " tries!" + Parser.ANSI_RESET);
        puzzle.attemptPuzzle(getUserInput(puzzle.getQuestion()));
        if (puzzle.getAttempts() < 3 && !puzzle.isCleared())
            solvePuzzle(room);
        else if (puzzle.isCleared()) {
            System.out.println(Parser.GREEN + "Right answer. You can now move to the available rooms" + Parser.ANSI_RESET);
            if (puzzle.getInventory().getItems().size() > 0) {
                System.out.println("You have solved the riddle, and unlocked. " + Parser.GREEN + puzzle.getInventory().toString() + "\n" + Parser.ANSI_RESET);
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
    }

    public static void combat(Role player, Role enemy) {
        var cleared = player.getCurrentPosition().getChallenge().isCleared();
        if (!cleared) {
            Combat.combat(player, enemy);
            while (player.getHealth() > 0 && enemy.getHealth() > 0) {
                //creat temporary list to check for fight synonyms while in combat
                List<String> fightTemp = new ArrayList<>();
                List<String> runTemp = new ArrayList<>();
                fightTemp.addAll(Parser.FIGHT_LIST);
                runTemp.addAll(Parser.RUN_LIST);

                String msg = "what would you like to do, \"fight\" or \"run\"?";
                String combatChoice = Prompter.getUserInput(msg).toLowerCase();

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
                    Prompter.getUserInput("You are dead. Press Enter to continue.");
                    Game.getInstance().stop();
                }
                currentPosition.getChallenge().setCleared(true);
                if (enemy.getHealth() <= 0) {
                    if (currentPosition.isExit()) {
                        Prompter.getUserInput("You have found the exit, killed the last monster, and beaten the game! Press Enter to continue");
                        Game.getInstance().stop();
                    }
                }

            }

        } else {
            System.out.println("Room has no Enemy");
        }
    }

    public static void displayCurrentScene(Player player) {
        Room currentRoom = player.getCurrentPosition();
        List<Room> availableRooms = currentRoom.getConnectedRooms();
        int numItemsInRoom = currentRoom.getInventory().getItems().size();
        String itemClueText = Parser.GREEN + "pick-up" + Parser.ANSI_RESET + ":";
        if (currentRoom instanceof Shop) {
            itemClueText = Parser.GREEN + "buy" + Parser.ANSI_RESET + " or" + Parser.GREEN + " sell" + Parser.ANSI_RESET + ":";
        }
        String numItemsString = numItemsInRoom > 0 ? numItemsInRoom + " item(s) in here which you can " + itemClueText : "";

        System.out.println("You are in the " + currentRoom + ". " + currentRoom.getDescription() + "\n");
        if (currentRoom.getChallenge() != null && !currentRoom.getChallenge().isCleared()) {
            Challenge currRoomChallenge = currentRoom.getChallenge();
            if (currRoomChallenge instanceof Puzzle) {
                System.out.println("The box pulses with power. You know not how, but it has a riddle for you, and it will not let you leave until you have solved it. Perhaps you should " + Parser.GREEN + "attempt puzzle" + Parser.ANSI_RESET + ".");
            } else if (currRoomChallenge instanceof Combat && currentRoom.getName().equals("Combat-Hall")) {
                System.out.println("A rotting hand reaches and knocks the lid to the ground with a resounding crash. A monster rises from the coffin and fixes its lifeless, pitiless gaze upon you. It's time to " + Parser.GREEN + "fight" + Parser.ANSI_RESET + ".");
            } else if (currRoomChallenge instanceof Combat && currentRoom.getName().equals("Grave-Yard")) {
                System.out.println("The ghost goes underground. A posed corps digs up from underground and fixes its lifeless, pitiless gaze upon you. It's time to " + Parser.GREEN + "fight" + Parser.ANSI_RESET + ".");
            } else if (currentRoom.getName().equalsIgnoreCase("Tomb")) {
                System.out.println("\nYou can keep playing" + Parser.GREEN + "spin" + Parser.ANSI_RESET + " or you can " + Parser.GREEN + "go" + Parser.ANSI_RESET + " to one of the following locations " + availableRooms);
            }
        } else {

            String roomInventory = currentRoom.getInventory().toString();
            if (currentRoom instanceof Shop) {
                roomInventory = ((Shop) currentRoom).toStringShopInventory() + "\nYou have $" + player.getAcctBalance();
            }
            System.out.println(numItemsString + " " + roomInventory);

            if (player.getInventory().getItems().size() > 0) {
                String dropOrSellText = (currentRoom instanceof Shop) ? "sell" : "drop";
                System.out.println("You have the following items that you can " + Parser.GREEN + dropOrSellText + Parser.ANSI_RESET + ": " + player.getInventory().toString());
            }
            System.out.println("\nYou can " + Parser.GREEN + "go" + Parser.ANSI_RESET + " to one of the following locations " + availableRooms);
        }
        System.out.print("\nActions applicable: " + sceneContextmenu(currentRoom, player) + "  ");

    }

    public static List<String> sceneContextmenu(Room room, Player player) {
        List<String> actionApplicable = new ArrayList<>(Arrays.asList(
                Parser.GREEN + "go" + Parser.ANSI_RESET,
                Parser.GREEN + "display status" + Parser.ANSI_RESET,
                Parser.GREEN + "help" + Parser.ANSI_RESET,
                Parser.GREEN + "quit" + Parser.ANSI_RESET));


        if (room.getChallenge() instanceof Puzzle && !room.getChallenge().isCleared())
            actionApplicable.add(Parser.GREEN + "attempt puzzle" + Parser.ANSI_RESET);
        if (room instanceof Shop) {
            actionApplicable.add(Parser.GREEN + "buy" + Parser.ANSI_RESET);
            if (player.getInventory().getItems().size() > 0)
                actionApplicable.add(Parser.GREEN + "sell" + Parser.ANSI_RESET);
        }
        if (!(room instanceof Shop) && room.getInventory().getItems().size() > 0) {
            actionApplicable.add(Parser.GREEN + "pick-up" + Parser.ANSI_RESET);
        }
        if (!(room instanceof Shop) && player.getInventory().getItems().size() > 0) {
            actionApplicable.add(Parser.GREEN + "drop" + Parser.ANSI_RESET);
        }
        if (room.getName().equalsIgnoreCase("Combat-Hall") && !room.getChallenge().isCleared()) {
            actionApplicable.add(Parser.GREEN + "fight" + Parser.ANSI_RESET);
        }
        if (room.getName().equalsIgnoreCase("Grave-Yard") && !room.getChallenge().isCleared()) {
            actionApplicable.add(Parser.GREEN + "fight" + Parser.ANSI_RESET);
        }
        if (room.getName().equalsIgnoreCase("Tomb")) {
            actionApplicable.add(Parser.GREEN + "spin" + Parser.ANSI_RESET);
        }
        return actionApplicable;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

    }
}