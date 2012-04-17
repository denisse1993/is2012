//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.presentacion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;

/**
 * Actividad de la segunda intro en la aplicacion
 * 
 * @author Cinnamon Team
 * @version 1.0 24.11.2011
 */
public class Intro2 extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro2);
		inicializar();
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.screen) {
			Launch.lanzaActivity(this, Props.Action.LOGIN);
			finish();
		}
	}

	/**
	 * Metodo de utilidad para inicializar la actividad
	 */
	private void inicializar() {
		LinearLayout screen = (LinearLayout) findViewById(R.id.screen);
		screen.setOnClickListener(this);
		// screen.setBackgroundResource(R.drawable.);
		// AnimationDrawable a = (AnimationDrawable) screen.getBackground();
		// a.start();
	}

}
