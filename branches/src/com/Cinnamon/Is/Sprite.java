package com.Cinnamon.Is;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
 
public class Sprite {
	   private String id;
	   private static final int BMP_ROWS = 4;
	   private static final int BMP_COLUMNS = 3;
       private int x; 
       private int y; 
       //private int xSpeed = 5;
       private GameView gameView;
       private Bitmap bmp;
	   private int sentidoX;
	   private int sentidoY;
	   private int width;
	   private int height;
	   private int currentFrame;
	   private Rect rect_char;
	   private Rect rect_png;
	   private boolean moveElevator;
	   private boolean enAscensor;
      
       public Sprite(GameView gameView, Bitmap bmp, int x1,String id) {
    	   Random rnd = new Random();
    	   this.x = x1; 
    	   this.y = 400;
    	   this.id = id;
    	   //this.y = Math.abs(rnd.nextInt()% gameView.getHeight()); 
    	   
    	     
    	   this.enAscensor=false;
    	   this.gameView=gameView;
           this.bmp=bmp;
           this.sentidoX = 1;
           this.sentidoY= 1;
           this.width = (bmp.getWidth() / BMP_COLUMNS);
           this.height = (bmp.getHeight() / BMP_ROWS);
             
             
             
       }
       public boolean getEnAscensor(){
    	   return this.enAscensor;
       }
       
       private void update() {
    	   
    	    /*if (( x + (bmp.getWidth()/ BMP_COLUMNS)) > gameView.getWidth()){
   				sentidoX = -1;
       		}
   			else if (x < 0){
   				sentidoX = 1;
   			}
   			if ((y+ (bmp.getHeight())/ BMP_ROWS) > gameView.getHeight()){
   				sentidoY = -1;
   			}
   			if (y < 0){
   				sentidoY=1;
   			}
   			if (sentidoX == 1){
   				this.x=this.x+5;
   				
   		    }else{
   			   this.x=this.x-5;
   			  
   		    }
   			if(sentidoY == 1){
   				y=y+5;
   			}
   			else {
   				y=y-5;
       		}*/
    	currentFrame = ( (++currentFrame) % BMP_COLUMNS); //alterna imagen
       }
      
       public void onDraw(Canvas canvas) {
             update();
             int currentX = (currentFrame*this.width);
             int currentY=0;
             if ((moveElevator == true) && (this.x < (gameView.getWidth()- 150))){
            	 currentY = 2*height;
            	 this.x = this.x+10;
            	 
             }
             else{
            	 if (this.x >= (gameView.getWidth()- 150) ){
            		 this.enAscensor = true;
            		 gameView.setEnAscensor(gameView.getEnAscensor()+1);
            	 }
            	 
            	 currentY = 0;
            	 
      	     }
            /* if (sentidoX == 1){
            	 currentY = 2*height;
             }
             if (sentidoX == -1){
            	 currentY = height;
             }*/
             rect_char = new Rect(currentX,currentY,currentX+width,currentY+height);
             rect_png = new Rect (x,y,x+width,y+width);
             canvas.drawBitmap(bmp, rect_char , rect_png, null);
       }

	public boolean isClick(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
		
	}

	public void toElevator() {
		moveElevator= true;
		
	}
}  