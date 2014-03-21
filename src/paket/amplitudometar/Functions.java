package paket.amplitudometar;

import android.app.Activity;
import android.content.Intent;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;



import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.LinearLayout;

public class Functions extends Activity {
	
	
	Button Limiter,Compressor; 
	File file = new File(Environment.getExternalStorageDirectory(), "test.pcm");
	int shortSizeInBytes = Short.SIZE/Byte.SIZE;
	  int bufferSizeInBytes = (int)(file.length()/shortSizeInBytes);
	  
	short[] audioData1 = new short[bufferSizeInBytes];

		

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph2);
	FunctionsMenu izbo = new FunctionsMenu();
	
	short min = (short)izbo.Min();
	short max = (short)izbo.Max();
	int check;
	short gain = (short)izbo.Gain();

		
			  try {
			   InputStream inputStream = new FileInputStream(file);
			   BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			   DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
			  GraphViewData[] data = new GraphViewData[(int) audioData1.length];
			   int i = 0;
			   
			   while(dataInputStream.available() > 0){
				   
					   audioData1[i] = dataInputStream.readShort();
				
					i++;
						  }   dataInputStream.close();   
        	
			   int v=0;
			 		
			   
			   if (getIntent().getStringExtra("Expander").equals("Limiter")) {  
			   
			   while(v<audioData1.length){
					   
					if(audioData1[v]>max){
						  audioData1[v]=(short) max;
							 data[v] = new GraphViewData(v, audioData1[v]);}
							 else if(audioData1[v]<-max){
								 audioData1[v]=(short) -max;
								 data[v] = new GraphViewData(v, audioData1[v]);		 
							 }
						  else
							  audioData1[v]=audioData1[v];
					 data[v] = new GraphViewData(v, audioData1[v]);
					v++;
			   }}	
			   else if (getIntent().getStringExtra("Expander").equals("CustomGain")){
				
				   while(v<audioData1.length){
					   check= audioData1[v]*gain;
					   if((audioData1[v]>min && audioData1[v]<max && check<32727) || (audioData1[v]<-min && audioData1[v]>-max && check>-32727)){
					       audioData1[v] = (short) check;
					}
					   else if(check>32272) {
						   audioData1[v]=32272;
					   }
					   else {
						   audioData1[v]=audioData1[v];
					   }
                       data[v] = new GraphViewData(v, audioData1[v]);
                       v++;
					}	
 
			   }
			      
			   else {
				 while(v<audioData1.length){
					 check=audioData1[v]*gain;
					 if(check>32272){
						 audioData1[v]=32272;
					 }
					 else {
						 audioData1[v]*=gain;
					 }
					 data[v] = new GraphViewData(v, audioData1[v]);
					 v++;
				 
				 }
				 
				   
			   }

			  GraphView graphView1 = new LineGraphView(this, "Amplitudometar");
			   graphView1.addSeries(new GraphViewSeries(data));
					// set view port, start=2, size=40
			//	    graphView1.setViewPort(2, 10000);
					graphView1.setScrollable(true);
					graphView1.setScalable(true);
					((LineGraphView) graphView1).setDrawBackground(true);
					LinearLayout layout = (LinearLayout) findViewById(R.id.graph3);
					layout.addView(graphView1);
			  
			  
		
			   
			  } catch (FileNotFoundException e) {
			   e.printStackTrace();
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		}
	
	
		
	
	
	
	
	
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu1, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.Sviraj:playRecord();
	        			
	    
	                            break;
	        case R.id.Nazad: 
	        	Intent Main = new Intent(Functions.this, FunctionsMenu.class);
    			startActivity(Main);
	                   break;
	        case R.id.beginning: 
	        	Intent graf = new Intent(Functions.this,Graph.class);
    			startActivity(graf);
	                   break;
	    
	    }
	    return true;
	}
	
	 void playRecord(){
		AudioTrack audioTrack = new AudioTrack(
		     AudioManager.STREAM_MUSIC,
		     11025,
		     AudioFormat.CHANNEL_CONFIGURATION_MONO,
		     AudioFormat.ENCODING_PCM_16BIT,
		     bufferSizeInBytes,
		     AudioTrack.MODE_STREAM);
		   
		   audioTrack.play();
		   audioTrack.write(audioData1, 0, bufferSizeInBytes);
		 }

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

