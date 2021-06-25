package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;

public class CoinGod {
    //bank is what coin god blesses player with
    private static double coinBank = 300.00;

    //gives the player the blessing of his bank that never runs out he can always give more
    public static String blessPlayer(Player player){
        StringBuilder blessString = new StringBuilder();
        String power = "\nThe power of the coin god is with you....\n you have been blessed\n";
        blessString.append(power);
        player.setAcctBalance(player.getAcctBalance()+ CoinGod.coinBank);
        blessString.append(player.getAcctBalance() + "\n");
        return null;
    }
    //chance of player to get blessed
    public static String chance(Player player){
        StringBuilder coinGod = new StringBuilder();
        int hope = (int)(Math.random()*(20-3))+3;
        String iAmGod = "COIN GOD: \"I am the coin god and may help your journey!\n You have rolled for a chance to be #ble$$ed.\nYou number is: "+ hope + "!\"\n";
        coinGod.append(iAmGod);
        if (hope==11){
            coinGod.append(blessPlayer(player));
        }
        return coinGod.toString();
    }
}