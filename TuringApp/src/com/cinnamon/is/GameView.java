package com.cinnamon.is;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

	private Bitmap bmp;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	Sprite tablero;
	int numReinas=0;
	private boolean inicio=false;


	public GameView(Context context) {
		
	      super(context);
	      gameLoopThread = new GameLoopThread(this);
	      holder = getHolder();
	      holder.addCallback(new SurfaceHolder.Callback() {

	            public void surfaceDestroyed(SurfaceHolder holder) {
	                    
	            	boolean retry = true;
	                gameLoopThread.setRunning(false);
	                     while (retry) {	                       
	                                  try {
	                                        gameLoopThread.join();
	                                        retry = false;

	                                  } catch (InterruptedException e) {
	                                  }
	                           }
	                    }
 
	            public void surfaceCreated(SurfaceHolder holder) {

	                           gameLoopThread.setRunning(true);
	                           gameLoopThread.start();
	                    }
	                  
	             public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {

	                    }

	             });

	       }
        
  private Sprite createSprite(int resouce,float x,float y) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this,bmp,x,y);
  }
  protected void onDraw(Canvas canvas) {
	  
	  	tablero=createSprite(R.drawable.tablero8reinas,0,0);
	  	tablero.onDraw(canvas);
        //canvas.drawColor(Color.BLACK);
        if(inicio){
        	for(int i=0;i<numReinas;i++){
        
        	sprites.get(i).onDraw(canvas);
        }
  }
  }
  

  public boolean onTouchEvent(MotionEvent event) {
	  	
	  	if(numReinas<=7){
	  		inicio=true;
	  		float xX = event.getX();
	  		float yY = event.getY();
	  		sprites.add(createSprite(R.drawable.reina,xX,yY));
	  		numReinas++;
	  	}
	  	/*
	  	for (int i = sprites.size()-1; i >= 0; i--) {
               Sprite sprite = sprites.get(i);
               if (sprite.isCollition(event.getX(),event.getY())) {
                    //si pinchamos y ahi un sprite se mete aqui  
            	   	sprites.remove(sprite);// queremos lo contrario que aparezca la reina
               }
        }*/
        return super.onTouchEvent(event);
  }
}