package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;

public class CoinGod {
    //bank is what coin god blesses player with
    private static double coinBank = 300.00;
    private String greeting;

    //constructor
    public CoinGod( String greeting){
        setGreeting(greeting);
    }
    //create greeting (to be called by constructor to make on initialization)
    public void setGreeting(String greeting){
        this.greeting = greeting;
    }
    //gives the player the blessing of his bank that never runs out he can always give more
    public static void blessPlayer(Player player){
        System.out.println("The power of the coin god is with you....\n you have been blessed");
        player.setAcctBalance(player.getAcctBalance()+ CoinGod.coinBank);
        System.out.println(player.getAcctBalance());
    }
    //chance of player to get blessed
    public static void chance(Player player){
        pause(1000);
        int hope = (int)(Math.random()*(20-3))+3;
        System.out.println("I AM THE COIN GOD AND MAY HELP YOUR JOURNEY\n YOU HAVE ROLLED FOR A CHANCE TO BE BLESSED\nYOUR NUMBER IS..."+ hope);
        pause(2000);
        if (hope==11){
            blessPlayer(player);
            pause(2000);
        }
    }
    //wait before cont
    public static void pause(int duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}