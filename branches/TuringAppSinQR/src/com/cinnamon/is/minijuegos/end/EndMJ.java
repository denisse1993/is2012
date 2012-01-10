package com.cinnamon.is.minijuegos.end;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.cinnamon.is.comun.Intents;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.game.Jugador;

public class EndMJ extends Minijuego {

	GameView game;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		game = new GameView(this);
		setContentView(game);

		// Pone valores del minijuego
		startTime();
		setNombre(Intents.Comun.minijuegos[4]);
		setObjeto(0);
		setFase(5);
		setSuperado(false);

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);

		//lanzarAvisoMJ(textoDialog, title);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO FIN
		if (game.getFinalJuego()) {
			superado = true;
			Intent r = new Intent(Intents.Action.ENDGAME);
			r.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivity(r);
			finish();
		}

		return super.onTouchEvent(event);
	}

}
