package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Role;
import com.zombiecastlerush.util.UserInterface;

import java.util.List;
import java.util.Random;

public class Combat extends Challenge {
    static int playerDamageToEnemy = new Random().nextInt(50) + 1;
    static int enemyDamageToPlayer = new Random().nextInt(30) + 1;

    public Combat(String description) {
        super(description);
    }

    public static String combat(Role player, Role enemy) {
        StringBuilder combatText = new StringBuilder();
        UserInterface ui = UserInterface.getInstance();

        List<Item> items = player.getInventory().getItems();
        playerDamageToEnemy = new Random().nextInt(50) + 1;
        enemyDamageToPlayer = new Random().nextInt(50) + 1;
        for (Item item : items) {
            if (item.getName().equals("Wand")){
                playerDamageToEnemy += 30; //replaces sword with wand
                items.remove(item);
                combatText.append("You draw your " + item.getDescription() + "\n");
                break;
            }
        }
        for (Item item: items) {
            if (item.getName().equals("Potion")) {
                player.increaseHealth(30);
                items.remove(item);
                combatText.append("Your health after your potion consumption is "+ player.getHealth() + "\n");
                break;
            }
        }
        Room current = player.getCurrentPosition();
        if(current.getName().equals("Grave-Yard")) {
            playerDamageToEnemy += 30;
        }

        if (player.getHealth() > 0 && enemy.getHealth() > 0) {
            combatText.append(playerAttack(player,enemy));
        }
        if (enemy.getHealth() > 0 && player.getHealth() > 0) {
            combatText.append(enemyAttack(player,enemy));
        }
        return combatText.toString();
    }

    public static String playerAttack(Role player, Role enemy) {
        StringBuilder playerAttack = new StringBuilder();
            playerAttack.append("You attack......\n");
            enemy.decreaseHealth(playerDamageToEnemy);
            if (enemy.getHealth() < 0) enemy.setHealth(0);
            playerAttack.append("Enemy sustained " + playerDamageToEnemy + " damage.\n");
            playerAttack.append("Enemy health is now: " + enemy.getHealth() + "\n");
            tauntOnPlayerAttack(player,enemy);
            return playerAttack.toString();
    }

    public static String enemyAttack(Role player, Role enemy) {
        StringBuilder enemyAttack = new StringBuilder();
            enemyAttack.append("Enemy attacks......\n");
            player.decreaseHealth(enemyDamageToPlayer);
            if (player.getHealth() < 0) player.setHealth(0);
            enemyAttack.append(String.format("You sustained %s damage \n", enemyDamageToPlayer));
            enemyAttack.append(String.format("Your health is now: %s \n", player.getHealth()));
            tauntOnEnemyAttack(player,enemy);
            return enemyAttack.toString();
    }
    public static void tauntOnPlayerAttack(Role player, Role enemy){
        UserInterface ui = UserInterface.getInstance();
        if(player.getHealth()<enemy.getHealth()){
            CoinGod.pause(1000);
            ui.updateEventText("LOL that tickled is that all you got???...");
            CoinGod.pause(2000);
        }else{
            CoinGod.pause(1000);
            ui.updateEventText("OUCH...");
            CoinGod.pause(1000);
            ui.updateEventText("you shall not defeat me!!");
            CoinGod.pause(2000);
        }
    }
    public static void tauntOnEnemyAttack(Role player, Role enemy){
        UserInterface ui = UserInterface.getInstance();
        if(player.getHealth()<enemy.getHealth()){
            CoinGod.pause(1000);
            ui.updateEventText("How was that DEATH SANDWICH??");
            CoinGod.pause(2000);
        }else{
            CoinGod.pause(1000);
            ui.updateEventText("OUCH...");
            CoinGod.pause(1000);
            ui.updateEventText("You lucky little shrimp");
            CoinGod.pause(2000);
        }
    }
}