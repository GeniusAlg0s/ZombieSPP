package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;

import javax.swing.*;
import java.awt.*;

/**
 * singleton class Game
 * it provides access to a Map and a role Controller
 */
public class Game {
    private static Game game;
    static Castle castle = new Castle();
    static Player player;
    static MusicPlayer maestro = MusicPlayer.getInstance();

    private Game() {
    }

    public static Game getInstance() {
        if (Game.game == null) {
            Game.game = new Game();
        }
        return Game.game;
    }
    public void start() throws JsonProcessingException {
        UserInterface ui = UserInterface.getInstance();
        ui.startUI();
        maestro.soundLoop();
    }

    public void stop() {
        String tyForPlaying = "Thank you for playing Zombie Castle Rush!";
        System.out.println(tyForPlaying);
        Frame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, tyForPlaying);
        System.exit(0);
    }
}