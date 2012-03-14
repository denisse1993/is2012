package com.cinnamon.is.minijuegos.mj1;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Cupula {
	private int x;
	private int y;
	private GameView view;
	private Bitmap bmp;
	//Getters y Setters
	public int getWidth(){
		return bmp.getWidth();
	}
	public int getHeight(){
		return bmp.getHeight();
	}
	
	
	public Cupula(Bitmap bmp,int x,int y){
		this.x =x;
		this.y=y;
		this.bmp = bmp;
	}
	public void onDraw(Canvas canvas,int x,int y){
		canvas.drawBitmap(this.bmp,x,y, null);
	}
}
