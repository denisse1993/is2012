package com.cinnamon.is.minijuegos.ascensor;

import android.graphics.Canvas;

public class GameLoop extends Thread {
	static final long FPS = 10;
	private GameView view;
	private boolean running = false;

	public GameLoop(GameView view) {
		this.view = view;
	}

	public void setRunning(boolean run) {
		running = run;
	}

	@Override
	// con .start() llama a este metodo
	public void run() {
		long ticksPS = 100 / FPS;
		long startTime;
		long sleepTime;
		while (running) {
			while (view.getFinJuego()) {// Loop para reiniciar el juego
				Canvas c = null;
				startTime = System.currentTimeMillis();

				try {
					c = view.getHolder().lockCanvas();
					synchronized (view.getHolder()) {
						view.onDraw2(c);
					}
				} finally {
					if (c != null) {
						view.getHolder().unlockCanvasAndPost(c);
					}
				}
				sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
				try {
					sleep(6000);
					view.setFInJUego(false);
					if (sleepTime > 0)
						sleep(sleepTime + 100);
					else
						sleep(100);
				} catch (Exception e) {
				}
			}
			// loop general
			Canvas c = null;
			startTime = System.currentTimeMillis();

			try {
				c = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					if(this.view.getWinner()==false)
						view.onDraw(c);
					else view.onDraw3(c);
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

