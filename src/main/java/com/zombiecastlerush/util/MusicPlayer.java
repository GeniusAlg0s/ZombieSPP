package com.zombiecastlerush.util;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;

public class MusicPlayer extends JFrame {
    private static MusicPlayer Mplayer;
    Clip clip;

    private MusicPlayer(String path) {
        try {
            File soundFile = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MusicPlayer getInstance(){
        if(MusicPlayer.Mplayer== null){
            MusicPlayer.Mplayer= new MusicPlayer("src/main/resources/sound/longSound.wav");
        }
        return MusicPlayer.Mplayer;
    }

    /*
     *these will allow the user to start and stop back ground music
     * to used in gui not the console version as it will
     * be attached to JButtons
     */
    public void soundLoop() {
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }

}