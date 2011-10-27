package com.Cinnamon.Is;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


public class Intro extends Activity {
	
	private MediaPlayer introTheme;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        introTheme =  MediaPlayer.create(Intro.this, R.raw.hearthbeat_sound);
        introTheme.start();
        Thread timer = new Thread(){
			public void run() {
				// TODO Auto-generated method stub
				try{
					sleep(3650);
					
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				finally {
					
					
					Intent openIntro2 = new Intent("com.Cinnamon.Is.Intro2");
					startActivity(openIntro2);
				}
			}        	
        };
        timer.start();        
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		introTheme.release();
		finish();
	}
    
    
}