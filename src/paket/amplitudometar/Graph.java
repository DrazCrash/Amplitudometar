package paket.amplitudometar;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;

import com.jjoe64.graphview.LineGraphView;



public class Graph extends Activity {		


	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph1);
	
		

		
	
		File file = new File(Environment.getExternalStorageDirectory(), "test.pcm");
        int shortSizeInBytes = Short.SIZE/Byte.SIZE;
  
  int bufferSizeInBytes = (int)(file.length()/shortSizeInBytes);
  short[] audioData = new short[bufferSizeInBytes];
 // short[] audioData1 = new short[bufferSizeInBytes];

	
	
	
  
  try {
   InputStream inputStream = new FileInputStream(file);
   BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
   DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
   
   int i = 0;
   GraphViewData[] data = new GraphViewData[(int) audioData.length];	
  
   while(dataInputStream.available() > 0){
    audioData[i] = dataInputStream.readShort();
    data[i] = new GraphViewData(i, audioData[i]);
    i++;
    
   }
  
  dataInputStream.close();
  


   GraphView graphView = new LineGraphView(this, "Amplitudometar");
   graphView.addSeries(new GraphViewSeries(data));
		// set view port, start=2, size=40
	//    graphView.setViewPort(2, 10000);
		graphView.setScrollable(true);
		graphView.setScalable(true);
		((LineGraphView) graphView).setDrawBackground(true);
		// optional - activate scaling / zooming

		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);
	
  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.Funkcije:
	        			Intent Main = new Intent(Graph.this, FunctionsMenu.class);
	        			startActivity(Main);
	    
	                            break;
	        case R.id.Nazad: 
	        			Intent Main2 = new Intent(Graph.this, MainActivity.class);
	        			startActivity(Main2);
	                   break;
	        case R.id.Sviraj: 
    			MainActivity activity = new MainActivity();
    			activity.playRecord();
               break;
	    }
	    return true;
	}
	



}