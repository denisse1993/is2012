package com.cinnamon.is.minijuegos.reinas;

import java.util.Iterator;

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

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Intents;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.game.Jugador;

public class ReinasMJ extends Minijuego implements OnTouchListener {

	OurSurface ourSurfaceView;
	float x, y;
	Tablero t;
	Canvas canvas;
	float celda;
	Iterator<Reina> itReinas;
	boolean actionUp = false;
	boolean muevo;
	boolean inicio;
	Reina rMuevo;
	Bitmap reinaImage = null;
	// Bitmap reinaNegro=null;
	boolean escorrecto = false;
	boolean fin = false;
	boolean down;
	boolean muevoFin = false;
	
	private static final int DIALOG_MINIJUEGOS_RESULT = 0;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		ourSurfaceView = new OurSurface(this);
		ourSurfaceView.setOnTouchListener(this);
		x = 0;
		y = 0;
		inicio = true;
		down = false;
		muevo = false;
		rMuevo = null;
		t = new Tablero();

		setContentView(ourSurfaceView);

		// Pone valores del minijuego
		startTime();
		setNombre(Intents.Comun.minijuegos[2]);
		setObjeto(2);
		setFase(3);
		setSuperado(false);

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		
		
		//TODO Dialogo inicial
		//Lanza el Dialogo
	//	String textoDialog = "Hola",
				/*"Enhorabuena has desbloqueado el minijuego de las 8 reinas." +
				"/nPara conseguir el siguiente objeto necesitaras completar el siguiente tablero" +
				"en el que se colocan ocho reinas sin que se amenacen. Una reina amenaza a otra si" +
				" se encuentren en su misma fila, columna o diagonal." +
				"/nSuerte.",*/
		/*		title = "8 Reinas";
		int idIvDialog = R.drawable.tablero8reinas;
		
		Bundle dialogBundle = new Bundle();
		dialogBundle.putString("textoDialog", textoDialog);
		dialogBundle.putInt("idIvDialog", idIvDialog);
		//dialogBundle.putBoolean(Intents.Comun.superado, superado);
		dialogBundle.putString("title", title);
		showDialog(DIALOG_MINIJUEGOS_INIT,dialogBundle);*/

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

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			x = event.getX();
			y = event.getY();
			muevo = true;
			break;
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			down = true;
			break;
		case MotionEvent.ACTION_UP:
			x = event.getX();
			y = event.getY();
			if (muevo) {
				muevoFin = true;
				muevo = false;
			}
			break;
		}

		return true;
	}

	public void onBackPressed() {
		super.onBackPressed();
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
	public class OurSurface extends SurfaceView implements Runnable {

		SurfaceHolder ourHolder;
		Thread ourThread = null;
		boolean isRunning = false;

		public OurSurface(Context context) {
			super(context);
			ourHolder = getHolder();

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
						R.drawable.boton_check_off);
				int centraBoton = (canvas.getHeight() - canvas.getWidth()) / 2;
				// Rect rect2=new
				// Rect(20,canvas.getWidth()+centraBoton,boton.getWidth()+20,canvas.getWidth()+centraBoton+20);
				
				//TODO aqui he cambiado el boton
				Rect rect2 = new Rect(canvas.getWidth()/2-35, canvas.getHeight()*2/3 , 
						canvas.getWidth()/2+35,canvas.getHeight()*2/3 + 70);
				canvas.drawBitmap(boton, null, rect2, null);
				
				//TODO aqui creo q comprueban si es correcto y lanzariamos el dialog 
				if (escorrecto) {
					Bitmap boton_correct = BitmapFactory.decodeResource(getResources(),
							R.drawable.boton_verde);
					canvas.drawBitmap(boton_correct, null, rect2, null);
					//lanzar dialogo
				} else {
					Bitmap boton_on = BitmapFactory.decodeResource(getResources(),
							R.drawable.boton_check_on);
					canvas.drawBitmap(boton_on, null, rect2, null);
				}
				//

				reinaImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.reina);

				int f;
				int c;
				Reina r;
				f = queFilaEs();
				c = queColEs();
				if (down && (c >= 0 && c <= 7) && (f >= 0 && f <= 7)) {
					if (!t.hayReina(f, c)) {
						if (t.getnumReinas() < 8) {
							r = new Reina(reinaImage, f, c);
							t.addReina(r, f, c);
						}
					} else {
						rMuevo = t.dameReina(f, c);
						muevo = true;
					}
					down = false;
				}
				if (muevo && (c >= 0 && c <= 7) && (f >= 0 && f <= 7)) {
					if (rMuevo != null) {
						if (f != rMuevo.dameFila() || c != rMuevo.dameCol()) {
							t.cambiaFyC(rMuevo, f, c);
						}

					}

				}
				if (muevoFin) {
					rMuevo = null;
					muevoFin = false;
					muevo = false;
				}

				if ((x >= 50 && x <= 150)
						&& (!fin)
						&& (y >= canvas.getWidth() + 50 && y <= canvas
								.getWidth() + 150) && t.getnumReinas() == 8) {

					escorrecto = comprobar();
					//TODO aqui no se que coño hacen
					if (escorrecto) {
						/*lanzarAvisoMJ("Enhorabuena has conseguido superar el juego de las 8 Reinas"
								, "Juego Superado");*/
						fin = true;
					}

					// prueba mia dejar las reinas si el jugador falla
				}

				if (!escorrecto) {// prueba
					// pinto todas las reinas que tenga en mi Set
					itReinas = t.getSetReinas().iterator();
					// Reina r;
					while (itReinas.hasNext()) {
						r = itReinas.next();
						f = r.dameFila();
						c = r.dameCol();
						canvas.drawBitmap(r.bmp, c * celda + 15,
								f * celda + 10, null);

					}
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
					if (t.hayReina(i, j)) {
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
				if (t.hayReina(i - 1, j - 1)) {
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
				if (t.hayReina(i + 1, j - 1)) {
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
				if (t.hayReina(i - 1, j + 1)) {
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
				if (t.hayReina(i + 1, j + 1)) {
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