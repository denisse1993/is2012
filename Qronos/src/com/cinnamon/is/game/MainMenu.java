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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		SharedPreferences getData = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		sonido = getData.getBoolean("cbSonido", true);
		l = new Launch(this);
		inicializar();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mDbHelper.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
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
		Bundle b = new Bundle();
		b.putSerializable(Props.Comun.JUGADOR, jugador);
		switch (v.getId()) {
		case R.id.bArcade:
			menuTheme.release();
			MediaPlayer openingTheme = MediaPlayer.create(MainMenu.this,
					R.raw.opening);
			if (sonido)
				openingTheme.start();
			if (creaJugadorLocalArcade())
				getJugadorLocalArcade();
			l.lanzaActivity(Props.Action.ARCADE, b);
			break;
		case R.id.bAventura:
			if (creaJugadorLocalPquest())
				getJugadorLocalPquest();
			//TODO crea aventura a pelo por ahora para pruebas
			b.putSerializable(Props.Comun.AVENTURA, new Aventura("aventura",
					"aventura"));
			// l.lanzaActivity(Props.Action.AVENTURA, b);
			l.lanzaActivity(Props.Action.SELECMJ, b);
			break;
		case R.id.bOpciones:
			l.lanzaActivity(Props.Action.INGAME, b);
			// Intent iOpciones = new Intent(Props.Action.OPCIONES);
			// iOpciones.putExtra(Props.Comun.J, jugador);
			// startActivity(iOpciones);
			break;
		case R.id.bSalir:
			finish();
			l.lanzaActivity(Props.Action.OPCIONES, b);
			break;
		}
		onClose();
	}

	/**
	 * Metodo de utilidad para inicializar la Actividad
	 */
	private void inicializar() {
		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		menuTheme = MediaPlayer.create(MainMenu.this, R.raw.menu);
		if (sonido)
			menuTheme.start();

		bArcade = (Button) findViewById(R.id.bArcade);
		bOpciones = (Button) findViewById(R.id.bOpciones);
		bAventura = (Button) findViewById(R.id.bAventura);
		bSalir = (Button) findViewById(R.id.bSalir);

		bArcade.setOnClickListener(this);
		bOpciones.setOnClickListener(this);
		bAventura.setOnClickListener(this);
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
	 * Realiza la obtención de campos de pquest en la BD local
	 * 
	 * @return si esta o no el jugador en la BD local
	 */
	private boolean getJugadorLocalPquest() {
		// leer de la BD si existe nombre
		boolean esta = false;
		// existe el registro
		if (mDbHelper.existsRow(jugador.getNombre(), Tabla.pquest)) {
			// recupera info
			Cursor mCursor = mDbHelper.fetchRow(jugador.getNombre(),
					Tabla.pquest);
			startManagingCursor(mCursor);
			int[] pquest = new int[] {
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE1),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE2),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE3),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE4),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE5),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE6),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE7),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE8),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE9),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE10),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE11),
					mCursor.getInt(DbAdapter.PQUEST_IDCOL_SCORE12) };
			stopManagingCursor(mCursor);
			jugador.setScoreQuest(pquest);
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

	/**
	 * Crea el jugador si no existe en la tabla pquest de la BD local
	 * 
	 * @return true o false en funcion de si existia o no
	 */
	private boolean creaJugadorLocalPquest() {
		// leer de la BD si existe nombre
		boolean esta = true;
		if (!mDbHelper.existsRow(jugador.getNombre(), Tabla.pquest)) {
			// crear nuevo jugador
			mDbHelper.createRowPquest(jugador.getNombre(), new int[] { 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0 }, 0);
			esta = false;
		}
		return esta;
	}
}
