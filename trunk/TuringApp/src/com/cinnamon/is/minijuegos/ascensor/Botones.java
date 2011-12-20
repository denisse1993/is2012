package com.cinnamon.is.minijuegos.ascensor;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Botones {
	private int x;
	private int y;
	private GameView gameView;
	private Bitmap bmp;
	private int width;
	private int height;

	public Botones(GameView gameView, Bitmap bitmap) {
		this.x = 0;
		this.y = 0;
		this.gameView = gameView;
		this.bmp = bitmap;
		this.width = (bitmap.getWidth());
		this.height = (bitmap.getHeight());
	}

	public void setCoordenadas(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void update() {

	}

	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(bmp, x, y, null);
	}

	public boolean isClick(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;

	}

}
