package com.cinnamon.is.minijuegos.mj2;

import android.graphics.Canvas;

public class GameLoopTopos extends Thread {
	static final long FPS = 15;
	private GameView view;
	private boolean running = true;
	private boolean paused = false;

	public GameLoopTopos(GameView view) {
		this.view = view;
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

	// con .start() llama a este metodo
	public void run() {
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		running = true;
		Canvas c = null;

		startTime = System.currentTimeMillis();
		
		while (running) {
			checkPaused();
			if (this.view.finJuego) {
				running = false;
			}
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
				if (sleepTime > 0)
					sleep(sleepTime + 100);
				else
					sleep(100);
			} catch (Exception e) {
			}
		}
	}

}
