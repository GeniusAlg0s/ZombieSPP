package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Entity;
import com.zombiecastlerush.entity.Role;

import java.util.List;
import java.util.Random;

public class Combat extends Challenge {
    static int playerDamageToEnemy = new Random().nextInt(50) + 1;
    static int enemyDamageToPlayer = new Random().nextInt(50) + 1;

    public Combat(String description) {
        super(description);
    }

    public static void combat(Role player, Role enemy) {
        List<Item> items = player.getInventory().getItems();
        for (Item item : items) {
            if (item.getName().equals("Sword")){
                playerDamageToEnemy += 20;
            if(item.getName().equals("Wand")) {
                playerDamageToEnemy +=100;
            }
                System.out.println("You draw your " + item.getDescription());
            }
        }

        if (player.getHealth() > 0 && enemy.getHealth() > 0) {
            playerAttack(player, enemy);
        }
        if (enemy.getHealth() > 0 && player.getHealth() > 0) {
            enemyAttack(player, enemy);
        }
    }

    public static void playerAttack(Role player, Role enemy) {
            System.out.println("You attack...... ");
            enemy.decreaseHealth(playerDamageToEnemy);
            if (enemy.getHealth() < 0) enemy.setHealth(0);
            System.out.println("Enemy sustained " + playerDamageToEnemy + " damage. ");
            System.out.println("Enemy health is now: " + enemy.getHealth());
    }

    public static void enemyAttack(Role player, Role enemy) {
            System.out.println("Enemy attacks...... ");
            player.decreaseHealth(enemyDamageToPlayer);
            if (player.getHealth() < 0) player.setHealth(0);
            System.out.printf("You sustained %s damage \n", enemyDamageToPlayer);
            System.out.printf("Your health is now: %s \n", player.getHealth());
    }
}