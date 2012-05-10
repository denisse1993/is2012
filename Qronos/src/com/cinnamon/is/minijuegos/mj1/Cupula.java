package com.cinnamon.is.minijuegos.mj1;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Cupula {
	private int x;
	private int y;
	private Bitmap bmp;

	// Getters y Setters
	public int getWidth() {
		return bmp.getWidth();
	}

	public int getHeight() {
		return bmp.getHeight();
	}

	public Cupula(Bitmap bmp, int x, int y) {
		this.setX(x);
		this.setY(y);
		this.bmp = bmp;
	}

	public void onDraw(Canvas canvas, int x, int y) {
		canvas.drawBitmap(this.bmp, x, y, null);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
}
