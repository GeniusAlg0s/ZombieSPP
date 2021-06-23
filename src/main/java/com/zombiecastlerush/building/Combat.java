package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Entity;
import com.zombiecastlerush.entity.Role;

import java.util.List;
import java.util.Random;

public class Combat extends Challenge {
    static int playerDamageToEnemy = new Random().nextInt(50) + 1;
    static int enemyDamageToPlayer = new Random().nextInt(30) + 1;

    public Combat(String description) {
        super(description);
    }

    public static void combat(Role player, Role enemy) {
        List<Item> items = player.getInventory().getItems();
        for (Item item : items) {
            if (item.getName().equals("Wand")){
                playerDamageToEnemy += 30; //replaces sword with wand
                items.remove(item);
                System.out.println("You draw your " + item.getDescription());
                break;
            }
        }
        for (Item item: items) {
            if (item.getName().equals("Potion")) {
                player.increaseHealth(30);
                items.remove(item);
                System.out.println("Your health after your potion consumption is "+ player.getHealth());
                break;
            }
        }
        Room current = player.getCurrentPosition();
        if(current.getName().equals("Grave-Yard")) {
            playerDamageToEnemy+=10;
        }

        if (player.getHealth() > 0 && enemy.getHealth() > 0) {
            playerAttack(player,enemy);
        }
        if (enemy.getHealth() > 0 && player.getHealth() > 0) {
            enemyAttack(player,enemy);
        }
    }

    public static void playerAttack(Role player, Role enemy) {
            System.out.println("You attack...... ");
            enemy.decreaseHealth(playerDamageToEnemy);
            if (enemy.getHealth() < 0) enemy.setHealth(0);
            System.out.println("Enemy sustained " + playerDamageToEnemy + " damage. ");
            System.out.println("Enemy health is now: " + enemy.getHealth());
            tauntOnPlayerAttack(player,enemy);
    }

    public static void enemyAttack(Role player, Role enemy) {
            System.out.println("Enemy attacks...... ");
            player.decreaseHealth(enemyDamageToPlayer);
            if (player.getHealth() < 0) player.setHealth(0);
            System.out.printf("You sustained %s damage \n", enemyDamageToPlayer);
            System.out.printf("Your health is now: %s \n", player.getHealth());
            tauntOnEnemyAttack(player,enemy);
    }
    public static void tauntOnPlayerAttack(Role player, Role enemy){
        if(player.getHealth()<enemy.getHealth()){
            CoinGod.pause(1000);
            System.out.println("LOL that tickled is that all you got???...");
            CoinGod.pause(2000);
        }else{
            CoinGod.pause(1000);
            System.out.println("OUCH...");
            CoinGod.pause(1000);
            System.out.println("you shall not defeat me!!");
            CoinGod.pause(2000);
        }
    }
    public static void tauntOnEnemyAttack(Role player, Role enemy){
        if(player.getHealth()<enemy.getHealth()){
            CoinGod.pause(1000);
            System.out.println("How was that DEATH SANDWICH??");
            CoinGod.pause(2000);
        }else{
            CoinGod.pause(1000);
            System.out.println("OUCH...");
            CoinGod.pause(1000);
            System.out.println("You lucky little shrimp");
            CoinGod.pause(2000);
        }
    }
}