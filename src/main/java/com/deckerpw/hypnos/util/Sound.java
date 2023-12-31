package com.deckerpw.hypnos.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    private static float volume = 0.1f;
    private final URL resource;

    public Sound(URL resource) {
        this.resource = resource;
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float getVolume() {
        return volume;
    }

    public static void setVolume(float volume) {
        Sound.volume = volume;
    }

    public void playSound() {
        if (volume > 0) {
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(resource);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                //set Volume
                FloatControl clipVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                clipVolume.setValue(getVolume());

                clip.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }
}
