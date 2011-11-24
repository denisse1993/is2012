package com.cinnamon.is.game;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Intents;

/**
 * Pantalla para el login en la aplicacion Lee el nombre del jugador y comprueba
 * si existe en la base de datos, para crearlo o actualizar la informacion
 * 
 * @author JUser
 */
public class Login extends Activity implements OnClickListener {

	// base de datos
	private DbAdapter mDbHelper;
	private Cursor mCursor;

	/**
	 * Nombre del jugador
	 */
	private String nombre;

	/**
	 * Jugador en si
	 */
	private Jugador jugador;

	private EditText etLogin;
	private Button bLogin;
	private TextView tvLogin;

	private Button bArrancar;
	private TextView tvbienvenida;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		inicializar();
	}

	private void inicializar() {
		etLogin = (EditText) findViewById(R.id.etlogin);
		tvLogin = (TextView) findViewById(R.id.tvLogin);
		bLogin = (Button) findViewById(R.id.bLogin);

		bArrancar = (Button) findViewById(R.id.bArrancar);
		tvbienvenida = (TextView) findViewById(R.id.tVbienvenida);

		tvbienvenida.setVisibility(View.INVISIBLE);
		bArrancar.setVisibility(View.INVISIBLE);

		bLogin.setOnClickListener(this);
		bArrancar.setOnClickListener(this);
		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// metodo para que hacer cuando se pulsa el boton de atras
		// ahora mismo no hace nada
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.bLogin:
			nombre = etLogin.getText().toString();
			if (!nombre.equals("")) {
				bienvenida(creaJugador());
			}
			break;
		case R.id.bArrancar:
			Intent openMainMenu = new Intent(Intents.Action.MAINMENU);
			openMainMenu.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivity(openMainMenu);
			break;
		}

	}

	/**
	 * Metodo que se lanza cuando es un jugador que ya ha jugado
	 * 
	 * @param esta
	 */
	private void bienvenida(boolean esta) {
		bLogin.setVisibility(View.INVISIBLE);
		tvLogin.setVisibility(View.INVISIBLE);
		etLogin.setVisibility(View.INVISIBLE);
		tvbienvenida.setVisibility(View.VISIBLE);
		bArrancar.setVisibility(View.VISIBLE);
		if (esta) {

			tvbienvenida.setText("Hola " + nombre + "!\n Tu puntuación es "
					+ jugador.getScore()
					+ ".\nToca arrancar para ir al menu principal");
		} else {
			tvbienvenida
					.setText("Hola "
							+ nombre
							+ "!\nEs la primera vez que juegas!\nToca arrancar para ir al menu principal:");
		}
	}

	/**
	 * Crea el jugador o recupera la info si existe
	 * 
	 * @return true o false en funcion de si existia o no
	 */
	private boolean creaJugador() {
		// leer de la BD si existe nombre
		// (se le añaden las comillas por sintaxis de SQL)
		boolean esta;
		if (!mDbHelper.existsRow("'" + nombre + "'")) {
			// crear nuevo jugador
			mDbHelper.createRow(nombre, 0, 0, 0, 0, 0, 0, 0);
			jugador = new Jugador(nombre);
			esta = false;
		} else {
			// recupera info
			mCursor = mDbHelper.fetchRow("'" + nombre + "'");
			startManagingCursor(mCursor);
			jugador = new Jugador(nombre,
					mCursor.getInt(DbAdapter.INFO_IDCOL_SCORE),
					mCursor.getInt(DbAdapter.MAPA_IDCOL_FASE1),
					mCursor.getInt(DbAdapter.MAPA_IDCOL_FASE2),
					mCursor.getInt(DbAdapter.MAPA_IDCOL_FASE3),
					mCursor.getInt(DbAdapter.MAPA_IDCOL_FASE4),
					mCursor.getInt(DbAdapter.INFO_IDCOL_HOJA),
					mCursor.getInt(DbAdapter.INFO_IDCOL_MOCHILA));
			esta = true;
		}
		stopManagingCursor(mCursor);
		mCursor.close();
		mDbHelper.close();
		return esta;
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
