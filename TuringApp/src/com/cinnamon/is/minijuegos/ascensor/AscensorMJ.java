//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.minijuegos.ascensor;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ascensormj);
		// Pone valores del minijuego
		startTime();
		setNombre(Intents.Comun.minijuegos[1]);
		setObjeto(1);
		setFase(2);
		setSuperado(false);

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
	}
}
