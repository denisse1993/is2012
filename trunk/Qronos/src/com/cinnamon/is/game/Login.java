//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Conexion;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.Props.Enum.Tabla;
import com.cinnamon.is.presentacion.Intro;

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

	// Connexion
	Conexion conex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		conex= new Conexion(this);
		inicializar();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mDbHelper.close();
		finish();
	}

	@Override
	public void onBackPressed() {
		// metodo para que hacer cuando se pulsa el boton de atras
		// ahora mismo no hace nada, con lo que lo tenemos deshabilitado
	}

	@Override
	public void onClick(View v) {
		boolean online, local;
		if (preparaLogin()) {
			switch (v.getId()) {
			case R.id.bLogin:
				try {
					online = conex.login(nombre, passMD5);
				} catch (ClientProtocolException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				local = loginLocal();
				if (local) // &&online para k tmb tenga online obligatorio
					lanzaMenuPrincipal();
				break;
			case R.id.bRegister:
				try {
					online = conex.register(nombre, passMD5);
				} catch (ClientProtocolException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				local = creaJugadorLocal();
				lanzaMenuPrincipal();
			}
		} else
			Toast.makeText(getApplicationContext(),
					"Alguno de los campos est‡n vac’os", Toast.LENGTH_LONG)
					.show();
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
				R.drawable.bmj1);

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
		try {
			passMD5 = Conexion.toMD5(pass);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String checkPref = prefs.getString("checked", "yes");

		if (checkPref.equals("yes")) {
			etUsername.setText(nombre);
			etPassword.setText(passMD5);// pone pass md5
			cbRemember.setChecked(true);
		}

	}

	/**
	 * Prepara el login
	 * 
	 * @return si el login se ha rellenado correctamente o no
	 */
	private boolean preparaLogin() {
		boolean mal = false;
		nombre = etUsername.getText().toString();
		String str = etPassword.getText().toString();
		if (!str.equals(passMD5))
			pass = etPassword.getText().toString();
		try {
			passMD5 = Conexion.toMD5(pass);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		if (nombre.equals("") || pass.equals("")) {
			mal = true;
		} else {
			// Si estan predefinidas las carga
			if (prefs.getString("checked", "yes").equals("yes")) {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("user", nombre);
				editor.putString("pass", pass);
				editor.putString("checked", "yes");
				editor.commit();
			}
			mal = true;
		}
		return mal;
	}

	/**
	 * Realiza el login del jugador en la BD local
	 * 
	 * @return si esta o no el jugador en la BD local
	 */
	private boolean loginLocal() {
		// leer de la BD si existe nombre
		boolean esta = false;
		if (mDbHelper.existsRow(nombre, Tabla.parcade))
			try {
				if (mDbHelper.passOk(nombre, Conexion.toMD5(pass))) {
					// recupera info
					mCursor = mDbHelper.fetchRow(nombre, Tabla.parcade);
					startManagingCursor(mCursor);
					jugador = new Jugador(
							nombre,
							mCursor.getString(DbAdapter.PARCADE_IDCOL_PASS),
							new int[] {
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
									mCursor.getInt(DbAdapter.PARCADE_IDCOL_SCORE12) });
					stopManagingCursor(mCursor);
					mCursor.close();
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
		if (!mDbHelper.existsRow(nombre, Tabla.parcade)) {
			// crear nuevo jugador
			mDbHelper.createRowParcade(nombre, passMD5, new int[] { 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0 });
			jugador = new Jugador(nombre, passMD5);
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
	}

}
