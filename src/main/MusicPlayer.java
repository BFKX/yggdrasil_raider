package main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

class MusicPlayer {

    AudioInputStream audioInputStream;
    AudioFormat format;
    DataLine.Info info;
    Clip audioClip;

    MusicPlayer(String path) {

        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(path));
        } catch(Exception e) {

            e.printStackTrace();
            System.exit(1);
        }

        format = audioInputStream.getFormat();
        info = new DataLine.Info(Clip.class, format);

        try {

            audioClip = (Clip) AudioSystem.getLine(info);
        } catch(LineUnavailableException e) {

            e.printStackTrace();
            System.exit(1);
        }

    }

    void start() throws Exception {

        audioClip.open(audioInputStream);
        audioClip.start();
    }

    void stop(){

        try {

            audioClip.close();
            audioInputStream.close();
        } catch(IOException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}