//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;

/**
 * Actividad que representa el menu principal de la aplicacion
 * 
 * @author Cinnamon Team
 * @version 1.2 24.11.2011
 */
public class MainMenu extends Activity implements OnClickListener {

	// interfaz
	private Button bArcade, bOpciones, bAventura, bSalir;

	/**
	 * MediaPlayer para contenido multimedia
	 */
	private MediaPlayer menuTheme;
	/**
	 * Usamos para indicar si el sonido se reproduce o no
	 */
	private boolean sonido;

	/**
	 * Jugador actual en la aplicacion
	 */
	private Jugador jugador;

	/**
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	private Launch l;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		jugador = (Jugador) getIntent().getSerializableExtra(Props.Comun.J);
		SharedPreferences getData = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		sonido = getData.getBoolean("cbSonido", true);
		l = new Launch(this);
		inicializar();
	}

	private void onClose() {
		menuTheme.release();
		mDbHelper.close();
	}

	@Override
	public void onClick(View v) {
		Bundle b = new Bundle();
		b.putSerializable(Props.Comun.J, jugador);
		switch (v.getId()) {
		case R.id.bArcade:
			menuTheme.release();
			MediaPlayer openingTheme = MediaPlayer.create(MainMenu.this,
					R.raw.opening);
			if (sonido)
				openingTheme.start();
			l.lanzaActivity(Props.Action.ARCADE, b);
			break;
		case R.id.bAventura:
			l.lanzaActivity(Props.Action.AVENTURA, b);
			break;
		case R.id.bOpciones:
			l.lanzaActivity(Props.Action.INGAME, b);
			// Intent iOpciones = new Intent(Props.Action.OPCIONES);
			// iOpciones.putExtra(Props.Comun.J, jugador);
			// startActivity(iOpciones);
			break;
		case R.id.bSalir:
			finish();
			break;
		}
		onClose();
	}

	/**
	 * Metodo de utilidad para inicializar la Actividad
	 */
	private void inicializar() {
		menuTheme = MediaPlayer.create(MainMenu.this, R.raw.menu);
		if (sonido)
			menuTheme.start();

		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		bArcade = (Button) findViewById(R.id.bArcade);
		bOpciones = (Button) findViewById(R.id.bOpciones);
		bAventura = (Button) findViewById(R.id.bAventura);
		bSalir = (Button) findViewById(R.id.bSalir);

		bArcade.setOnClickListener(this);
		bOpciones.setOnClickListener(this);
		bAventura.setOnClickListener(this);
		bSalir.setOnClickListener(this);
	}

}
