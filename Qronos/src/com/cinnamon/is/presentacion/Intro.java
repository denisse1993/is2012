//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Juego
// ASIGNATURA : Ingeniería del Software
//

package com.cinnamon.is.presentacion;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

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
		introTheme.start();
		Thread timer = new Thread() {
			@Override
			public void run() {
				try {
					sleep(0);// 3650
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Launch.lanzaActivity(Intro.this, Props.Action.MJ6);
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