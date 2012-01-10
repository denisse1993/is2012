//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//

/**
 * Paquete para clases con caracter visual y de presentacion
 */
package com.cinnamon.is.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Intents;

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		introTheme = MediaPlayer.create(Intro.this, R.raw.hearthbeat_sound);
		introTheme.start();
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(3650);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openIntro2 = new Intent(Intents.Action.INTRO2);
					startActivity(openIntro2);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		introTheme.release();
		finish();
	}

}