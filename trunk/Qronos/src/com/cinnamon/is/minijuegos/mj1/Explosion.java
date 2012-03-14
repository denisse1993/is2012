package com.cinnamon.is.minijuegos.mj1;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion {
	private GameView view;
	private Bitmap bmp;
	private int x;
	private int y;
	private int loops;
	//Getters y setters
	
	public int getLoops(){
		return loops;
	}
	
	public Explosion(Bitmap bmp,int x,int y){
		this.bmp=bmp;
		this.x =x;
		this.y=y;
		this.loops=8;
	}
	public void onDraw(Canvas canvas){
		canvas.drawBitmap(this.bmp,this.x,this.y,null);
	}
	public void restarLoops(){
		this.loops--;
	}
}
