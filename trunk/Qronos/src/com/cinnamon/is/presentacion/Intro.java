//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Juego
// ASIGNATURA : Ingeniería del Software
//

package com.cinnamon.is.presentacion;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;

/**
 * Actividad de intro en la aplicacion
 * 
 * @author Cinnamon Team
 * @version 1.0 24.11.2011
 */
public class Intro extends Activity {

	/**
	 * MediaPlayer para contenido multimedia
	 */
	private MediaPlayer introTheme;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);

		introTheme = MediaPlayer.create(Intro.this, R.raw.hearthbeat_sound);
		SharedPreferences getData = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if (getData.getBoolean(Props.Comun.CB_SONIDO, true)) {
			introTheme.start();
		}
		Thread timer = new Thread() {
			@Override
			public void run() {
				try {
					sleep(0);// 3650
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					finish();
					Launch.lanzaActivity(Intro.this, Props.Action.INTRO2);
				}
			}
		};
		timer.start();
		// Dialogos dialog = new Dialogos (this, "Hola", false,
		// R.style.CenterDialog);
		// dialog.show();
	}

	@Override
	protected void onPause() {
		super.onPause();
		introTheme.release();
		finish();
	}

}