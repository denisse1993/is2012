package com.cinnamon.is.minijuegos.mj1;

import android.os.Bundle;

import com.cinnamon.is.comun.Minijuego;

public class StartingMarcianos extends Minijuego {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		game = new GameView(this);
		run = ((GameView) game).loop;
		setContentView(game);
	}
}
