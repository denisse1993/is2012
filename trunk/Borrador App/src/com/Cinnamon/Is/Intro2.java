package com.Cinnamon.Is;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class Intro2 extends Activity implements OnClickListener{
	
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.screen)
			{
			Intent openMainMenu = new Intent("com.Cinnamon.Is.MAINMENU");
			startActivity(openMainMenu);
			}
	}

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro2);
        LinearLayout screen = (LinearLayout) findViewById(R.id.screen);
        screen.setOnClickListener(this);
       
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//introTheme.release(); //poner la musica ya del juego 
		finish();
	}

	

}
