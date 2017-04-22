package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class audioPlayer implements Runnable{
	private String file;
	private int play = 0;
	private Thread thread = new Thread(this);
	private final int BUFFER = 65536; 
	
	public audioPlayer(String file){
		this.file = file;
	}
	
	public void run(){
		while(play > 0){
		File audio = new File(file);
		if(!audio.exists()){
			return;
		}
		AudioInputStream inputStream = null;
		try{
			inputStream = AudioSystem.getAudioInputStream(audio);
			
		 } catch (UnsupportedAudioFileException e1) {
             e1.printStackTrace();
             return;
         } catch (IOException e1) {
             e1.printStackTrace();
             return;
         }
		AudioFormat format = inputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		try{
			auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		
		auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER];
        try {
        	while (nBytesRead != -1 && play > 0) {
        		nBytesRead = inputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0 && play > 0)
                	auline.write(abData, 0, nBytesRead);
        	}	
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
            play--;
        }
		}
	}

	
	public synchronized boolean isAlive(){
		return thread.isAlive();
	}
	public synchronized void stop(){
		play = 0;
		try{
			thread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	public synchronized void start(int p){
		play = p;
		thread = new Thread(this);
		thread.start();
	}
	
}
