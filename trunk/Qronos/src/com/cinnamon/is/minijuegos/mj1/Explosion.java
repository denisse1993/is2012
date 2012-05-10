package com.cinnamon.is.minijuegos.mj1;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion {
	private Bitmap bmp;
	private int x;
	private int y;
	private int loops;
	private int width;
	private int height;
	private boolean used;

	// Getters y setters

	public int getLoops() {
		return loops;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void cambiarEstado(boolean x) {
		this.used = x;
	}

	public boolean getEstado() {
		return this.used;
	}

	public Explosion(Bitmap bmp, int x, int y) {
		this.bmp = bmp;
		used = false;
		this.x = x;
		this.y = y;
		this.loops = 5;
		width = bmp.getWidth();
		height = bmp.getHeight();

	}

	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(this.bmp, this.x, this.y, null);
	}

	public boolean isClick(float x, float y) {
		if (x >= this.x && x <= this.x + width && y >= this.y
				&& y <= this.y + height)
			return true;
		else
			return false;
	}

	public void restarLoops() {
		this.loops--;
	}
}
