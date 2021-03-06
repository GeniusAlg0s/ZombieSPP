package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;

public class Roulette extends Challenge {
    static int[] spins = new int[4];

    public Roulette(String description) {
        super(description);
    }

    public static int[] spin() {
        int a = (int) (Math.random() * (7 - 1)) + 1;
        int b = (int) (Math.random() * (7 - 1)) + 1;
        int c = (int) (Math.random() * (7 - 1)) + 1;
        int d = (int) (Math.random() * (7 - 1)) + 1;

        spins[0] = a;
        spins[1] = b;
        spins[2] = c;
        spins[3] = d;
        return spins;
    }

    public static String checkMatch(int[] arr, Player player) {
        StringBuilder rouletteString = new StringBuilder();
        if (arr.length != 4) {
            System.out.println("invalid arr");
            return null;
        }

        ///wins
        else if (arr[0] == 7 && arr[1] == 7 && arr[2] == 7 && arr[3] == 7) {
            player.setAcctBalance(player.getAcctBalance() + 140);
            rouletteString.append("Congratulations!\n");
            rouletteString.append("BIG JACKPOT!!... New balance: " + player.getAcctBalance() + "\n");
        } else if (arr[0] == arr[1] && arr[1] == arr[2] && arr[2] == arr[3]) {
            player.setAcctBalance(player.getAcctBalance() + 70);
            rouletteString.append("JackPot!!... New balance: " + player.getAcctBalance() + "\n");
        } else if ((arr[0] == arr[1] && arr[1] == arr[2]) || (arr[1] == arr[2] && arr[2] == arr[3]) || (arr[0] == arr[1] && arr[1] == arr[3]) || (arr[0] == arr[2] && arr[2] == arr[3])) {
            player.setAcctBalance(player.getAcctBalance() + 35);
            rouletteString.append("Mini JackPot!!... New balance: " + player.getAcctBalance() + "\n");
        } else if (arr[0] == arr[1] || arr[1] == arr[2] || arr[2] == arr[3]) {
            player.setAcctBalance(player.getAcctBalance() + 12);
            rouletteString.append("Doubles!!... New balance: " + player.getAcctBalance() + "\n");
        } else if (arr[0] == arr[3]) {
            player.setAcctBalance(player.getAcctBalance() + 7);
            rouletteString.append("Corners!!... New balance: " + player.getAcctBalance() + "\n");
        }
        return rouletteString.toString();
    }
}