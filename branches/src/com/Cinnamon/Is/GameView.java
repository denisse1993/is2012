package com.Cinnamon.Is;


import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class GameView extends SurfaceView{
	private GameLoop loop;
	private SurfaceHolder holder;
	private List<Sprite> listaSprites= new LinkedList<Sprite>();
	private Ascensor ascensor;
	private int xInicio;
	private boolean movimiento;
	private int enAscensor; //lleva la cuenta de cuantos sprites hay en el ascensor
	
	
	public GameView(Context context) {
		super(context);
		loop = new GameLoop(this);
		holder = getHolder();
		xInicio = 0;
		movimiento = false;
		enAscensor = 0;
		holder.addCallback(new Callback() {
			

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				creaSprites(R.drawable.gobierno,"Nazi");
				creaSprites(R.drawable.novio,"Novio");
				creaSprites(R.drawable.turing,"Turing");
				creaAscensor(R.drawable.ascen);
				loop.setRunning(true);
				loop.start();
				
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	public boolean getMovimiento(){
		return this.movimiento;
	}
	
	public void setMovimiento(boolean b){
		this.movimiento = b;
	}
	
	public List<Sprite> getListaSprites(){
		return this.listaSprites;
	}
	
	public int getEnAscensor(){
		return this.enAscensor;
	}
	public void setEnAscensor(int num){
		this.enAscensor = num;
	}
	private void creaSprites(int resource,String id) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),resource);
		Sprite sprite = new Sprite(this, bmp , xInicio,id);
		listaSprites.add(sprite);
		xInicio = xInicio+50;
		
	}
	
	private void creaAscensor(int resource){
		Bitmap bmpA = BitmapFactory.decodeResource(getResources(), resource);
		this.ascensor = new Ascensor(this, bmpA);
	}
	protected void onDraw (Canvas canvas){
		canvas.drawColor(Color.BLUE);
		for (Sprite sprite:listaSprites){
			sprite.onDraw(canvas);
		}
		if (ascensor != null){
			ascensor.onDraw(canvas);
		}
		
	}
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
          for (int i = listaSprites.size()-1; i >= 0; i--) {
                 Sprite sprite = listaSprites.get(i);
                 if (sprite.isClick(event.getX(),event.getY())) {
                        sprite.toElevator();
                        ascensor.addListaAscensor(sprite);
                 }
          }
          return super.onTouchEvent(event);
    }
		
	
}
