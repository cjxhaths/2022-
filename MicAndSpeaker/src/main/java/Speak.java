import lombok.extern.slf4j.Slf4j;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
@Slf4j
public class Speak extends Thread{
    private String wavPath;
    public Speak(String path){
        this.wavPath = path;
    }
    public void run(){
        File file = new File(wavPath);
        try {
            log.info("speak begin");
            FileInputStream fileInputStream = new FileInputStream(file);
            AudioStream audioStream = new AudioStream(fileInputStream);
            AudioPlayer.player.start(audioStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("speak end");
    }
}
