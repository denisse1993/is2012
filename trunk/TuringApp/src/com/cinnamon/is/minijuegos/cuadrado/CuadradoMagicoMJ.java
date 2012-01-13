package com.cinnamon.is.minijuegos.cuadrado;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.cinnamon.is.R;
import com.cinnamon.is.R.color;
import com.cinnamon.is.comun.Intents;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.game.Jugador;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

public class CuadradoMagicoMJ extends Minijuego implements
		OnTouchListener {

	// Aquí tiene que ir la única actividad que necesita la
	// la aplicación, el juego en sí.
	// Habrá que crear un tablero de 3x3 para insertar los números
	// Habrá que poner las imagenes en los bordes del cuadrado,
	// alineadas arriba y abajo.
	// las imagenes tendrán que tener un evento onClick o
	// cuando se arrastren.
	// Tendrán que quedarse fijas en el cuadrado que se pinche
	// con el ratón, o sea que tendrá que haber una variable
	// "seleccionado" que indique cuando hay que coger una imagen
	// y cuando hay que ponerla en uno de los huecos
	// las imágenes tienen que tener un valor asociado al número
	// correspondiente en la imagen, para después calcular el
	// número que suman las filas, las columnas y las diagonales
	// y si todas suman 15 entonces has ganado el juego,y se
	// lanzaría una nueva actividad.
	// La comprobación del resultado se hará después de pulsar un
	// botón "Comprobar" para ver si está bien hecho, sino se
	// muestra un mensaje de que algo falla.

	OurView v;
	Bitmap comprobar;
	Bitmap tick;
	Bitmap error;
	Bitmap cuadricula; // el tablero
	Bitmap numero1; // todos los números
	Bitmap numero2;
	Bitmap numero3;
	Bitmap numero4;
	Bitmap numero5;
	Bitmap numero6;
	Bitmap numero7;
	Bitmap numero8;
	Bitmap numero9;
	Bitmap restart;
	Bitmap proyecto1;
	Bitmap imagenSel;
	float x, y, ancho, alto, inicio, inicioc, espaciotitulo, espacioboton;
	Canvas c;
	int tabla[][]; // tabla para ir metiendo los números segun la fila y la
					// columna
	boolean correcto = false;
	float iniCuadroX, iniCuadroY, anchoNum, altoNum;
	float bordeExt = 11;
	float bordeIntHoriz = 5;
	float bordeIntVert = 4;
	int fila, col;
	boolean seleccionado = false;
	boolean comprobado = false;
	boolean reiniciada = false;
	boolean[] usados;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		v = new OurView(this);
		v.setOnTouchListener(this);

		// fondo
		proyecto1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.proyecto1);

		cuadricula = BitmapFactory.decodeResource(getResources(),
				R.drawable.cuadricula);

		numero1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.numero1);
		numero2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.numero2);
		numero3 = BitmapFactory.decodeResource(getResources(),
				R.drawable.numero3);
		numero4 = BitmapFactory.decodeResource(getResources(),
				R.drawable.numero4);
		numero5 = BitmapFactory.decodeResource(getResources(),
				R.drawable.numero5);
		numero6 = BitmapFactory.decodeResource(getResources(),
				R.drawable.numero6);
		numero7 = BitmapFactory.decodeResource(getResources(),
				R.drawable.numero7);
		numero8 = BitmapFactory.decodeResource(getResources(),
				R.drawable.numero8);
		numero9 = BitmapFactory.decodeResource(getResources(),
				R.drawable.numero9);
		restart = BitmapFactory.decodeResource(getResources(),
				R.drawable.restart);

		comprobar = BitmapFactory.decodeResource(getResources(),
				R.drawable.comprobar);
		tick = BitmapFactory.decodeResource(getResources(), R.drawable.tick);
		error = BitmapFactory.decodeResource(getResources(), R.drawable.error);

		tabla = new int[3][3]; // tabla de 3x3 para determinar la posicion de
								// los numeros en el canvas
		usados = new boolean[10];
		for (int i = 0; i < 10; i++)
			usados[i] = false;
		/*
		 * LinearLayout mylayout=new LinearLayout(this);
		 * 
		 * mylayout.addView(v); Drawable d =new BitmapDrawable(proyecto1);
		 * v.setBackgroundDrawable(d);
		 */
		x = y = 0;
		// setContentView(R.layout.main); //para ver la interfaz
		// setContentView(mylayout); //para ver el canvas
		setContentView(v);

		// Pone valores del minijuego
		startTime();
		setNombre(Intents.Comun.minijuegos[0]);
		setObjeto(4);
		setFase(1);
		setSuperado(false);

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		
		/*TODO	Dialog		
		//	String textoDialog = "Hola",
		/*"Enhorabuena has desbloqueado el minijuego de las 8 reinas." +
		"/nPara conseguir el siguiente objeto necesitaras completar el siguiente tablero" +
		"en el que se colocan ocho reinas sin que se amenacen. Una reina amenaza a otra si" +
		" se encuentren en su misma fila, columna o diagonal." +
		"/nSuerte.",*/
		/*title = "8 Reinas";
		int idIvDialog = R.drawable.tablero8reinas;

		Bundle dialogBundle = new Bundle();
		dialogBundle.putString("textoDialog", textoDialog);
		dialogBundle.putInt("idIvDialog", idIvDialog);
		//	dialogBundle.putBoolean(Intents.Comun.superado, superado);
		dialogBundle.putString("title", title);
		showDialog(DIALOG_MINIJUEGOS_INIT,dialogBundle);*/
		//lanzarAvisoMJ(textoDialog, title);
	}

	@Override
	protected void onPause() {
		super.onPause();
		v.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		v.resume();
	}

	public class OurView extends SurfaceView implements Runnable {

		Thread t = null;
		SurfaceHolder holder;
		boolean enEjecucion = false;

		public OurView(Context context) {
			super(context);
			holder = getHolder();
		}

		public void resume() {
			enEjecucion = true;
			t = new Thread(this);
			t.start();
		}

		public void pause() {
			enEjecucion = false;
			while (true) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}

		public void run() {
			while (enEjecucion == true) {
				if (!holder.getSurface().isValid()) {
					continue; // se sale del if y vuelve al principio
				}
				c = holder.lockCanvas(); // bloquea el canvas para pintarlo
				// aqui en medio se dibujan las cosas
				/*
				 * Calculamos la posicion de inicio de la cuadrícula, para
				 * moverse entre los puntos de inicio de las celdas sólo hay que
				 * sumar la anchura y altura de tantos números como celdas
				 * queramos avanzar + los bordes que haya que atravesar
				 */
				ancho = c.getWidth();
				alto = c.getHeight();
				inicio = (ancho - (numero1.getWidth() * 5)) / 2;
				espaciotitulo = c.getHeight() / 10;
				espacioboton = c.getHeight() / 5;

				anchoNum = numero1.getWidth();
				altoNum = numero1.getHeight();
				iniCuadroX = ((c.getWidth() - cuadricula.getWidth()) / 2)
						+ bordeExt;
				iniCuadroY = ((c.getHeight() - (cuadricula.getHeight() + espaciotitulo)) / 2)
						+ bordeExt;

				// FONDO LISO
				c.drawARGB(255, 0, 0, 0);

				// FONDO CON IMAGEN
				// int width = c.getWidth();
				// int height = c.getHeight();
				// Rect rect=new
				// Rect(0,(int)espaciotitulo+1,0,(int)(c.getHeight()-espacioboton-1));
				// c.drawBitmap(proyecto1, 0, 0, null);
				dibujaNumeros();
				// Colocamos los números en la parte superior centrados
				c.drawBitmap(restart, inicio + (restart.getWidth() * 4), alto
						- restart.getHeight() - espacioboton, null);

				inicioc = (c.getWidth() - comprobar.getWidth() - error
						.getWidth() * 2) / 2;
				c.drawBitmap(comprobar, inicioc, c.getHeight() - espacioboton
						+ espaciotitulo, null);

				// Ejemplo de llenado de la cuadricula con respecto a las nuevas
				// variables
				/*
				 * c.drawBitmap(numero1, iniCuadroX, iniCuadroY, null);
				 * c.drawBitmap(numero7, iniCuadroX+anchoNum+bordeIntHoriz,
				 * iniCuadroY, null); c.drawBitmap(numero6,
				 * iniCuadroX+anchoNum*2+bordeIntHoriz*2, iniCuadroY, null);
				 * c.drawBitmap(numero5, iniCuadroX,
				 * iniCuadroY+altoNum+bordeIntVert, null); c.drawBitmap(numero3,
				 * iniCuadroX, iniCuadroY+altoNum*2+bordeIntVert*2, null);
				 * c.drawBitmap(numero2, iniCuadroX+anchoNum+bordeIntHoriz,
				 * iniCuadroY+altoNum+bordeIntVert, null); c.drawBitmap(numero4,
				 * iniCuadroX+anchoNum*2+bordeIntHoriz*2,
				 * iniCuadroY+altoNum+bordeIntVert, null); c.drawBitmap(numero8,
				 * iniCuadroX+anchoNum+bordeIntHoriz,
				 * iniCuadroY+altoNum*2+bordeIntVert*2, null);
				 * c.drawBitmap(numero9, iniCuadroX+anchoNum*2+bordeIntHoriz*2,
				 * iniCuadroY+altoNum*2+bordeIntVert*2, null);
				 */

				if (imagenSel != null && imagenSel != restart
						&& imagenSel != comprobar && seleccionado) {
					c.drawBitmap(imagenSel, x - (anchoNum / 2), y
							- (altoNum / 2), null);
					// comprobado=false;
					reiniciada = false;
				}

				dibujaTabla();
				dibujaTick();
				try {
					t.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				holder.unlockCanvasAndPost(c); // lo desbloquea para mostrarlo
			}

		}

		private void dibujaTabla() {

			inicio = (ancho - (numero1.getWidth() * 5)) / 2;
			anchoNum = numero1.getWidth();
			altoNum = numero1.getHeight();

			// Aqui se va a dibujar la tabla conforme a la imagen que tenga
			if (tabla[0][0] != 0)
				c.drawBitmap(dameImagen(tabla[0][0]), iniCuadroX, iniCuadroY,
						null);
			if (tabla[0][1] != 0)
				c.drawBitmap(dameImagen(tabla[0][1]), iniCuadroX, iniCuadroY
						+ anchoNum + bordeIntHoriz, null);
			if (tabla[0][2] != 0)
				c.drawBitmap(dameImagen(tabla[0][2]), iniCuadroX, iniCuadroY
						+ anchoNum * 2 + bordeIntHoriz * 2, null);
			if (tabla[1][0] != 0)
				c.drawBitmap(dameImagen(tabla[1][0]), iniCuadroX + altoNum
						+ bordeIntVert, iniCuadroY, null);
			if (tabla[1][1] != 0)
				c.drawBitmap(dameImagen(tabla[1][1]), iniCuadroX + altoNum
						+ bordeIntVert, iniCuadroY + anchoNum + bordeIntHoriz,
						null);
			if (tabla[1][2] != 0)
				c.drawBitmap(dameImagen(tabla[1][2]), iniCuadroX + altoNum
						+ bordeIntVert, iniCuadroY + anchoNum * 2
						+ bordeIntHoriz * 2, null);
			if (tabla[2][0] != 0)
				c.drawBitmap(dameImagen(tabla[2][0]), iniCuadroX + altoNum * 2
						+ bordeIntVert * 2, iniCuadroY, null);
			if (tabla[2][1] != 0)
				c.drawBitmap(dameImagen(tabla[2][1]), iniCuadroX + altoNum * 2
						+ bordeIntVert * 2, iniCuadroY + anchoNum
						+ bordeIntHoriz, null);
			if (tabla[2][2] != 0)
				c.drawBitmap(dameImagen(tabla[2][2]), iniCuadroX + altoNum * 2
						+ bordeIntVert * 2, iniCuadroY + anchoNum * 2
						+ bordeIntHoriz * 2, null);
			dibujaNegros(usados);
			if (reiniciada == true)
				dibujaNumeros();
			if (comprobado)
				dibujaTick();
		}
	}
	
	//TODO FIN MINIJUEGO
	//Creo que aqui comprueban si esta bien y dibujan un tic...
	public void dibujaTick() {
		inicioc = (c.getWidth() - comprobar.getWidth() - error.getWidth() * 2) / 2;
		if (comprobado) {
			if (correcto) {
				superado=true;
				finalizar();
				// imagenSel=tick;
				//c.drawBitmap(tick,
					//	inicioc + comprobar.getWidth() + tick.getWidth(),
						//c.getHeight() - espacioboton + espaciotitulo, null);
			} else {
				// imagenSel=error;
				c.drawBitmap(error,
						inicioc + comprobar.getWidth() + tick.getWidth(),
						c.getHeight() - espacioboton + espaciotitulo, null);
			}
		}
	}

	public void dibujaNumeros() {

		c.drawBitmap(numero1, inicio, espaciotitulo, null);
		c.drawBitmap(numero2, inicio + numero2.getWidth(), espaciotitulo, null);
		c.drawBitmap(numero3, inicio + (numero3.getWidth() * 2), espaciotitulo,
				null);
		c.drawBitmap(numero4, inicio + (numero4.getWidth() * 3), espaciotitulo,
				null);
		c.drawBitmap(numero5, inicio + (numero5.getWidth() * 4), espaciotitulo,
				null);

		// Colocamos la cuadrícula completamente en el CENTRO
		c.drawBitmap(
				cuadricula,
				((c.getWidth() - cuadricula.getWidth()) / 2),
				((c.getHeight() - (cuadricula.getHeight() + espaciotitulo)) / 2),
				null);

		// Colocamos los números en la parte inferior centrados
		c.drawBitmap(numero6, inicio,
				alto - numero6.getHeight() - espacioboton, null);
		c.drawBitmap(numero7, inicio + numero7.getWidth(),
				alto - numero7.getHeight() - espacioboton, null);
		c.drawBitmap(numero8, inicio + (numero8.getWidth() * 2),
				alto - numero8.getHeight() - espacioboton, null);
		c.drawBitmap(numero9, inicio + (numero9.getWidth() * 3),
				alto - numero9.getHeight() - espacioboton, null);

	}

	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // toque con el dedo
			x = event.getX(); // me devuelve la posicion x,y de donde se ha
								// tocado
			y = event.getY();
			// ahora compruebo que número es según la posición de la pantalla en
			// la que ha tocado
			dameNumero(x, y);
			break;
		case MotionEvent.ACTION_UP:

			x = event.getX(); // me devuelve la posicion x,y de donde se ha
								// soltado
			y = event.getY();

			fila = queFilaEs(y);
			col = queColEs(x);
			colocaNumero(fila, col);
			seleccionado = false;
			break;
		case MotionEvent.ACTION_MOVE:
			// muestra el movimiento/recorrido
			if (imagenSel != null) {
				if (!usados[dameValor(imagenSel)]) {
					x = event.getX(); // me devuelve la posicion x,y en la que
										// se encuentra
					y = event.getY();
					break;
				}
			}
		}
		return true;
	}

	private void colocaNumero(int fila, int col) {
		// Aquí se va a rellenar la tabla con cada número cuando se situe encima
		// de alguna casilla
		if (fila != -1 && col != -1) {
			int pepe = dameValor(dameImagen(tabla[col - 1][fila - 1]));
			if (valido(imagenSel)) {
				tabla[col - 1][fila - 1] = dameValor(imagenSel);
				if (pepe != 0 && (pepe != -1))
					usados[pepe] = false;

			}
		}
		imagenSel = null;
	}

	private boolean valido(Bitmap imagenSel2) {
		// Aquí se va a comprobar que el número no haya sido colocado antes en
		// la cuadricula
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (dameValor(imagenSel2) == tabla[i][j])
					return false;
			}
		}
		if (imagenSel2 == null || imagenSel2 == comprobar
				|| imagenSel2 == restart)
			return false;
		return true;
	}

	private void dameNumero(float x, float y) {
		// devuelve el número en el que se haya pinchado segun las coordenadas y
		// se le selecciona para moverlo
		inicioc = (c.getWidth() - comprobar.getWidth() - error.getWidth() * 2) / 2;
		if ((x >= inicioc) && (x <= inicioc + comprobar.getWidth())
				&& (y >= c.getHeight() - espacioboton + espaciotitulo)
				&& (y <= (c.getHeight()))) {
			// imagenSel=comprobar;
			comprobar();

		} else {

			if (x >= inicio && x <= inicio + anchoNum) {
				if (y >= espaciotitulo && y <= altoNum + espaciotitulo) {
					imagenSel = numero1;
					// c.drawRect(inicio, 0, inicio+anchoNum,
					// altoNum+espaciotitulo, new Paint(color.black));
				} else if (y >= alto - altoNum - espacioboton
						&& y <= alto - espacioboton) {
					imagenSel = numero6;
					// c.drawRect(inicio, alto-espacioboton-altoNum,
					// inicio+anchoNum, alto-espacioboton, new
					// Paint(color.black));
				}
			} else if (x >= inicio + anchoNum && x <= inicio + anchoNum * 2) {
				if (y >= espaciotitulo && y <= altoNum + espaciotitulo) {
					imagenSel = numero2;
					// c.drawRect(inicio+anchoNum, 0, inicio+anchoNum*2,
					// altoNum+espaciotitulo, new Paint(color.black));
				} else if (y >= alto - altoNum - espacioboton
						&& y <= alto - espacioboton) {
					imagenSel = numero7;
					// c.drawRect(inicio+anchoNum, alto-espacioboton-altoNum,
					// inicio+anchoNum*2, alto-espacioboton, new
					// Paint(color.black));
				}
			} else if (x >= inicio + anchoNum * 2 && x <= inicio + anchoNum * 3) {
				if (y >= espaciotitulo && y <= altoNum + espaciotitulo) {
					imagenSel = numero3;
					// c.drawRect(inicio+anchoNum*2, 0, inicio+anchoNum*3,
					// altoNum+espaciotitulo, new Paint(color.black));
				} else if (y >= alto - altoNum - espacioboton
						&& y <= alto - espacioboton) {
					imagenSel = numero8;
					// c.drawRect(inicio+anchoNum*2, alto-espacioboton-altoNum,
					// inicio+anchoNum*3, alto-espacioboton, new
					// Paint(color.black));
				}
			} else if (x >= inicio + anchoNum * 3 && x <= inicio + anchoNum * 4) {
				if (y >= espaciotitulo && y <= altoNum + espaciotitulo) {
					imagenSel = numero4;
					// c.drawRect(inicio+anchoNum*3, 0, inicio+anchoNum*4,
					// altoNum+espaciotitulo, new Paint(color.black));
				} else if (y >= alto - altoNum - espacioboton
						&& y <= alto - espacioboton) {
					imagenSel = numero9;
					// c.drawRect(inicio+anchoNum*3, alto-espacioboton-altoNum,
					// inicio+anchoNum*4, alto-espacioboton, new
					// Paint(color.black));
				}
			} else if (x >= inicio + anchoNum * 4 && x <= inicio + anchoNum * 5) {
				if (y >= espaciotitulo && y <= altoNum + espaciotitulo) {
					imagenSel = numero5;
					// c.drawRect(inicio+anchoNum*4, 0, inicio+anchoNum*5,
					// altoNum+espaciotitulo, new Paint(color.black));
				} else if (y >= alto - altoNum - espacioboton
						&& y <= alto - espacioboton) {
					imagenSel = restart;
					reiniciarTabla();
				}

			}
		}
		if (imagenSel != restart && imagenSel != null && imagenSel != comprobar)
			seleccionado = true;
	}

	private Bitmap dameImagen(int i) {
		// Me devuelve la imagen asociada al número entero i
		if (i == 1) {
			usados[1] = true;
			return numero1;
		}
		if (i == 2) {
			usados[2] = true;
			return numero2;
		}
		if (i == 3) {
			usados[3] = true;
			return numero3;
		}
		if (i == 4) {
			usados[4] = true;
			return numero4;
		}
		if (i == 5) {
			usados[5] = true;
			return numero5;
		}
		if (i == 6) {
			usados[6] = true;
			return numero6;
		}
		if (i == 7) {
			usados[7] = true;
			return numero7;
		}
		if (i == 8) {
			usados[8] = true;
			return numero8;
		}
		if (i == 9) {
			usados[9] = true;
			return numero9;
		}
		return null;
	}

	private int dameValor(Bitmap image) {
		// Devuelve el valor asociado al bitmap de cada numero
		if (image == numero1)
			return 1;
		if (image == numero2)
			return 2;
		if (image == numero3)
			return 3;
		if (image == numero4)
			return 4;
		if (image == numero5)
			return 5;
		if (image == numero6)
			return 6;
		if (image == numero7)
			return 7;
		if (image == numero8)
			return 8;
		if (image == numero9)
			return 9;
		return -1;
	}

	int queFilaEs(float y2) { // *Para devolver el número de la fila de la
								// cuadricula donde hechos tocado
		int fila = -1;
		if (y2 >= iniCuadroY && y2 <= (iniCuadroY + altoNum))
			fila = 1;
		else if (y2 >= (iniCuadroY + altoNum + bordeIntVert)
				&& y2 <= (iniCuadroY + altoNum * 2 + bordeIntVert))
			fila = 2;
		else if (y2 >= (iniCuadroY + altoNum * 2 + bordeIntVert * 2)
				&& y2 <= (iniCuadroY + altoNum * 3 + bordeIntVert * 2))
			fila = 3;
		return fila;
	}

	int queColEs(float x2) { // Para devolver el número de la columna de la
								// cuadricula donde hechos tocado
		int col = -1;
		if (x2 >= iniCuadroX && x2 <= (iniCuadroX + anchoNum))
			col = 1;
		else if (x2 >= (iniCuadroX + anchoNum + bordeIntHoriz)
				&& x2 <= (iniCuadroX + anchoNum * 2 + bordeIntHoriz))
			col = 2;
		else if (x2 >= (iniCuadroX + anchoNum * 2 + bordeIntHoriz * 2)
				&& x2 <= (iniCuadroX + anchoNum * 3 + bordeIntHoriz * 2))
			col = 3;
		return col;
	}

	private void reiniciarTabla() {
		// Reinicia los valores de la tabla
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tabla[i][j] = 0;
			}
		}
		for (int j = 1; j < 10; j++)
			usados[j] = false;

		reiniciada = true;
		comprobado = false;
		dibujaNumeros();
		imagenSel = null;
	}

	void comprobar() {
		// Aquí se va a comprobar que todas las filas y las columnas sumen 15
		// (constante mágica)
		int acumulador;
		int i = 0;
		int j = 0;
		correcto = true;
		// Aquí se comprueban las filas
		while (i < 3 && correcto == true) {
			acumulador = 0;
			while (j < 3 && correcto) {
				acumulador += tabla[i][j];
				j++;
			}
			j = 0;
			if (acumulador != 15) {
				correcto = false;
			}
			i++;
		}
		i = 0;
		j = 0;
		// Aquí se comprueban las columnas
		while (j < 3 && correcto == true) {
			acumulador = 0;
			while (i < 3 && correcto) {
				acumulador += tabla[i][j];
				i++;
			}
			i = 0;
			if (acumulador != 15) {
				correcto = false;
			}
			j++;
		}
		comprobado = true;

		/*
		 * inicioc=(c.getWidth()-comprobar.getWidth()-error.getWidth()*2)/2;
		 * 
		 * if (correcto){ imagenSel=tick; c.drawBitmap(tick,
		 * inicioc+comprobar.getWidth()+tick.getWidth(),
		 * c.getHeight()-espacioboton+espaciotitulo, null); }else {
		 * imagenSel=error; c.drawBitmap(error,
		 * inicioc+comprobar.getWidth()+tick.getWidth(),
		 * c.getHeight()-espacioboton+espaciotitulo, null); }
		 */
		imagenSel = null;
	}

	public void dibujaNegros(boolean[] usados) {
		for (int i = 1; i < 10; i++) {
			if (usados[i] == true) {
				if (i == 1)
					c.drawRect(inicio, 0, inicio + anchoNum, altoNum
							+ espaciotitulo, new Paint(color.black));
				if (i == 2)
					c.drawRect(inicio + anchoNum, 0, inicio + anchoNum * 2,
							altoNum + espaciotitulo, new Paint(color.black));
				if (i == 3)
					c.drawRect(inicio + anchoNum * 2, 0, inicio + anchoNum * 3,
							altoNum + espaciotitulo, new Paint(color.black));
				if (i == 4)
					c.drawRect(inicio + anchoNum * 3, 0, inicio + anchoNum * 4,
							altoNum + espaciotitulo, new Paint(color.black));
				if (i == 5)
					c.drawRect(inicio + anchoNum * 4, 0, inicio + anchoNum * 5,
							altoNum + espaciotitulo, new Paint(color.black));
				if (i == 6)
					c.drawRect(inicio, alto - espacioboton - altoNum, inicio
							+ anchoNum, alto - espacioboton, new Paint(
							color.black));
				if (i == 7)
					c.drawRect(inicio + anchoNum,
							alto - espacioboton - altoNum, inicio + anchoNum
									* 2, alto - espacioboton, new Paint(
									color.black));
				if (i == 8)
					c.drawRect(inicio + anchoNum * 2, alto - espacioboton
							- altoNum, inicio + anchoNum * 3, alto
							- espacioboton, new Paint(color.black));
				if (i == 9)
					c.drawRect(inicio + anchoNum * 3, alto - espacioboton
							- altoNum, inicio + anchoNum * 4, alto
							- espacioboton, new Paint(color.black));

			}
		}
	}

}