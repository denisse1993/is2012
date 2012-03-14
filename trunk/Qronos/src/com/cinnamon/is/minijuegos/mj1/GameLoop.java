package com.cinnamon.is.minijuegos.mj1;

import com.cinnamon.is.R;

import android.graphics.Canvas;

public class GameLoop extends Thread {
	static final long FPS = 10;
	private GameView view;
	private boolean running;

	public GameLoop(GameView view) {
		this.view = view;
	}

	public void run() {
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		running = true;
		Canvas c = null;
		int crearMarciano = 10;
		int crearMarcianolili = 43;
		int crearMarcianoOgro = 97;
		int velocidad =100;
		startTime = System.currentTimeMillis();
		while (running) {
			crearMarciano--;
			crearMarcianolili--;
			crearMarcianoOgro--;
			velocidad--;
			try {
				if (this.view.getNumVidas()==0){
					//view.onDraw2(c);
					running =false;
				}
				if(crearMarciano==0){
					crearMarciano=10;
					view.crearMarciano(R.drawable.marcianito,1);
				}
				if(crearMarcianolili==0){
					crearMarcianolili=33;
					view.crearMarciano(R.drawable.marcianolili,2);
				}
				if(crearMarcianoOgro==0){
					crearMarcianoOgro=57;
					view.crearMarciano(R.drawable.marcianoogro,3);
				}	
				if(velocidad==0){
					velocidad=100;
					this.view.acelerar();
					
				}	
				c = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					this.view.onDraw(c);
				}
			} finally {
				if (c != null) {
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try {
				if (sleepTime > 0)
					sleep(sleepTime + 100);
				else
					sleep(100);
			} catch (Exception e) {
			}
		}
	}

}
