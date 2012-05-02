package com.cinnamon.is.minijuegos.mj1;

import com.cinnamon.is.R;

import android.graphics.Canvas;

public class GameLoop extends Thread {
	static final long FPS = 10;
	private final GameView view;
	private boolean running;
	private final StartingMarcianos activity;

	public GameLoop(final GameView view, final StartingMarcianos padre) {
		this.view = view;
		this.activity = padre;
	}

	public void setRunning(final boolean b) {
		this.running = b;
	}

	public void setRunning() {
		this.running = false;
	}

	@Override
	public void run() {
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		this.running = true;
		Canvas c = null;
		int crearMarciano = 10;
		int crearMarcianolili = 43;
		int crearMarcianoOgro = 97;
		int velocidad = 100;
		int bomba = 300;
		startTime = System.currentTimeMillis();
		while (this.running) {
			crearMarciano--;
			crearMarcianolili--;
			crearMarcianoOgro--;
			velocidad--;
			if (this.view.getBomba().getEstado() == true) {
				bomba--;
			}
			try {
				if (this.view.getNumVidas() <= 0) {
					this.view.musicaOff();
					this.running = false;
					this.activity.onStop();
					this.activity.finalizar(true);

				}
				if (bomba == 0) {

					bomba = 300;
					this.view.crearBomba(R.drawable.bomba1, 120, 0);
				}
				if (this.view.getNumVidas() == 2) {
					this.view.crearCupula(R.drawable.cupularota1);
				}
				if (this.view.getNumVidas() == 1) {
					this.view.crearCupula(R.drawable.cupularota2);
				}
				if (crearMarciano == 0) {
					crearMarciano = 10;
					this.view.crearMarciano(R.drawable.marcianoprueba1, 1);
				}
				if (crearMarcianolili == 0) {
					crearMarcianolili = 33;
					this.view.crearMarciano(R.drawable.marcianolili, 2);
				}
				if (crearMarcianoOgro == 0) {
					crearMarcianoOgro = 57;
					this.view.crearMarciano(R.drawable.marcianoogro, 3);
				}
				if (velocidad == 0) {
					velocidad = 100;
					this.view.acelerar();

				}
				c = this.view.getHolder().lockCanvas();
				synchronized (this.view.getHolder()) {
					this.view.onDraw(c);
				}
			} finally {
				if (c != null) {
					this.view.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try {
				if (sleepTime > 0) {
					sleep(sleepTime + 100);
				} else {
					sleep(100);
				}
			} catch (Exception e) {
				//
			}
		}
	}

}
