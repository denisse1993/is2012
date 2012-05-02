package com.cinnamon.is.minijuegos.mj1;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Marcianos {
	private GameView gameView;
	private Bitmap bmp;
	private int x;
	private int y;
	private int width;
	private int height;
	private int tipoMarciano;
	private int vidasMarciano;
	private int velocidad;
	//Getters y Setters
	public int getWidth(){
		return bmp.getWidth();
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getTipo(){
		return tipoMarciano;
	}
	public int getVidasMarciano(){
		return this.vidasMarciano;
	}
	
	public Marcianos(GameView gameView, int x, Bitmap bmp,int tipoMarciano,int velocidad) {
		this.x = x;
		this.y = 0;
		this.bmp = bmp;
		this.gameView = gameView;
		this.tipoMarciano=tipoMarciano;
		this.vidasMarciano=5;
		this.velocidad =velocidad;
		width = bmp.getWidth();
		height = bmp.getHeight();
	}

	public void onDraw(Canvas canvas) {
		y = y + velocidad;
		canvas.drawBitmap(this.bmp, this.x, this.y, null);
		if(y>=gameView.getHeight()-gameView.getHeightCupula()){//El marcinao llega donde no queremos y perdemos una vida(evidentemente ese marciano deja de existir)
			quitarVida();
			gameView.anadirExplosion(this.x,this.y);
			gameView.quitarMarciano(this);
		} 

	}

	public boolean isClick(float x, float y) {
		if (x >= this.x && x <= this.x + width && y >= this.y
				&& y <= this.y + height)
			return true;
		else
			return false;
	}
	public void quitarVida(){
		int vidas=gameView.getNumVidas();
		vidas--;
		gameView.setNumVidas(vidas);
		
	}
	public void quitarVidaMarciano(){
		this.vidasMarciano--;
	}

}
