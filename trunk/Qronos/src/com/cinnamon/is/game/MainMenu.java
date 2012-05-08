//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import com.cinnamon.is.comun.Props.Enum.Tabla;

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
	 * Adaptador para conectar con la BD
	 */
	private DbAdapter mDbHelper;

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
	 * Lanzador auxiliar
	 */
	private Launch l;

	SharedPreferences getData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		getData = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		sonido = getData.getBoolean(Props.Comun.CB_SONIDO, true);
		l = new Launch(this);
		inicializar();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mDbHelper.close();
		if (sonido)
			onClose();
	}

	@Override
	protected void onResume() {
		super.onResume();
		sonido = getData.getBoolean(Props.Comun.CB_SONIDO, true);
		if (sonido) {
			menuTheme = MediaPlayer.create(MainMenu.this, R.raw.menu);
			menuTheme.start();
		}

		if (!mDbHelper.isOpen())
			mDbHelper.open(false);
	}

	/**
	 * A ejecutar al cerrarse
	 */
	private void onClose() {
		menuTheme.release();
	}

	@Override
	public void onClick(View v) {
		if (sonido)
			onClose();
		Bundle b = new Bundle();
		b.putSerializable(Props.Comun.JUGADOR, jugador);
		switch (v.getId()) {
		case R.id.bArcade:
			if (sonido) {
				MediaPlayer openingTheme = MediaPlayer.create(MainMenu.this,
						R.raw.opening);
				openingTheme.start();
			}
			if (creaJugadorLocalArcade())
				getJugadorLocalArcade();
			Props.Comun.ACTIVIDAD = MainMenu.this;
			l.lanzaActivity(Props.Action.ARCADE, b);
			// openingTheme.release();
			break;
		case R.id.bAventura:
			l.lanzaActivity(Props.Action.ELIGEMODOAVENTURA, b);
			// b.putSerializable(Props.Comun.AVENTURA, new Aventura("aventura",
			// "aventura"));
			// l.lanzaActivity(Props.Action.SELECMJ, b);
			break;
		case R.id.bOpciones:
			b.putString(Props.Comun.RETORNO, Props.Action.MAINMENU);
			l.lanzaActivity(Props.Action.OPCIONES, b);
			// l.lanzaActivity(Props.Action.INGAME, b);
			break;
		case R.id.bSalir:
			finish();
			break;
		}
	}

	/**
	 * Metodo de utilidad para inicializar la Actividad
	 */
	private void inicializar() {
		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		// menuTheme = MediaPlayer.create(MainMenu.this, R.raw.menu);
		// if (sonido)
		// menuTheme.start();

		bArcade = (Button) findViewById(R.id.bArcade);
		bOpciones = (Button) findViewById(R.id.bOpciones);
		bAventura = (Button) findViewById(R.id.bAventura);
		bSalir = (Button) findViewById(R.id.bSalir);

		bArcade.setOnClickListener(this);
		bOpciones.setOnClickListener(this);
		if (Props.Comun.ONLINE)
			bAventura.setOnClickListener(this);
		else {
			bAventura.setOnClickListener(null);
			// cambiar color,ocultar,etc
		}
		bSalir.setOnClickListener(this);
	}

	/**
	 * Realiza la obtención de campos de parcade en la BD local
	 * 
	 * @return si esta o no el jugador en la BD local
	 */
	private boolean getJugadorLocalArcade() {
		// leer de la BD si existe nombre
		boolean esta = false;
		// existe el registro
		if (mDbHelper.existsRow(jugador.getNombre(), Tabla.parcade)) {
			// recupera info
			Cursor mCursor = mDbHelper.fetchRow(jugador.getNombre(),
					Tabla.parcade);
			startManagingCursor(mCursor);
			int[] arcade = new int[] {
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE1),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE2),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE3),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE4),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE5),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE6),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE7),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE8),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE9),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE10),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE11),
					mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE12) };
			stopManagingCursor(mCursor);
			jugador.setScore(arcade);
			mCursor.close();
			esta = true;
		}
		return esta;
	}

	/**
	 * Crea el jugador si no existe en la tabla arcade de la BD local
	 * 
	 * @return true o false en funcion de si existia o no
	 */
	private boolean creaJugadorLocalArcade() {
		// leer de la BD si existe nombre
		boolean esta = true;
		if (!mDbHelper.existsRow(jugador.getNombre(), Tabla.parcade)) {
			// crear nuevo jugador
			mDbHelper.createRowParcade(jugador.getNombre(), new int[] { 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
			esta = false;
		}
		return esta;
	}

}
