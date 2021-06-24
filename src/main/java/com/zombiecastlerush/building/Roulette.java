package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;
import com.zombiecastlerush.util.Prompter;

public class Roulette extends Challenge{
    static int [] spins = new int[4];
    String description;

    public Roulette(String description){
        super(description);
    }
    public static int[] spin(){
        int a=(int)(Math.random()*(7-1))+1;
        int b=(int)(Math.random()*(7-1))+1;
        int c=(int)(Math.random()*(7-1))+1;
        int d=(int)(Math.random()*(7-1))+1;

        spins[0]=a;
        spins[1]=b;
        spins[2]=c;
        spins[3]=d;
        return spins;
    }
    public static void checkMatch(int [] arr, Player player){
        if(arr.length!=4){
            System.out.println("invalid arr");
            return;
        }

        ///wins
        else if(arr[0]==7 && arr[1]==7 && arr[2]==7 && arr[3]==7){
            player.setAcctBalance(player.getAcctBalance()+70);
            System.out.println("Congratulations");
            CoinGod.pause(2000);
            System.out.println("Big JackPot!!...new balance: "+ player.getAcctBalance());
        } else if(arr[0]==arr[1] && arr[1]==arr[2] && arr[2]==arr[3]){
            player.setAcctBalance(player.getAcctBalance()+70);
            System.out.println("JackPot!!...new balance: "+ player.getAcctBalance());
        } else if(arr[0]==arr[1] && arr[1]==arr[2]){
            player.setAcctBalance(player.getAcctBalance()+35);
            System.out.println("Mini JackPot!!...new balance: "+ player.getAcctBalance());
        }else if(arr[1]==arr[2] && arr[2]==arr[3]){
            player.setAcctBalance(player.getAcctBalance()+35);
            System.out.println("Mini JackPot!!...new balance: "+ player.getAcctBalance());
        }else if(arr[0]==arr[1] && arr[1]==arr[3]) {
            player.setAcctBalance(player.getAcctBalance() + 35);
            System.out.println("Mini JackPot!!...new balance: " + player.getAcctBalance());
        }else if(arr[0]==arr[2] && arr[2]==arr[3]) {
            player.setAcctBalance(player.getAcctBalance() + 35);
            System.out.println("Mini JackPot!!...new balance: " + player.getAcctBalance());
        }else if(arr[0]==arr[1]){
            player.setAcctBalance(player.getAcctBalance() + 12);
            System.out.println("Doubles!!...new balance: " + player.getAcctBalance());
        }else if(arr[1]==arr[2]){
            player.setAcctBalance(player.getAcctBalance() + 12);
            System.out.println("Doubles!!...new balance: " + player.getAcctBalance());
        }else if(arr[2]==arr[3]){
            player.setAcctBalance(player.getAcctBalance() + 12);
            System.out.println("Doubles!!...new balance: " + player.getAcctBalance());
        }else if(arr[0]==arr[3]){
            player.setAcctBalance(player.getAcctBalance() + 7);
            System.out.println("Corners!!...new balance: " + player.getAcctBalance());
        }
    }
public  void abd(){
        this.setCleared(true);
}
}