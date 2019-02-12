package main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

class MusicPlayer {

    AudioInputStream audioInputStream;
    AudioFormat format;
    DataLine.Info info;
    Clip audioClip;

    MusicPlayer(String path) throws Exception{

        audioInputStream = AudioSystem.getAudioInputStream(new File(path));
        format = audioInputStream.getFormat();
        info = new DataLine.Info(Clip.class, format);
        audioClip = (Clip) AudioSystem.getLine(info);
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
        }
    }
}