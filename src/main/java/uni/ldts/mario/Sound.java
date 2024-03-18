package uni.ldts.mario;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private final Clip clip;
    public static final String SOUNDS_PATH; // <- path to sounds folder

    static {
        String s = File.separator;
        SOUNDS_PATH = "src"+s+"main"+s+"resources"+s+"sounds"+s;
    }

    public Sound(String sound) {
        AudioInputStream stream;
        try {
            stream = AudioSystem.getAudioInputStream(new File(SOUNDS_PATH+sound));
            clip = AudioSystem.getClip();
            clip.open(stream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stopAndClose() {
        if (clip != null) {
            if (clip.isRunning()) clip.stop();
            clip.close();
        }
    }
}
