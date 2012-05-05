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
	Button bCrear, bUnirse, bEditar, bUsar;

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
	private final String nameQuest = "quest";
	/**
	 * Pass de la aventura
	 */
	private final String passQuest = "quest";

	/**
	 * Para contactar con BD online
	 */
	private Conexion conexion;

	/**
	 * Aventura inicial
	 */
	private Aventura a;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elige_modo_aventura);
		launch = new Launch(this);

		conexion = new Conexion(this);
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);
		
		bCrear = (Button) findViewById(R.id.b_crear_aventura);
		bCrear.setOnClickListener(this);

		bUnirse = (Button) findViewById(R.id.b_unirse_aventura);
		bUnirse.setOnClickListener(this);

		bEditar = (Button) findViewById(R.id.b_editar_aventura);
		bEditar.setOnClickListener(this);

		bUsar = (Button) findViewById(R.id.b_usar_aventura);
		bUsar.setOnClickListener(this);

		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
	}

	@Override
	public void onClick(final View v) {
		a = new Aventura(nameQuest, passQuest);
		switch (vClicked = v.getId()) {
		case R.id.b_crear_aventura:
			// name y pass escritos de la aventura no existe ya en la BD
			launch.lanzaDialogoEsperaCreaQuest(a);
			break;
		case R.id.b_editar_aventura:
			// leer name y password check si existe y concuerda, descargar,
			// rellenar y lanzar SELECMJ
			launch.lanzaDialogoEsperaGetQuest(a);
			break;
		case R.id.b_usar_aventura:
			// leer name, check si existe, descargar,rellenar y lanzar
			// SelecPista en modo lectura
			launch.lanzaDialogoEsperaGetQuestPass(a);
			break;
		case R.id.b_unirse_aventura:
			q = new UtilQR(this);
			q.lanzarQR();
			break;
		}
	}

	/**
	 * Abre el selecMJ
	 */
	public void lanzaSelecMJ() {
		Bundle b = new Bundle();
		b.putSerializable(Props.Comun.AVENTURA, a);
		Launch.lanzaActivity(this, Props.Action.SELECMJ, b);
		finish();
	}

	/**
	 * Abre el selecPISTA modo lectura
	 */
	public void lanzaSelecPISTA() {
		Bundle b = new Bundle();
		b.putSerializable(Props.Comun.AVENTURA, a);
		b.putBoolean(Props.Comun.READ, true);
		Launch.lanzaActivity(this, Props.Action.SELECPISTA, b);
		finish();
	}

	/**
	 * Lanza InGameAventura
	 */
	public void lanzaInGameAventura() {
		Bundle b = new Bundle();
		b.putSerializable(Props.Comun.AVENTURA, a);
		b.putSerializable(Props.Comun.JUGADOR, jugador);
		Launch.lanzaActivity(this, Props.Action.INGAMEAVENTURA, b);
		finish();
	}

	/**
	 * Crea la aventura si no existe en la tabla arcade de la BD local
	 * 
	 * @return true o false en funcion de si existia o no
	 */
	public boolean creaAventuraLocalActualizada() {
		// leer de la BD si existe nombre
		boolean esta = true;
		if (!mDbHelper.existsRow(nameQuest, Tabla.quest)) {
			// crear nuevo jugador
			mDbHelper.createRowQuest(nameQuest, a.getPass(),
					a.getMJArrayInteger(), a.getPistasArrayString());
			esta = false;
		} else {
			mDbHelper.updateRowQuest(nameQuest, a.getPass(),
					a.getMJArrayInteger(), a.getPistasArrayString());
		}
		return esta;
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
		if (!mDbHelper.isOpen()) {
			mDbHelper.open(false);
		}
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
					0, 0, 0, 0, 0, 0, 0, 0, 0 }, 0, a.getNombre());
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
			String name=mCursor.getString(DbAdapter.PQUEST_IDCOL_NAME);
			int actual = mCursor.getInt(DbAdapter.PQUEST_IDCOL_ACTUAL);
			stopManagingCursor(mCursor);
			jugador.setScoreQuest(pquest);
			jugador.setAventura(name);
			jugador.setFase(actual);
			mCursor.close();
			esta = true;
		}
		return esta;
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		String contents = q.getRawQR(requestCode, resultCode, data);
		if (requestCode == UtilQR.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				a = new Aventura(contents, null);
				if (creaJugadorLocalPquest()) {
					getJugadorLocalPquest();
				}
				launch.lanzaDialogoEsperaGetQuestUnirse(a);
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
