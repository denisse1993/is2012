package com.cinnamon.is.minijuegos.mj6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cinnamon.is.R;

public class GameView extends View {
	// private GameLoop loop;
	private int estado;
	private final Paint paint;
	private boolean explosion;
	private final int alto, ancho;
	private float y, z;
	private Bitmap fondo, boom, bomba, camara;
	private final int centroY, centroZ;
	// private Button boton;
	// private Button btnQR;
	private int numVibraciones;
	private int score;
	private int estadoAnterior;
	private int vibracionesEspera;
	private final MinijuegoBomba a;

	private final static int ANCHO_CAMARA = 72;
	private final static int ALTO_CAMARA = 72;

	private final int posicionZ_camara;
	private final int posicionY_camara;

	/**
	 * Vista pulsada en onClick para uso en dialog onclick
	 */
	private int vClicked;

	public GameView(final Context context, final int width, final int height,
			final MinijuegoBomba minijuegoBomba) {
		super(context);
		estado = 0;
		paint = new Paint();
		paint.setTextSize(50);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		ancho = width;
		alto = height;
		centroZ = ancho / 2;
		centroY = alto / 2;
		posicionZ_camara = (centroZ - ANCHO_CAMARA / 2);
		posicionY_camara = (alto) - ALTO_CAMARA - 20;
		explosion = false;
		a = minijuegoBomba;
		crearFondo(R.drawable.fondobomba);
		crearBomba(R.drawable.bombared);
		crearFondoExplosion(R.drawable.estallido);
		crearBotonCamara(R.drawable.ic_menu_photo);
		numVibraciones = 0;
		score = 1000;
		vibracionesEspera = 0;
		estadoAnterior = 0;
		estado = 0;
	}

	public void setY(final float Y) {
		y = Y;
	}

	public void setZ(final float Z) {

		z = Z;
	}

	public boolean getExplosion() {
		return this.explosion;
	}

	public void setEstado(final int estado) {
		this.estado = estado;

	}

	public void crearFondoExplosion(final int resource) {

		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = this.ancho;
		int height = this.alto;
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width, height, true);

		this.boom = tmp;
	}

	public void crearFondo(final int resource) {

		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = this.ancho;
		int height = this.alto;
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width, height, true);

		this.fondo = tmp;
	}

	public void crearBomba(final int resource) {

		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		this.bomba = bmp;
	}

	public void crearBotonCamara(final int resource) {

		// ibCamara = new ImageButton(a);
		// ibCamara.setImageResource(resource);

		// ibCamara.setId(R.id.ib_camara_bomba);
		// ibCamara.layout(0, 0, 0, 0);
		// ibCamara.setPadding(centroZ, 5/6*alto, centroZ-32, 1/6*alto);
		// ibCamara.setOnClickListener(this);
		// ibCamara.setVisibility(View.VISIBLE);

		Bitmap camara = BitmapFactory.decodeResource(getResources(), resource);
		Bitmap tmp = Bitmap.createScaledBitmap(camara, ANCHO_CAMARA,
				ALTO_CAMARA, true);
		this.camara = tmp;

	}

	@Override
	public void onDraw(final Canvas canvas) {

		// poner match parent, match parent keda mejor y no estirada
		// canvas.drawBitmap(fondo, 0, 0, paint);
		// boton = new Button(null);

		int y1, y2, z1, z2;
		int altobomba = this.bomba.getHeight();
		int anchobomba = this.bomba.getWidth();

		if (!explosion) {

			z1 = (int) (centroZ - ((centroZ / 10) * z));
			z2 = z1 - (altobomba / 2);
			y1 = (int) (centroY - ((centroY / 10) * y));
			y2 = y1 - (anchobomba / 2);

			canvas.drawBitmap(fondo, 0, 0, paint);
			canvas.drawBitmap(bomba, z2, y2, paint);

			canvas.drawBitmap(camara, posicionZ_camara, posicionY_camara, paint);
			canvas.drawText(Integer.toString(numVibraciones), ancho - 50, 50,
					paint);

		} else {
			canvas.drawBitmap(boom, 0, 0, paint);
			a.finalizar(true);
			// ///
			/*
			 * btnQR=(Button) findViewById(R.id.leer); //QR= new UtilQR(this);
			 * btnQR.setOnClickListener(new OnClickListener(){
			 * 
			 * public void onClick(View v) { // TODO Auto-generated method stub
			 * try{ //QR.lanzarQR(); //lanza el scan del Barcode Scanner Intent
			 * intent = new Intent("com.google.zxing.client.android.SCAN");
			 * intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			 * //startActivityForResult(intent, 0); }catch
			 * (ActivityNotFoundException e) { e.printStackTrace(); } }
			 * 
			 * });
			 */
			// ////

		}

	}

	public void setExplosion(final Boolean b) {
		explosion = b;
	}

	public void crearRecursos() {
		// crearFondo(R.drawable.fondoprueba2);

	}

	public void restaPuntuacion() {
		if (((estadoAnterior == 2) && (vibracionesEspera > 20))
				|| (estadoAnterior != 2)) {
			score = score - 100;
			numVibraciones = numVibraciones + 1;
			vibracionesEspera = 0;

		}

		vibracionesEspera++;
		estadoAnterior = 2;

	}

	public int getScore() {
		return score;
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if (areaCamara(x, y)) {
			a.pulsarCamara();
			return true;
		}
		return false;
	}

	private boolean areaCamara(final int x, final int y) {
		Toast.makeText(a, "Hola", 5000);
		if (posicionZ_camara < x && posicionZ_camara + ANCHO_CAMARA > x) {
			if (posicionY_camara < y && posicionY_camara + ALTO_CAMARA > y) {
				return true;
			}
		}
		return false;
	}

}
