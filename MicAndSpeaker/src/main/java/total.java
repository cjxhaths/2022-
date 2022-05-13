import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class total {
    public static void main(String[] args) throws LineUnavailableException, IOException, InterruptedException {
        String path = "D:\\2022声音测液体\\2022-\\Record\\chirp_44100_44100_0_16_4s.wav";
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 1;i<=10;i++){
            executorService.execute(new Speak(path));
            executorService.execute(new Record(i));
            Thread.sleep(5000);
        }
        executorService.shutdown();
    }
}
