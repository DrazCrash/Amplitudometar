package paket.amplitudometar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;





import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi", "NewApi", "NewApi", "NewApi" })
public class MainActivity extends Activity {
 
 Button start, stop, play, show;
 
 
 Boolean recording;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        start = (Button)findViewById(R.id.record);
        stop = (Button)findViewById(R.id.stop);
        play = (Button)findViewById(R.id.play);
        show = (Button)findViewById(R.id.show);
        
        start.setOnClickListener(startRecOnClickListener);
        stop.setOnClickListener(stopRecOnClickListener);
        play.setOnClickListener(playBackOnClickListener);
        show.setOnClickListener(showGraph);

    }
 
    
    OnClickListener showGraph
    = new OnClickListener(){

	
		public void onClick(View v) {
 
			// Intent intent = new Intent( v.getContext(), Graf.class);
			 //startActivity(intent);    
			 Intent Main = new Intent(MainActivity.this, Graph.class);
			 startActivity(Main);
			 
			 
			
		}
    	
    	
    };

 
    OnClickListener startRecOnClickListener
    = new OnClickListener(){

  
  public void onClick(View arg0) {
   
   Thread recordThread = new Thread(new Runnable(){

  
    public void run() {
     recording = true;
     startRecord();
    }
    
   });

   recordThread.start();

  }};
  
 OnClickListener stopRecOnClickListener
 = new OnClickListener(){
  
  
  public void onClick(View arg0) {
   recording = false;
  }};
  
 OnClickListener playBackOnClickListener
     = new OnClickListener(){

  
   public void onClick(View v) {
    playRecord();
   }
  
 };
  
 private void startRecord(){

  File file = new File(Environment.getExternalStorageDirectory(), "test.pcm"); 
    
  try {
   file.createNewFile();
   
   OutputStream outputStream = new FileOutputStream(file);
   BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
   DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
   
   int minBufferSize = AudioRecord.getMinBufferSize(11025, 
     AudioFormat.CHANNEL_CONFIGURATION_MONO, 
     AudioFormat.ENCODING_PCM_16BIT);
   
   short[] audioData = new short[minBufferSize];
   
   AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
     11025,
     AudioFormat.CHANNEL_CONFIGURATION_MONO,
     AudioFormat.ENCODING_PCM_16BIT,
     minBufferSize);
   
   audioRecord.startRecording();
   
   while(recording){
    int numberOfShort = audioRecord.read(audioData, 0, minBufferSize);
    for(int i = 0; i < numberOfShort; i++){
     dataOutputStream.writeShort(audioData[i]);
    }
   }
   
   audioRecord.stop();
   dataOutputStream.close();
   
  } catch (IOException e) {
   e.printStackTrace();
  }

 }

public void playRecord(){
  
  File file = new File(Environment.getExternalStorageDirectory(), "test.pcm");
  
        int shortSizeInBytes = Short.SIZE/Byte.SIZE;
  
  int bufferSizeInBytes = (int)(file.length()/shortSizeInBytes);
  short[] audioData = new short[bufferSizeInBytes];
  
  try {
   InputStream inputStream = new FileInputStream(file);
   BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
   DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
   
   int i = 0;
   while(dataInputStream.available() > 0){
    audioData[i] = dataInputStream.readShort();
    i++;
   }
   
   dataInputStream.close();
   
   AudioTrack audioTrack = new AudioTrack(
     AudioManager.STREAM_MUSIC,
     11025,
     AudioFormat.CHANNEL_CONFIGURATION_MONO,
     AudioFormat.ENCODING_PCM_16BIT,
     bufferSizeInBytes,
     AudioTrack.MODE_STREAM);
   
   audioTrack.play();
   audioTrack.write(audioData, 0, bufferSizeInBytes);

   
  } catch (FileNotFoundException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  }
 }
 
}