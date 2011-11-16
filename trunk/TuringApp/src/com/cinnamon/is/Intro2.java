package com.cinnamon.is;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class Intro2 extends Activity implements OnClickListener {

	public void onClick(View arg0) {
		if (arg0.getId() == R.id.screen) {
			Intent openMainMenu = new Intent(Intents.Action.MAINMENU);
			startActivity(openMainMenu);
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro2);
		LinearLayout screen = (LinearLayout) findViewById(R.id.screen);
		screen.setOnClickListener(this);
		screen.setBackgroundResource(R.drawable.animacion_intro2);
		AnimationDrawable a = (AnimationDrawable) screen.getBackground();
		a.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// introTheme.release(); //poner la musica ya del juego
		finish();
	}

}
