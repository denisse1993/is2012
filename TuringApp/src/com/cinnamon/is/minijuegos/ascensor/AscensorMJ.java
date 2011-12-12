//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
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
	 * Jugador actual de la aplicacion
	 */
	private Jugador jugador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// comienza a contar el tiempo
		startTime();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ascensormj);
		// Pone valores del minijuego
		setNombre(Intents.Comun.minijuegos[1]);
		setObjeto(1);
		setFase(2);
		setSuperado(false);

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
	}

	@Override
	public void onBackPressed() {
		// termina de contar el tiempo
		finishTime();
		int puntuacion = calcularPuntuacion();
		superado=true;
		if (superado) {
			jugador.setScore(jugador.getScore() + puntuacion);
			jugador.addObjeto(objeto);
			jugador.superaFase(fase);
		} else
			jugador.actualFase(fase);
		
		Intent r = new Intent();
		r.putExtra(Intents.Comun.superado, superado);
		r.putExtra(Intents.Comun.objeto, objeto);
		r.putExtra(Intents.Comun.JUGADOR, jugador);
		setResult(RESULT_OK, r);
		finish();
	}
}
