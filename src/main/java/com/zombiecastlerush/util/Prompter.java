package com.zombiecastlerush.util;

import com.zombiecastlerush.building.*;
import com.zombiecastlerush.entity.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * static class and methods
 * interacting between users and this game
 */
public class Prompter {

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

    static String playSlot(Player player){
        StringBuilder gambleString = new StringBuilder();
        Room currentRoom = player.getCurrentPosition();
        Roulette roulette = (Roulette) currentRoom.getChallenge();
        roulette.setCleared(true);
        if(player.getAcctBalance()<=0){
            gambleString.append("*********\nOUT OF CASH\n*********");
            return gambleString.toString();
        }
        if (player.getAcctBalance() <= 2) {
            player.setAcctBalance(0);
            gambleString.append("Place your bet! New balance: "+ player.getAcctBalance() + "\n");
        }else {
            player.setAcctBalance(player.getAcctBalance() - 2);
            gambleString.append("Place your bet! New balance: " + player.getAcctBalance() + "\n");
        }
      
        int [] currentSlot = Roulette.spin();
        gambleString.append("Last Spin : "+ Arrays.toString(currentSlot) + "\n");
        gambleString.append(Roulette.checkMatch(currentSlot,player));
        return gambleString.toString();
    }

    static String solvePuzzle(Room room, String userInput) {
        StringBuilder puzzleString = new StringBuilder();
        Puzzle puzzle = (Puzzle) room.getChallenge();
        String triesLeft = "Here is your puzzle....Remember you only have " + (3 - puzzle.getAttempts()) + " tries!";
        System.out.println(triesLeft);
        puzzle.attemptPuzzle(userInput);
        if (puzzle.getAttempts() < 3 && !puzzle.isCleared())
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
            Game.getInstance().stop();
        }
        return puzzleString.toString();
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
                String ifGrave = "The ghost goes underground. A tomb robber is digging up a grave, he glances your direction menacingly. It's time to fight.";
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
}