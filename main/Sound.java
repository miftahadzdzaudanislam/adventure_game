package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip; // var menyimpan sound
    URL soundURL[] = new URL[30]; // var path sound

    // fungsi untuk mengambil sound
    public Sound() {
        soundURL[0] = getClass().getResource("/res/sound/bgm.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/res/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/res/sound/fanfare.wav");
    }

    // fungsi untuk memuat sound wav
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // fungsi untuk memulai sound
    public void play() {
        clip.start();
    }

    // fungsi untuk mengulang sound
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // fungsi untuk menghentikan sound
    public void stop() {
        clip.stop();
    }
}
