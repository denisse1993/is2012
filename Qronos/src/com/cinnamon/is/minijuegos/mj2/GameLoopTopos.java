package com.cinnamon.is.minijuegos.mj2;

import android.app.Activity;
import android.graphics.Canvas;

public class GameLoopTopos extends Thread {
	static final long FPS = 15;
	private final GameView view;
	private boolean running = true;
	private final ToposMJ activity;

	public GameLoopTopos(final GameView view, final ToposMJ a) {
		this.view = view;
		activity = a;
	}

	// con .start() llama a este metodo
	@Override
	public void run() {
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		running = true;
		Canvas c = null;

		startTime = System.currentTimeMillis();
		while (running) {
			if (this.view.finJuego) {
				running = false;

				activity.finalizar(true);
				activity.onStop();
			} else {
				try {

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

	public void setRunning(final boolean b) {
		running = b;

	}

}
