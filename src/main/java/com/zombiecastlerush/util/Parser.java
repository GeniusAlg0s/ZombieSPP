package com.zombiecastlerush.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\u001B[32m"; //GREEN
    public static final String YELLOW = "\u001B[33m"; //YELLOW
    public static final String ANSI_RESET = "\u001B[0m";


    final static List<String> ALLOWED_ACTIONS = Arrays.asList("go", "look", "pick-up", "drop", "attempt", "display", "quit", "buy", "sell", "fight", "spin","run");
    // synonym lists for commands
    public static final List<String> GO_LIST = Arrays.asList("go", "walk", "move");
    public static final List<String> ATTEMPT_LIST = Arrays.asList("attempt", "solve", "try");
    public static final List<String> PICK_UP_LIST = Arrays.asList("pick-up", "get", "try");
    public static final List<String> DISPLAY_LIST = Arrays.asList("display", "show", "list");
    public static final List<String> QUIT_LIST = Arrays.asList("quit", "exit", "die");

    //synonym for combat scenarios in Prompter.combat()
    public static final List<String> FIGHT_LIST = Arrays.asList("fight", "attack", "hit");
    public static final List<String> RUN_LIST = Arrays.asList("run", "flee", "hide");

    public static List<String> parse(String input) {
        List<String> inputWords = Arrays.asList(input.toLowerCase().split(" "));
        //assign local variable to value of corresponding synonym command if found
        String check = checkSyn(input);
        inputWords.set(0, check);
        List<String> result = ALLOWED_ACTIONS.contains(check) ? reduceInputWordsToList(inputWords) : null;
        return result;
    }

    public static List<String> reduceInputWordsToList(List<String> arr) {
        List<String> list = new ArrayList<>(arr);
        list.removeAll(Arrays.asList("", null, " "));  //removes empty and null elements
        list.replaceAll(x -> x.trim());
        return list;
    }

    /*
     *checks the input against synonym list if found return corresponding synonym command if found
     * if not found return the original input
     */
    public static String checkSyn(String input) {
        List<String> inputWords = Arrays.asList(input.toLowerCase().split(" "));
        if (GO_LIST.contains(inputWords.get(0))) {
            return "go";
        } else if (ATTEMPT_LIST.contains(inputWords.get(0))) {
            return "attempt";
        } else if (PICK_UP_LIST.contains(inputWords.get(0))) {
            return "pick-up";
        } else if (DISPLAY_LIST.contains(inputWords.get(0))) {
            return "display";
        } else if (QUIT_LIST.contains(inputWords.get(0))) {
            return "quit";
        }else if (FIGHT_LIST.contains(inputWords.get(0))) {
            return "fight";
        } else {
            return inputWords.get(0);
        }
    }
}