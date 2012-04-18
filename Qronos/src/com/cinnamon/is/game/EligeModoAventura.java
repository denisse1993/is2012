//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//

package com.cinnamon.is.game;

import android.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.Props.Enum.Tabla;

/**
 * Activity que nos da a elegir entre Crear nuestra propia aventura o Unirnos a
 * ella
 * 
 * @author Cinnamon Team
 * @version 1.0 16.04.2012
 */
public class EligeModoAventura extends Activity implements OnClickListener {

	/**
	 * Botones del view
	 */
	Button bCrear, bUnirse;

	/**
	 * Vista pulsada en onClick para uso en dialog onclick
	 */
	private int vClicked;

	/**
	 * Launch de la actividad
	 */
	Launch launch;
	
	/**
	 * Adaptador para conectar con la BD
	 */
	private DbAdapter mDbHelper;
	

	/**
	 * Jugador actual en la aplicacion
	 */
	private Jugador jugador;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elige_modo_aventura);

		launch = new Launch(this);

		bCrear = (Button) findViewById(R.id.b_crear_aventura);
		bCrear.setOnClickListener(this);

		bUnirse = (Button) findViewById(R.id.b_unirse_aventura);
		bUnirse.setOnClickListener(this);
	
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);
		
		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
	}

	@Override
	public void onClick(View v) {
		switch (vClicked = v.getId()) {
		case R.id.b_crear_aventura:
			if (creaJugadorLocalPquest())
				getJugadorLocalPquest();
			Bundle bundle = new Bundle();
			bundle.putSerializable(Props.Comun.AVENTURA, new Aventura("aventura",
					"aventura"));
			launch.lanzaActivity(Props.Action.SELECMJ, bundle);
		case R.id.b_unirse_aventura:
			launch.lanzaActivity(Props.Action.LANZARAVENTURA);
		}
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

}
