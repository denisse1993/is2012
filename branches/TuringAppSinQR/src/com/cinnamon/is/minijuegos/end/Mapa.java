package com.cinnamon.is.minijuegos.end;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Mapa {
	private GameView game;
	private int posX;
	private int posY;
	
	private Bitmap bmp;
	private Bitmap bmpf;
	
	private String id;
	private int mapaActual; //1 arriba 2 abajo 3derecha
	
	private int cuadrado1_y1;
	private int cuadrado1_y2;
	private int cuadrado1_x1;
	private int cuadrado1_x2;
	
	private int cuadrado2_y1;
	private int cuadrado2_y2;
	private int cuadrado2_x1;
	private int cuadrado2_x2;
	
	private int cuadrado3_y1;
	private int cuadrado3_y2;
	private int cuadrado3_x1;
	private int cuadrado3_x2;
	
	private int flechaX;
	private int flechaY;
	
	public Mapa( Bitmap bmp,GameView game){
		this.game = game;
		this.bmp = bmp;
		this.mapaActual = 1;
		posX = 3*(this.game.getWidth()/4);
		posY = 0;
		
		 cuadrado1_y1= ((bmp.getHeight()*14)/100); //14%
		 cuadrado1_y2= ((bmp.getHeight()*55)/100);
		 cuadrado1_x1= posX+((bmp.getWidth()*11)/100);
		 cuadrado1_x2= posX+((bmp.getWidth()*46)/100);
		
		 cuadrado2_y1= ((bmp.getHeight()*58)/100); //14%
		 cuadrado2_y2= ((bmp.getHeight()*84)/100);
		 cuadrado2_x1= cuadrado1_x1;
		 cuadrado2_x2= cuadrado1_x2;
		
		 cuadrado3_y1= cuadrado2_y1; //14%
		 cuadrado3_y2= cuadrado2_y2;
		 cuadrado3_x1= posX+((bmp.getWidth()*49)/100);
		 cuadrado3_x2= posX+((bmp.getWidth()*91)/100);
		 ajustaFlecha();
	}
	
	public void onDraw (Canvas canvas){
		canvas.drawBitmap(bmp, posX, posY,null);
		ajustaFlecha();
		canvas.drawBitmap(bmpf, flechaX, flechaY,null);
		
	}
	
	public boolean isClick(float x2, float y2) {
		int cual=0;
		if (x2 > posX && x2 < posX + bmp.getWidth() && y2 > posY && y2 < posY + bmp.getHeight()){
			 cual = queCuadrado(x2,y2);
		}
		if (cual == 0) return false;
		else {mapaActual = cual;
			return true;}
	}

	private int queCuadrado(float clickX, float clickY) {
		
		if  (clickX > cuadrado1_x1 && clickX < cuadrado1_x2 && clickY > cuadrado1_y1 && clickY < cuadrado1_y2){
			return 1;
		}
		if  (clickX > cuadrado2_x1 && clickX < cuadrado2_x2 && clickY > cuadrado2_y1 && clickY < cuadrado2_y2){
			return 2;
		}
		if  (clickX > cuadrado3_x1 && clickX < cuadrado3_x2 && clickY > cuadrado3_y1 && clickY < cuadrado3_y2){
			return 3;
		}
		else return 0;
	}
	public void ajustaFlecha(){
		if (mapaActual ==1){
			
			flechaX = ((cuadrado1_x1+cuadrado1_x2)/2)-10;
			flechaY = ((cuadrado1_y1+cuadrado1_y2)/2)-10;
		}
		if (mapaActual ==2){
			flechaX = ((cuadrado2_x1+cuadrado2_x2)/2)-10;
			flechaY = ((cuadrado2_y1+cuadrado2_y2)/2)-10;
		}
		if (mapaActual ==3){
			flechaX = ((cuadrado3_x1+cuadrado3_x2)/2)-10;
			flechaY = ((cuadrado3_y1+cuadrado3_y2)/2)-10;
		}
	}

	public int getMiniMap() {
		// TODO Auto-generated method stub
		return this.mapaActual;
	}

	public void setFlecha(Bitmap flecha) {
		this.bmpf = flecha;
		
	}
	public int getWidthFlecha(){
		return ( ((bmp.getWidth()*11)/100)+(bmp.getWidth()*46)/100 )/4;
	}
	public int getHeightFlecha(){
		return (cuadrado1_y1+cuadrado1_y2)/2;
		
	}
}
