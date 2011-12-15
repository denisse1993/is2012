package com.cinnamon.is.minijuegos.reinas;

import android.graphics.Bitmap;

public class Reina {

	Bitmap bmp;
	int fila;
	int col;

	public Reina(Bitmap bmp, int f, int c) {

		this.fila = f;
		;
		this.col = c;
		;
		this.bmp = bmp;
	}

	public void cambiaPosicion(int f, int c) {

		this.fila = f;
		this.col = c;
	}

	public int dameFila() {
		return this.fila;
	}

	public int dameCol() {
		return this.col;
	}

}