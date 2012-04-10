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
	 * Jugador de la aplicacion
	 */
	private Jugador jugador;

	// Interfaz
	private EditText etUsername;
	private EditText etPassword;
	private Button bLogin;
	private Button bRegister;
	private Button bArcade;
	private TextView tvLogin;
	private Button bArrancar;
	private TextView tvbienvenida;
	private SharedPreferences prefs;
	private CheckBox cbRemember;
	Bitmap uploadIMG; 
	
	//Connexion 
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
		{
				initLogin();			
			//	bienvenida(creaJugador());
				break;
		}
		case R.id.bRegister:
		{
			 try {
             		String passMD5 = conex.toMD5(etPassword.getText().toString());
					conex.register(etUsername.getText().toString(), passMD5);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 break;
		}	
		case R.id.bArrancar:
			Bundle b = new Bundle();
			b.putSerializable(Props.Comun.JUGADOR, jugador);
			Launch.lanzaActivity(this, Props.Action.MAINMENU, b);
			break;
		}
	}

	/**
	 * Metodo de utilidad para inicializar la actividad
	 */
	private void inicializar() {
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText)this.findViewById(R.id.etPassword);
		//tvLogin = (TextView) findViewById(R.id.tvLogin);
		bLogin = (Button) findViewById(R.id.bLogin);
		bRegister= (Button) findViewById(R.id.bRegister);
		
	    prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    
	    cbRemember=(CheckBox)this.findViewById(R.id.cbRememberLogin);
	    
	    uploadIMG = BitmapFactory.decodeResource(getResources(),R.drawable.bmj1);

		bArrancar = (Button) findViewById(R.id.bArrancar);
		tvbienvenida = (TextView) findViewById(R.id.tVbienvenida);

		// escondemos hasta que se loguee
		tvbienvenida.setVisibility(View.INVISIBLE);
		bArrancar.setVisibility(View.INVISIBLE);

		//Asignamos Listener a los botones
		bLogin.setOnClickListener(this);
		bArrancar.setOnClickListener(this);
		bRegister.setOnClickListener(this);
		
		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);
		
		//Intenta subir una imagen al servidor (Prueba)
		/*try {
				conex.uploadImage(R.drawable.mj1);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
		
		//Devuelve las preferencias del jugador
		String userPref = prefs.getString("Username","0" );
		String passPref = prefs.getString("Password", "0");
		String checkPref = prefs.getString("checked", "no");
		
		if (checkPref.equals("yes")){
			etUsername.setText(userPref);
		    etPassword.setText(passPref);
		    cbRemember.setChecked(true);
		}
		
		
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
		//etLogin.setVisibility(View.INVISIBLE);
		tvbienvenida.setVisibility(View.VISIBLE);
		bArrancar.setVisibility(View.VISIBLE);

		if (esta)
			tvbienvenida.setText("Hola " + nombre + "!\n Tu puntuación es "
					+ jugador.getScoreTotal()
					+ ".\nToca arrancar para ir al menu principal");
		else
			tvbienvenida
					.setText("Hola "
							+ nombre
							+ "!\nEs la primera vez que juegas!\nToca arrancar para ir al menu principal:");
	}

	/**
	 * Crea el jugador o recupera su informacion si existe
	 * 
	 * @return true o false en funcion de si existia o no
	 */
	private boolean creaJugador() {
		// leer de la BD si existe nombre
		// (se le añaden las comillas por sintaxis de SQL)
		boolean esta;
		if (!mDbHelper.existsRow(nombre, Tabla.parcade)) {
			// crear nuevo jugador
			mDbHelper.createRowParcade(nombre, new int[] { 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 });
			jugador = new Jugador(nombre);
			esta = false;
		} else {
			// recupera info
			mCursor = mDbHelper.fetchRow(nombre, Tabla.parcade);
			startManagingCursor(mCursor);
			jugador = new Jugador(nombre, new int[] {
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
			esta = true;
			stopManagingCursor(mCursor);
			mCursor.close();
		}
		mDbHelper.close();
		return esta;
	}
	
	/**
	 * Inicializa el login
	 */
	private void initLogin(){
		String name1="";
 	   	String pass1="";
 	   	String Str_check2 = prefs.getString("checked", "no");
 	   	
 	   	//Si estan predefinidas las carga
 	   	if(Str_check2.equals("yes")){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", name1);
            editor.putString("password", pass1);
            editor.commit();
        }
 	   	
        name1 = etUsername.getText().toString();
        
        if (name1.equals("") || etPassword.toString().equals("")){
     	   Toast.makeText(getApplicationContext(), "Alguno de los campos est‡n vac’os", Toast.LENGTH_LONG).show();
        } 
        else{
        	try {
        		String passMD5 = conex.toMD5(etPassword.getText().toString());
				conex.login(etUsername.getText().toString(), passMD5);
				}
        	catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }         
     }
	
	
	
}

