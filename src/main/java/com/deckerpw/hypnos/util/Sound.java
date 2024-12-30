package com.deckerpw.hypnos.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    public final SoundGroup group;
    private final URL resource;

    public Sound(URL resource, SoundGroup group) {
        this.resource = resource;
        this.group = group;
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound(int volume) {
        try {
            if (group.getVolume() > 0 || volume > 0) {
                try {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(resource);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    //set Volume
                    FloatControl clipVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float setVolume = (group.getVolume() / 100f) * (volume / 100f);
                    clipVolume.setValue(20f * (float) Math.log10(setVolume));
                    clip.loop(0);
                    clip.start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void playSound() {
        playSound(100);
    }

    public enum SoundGroup {
        MUSIC,
        SFX;

        int volume;

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

    }
}
