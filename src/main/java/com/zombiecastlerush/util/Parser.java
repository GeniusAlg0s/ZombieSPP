package com.zombiecastlerush.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\u001B[32m"; //GREEN
    public static final String YELLOW = "\u001B[33m"; //YELLOW
    public static final String ANSI_RESET = "\u001B[0m";

    final static List<String> ALLOWED_ACTIONS = Arrays.asList("go", "look", "pick-up", "drop", "attempt", "display", "quit", "buy", "sell", "fight");

    public static List<String> parse(String input) {
        List<String> inputWords = Arrays.asList(input.toLowerCase().split(" "));
        List<String> result = ALLOWED_ACTIONS.contains(inputWords.get(0)) ? reduceInputWordsToList(inputWords) : null;
        return result;
    }

    public static List<String> reduceInputWordsToList(List<String> arr) {
        List<String> list = new ArrayList<>(arr);
        list.removeAll(Arrays.asList("", null, " "));  //removes empty and null elements
        list.replaceAll(x -> x.trim());
        return list;
    }
}