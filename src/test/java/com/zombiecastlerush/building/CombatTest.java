package com.zombiecastlerush.building;

import com.zombiecastlerush.entity.Player;
import com.zombiecastlerush.entity.Role;
import com.zombiecastlerush.entity.Enemy;

import com.zombiecastlerush.util.UserInterface;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CombatTest {
    Role player = new Player("Player");
    Role enemy = new Enemy("Enemy");

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void combat() {
    }

    @Test
    public void playerFight_enemyAttacked_healthReducedToZero() {
        player.setHealth(100);
        enemy.setHealth(1);
        Combat.playerAttack(player, enemy);
        System.out.println(enemy.getHealth());
        assertEquals(0, enemy.getHealth());
    }

    @Test
    public void enemyFight() {
        player.setHealth(1);
        enemy.setHealth(100);
        Combat.enemyAttack(player,enemy);
        assertEquals(0, player.getHealth());
    }
}