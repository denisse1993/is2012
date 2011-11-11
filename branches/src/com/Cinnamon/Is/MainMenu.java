package com.Cinnamon.Is;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu  extends Activity {
	    /** Called when the activity is first created. */
	    int contador;
		Button s,r;
		TextView display;
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //setContentView(R.layout.main);
	        setContentView(new GameView(this));
	        
	    }
}
