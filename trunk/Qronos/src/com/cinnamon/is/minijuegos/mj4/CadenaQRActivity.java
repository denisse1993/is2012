package com.cinnamon.is.minijuegos.mj4;


import com.cinnamon.is.R;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;


public class CadenaQRActivity extends Activity {
    private Button Comenzar;
    private long start;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.instcadqr);
        Comenzar=(Button) findViewById(R.id.Comenzar);
        Comenzar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try{
					//iniciamos el tiempo y se lo pasamos al otro intent
					 startTime();
					 Intent openGame=new Intent("com.cinnamon.is.minijuegos.MJ4.Game");
					 openGame.putExtra("inicio", start);
					 startActivity(openGame);
					}catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
			}
			});
    }	
	protected void onPause() {
		super.onPause();
		finish();
	}
	
	 protected void startTime() {
			start = System.nanoTime();
		}

}