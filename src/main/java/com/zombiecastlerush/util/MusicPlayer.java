package com.zombiecastlerush.util;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;

class MusicPlayer extends JFrame {
    Clip clip;

    public MusicPlayer(String path) {
        try {
            File soundFile = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void soundLoop() {
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

}