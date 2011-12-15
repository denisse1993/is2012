package com.cinnamon.is.minijuegos.reinas;

import java.util.Iterator;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Intents;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.game.Jugador;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class ReinasMJ extends Minijuego implements OnTouchListener {

	MyBringBackSurface ourSurfaceView;
	float x, y;
	Tablero t;
	Canvas canvas;
	float celda;
	Iterator<Reina> itReinas;
	boolean actionUp = false;
	boolean muevo = false;
	boolean inicio;
	Reina rMuevo = null;
	Bitmap reinaImage = null;
	boolean escorrecto = false;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		ourSurfaceView = new MyBringBackSurface(this);
		ourSurfaceView.setOnTouchListener(this);
		x = 0;
		y = 0;
		inicio = true;
		t = new Tablero();
		setContentView(ourSurfaceView);

		// Herencia Minijuego
		startTime();
		setNombre(Intents.Comun.minijuegos[2]);
		setObjeto(1);
		setFase(3);
		setSuperado(false);
		// jugador
		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);

	}

	@Override
	protected void onPause() {

		super.onPause();
		ourSurfaceView.pause();
	}

	@Override
	protected void onResume() {

		super.onResume();
		ourSurfaceView.resume();
	}

	@Override
	public void onBackPressed() {
		lanzaExitDialog();
	}

	public boolean onTouch(View v, MotionEvent event) {

		//
		/*
		 * switch(event.getAction()){ case MotionEvent.ACTION_DOWN: x =
		 * event.getX(); y = event.getY(); inicio=false; break; case
		 * MotionEvent.ACTION_UP: x = event.getX(); y = event.getY();
		 * if(muevoReina){ actionUp=true; muevoReina=false; }
		 * 
		 * break;
		 */
		if (muevo) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				muevo = false;
				break;
			}
		}
		x = event.getX();
		y = event.getY();
		// break;

		// }
		return true;
	}

	int queColEs() {
		int coorX;
		celda = canvas.getWidth() / 8;
		coorX = (int) ((x - (x % celda)) / celda);
		return coorX;
	}

	int queFilaEs() {
		int coorY;
		celda = canvas.getWidth() / 8;
		coorY = (int) ((y - (y % celda)) / celda);
		return coorY;
	}

	// //////////////
	public class MyBringBackSurface extends SurfaceView implements Runnable {

		SurfaceHolder ourHolder;
		Thread ourThread = null;
		boolean isRunning = false;

		// boolean escorrecto = false;
		// Button comprobar;

		public MyBringBackSurface(Context context) {
			super(context);
			ourHolder = getHolder();

			// setContentView(R.layout.gfxsurface);
			// this.comprobar = (Button)this.findViewById(R.id.bComprobar);
			/*
			 * this.comprobar.setOnClickListener(new OnClickListener() { public
			 * void onClick(View v) {
			 * 
			 * 
			 * } });
			 */

		}

		public void pause() {
			isRunning = false;
			while (true) {
				try {
					ourThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			ourThread = null;
		}

		public void resume() {
			isRunning = true;
			ourThread = new Thread(this);
			ourThread.start();
		}

		@Override
		public void run() {

			while (isRunning) {
				if (!ourHolder.getSurface().isValid())
					continue;
				canvas = ourHolder.lockCanvas();
				// pinto el tablero
				Bitmap table = BitmapFactory.decodeResource(getResources(),
						R.drawable.tablero8reinas);
				Rect rectangulo = new Rect(0, 0, canvas.getWidth(),
						canvas.getWidth());
				canvas.drawBitmap(table, null, rectangulo, null);
				Bitmap boton = BitmapFactory.decodeResource(getResources(),
						R.drawable.boton);
				Rect rect2 = new Rect(50, canvas.getWidth() + 50, 200,
						canvas.getWidth() + 150);
				canvas.drawBitmap(boton, null, rect2, null);
				if (escorrecto) {
					Bitmap tick = BitmapFactory.decodeResource(getResources(),
							R.drawable.tick);
					Rect rect3 = new Rect(300, canvas.getWidth() + 50, 350,
							canvas.getWidth() + 150);
					canvas.drawBitmap(tick, null, rect3, null);
				} else {
					Bitmap error = BitmapFactory.decodeResource(getResources(),
							R.drawable.error);
					Rect rect4 = new Rect(300, canvas.getWidth() + 50, 350,
							canvas.getWidth() + 150);
					canvas.drawBitmap(error, null, rect4, null);
				}
				//

				reinaImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.reina);

				int f;
				int c;
				Reina r;
				if ((x >= 50 && x <= 150)
						&& (y >= canvas.getWidth() + 50 && y <= canvas
								.getWidth() + 150) && t.getnumReinas() == 8) {

					escorrecto = comprobar();
					// prueba mia dejar las reinas si el jugador falla
				} else {
					if (muevo) {
						canvas.drawBitmap(rMuevo.bmp,
								x - (reinaImage.getWidth() / 2), y
										- (reinaImage.getHeight() / 2), null);
					} else {
						// if(x!= 0 && y!=0){ // cambiar ah si (f>=0 && f<8 ||
						// c>=0 $$ c<8)

						f = queFilaEs();
						c = queColEs();
						if ((x != 0 && y != 0) && (f >= 0 && f < 8)
								&& (c >= 0 && c < 8)) {
							if (!t.hayReina(f, c)) { // como no hay reina la
														// aniado
								if (t.getnumReinas() != 8) {
									r = new Reina(reinaImage, f, c);
									t.addReina(r, f, c);
									// canvas.drawBitmap(reinaImage, c*celda+8 ,
									// f*celda+10 , null);
								}
								x = 0;
								y = 0;
							} else {
								rMuevo = t.dameReina(f, c);
								t.removeReina(rMuevo, f, c);
								muevo = true;
								//
							}

						}
					}
				}

				if (!escorrecto) {// prueba
					// pinto todas las reinas que tenga en mi Set
					itReinas = t.getSetReinas().iterator();
					// Reina r;
					while (itReinas.hasNext()) {
						r = itReinas.next();
						f = r.dameFila();
						c = r.dameCol();
						// Bitmap test =
						// BitmapFactory.decodeResource(getResources(),
						// R.drawable.reina);
						canvas.drawBitmap(r.bmp, c * celda + 8, f * celda + 10,
								null);

					}
				}// minijuego resuelto
				else {
					superado = true;
					finalizar();
				}

				ourHolder.unlockCanvasAndPost(canvas);
			}

		}

		public boolean comprobar() {

			int contHztal = 0;
			boolean vector[] = new boolean[8];
			for (int k = 0; k < 8; k++)
				vector[k] = false;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (t.dameCelda(i, j)) {
						contHztal++;
						if (!compruebaDiagonal(i, j)) {
							return false;
						}
						if (!vector[j]) { // si no habia una reina en esa
											// columna, lo pongo a true
							vector[j] = true;
						} else { // si ya habia una reina en esa columna j
									// devuelvo false
							return false;
						}
					}
				}
				if (contHztal != 1) { // si hay mas de una reina en la fila
										// devuelvo false
					return false;
				}
				contHztal = 0; // reinicio el contador para recorrer una nueva
								// fila
			}
			return true;// si no hemos encontrado por el camino un return false,
						// es porq es solucion

		}

		public boolean compruebaDiagonal(int f, int c) {

			int i = f;
			int j = c;
			// diagonal izquierda superior
			while (i > 0 && j > 0) {
				if (t.dameCelda(i - 1, j - 1)) {
					return false;
				} else {
					i--;
					j--;
				}
			}
			i = f;
			j = c;
			// diagonal izquierda inferior
			while (i < 7 && j > 0) {
				if (t.dameCelda(i + 1, j - 1)) {
					return false;
				} else {
					i++;
					j--;
				}
			}
			i = f;
			j = c;
			// diagonal derecha superior
			while (i > 0 && j < 7) {
				if (t.dameCelda(i - 1, j + 1)) {
					return false;
				} else {
					i--;
					j++;
				}
			}
			i = f;
			j = c;
			// diagonal derecha inferior
			while (i < 7 && j < 7) {
				if (t.dameCelda(i + 1, j + 1)) {
					return false;
				} else {
					j++;
					i++;
				}
			}

			return true;
		}

	}
}