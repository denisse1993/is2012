//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: QRonos
// ASIGNATURA : Ingeniería del Software
//

package com.cinnamon.is.game;

import com.cinnamon.is.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cinnamon.is.comun.Conexion;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Inet;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.UtilQR;
import com.cinnamon.is.comun.Props.Enum.Tabla;

/**
 * Activity que nos da a elegir entre Crear nuestra propia aventura o Unirnos a
 * ella
 * 
 * @author Cinnamon Team
 * @version 1.3 19.04.2012
 */
public class EligeModoAventura extends Activity implements Inet,
		OnClickListener {

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

	/**
	 * Utilidad para lanzar el Scanner QR
	 */
	private UtilQR q;

	/**
	 * Nombre de la aventura
	 */
	private String nameQuest="quest";

	/**
	 * Para contactar con BD online
	 */
	private Conexion conexion;

	/**
	 * Aventura inicial
	 */
	private Aventura a;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elige_modo_aventura);
		launch = new Launch(this);

		conexion = new Conexion(this);

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
			// name escrito de la aventura no existe ya en la BD
			a = new Aventura(nameQuest, nameQuest);
			launch.lanzaDialogoEsperaCreaQuest(a);
			break;
		case R.id.b_unirse_aventura:
			if (creaJugadorLocalPquest())
				getJugadorLocalPquest();

		}
	}

	/**
	 * Abre el menu principal
	 */
	public void lanzaSelecMJ() {
		Bundle b = new Bundle();
		b.putSerializable(Props.Comun.AVENTURA, a);
		Launch.lanzaActivity(this, Props.Action.SELECMJ, b);
		finish();
	}

	/**
	 * Crea la aventura si no existe en la tabla arcade de la BD local
	 * 
	 * @return true o false en funcion de si existia o no
	 */
	public boolean creaAventuraLocal() {
		// leer de la BD si existe nombre
		boolean esta = true;
		if (!mDbHelper.existsRow(nameQuest, Tabla.quest)) {
			// crear nuevo jugador
			mDbHelper.createRowQuest(nameQuest, nameQuest, new Integer[12],
					new String[12]);
			esta = false;
		}
		return esta;
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
					0, 0, 0, 0, 0, 0, 0, 0, 0 }, 0, null);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String contents = q.getRawQR(requestCode, resultCode, data);
		if (requestCode == UtilQR.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// TODO
				/*
				 * if (dameOnlineAventura(contents){
				 * 
				 * launch.lanzaActivity(Props.Action.LANZARAVENTURA); }
				 */
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancell
			}
		}

	}

	@Override
	public Launch l() {
		return launch;
	}

	@Override
	public Conexion c() {
		return conexion;
	}

}
