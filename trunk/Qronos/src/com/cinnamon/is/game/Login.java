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
 * Pantalla para el login en la aplicacion lee el nombre del jugador y comprueba
 * si existe en la base de datos, para crearlo o actualizar la informacion
 * 
 * @author Cinnamon Team
 * @version 1.1 24.11.2011
 */
public class Login extends Activity implements OnClickListener {

	/**
	 * Adaptador para conectar con la BD
	 */
	private DbAdapter mDbHelper;
	/**
	 * Cursor para tratar las consultas en la BD
	 */
	private Cursor mCursor;

	/**
	 * Nombre del jugador leido del EditText
	 */
	private String nombre;

	/**
	 * Jugador de la aplicacion
	 */
	private Jugador jugador;

	// Interfaz
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

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onBackPressed() {
		// metodo para que hacer cuando se pulsa el boton de atras
		// ahora mismo no hace nada, con lo que lo tenemos deshabilitado
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bLogin:
			nombre = etLogin.getText().toString();
			bienvenida(false);
			break;
		case R.id.bArrancar:
			Intent openMainMenu = new Intent(Intents.Action.MAINMENU);
			openMainMenu.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivity(openMainMenu);
			break;
		}
	}

	/**
	 * Metodo de utilidad para inicializar la actividad
	 */
	private void inicializar() {
		etLogin = (EditText) findViewById(R.id.etlogin);
		tvLogin = (TextView) findViewById(R.id.tvLogin);
		bLogin = (Button) findViewById(R.id.bLogin);
	
		bArrancar = (Button) findViewById(R.id.bArrancar);
		tvbienvenida = (TextView) findViewById(R.id.tVbienvenida);
	
		// escondemos hasta que se loguee
		tvbienvenida.setVisibility(View.INVISIBLE);
		bArrancar.setVisibility(View.INVISIBLE);
	
		bLogin.setOnClickListener(this);
		bArrancar.setOnClickListener(this);
		// abre base de datos
//		mDbHelper = new DbAdapter(this);
//		mDbHelper.open(false);
	}

	/**
	 * Metodo para dar la bienvenida al nuevo (o no) jugador
	 * 
	 * @param esta
	 *            si el jugador ya ha jugado al juego o no
	 */
	private void bienvenida(boolean esta) {
		// cambiamos interfaz
		bLogin.setVisibility(View.INVISIBLE);
		tvLogin.setVisibility(View.INVISIBLE);
		etLogin.setVisibility(View.INVISIBLE);
		tvbienvenida.setVisibility(View.VISIBLE);
		bArrancar.setVisibility(View.VISIBLE);

		if (esta)
			tvbienvenida.setText("Hola " + nombre + "!\n Tu puntuación es "
					+ jugador.getScore()
					+ ".\nToca arrancar para ir al menu principal");
		else
			tvbienvenida
					.setText("Hola "
							+ nombre
							+ "!\nEs la primera vez que juegas!\nToca arrancar para ir al menu principal:");
	}
}
