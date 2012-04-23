package com.cinnamon.is.minijuegos.mj3;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.comun.Props;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ZigZagQRActivity extends Minijuego {
    private Button Comenzar;
	private long start;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.iniciozigzagqr);
        final Launch lanzador=new Launch(this);
        Comenzar=(Button) findViewById(R.id.Comenzar);
        Comenzar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try{
					//iniciamos el tiempo y se lo pasamos al otro intent
					 startTime();
					 Bundle b=new Bundle();
					 b.putLong("inicio", start);
				//	 lanzador.lanzaActivity(Props.Action.MJ3G, b, Props.Comun.cmj3);
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
	 
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == RESULT_OK) {
				Bundle b = data.getExtras();
				if (requestCode==Props.Comun.cmj3) {
					Bundle bundle=new Bundle();
					bundle.getInt(Props.Comun.SCORE);
					Launch.returnActivity(this, b, RESULT_OK);
				}
			}
		}
}