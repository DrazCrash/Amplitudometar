package paket.amplitudometar;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class FunctionsMenu extends Activity {
public static int k=0;
public static int l=0;
public static int r=0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewmenu);
        SeekBar seekBar2 = (SeekBar)findViewById(R.id.seekBar2);
		SeekBar seekBar3 = (SeekBar)findViewById(R.id.CustomGainControl);
		SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar1);
		((Button) findViewById(R.id.Obradi)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startGraphActivity(Functions.class);
			}
		});

////////////////////////////////////////////////////////////////////////////////////seekbar1	
		final TextView seekBarValue = (TextView)findViewById(R.id.Min);
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

    	
    
    	

public void onProgressChanged(SeekBar seekBar, int progress,
  boolean fromUser) {
 // TODO Auto-generated method stub
 seekBarValue.setText(String.valueOf(progress));
l=progress;
}


public void onStartTrackingTouch(SeekBar seekBar) {
 // TODO Auto-generated method stub
}


public void onStopTrackingTouch(SeekBar seekBar) {
 // TODO Auto-generated method stub
}
    });
///////////////////////////////////////////////////////////////////////////////////////seekbar2
	
    final TextView seekBarValue1 = (TextView)findViewById(R.id.Max);
	    seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
       
	    	
	
	    	
	
	public void onProgressChanged(SeekBar seekBar, int progress,
	  boolean fromUser) {
	 // TODO Auto-generated method stub
	 seekBarValue1.setText(String.valueOf(progress));
	k=progress;
	
	}


	
	public void onStartTrackingTouch(SeekBar seekBar) {
	 // TODO Auto-generated method stub
	}

	
	public void onStopTrackingTouch(SeekBar seekBar) {
	 // TODO Auto-generated method stub
	}
	    });
	    ////////////////////////////////////////////////////////////////////////Seekbar3
    final TextView seekBarValue3 = (TextView)findViewById(R.id.GainValue);
    seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
   
    	

    	
public void onProgressChanged(SeekBar seekBar, int progress,
  boolean fromUser) {
 // TODO Auto-generated method stub
 seekBarValue3.setText(String.valueOf(progress));
r=progress;

}

public void onStartTrackingTouch(SeekBar seekBar) {
 // TODO Auto-generated method stub
}

public void onStopTrackingTouch(SeekBar seekBar) {
 // TODO Auto-generated method stub
}
    });
}
	
	
	
	
	
public int Min(){
	
		return l;
	}
public int Max(){
	return k;

}
public int Gain(){
	return r;

}

	
	private void startGraphActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(FunctionsMenu.this, activity);
		
		if (((RadioButton) findViewById(R.id.Gain)).isChecked()) {
	
			intent.putExtra("Expander", "Gain");
			}
		else if (((RadioButton) findViewById(R.id.CustomGain)).isChecked()) {
			
			intent.putExtra("Expander", "CustomGain");
			}
			
		 else {
			intent.putExtra("Expander", "Limiter");
		}
		startActivity(intent);
		}



	
}
