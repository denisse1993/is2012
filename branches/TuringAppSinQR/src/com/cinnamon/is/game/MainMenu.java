//
// Universidad Complutense de Madrid
// Ingenieria Inform�tica
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingenier�a del Software
//
package com.cinnamon.is.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Intents;

/**
 * Actividad que representa el menu principal de la aplicacion
 * 
 * @author Cinnamon Team
 * @version 1.2 24.11.2011
 */
public class MainMenu extends Activity implements OnClickListener {

	// interfaz
	private Button bNuevaPartida, bOpciones, bAyuda, bSalir;

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
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		SharedPreferences getData = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		sonido = getData.getBoolean("cbSonido", true);
		inicializar();
	}

	/**
	 * No toqueis esto que ahora no lo necesitamos pero puede que en un futuro
	 * si es es layout del main pero hecho en java o parte de �l.
	 */
	/*
	 * private View getContentView() { LinearLayout layoutMain = new
	 * LinearLayout(this);
	 * layoutMain.measure(android.widget.LinearLayout.LayoutParams.FILL_PARENT,
	 * android.widget.LinearLayout.LayoutParams.FILL_PARENT);
	 * layoutMain.setOrientation(LinearLayout.VERTICAL);
	 * layoutMain.setBackgroundResource(R.drawable.menu);
	 * 
	 * LinearLayout layoutText = new LinearLayout(this);
	 * layoutText.setGravity(Gravity.CENTER);
	 * layoutText.measure(android.widget.LinearLayout
	 * .LayoutParams.FILL_PARENT,144);
	 * layoutText.setOrientation(LinearLayout.VERTICAL);
	 * 
	 * TextView textTitulo = new TextView(this);
	 * textTitulo.setWidth(android.widget
	 * .LinearLayout.LayoutParams.FILL_PARENT);
	 * textTitulo.setHeight(android.widget
	 * .LinearLayout.LayoutParams.WRAP_CONTENT); textTitulo.setText("Hola");
	 * textTitulo.setTextSize(100); textTitulo.setTextColor(Color.WHITE);
	 * 
	 * CustomButton bNuevaPartida = new
	 * CustomButton(this,R.drawable.b_nueva_partida,"bNuevaPartida");
	 * bNuevaPartida = new CustomButton(this, R.drawable.b_nueva_partida,
	 * "bNuevaPartida"); bNuevaPartida.setOnClickListener(this);
	 * bNuevaPartida.setGravity(Gravity.CENTER);
	 * 
	 * 
	 * 
	 * //layoutText.addView(textTitulo); layoutText.addView(bNuevaPartida);
	 * layoutMain.addView(layoutText);
	 * 
	 * return layoutMain; }
	 */

	@Override
	protected void onPause() {
		super.onPause();
		menuTheme.release();
		mDbHelper.close();
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bNuevaPartida:
			menuTheme.release();
			MediaPlayer openingTheme = MediaPlayer.create(MainMenu.this,
					R.raw.opening);
			if (sonido)
				openingTheme.start();
			if (jugador.getScore() == 0) {
				lanzaInGame();
			} else {
				lanzaResetDialog();
			}
			break;
		case R.id.bOpciones:
			Intent iOpciones = new Intent(Intents.Action.OPCIONES);
			iOpciones.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivity(iOpciones);
			break;
		// case R.id.bAyuda:
		// TODO Ayuda pendiente
		// break;
		case R.id.bSalir:
			finish();
			break;
		}

	}

	/**
	 * Metodo de utilidad para inicializar la Actividad
	 */
	private void inicializar() {
		menuTheme = MediaPlayer.create(MainMenu.this, R.raw.menu);
		if (sonido)
			menuTheme.start();

		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		bNuevaPartida = (Button) findViewById(R.id.bNuevaPartida);
		bOpciones = (Button) findViewById(R.id.bOpciones);
		// bAyuda = (Button) findViewById(R.id.bAyuda);
		bSalir = (Button) findViewById(R.id.bSalir);

		bNuevaPartida.setOnClickListener(this);
		bOpciones.setOnClickListener(this);
		//bAyuda.setOnClickListener(this);
		bSalir.setOnClickListener(this);
	}

	/**
	 * Metodo que resetea al jugador accediendo a la base de datos
	 */
	private void resetJugador() {
		jugador.setScore(0);
		jugador.setHoja(0);
		jugador.setMochila(0);
		jugador.setFase1(0);
		jugador.setFase2(0);
		jugador.setFase3(0);
		jugador.setFase4(0);
		mDbHelper.updateRow("'" + jugador.getNombre() + "'",
				jugador.getScore(), jugador.getHoja(), jugador.mochilaToInt(),
				jugador.getFase1(), jugador.getFase2(), jugador.getFase3(),
				jugador.getFase4());
	}

	/**
	 * Metodo que lanza el ingame con la informacion de jugador
	 */
	private void lanzaInGame() {
		Intent iInGame = new Intent(Intents.Action.INGAME);
		iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivity(iInGame);
	}

	/**
	 * Metodo que lanza el Dialog para reseteo o no de partida
	 */
	private void lanzaResetDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("�Ya hay una partida empezada, quieres continuarla?")
				.setCancelable(false)
				.setPositiveButton("S�", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						MainMenu.this.finish();
						lanzaInGame();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						resetJugador();
						MainMenu.this.finish();
						lanzaInGame();
					}
				});
		builder.show();
	}

}