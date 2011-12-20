package com.cinnamon.is.minijuegos.end;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.Toast;

public class Objeto{
	private int id ;
	private String ids;
	private int posX;
	private int posY;
	private int posX2;
	private int posY2;
	private Bitmap bmp;
	private GameView game;
	private String descripcion;
	private boolean usado; // para ver si ha sido usado el escudo
	private boolean yaSeleccionado; //para evitar que se vuelvan a añadir a la mochila

	
	public Objeto(GameView game, Bitmap bmp, int x1, int x2, int y1, int y2, int id, String string,String descrip) {
		this.bmp = bmp;
		this.posX = x1;
		this.posX2 = x2;
		this.posY = y1;
		this.posY2 = y2;
		this.game = game;
		this.id = id;
		this.ids = string;
		this.descripcion=descrip;
		yaSeleccionado = false;
	}
	public Objeto(GameView game2, int x1, int x2, int y1, int y2,int id, String string,String descripcion) {
		this.posX = x1;
		this.posX2 = x2;
		this.posY = y1;
		this.posY2 = y2;
		this.game = game2;
		this.id = id;
		this.ids = string;
		this.descripcion = descripcion;
		yaSeleccionado = false;
	}
	
	public void onDraw(Canvas canvas){
		if (bmp !=null){
			canvas.drawBitmap(bmp, posX, posY,null);
		}
	}
	public boolean isClick(float x2, float y2){
		
		if (x2 > posX && x2 < posX2 && y2 > posY && y2 < posY2){	
			final Objeto o = this;
			game.getHandler().post(new Runnable(){
			    public void run(){
			    	if (!o.getSeleccionado()){
			    		Toast.makeText(game.getContext(), "Has encontrado un "+ o.getIds() + "!! Ha sido añadida a" +
			    				" tu mochila ", Toast.LENGTH_LONG).show();
			    		o.setSeleccionado(true);
			    		game.addMochila(o);
			    	}
			    }
			});
			/*if (!yaSeleccionado){
				game.addMochila(this);
				yaSeleccionado = true;*/
				return true;
			//}
		}
		//bmp.recycle();
		return false;
	}
	public String getIds() {
		
		return this.ids;
	}
	public String getDescripcion() {
		
		return this.descripcion;
	}
	
	public int getId(){
		return this.id;
	}
	public boolean getUsado(){
		return this.usado;
	}
	public void setUsado(boolean b){
		usado = b;
	}
	public boolean getSeleccionado(){
		return this.yaSeleccionado;
	}
	public void setSeleccionado(boolean b){
		this.yaSeleccionado = b;
	}
}

