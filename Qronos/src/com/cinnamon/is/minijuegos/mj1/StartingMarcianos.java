package com.cinnamon.is.minijuegos.mj1;

import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.comun.Props;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class StartingMarcianos extends Minijuego {
	GameView game;
	boolean yaFin = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		game = new GameView(this, this);
		setContentView(game);
	}

	/*
	 * public void onBackPressed() { lanzaOpcionesDialog(); //definido en
	 * Minijuego }
	 */

	public void reiniciar() {
		/*
		 * Intent intent = getIntent(); finish(); startActivity(intent);
		 */
		game = new GameView(this, this);
		setContentView(game);
	}

	public void terminar() {
		this.finish();
	}

	public void pausar() {
		this.onPause();
	}

	// TODO se keda dialog pintao sobre el canvas despues de cerrarlo
	public void resumir() {
		onResume();
		game.reanudar();
		SharedPreferences getData = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if (getData.getBoolean(Props.Comun.CB_SONIDO, true))
			game.musicaOn();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// el sensormanager.sensor... es el rate para ver cada cuanto actualiza
		// la captura del sensor
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	public void finalizar(boolean s) {

		yaFin = true;
		// Establece valores de puntuacion y superado
		int puntuacion = game.getScore() * 10;
		superado = s;
		// Creo el bundle con la info usando strings genericos de clase
		// Props.Comun
		Bundle b = new Bundle();
		b.putInt(Props.Comun.SCORE, puntuacion);
		b.putBoolean(Props.Comun.SUPERADO, superado);
		// Devuelvo resultado a actividad padre
		Launch.returnActivity(this, b, RESULT_OK);
		// setResult(puntuacion);
		//
		// this.finish();
	}

	@Override
	public void parar() {
		SharedPreferences getData = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		if (getData.getBoolean(Props.Comun.CB_SONIDO, true)) {
			game.musicaOff();
		}
		game.loopStop();
	}

	protected void onDestroy() {
		if (!yaFin)
			finalizar(true);
		super.onDestroy();

	}

	// protected void lanzaOpcionesDialog() {
	// // TODO Ahora mismo muestra 3 opciones, continuar, reiniciar o salir
	// // para el mj y luego lanza las opciones
	// // Launch.lanzaConfirmacion("Salir del minijuego",
	// // "�Quieres salir del minijuego sin completarlo?", this);
	// parar();
	// Launch.lanzaOpciones(this, "Juego Pausado", modo, this);
	// }

}
