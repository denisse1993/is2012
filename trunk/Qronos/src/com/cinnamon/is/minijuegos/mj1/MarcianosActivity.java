package com.cinnamon.is.minijuegos.mj1;

import android.app.Activity;
import android.os.Bundle;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;

public class MarcianosActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mj1);
		// introTheme = MediaPlayer.create(Intro.this, R.raw.hearthbeat_sound);
		// introTheme.start();
		Thread timer = new Thread() {
			public void run() {

				try {
					sleep(6650);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// lanza actividad propia del minijuego
					// Intent i = new
					// Intent("com.cinnamon.is.minijuegos.MJ1.Start");
					// a.startActivityForResult(i, CODIGO_RETORNO);
					Launch.lanzaActivity(MarcianosActivity.this,
							Props.Action.MJ1st);
					finish();
				}
			}
		};
		timer.start();
	}
}