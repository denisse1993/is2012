//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.minijuegos.ascensor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;



public class Ascensor {
	private static final int BMP_ROWS = 1;
	private static final int BMP_COLUMNS = 4;
	private Bitmap bmp;
	private int x;
	private int y;
	private int height;
	private int width;
	private GameView gameView;
	private int currentFrame;
	public int enAscensor;
	private int secuencia;
	private boolean onMovementUp;
	private boolean onMovementDown;
	

public Ascensor(GameView View, Bitmap bmp) {
	   this.x = 0;
	   this.y = 640;    
	   this.gameView=View;
	   this.bmp=bmp;
	   this.width = (bmp.getWidth() / BMP_COLUMNS);
	   this.height = (bmp.getHeight() / BMP_ROWS);
       this.currentFrame = 0; 
       this.secuencia = 0; 
       
		
		
  }

  private void update() {
	  
	  
	  if(gameView.getMoving()==true)	
 	 	currentFrame = 3;
	  else{
		  currentFrame = 0;
	  }
  }
 
  private void abreAscensor() {
	currentFrame = 2;
	
}

public void onDraw(Canvas canvas) {
        update();
        
        int srcX = currentFrame * width;
        int srcY = 0;
        if((onMovementUp == true) && this.y > 260){
    	 	this.y = this.y -10;
    	 
	        if((this.y == 260)&&(onMovementUp == true)){
	    		 onMovementUp = false;
	        } 
        }
        if((onMovementDown == true) && this.y < 640){
     	 	this.y = this.y +10;
     	 
	         if((this.y == 640)&&(onMovementDown == true)){
	     		 onMovementDown = false;
	         }	 
         }
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(gameView.getWidth()-this.width-40, y, gameView.getWidth()-40, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
  }
  
public void onMovementUp() {
	onMovementUp = true;
	
}
public void onMovementDown() {
	onMovementDown = true;
	
}

}