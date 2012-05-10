package com.cinnamon.is.minijuegos.mj1;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SangreMarciano {
	private GameView view;
	private Bitmap bmp;
	private float x;
	private float y;
	private int loops;// Para controlar el tiempo que la sangre aparece

	// Getters y Setters
	public int getLoops() {
		return loops;
	}

	public SangreMarciano(Bitmap bmp, float x, float y) {
		this.bmp = bmp;
		this.x = x;
		this.y = y;
		this.loops = 20;
	}

	public void restarLoops() {
		this.loops--;
	}

	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(this.bmp, this.x, this.y, null);
	}

	/**
	 * @return the view
	 */
	public GameView getView() {
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(GameView view) {
		this.view = view;
	}
}
