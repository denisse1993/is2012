package com.cinnamon.is.minijuegos.ascensor;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Fondo {
	private Bitmap fondo;
	private GameView game;
	
	public Fondo(Bitmap bitmap,GameView game){
		this.game=game;
		this.fondo=bitmap;
	}
	
	public void onDraw(Canvas canvas,int i){
		canvas.drawBitmap(fondo,0,i,null);
	}
}

