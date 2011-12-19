package com.cinnamon.is.minijuegos.cuadrado;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import com.cinnamon.is.comun.Minijuego;

public class CuadradoMagicoMJActivity extends Minijuego implements
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

	// enum
	// tNumero{numero1,numero2,numero3,numero4,numero5,numero6,numero7,numero8,numero9};
	// *creo un tipo número para crear una tabla de números para la comprobación

	OurView v;
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
	float x, y, ancho, alto, inicio;
	Canvas c;
	int tabla[][]; // *creo una tabla para ir metiendo los números segun la fila
					// y la columna
	float iniCuadroX;
	float iniCuadroY;
	float bordeExt = 11;
	float bordeIntHoriz = 5;
	float bordeIntVert = 4;
	float anchoNum;
	float altoNum;
	int fila, col;
	boolean seleccionado;
	Bitmap imagenSel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		v = new OurView(this);
		v.setOnTouchListener(this);
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

		tabla = new int[3][3]; // +creo una tabla de 3x3 para determinar la
								// posicion de los numeros en el canvas

		x = y = 0;
		setContentView(v); // para ver el canvas
		// setContentView(R.layout.main); //para ver la interfaz
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		v.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		v.resume();
	}

	public class OurView extends SurfaceView implements Runnable {

		Thread t = null;
		SurfaceHolder holder;
		boolean enEjecucion = false;

		public OurView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			holder = getHolder();
		}

		public void resume() {
			// TODO Auto-generated method stub
			enEjecucion = true;
			t = new Thread(this);
			t.start();
		}

		public void pause() {
			// TODO Auto-generated method stub
			enEjecucion = false;
			while (true) {
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			t = null;
		}

		public void run() {
			// TODO Auto-generated method stub
			while (enEjecucion == true) {
				if (!holder.getSurface().isValid()) {
					continue; // se sale del if y vuelve al principio
				}
				c = holder.lockCanvas(); // bloquea el canvas para pintarlo
				// aqui en medio se dibujan las cosas
				ancho = c.getWidth();
				alto = c.getHeight();
				inicio = (ancho - (numero1.getWidth() * 5)) / 2;

				anchoNum = numero1.getWidth();
				altoNum = numero1.getHeight();
				iniCuadroX = ((c.getWidth() - cuadricula.getWidth()) / 2)
						+ bordeExt;
				iniCuadroY = (c.getHeight() - cuadricula.getHeight()) / 2
						+ bordeExt;
				// *Marco la posicion de inicio de la cuadrícula, para moverse
				// entre los puntos de inicio
				// de las celdas sólo hay que sumar la anchura y altura de
				// tantos números como celdas queramos
				// avanzar + los bordes que haya que atravesar

				// Colocamos los números en la parte superior centrados
				c.drawBitmap(numero1, inicio, 0, null);
				c.drawBitmap(numero2, inicio + numero2.getWidth(), 0, null);
				c.drawBitmap(numero3, inicio + (numero3.getWidth() * 2), 0,
						null);
				c.drawBitmap(numero4, inicio + (numero4.getWidth() * 3), 0,
						null);
				c.drawBitmap(numero5, inicio + (numero5.getWidth() * 4), 0,
						null);

				// Colocamos la cuadrícula completamente en el centro
				c.drawBitmap(cuadricula,
						((c.getWidth() - cuadricula.getWidth()) / 2),
						((c.getHeight() - cuadricula.getHeight()) / 2), null);

				// Colocamos los números en la parte inferior centrados

				c.drawBitmap(numero6, inicio, alto - numero6.getHeight(), null);
				c.drawBitmap(numero7, inicio + numero7.getWidth(), alto
						- numero7.getHeight(), null);
				c.drawBitmap(numero8, inicio + (numero8.getWidth() * 2), alto
						- numero8.getHeight(), null);
				c.drawBitmap(numero9, inicio + (numero9.getWidth() * 3), alto
						- numero9.getHeight(), null);
				c.drawBitmap(restart, inicio + (restart.getWidth() * 4), alto
						- restart.getHeight(), null);
				
				

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

				if (imagenSel != null && imagenSel != restart && seleccionado) {
					c.drawBitmap(imagenSel, x - (anchoNum / 2), y
							- (altoNum / 2), null);
				}
				dibujaTabla();
				// c.drawARGB(255, 150, 150, 10);
				// c.drawBitmap(ball, x-(ball.getWidth()/2),
				// y-(ball.getHeight()/2), null);
				holder.unlockCanvasAndPost(c); // lo desbloquea para mostrarlo
			}

		}

		private void dibujaTabla() {
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
		}

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
			x = event.getX(); // me devuelve la posicion x,y donde se ha soltado
			y = event.getY();
			fila = queFilaEs(y);
			col = queColEs(x);
			colocaNumero(fila, col);
			seleccionado = false;
			break;
		case MotionEvent.ACTION_MOVE:
			x = event.getX(); // me devuelve la posicion x,y de donde va pasando
			y = event.getY();
			break;
		}

		return true;
	}

	private void colocaNumero(int fila, int col) {
		// Aquí se va a rellenar la tabla con cada número cuando se situe encima
		// de alguna casilla
		if (fila != -1 && col != -1) {
			if (valido(imagenSel))
				tabla[col - 1][fila - 1] = dameValor(imagenSel);
		}
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
		return true;
	}

	private Bitmap dameImagen(int i) {
		// Me devuelve la imagen asociada al número entero i
		if (i == 1)
			return numero1;
		if (i == 2)
			return numero2;
		if (i == 3)
			return numero3;
		if (i == 4)
			return numero4;
		if (i == 5)
			return numero5;
		if (i == 6)
			return numero6;
		if (i == 7)
			return numero7;
		if (i == 8)
			return numero8;
		if (i == 9)
			return numero9;
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

	private void dameNumero(float x, float y) {
		// devuelve el número en el que se haya pinchado segun las coordenadas y
		// se le selecciona para moverlo
		if (x >= inicio && x <= inicio + anchoNum) {
			if (y >= 0 && y <= altoNum)
				imagenSel = numero1;
			else if (y >= alto - altoNum && y <= alto)
				imagenSel = numero6;
		} else if (x >= inicio + anchoNum && x <= inicio + anchoNum * 2) {
			if (y >= 0 && y <= altoNum)
				imagenSel = numero2;
			else if (y >= alto - altoNum && y <= alto)
				imagenSel = numero7;
		} else if (x >= inicio + anchoNum * 2 && x <= inicio + anchoNum * 3) {
			if (y >= 0 && y <= altoNum)
				imagenSel = numero3;
			else if (y >= alto - altoNum && y <= alto)
				imagenSel = numero8;
		} else if (x >= inicio + anchoNum * 3 && x <= inicio + anchoNum * 4) {
			if (y >= 0 && y <= altoNum)
				imagenSel = numero4;
			else if (y >= alto - altoNum && y <= alto)
				imagenSel = numero9;
		} else if (x >= inicio + anchoNum * 4 && x <= inicio + anchoNum * 5) {
			if (y >= 0 && y <= altoNum)
				imagenSel = numero5;
			else if (y >= alto - altoNum && y <= alto) {
				imagenSel = restart;
				reiniciarTabla();
			}
		}
		if (imagenSel != restart && imagenSel != null)
			seleccionado = true;
	}

	private void reiniciarTabla() {
		// Reinicia los valores de la tabla
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tabla[i][j] = 0;
			}
		}
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

}