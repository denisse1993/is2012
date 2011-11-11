package com.Cinnamon.Is;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ascensor {
	private static final int BMP_ROWS = 1;
	private static final int BMP_COLUMNS = 3;
	private Bitmap bmp;
	private int x;
	private int y;
	private int height;
	private int width;
	private GameView game;
	private int currentFrame;
	public int enAscensor;
	private int secuencia;
	private List<Sprite> listaSpritesAscensor;


public Ascensor(GameView View, Bitmap bmp) {
	   //Random rnd = new Random();
	   this.x = 0;
	   this.y = 390;    
	   this.game=View;
	   this.bmp=bmp;
	   this.width = (bmp.getWidth() / BMP_COLUMNS);
	   this.height = (bmp.getHeight() / BMP_ROWS);
       this.currentFrame = 2; 
       this.secuencia = 0; 
       this.listaSpritesAscensor = new LinkedList<Sprite>();
		
		
  }

  private void update() {
	/*  if (game.getMovimiento() == true){
		  currentFrame = 0;
	  }
	  List <Sprite> sprites = game.getListaSprites();
	  for (Sprite sprite: sprites){
		  if ((game.getEnAscensor()==2)&&(sprite.getEnAscensor() == true)&& (secuencia == 0)){
			  currentFrame = ( (++currentFrame) % BMP_COLUMNS); 
			  if (currentFrame == 2){
				  secuencia=1;
			  }
		  }
		  
		  else if ((sprite.getEnAscensor() == true)&& (secuencia ==0)){
			  currentFrame = 0;
		  }
		  
	  }*/
	  if (listaSpritesAscensor.size() == 2){
		  
	  }
 	 	//currentFrame = ( (++currentFrame) % BMP_COLUMNS); //alterna imagen
  }
 
  public void onDraw(Canvas canvas) {
        update();
        
        int srcX = currentFrame * width;
        int srcY = 0;
        
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(game.getWidth()-this.width-20, y, game.getWidth()-20, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
  }
  public void addListaAscensor (Sprite spr){
	  listaSpritesAscensor.add(spr);
  }
}