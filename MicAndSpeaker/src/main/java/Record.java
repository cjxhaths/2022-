import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import javax.sound.sampled.*;
import java.io.*;
@Slf4j
public class Record extends Thread{
    byte[] fileByte;
    String fileName;
    int baosBufIndex = 0;
    TargetDataLine targetDataLine;
    AudioFormat af;
    int cnt;
    public Record() throws LineUnavailableException {
        af = getAudioFormat();
        fileName = "../Record/record.wav";
        targetDataLine = AudioSystem.getTargetDataLine(af);
        targetDataLine.open();
        targetDataLine.start();
        fileByte = new byte[0];
    }
    public Record(int cnt) throws LineUnavailableException {
        af = getAudioFormat();
        fileName = "../Record/cola_nogas/record"+cnt+".wav";
        targetDataLine = AudioSystem.getTargetDataLine(af);
        if(!targetDataLine.isOpen()){
            targetDataLine.open();
        }
        targetDataLine.start();
        fileByte = new byte[0];
        this.cnt = cnt;
    }
    @SneakyThrows
    @Override
    public void run() {
        log.info("record begin");
        File file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        else{
            file.delete();
            file.createNewFile();
        }
        int flag = 0;
        byte[] tmp = new byte[1024];
        long time = System.currentTimeMillis();
        while((flag = targetDataLine.read(tmp,0,tmp.length))> 0 && System.currentTimeMillis() - time <= 4000) {
            byte[] tmp1 = new byte[fileByte.length + tmp.length];
            System.arraycopy(fileByte, 0, tmp1, 0, fileByte.length);
            System.arraycopy(tmp, 0, tmp1, fileByte.length, tmp.length);
            fileByte = tmp1;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(fileByte);
        AudioInputStream ais = new AudioInputStream(bais,af,44100*8/af.getFrameSize());
        AudioSystem.write(ais,AudioFileFormat.Type.WAVE,file);
        targetDataLine.close();
        log.info("record end");
    }
    private AudioFormat getAudioFormat(){
        float sampleRate = 44100f;
        int sampleSizeInBits = 16;
        int channel = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate,sampleSizeInBits,channel,signed,bigEndian);
    }
}
