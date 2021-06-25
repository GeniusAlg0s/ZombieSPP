package com.zombiecastlerush.util;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;

public class MusicPlayer extends JFrame {
    private static MusicPlayer Mplayer;
    Clip clip;
    float level =0;
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
            MusicPlayer.Mplayer= new MusicPlayer("resources/longSound.wav");
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
    /*
     *these will allow the user to start and stop back ground music
     * to used in gui not the console version as it will
     * be attached to JButtons
     */
    public void lowerVolume(){
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
       level -= 5.0f;
       gainControl.setValue(level);
    }
    public void raiseVolume(){
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        level += 5.0f;
        gainControl.setValue(level);
    }
}