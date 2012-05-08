package com.cinnamon.is.minijuegos.mj1;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Props;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.preference.PreferenceManager;

public class GameLoop extends Thread {
	static final long FPS = 10;
	private GameView view;
	private boolean running;
	private StartingMarcianos activity;
	private boolean paused = false;

	// private Object pauseLock = new Object();

	public GameLoop(GameView view, StartingMarcianos padre) {
		this.view = view;
		this.activity = padre;
	}

	public synchronized void stopLoop() {
		this.running = false;
		if (paused) {
			resumeLoop();
		}
	}

	public synchronized void pauseLoop() {
		if (paused) {
			return;
		}

		paused = true;
	}

	public synchronized void resumeLoop() {

		if (!paused) {
			return;
		}

		this.notify();
		paused = false;
	}

	private synchronized void checkPaused() {
		if (paused) {

			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		int velocidad = 100;
		int bomba = 300;
		startTime = System.currentTimeMillis();
		while (running) {
			crearMarciano--;
			crearMarcianolili--;
			crearMarcianoOgro--;
			velocidad--;

			checkPaused();

			if (this.view.getBomba().getEstado() == true) {
				bomba--;
			}
			try {
				if (this.view.getNumVidas() <= 0) {
					SharedPreferences getData = PreferenceManager
							.getDefaultSharedPreferences(activity
									.getApplicationContext());
					if (getData.getBoolean(Props.Comun.CB_SONIDO, true)) {
						this.view.musicaOff();
					}
					running = false;
					activity.onStop();
					activity.finalizar(true);

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
					view.crearMarciano(R.drawable.marcianoprueba1, 1);
				}
				if (crearMarcianolili == 0) {
					crearMarcianolili = 33;
					view.crearMarciano(R.drawable.marcianolili, 2);
				}
				if (crearMarcianoOgro == 0) {
					crearMarcianoOgro = 57;
					view.crearMarciano(R.drawable.marcianoogro, 3);
				}
				if (velocidad == 0) {
					velocidad = 100;
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
