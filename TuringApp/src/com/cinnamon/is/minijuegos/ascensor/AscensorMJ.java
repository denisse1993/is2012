package com.cinnamon.is.minijuegos.ascensor;

import android.content.Intent;
import android.os.Bundle;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Intents;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.game.Jugador;

/**
 * Actividad que representa el minijuego del Ascensor
 * 
 * @author Cinnamon Team
 * 
 */
public class AscensorMJ extends Minijuego {

	/**
	 * Jugador de login
	 */
	private Jugador jugador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// comienza a contar el tiempo
		startTime();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ascensormj);
		setNombre("AscensorMJ");

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
	}

	@Override
	public void onBackPressed() {
		// termina de contar el tiempo
		finishTime();
		jugador.setScore(jugador.getScore() + calcularPuntuacion());
		Intent r = new Intent();
		r.putExtra(Intents.Comun.JUGADOR, jugador);
		setResult(RESULT_OK, r);
		finish();
	}
}
