//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Conexion;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.Props.Enum.Tabla;

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
	 * Pass del jugador leido del EditText
	 */
	private String pass;
	/**
	 * Pass del jugador codificada del EditText
	 */
	private String passMD5;
	/**
	 * Jugador de la aplicacion
	 */
	private Jugador jugador;

	// Interfaz
	private EditText etUsername;
	private EditText etPassword;
	private Button bLogin;
	private Button bRegister;
	private SharedPreferences prefs;
	private CheckBox cbRemember;
	Bitmap uploadIMG;
	// Interfaz antigua
	// private Button bArrancar;
	// private TextView tvbienvenida;
	// private TextView tvLogin;

	// Utiles
	private Conexion conexion;
	private Launch l;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		conexion = new Conexion(this);
		l = new Launch(this);
		inicializar();
	}

	/**
	 * Metodo de utilidad para inicializar la actividad
	 */
	private void inicializar() {
		// antiguo login
		// tvLogin = (TextView) findViewById(R.id.tvLogin);
		// bArrancar = (Button) findViewById(R.id.bArrancar);
		// tvbienvenida = (TextView) findViewById(R.id.tVbienvenida);
		//
		// // escondemos hasta que se loguee
		// tvbienvenida.setVisibility(View.INVISIBLE);
		// bArrancar.setVisibility(View.INVISIBLE);
		// bArrancar.setOnClickListener(this);

		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) this.findViewById(R.id.etPassword);
		bLogin = (Button) findViewById(R.id.bLogin);
		bRegister = (Button) findViewById(R.id.bRegister);
		cbRemember = (CheckBox) this.findViewById(R.id.cbRememberLogin);
		uploadIMG = BitmapFactory.decodeResource(getResources(),
				R.drawable.ibmj0);

		// obtiene preferencias
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		// Asignamos Listener a los botones
		bLogin.setOnClickListener(this);
		bRegister.setOnClickListener(this);

		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		// Intenta subir una imagen al servidor (Prueba)
		/*
		 * try { conex.uploadImage(R.drawable.mj1); } catch
		 * (ClientProtocolException e1) { e1.printStackTrace(); } catch
		 * (IOException e1) { e1.printStackTrace(); }
		 */

		// Devuelve las preferencias del jugador
		nombre = prefs.getString("user", "");
		pass = prefs.getString("pass", "");
		passMD5 = "";
		try {
			if (!pass.equals(""))
				passMD5 = Conexion.toMD5(pass);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// Pondra "" y false si no se ha guardado el prefs
		etUsername.setText(nombre);
		etPassword.setText(passMD5);// pone pass md5
		if (prefs.getString("checked", "no").equals("yes"))
			cbRemember.setChecked(true);
		else
			cbRemember.setChecked(false);

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

	@Override
	public void onBackPressed() {
		mDbHelper.close();
		finish();
	}

	@Override
	public void onClick(View v) {
		boolean online, local;
		if (preparaLogin()) {
			switch (v.getId()) {
			case R.id.bLogin:
				try {
					online = conexion.login(nombre, passMD5);
				} catch (IOException e1) {
					e1.printStackTrace();
					l.lanzaToast(Props.Strings.ERROR_INET);
					Props.Comun.ONLINE = false;
				}
				local = loginLocal();
				if (local) // &&online para k tmb tenga online obligatorio
					lanzaMenuPrincipal();
				else
					l.lanzaToast(Props.Strings.USER_PASS_MAL);
				break;
			case R.id.bRegister:
				try {
					online = conexion.register(nombre, passMD5);
				} catch (IOException e) {
					e.printStackTrace();
					l.lanzaToast(Props.Strings.ERROR_INET);
					Props.Comun.ONLINE = false;
				}
				local = creaJugadorLocal();
				if (local)
					l.lanzaToast(Props.Strings.USER_YA_EXISTE);
				else {
					l.lanzaToast(Props.Strings.USER_CREADO);
					lanzaMenuPrincipal();
				}
			}
		} else
			l.lanzaToast(Props.Strings.CAMPOS_VACIOS);
	}

	/**
	 * Prepara el login
	 * 
	 * @return si el login se ha rellenado correctamente o no
	 */
	private boolean preparaLogin() {
		boolean bien = true;
		nombre = etUsername.getText().toString();
		String str = etPassword.getText().toString();
		if (!str.equals(passMD5))// si la pass no esta en md5
			pass = etPassword.getText().toString();
		try {
			passMD5 = Conexion.toMD5(pass);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		if (nombre.equals("") || pass.equals("")) {
			bien = false;
		} else {
			// Si esta activo las guarda
			SharedPreferences.Editor editor = prefs.edit();
			if (cbRemember.isChecked()) {
				editor.putString("user", nombre);
				editor.putString("pass", pass);
				editor.putString("checked", "yes");
				editor.commit();
			} else {
				editor.clear();
				editor.commit();
			}
		}
		return bien;
	}

	/**
	 * Realiza el login del jugador en la BD local
	 * 
	 * @return si esta o no el jugador en la BD local
	 */
	private boolean loginLocal() {
		// leer de la BD si existe nombre
		boolean esta = false;
		// existe el registro
		if (mDbHelper.existsRow(nombre, Tabla.users))
			try {
				// la password concuerda
				String passMD5 = Conexion.toMD5(pass);
				if (mDbHelper.passOk(nombre, passMD5)) {
					// crea jugador por defecto
					jugador = new Jugador(nombre, pass);
					esta = true;
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		return esta;
	}

	/**
	 * Crea el jugador si no existe en la BD local
	 * 
	 * @return true o false en funcion de si existia o no
	 */
	private boolean creaJugadorLocal() {
		// leer de la BD si existe nombre
		boolean esta = true;
		if (!mDbHelper.existsRow(nombre, Tabla.users)) {
			// crear nuevo jugador
			mDbHelper.createRowUsers(nombre, passMD5);
			jugador = new Jugador(nombre, pass);
			esta = false;
		}
		return esta;
	}

	/**
	 * Abre el menu principal
	 */
	private void lanzaMenuPrincipal() {
		Bundle b = new Bundle();
		b.putSerializable(Props.Comun.JUGADOR, jugador);
		Launch.lanzaActivity(this, Props.Action.MAINMENU, b);
		finish();
	}

}
