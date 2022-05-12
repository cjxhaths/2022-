import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class total {
    public static void main(String[] args) throws LineUnavailableException, IOException {
        String path = "D:\\声音测液体\\chirp_44100_44100_0_16_4s.wav";
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Speak(path));
        executorService.execute(new Record());
        executorService.shutdown();
    }
}
