/*
This class is managing all audio operations in Game, like eating, dying & clicking the button.
 */

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
    Clip audio;
    Audio(String path){         //Path of audio file
        File file =  new File(path);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            audio = AudioSystem.getClip();
            audio.open(audioInputStream);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }
}
