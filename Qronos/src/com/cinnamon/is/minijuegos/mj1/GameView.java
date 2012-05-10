package com.cinnamon.is.minijuegos.mj1;

import java.util.ArrayList;
import java.util.Random;
import com.cinnamon.is.R;
import com.cinnamon.is.comun.Props;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class GameView extends SurfaceView {
	GameLoop loop;
	private SurfaceHolder holder;
	private Marcianos marciano;
	private ArrayList<Marcianos> marcianosList = new ArrayList<Marcianos>();
	private ArrayList<SangreMarciano> listaSangre = new ArrayList<SangreMarciano>();
	private ArrayList<Explosion> listaExplosion = new ArrayList<Explosion>();
	private int numeroVidas;
	private Bitmap fondo;
	private Cupula cupula;
	private int posVidas;
	private Bitmap vida;
	private SangreMarciano blood;
	private Explosion explosion;
	private int velocidad;
	private MediaPlayer music;
	private Integer marcianosEliminados;
	private Explosion bomba;
	private int posBomba;
	private Context contexto;
	private StartingMarcianos activity;

	// Setters y getters
	public int getNumVidas() {
		return numeroVidas;
	}

	public void musicaOff() {
		music.pause();
	}

	public void musicaOn() {
		music.start();
	}

	public Explosion getBomba() {
		return this.bomba;
	}

	public void setNumVidas(int vidas) {
		numeroVidas = vidas;
	}

	public void quitarMarciano(Marcianos marciano) {
		marcianosList.remove(marciano);
	}

	public int getHeightCupula() {
		return cupula.getHeight();
	}

	public void anadirExplosion(int x, int y) {
		crearExplosion(R.drawable.explosion1, x, y);
		listaExplosion.add(explosion);
	}

	public void anadirBomba(int x, int y) {
		crearBomba(R.drawable.img_mj1_bomba1, posBomba, 0);
		listaExplosion.add(bomba);
	}

	public void acelerar() {
		this.velocidad = this.velocidad + 5;
	}

	public void loopStop() {
		this.loop.pauseLoop();
	}

	public GameView(Context context, StartingMarcianos ac) {
		super(context);
		contexto = context;
		this.setActivity(ac);
		loop = new GameLoop(this, ac);
		holder = getHolder();
		numeroVidas = 3;
		posVidas = 10;
		velocidad = 10;
		posBomba = 120;
		marcianosEliminados = 0;
		holder.addCallback(new Callback() {

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {

			}

			public void surfaceCreated(SurfaceHolder holder) {
				temazo(R.raw.sound_mj1);
				crearBomba(R.drawable.img_mj1_bomba1, posBomba, 0);
				crearFondo(R.drawable.img_mj1_fondo);
				crearVida(R.drawable.img_mj1_vida);
				crearCupula(R.drawable.img_mj1_cupula1);
				loop.start();

			}

			public void surfaceDestroyed(SurfaceHolder holder) {

			}

		});
	}

	private void temazo(int resource) {

		SharedPreferences getData = PreferenceManager
				.getDefaultSharedPreferences(contexto.getApplicationContext());
		if (getData.getBoolean(Props.Comun.CB_SONIDO, true)) {
			music = MediaPlayer.create(getContext(), resource);
			music.setVolume(1000, 1000);
			music.start();
			music.setLooping(true);
		}
	}

	public void crearVida(int resource) {
		vida = BitmapFactory.decodeResource(getResources(), resource);

	}

	public void crearSangreMarciano(int resource, float x, float y) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		blood = new SangreMarciano(bmp, x, y);
		listaSangre.add(blood);
	}

	public void crearMarciano(int resource, int tipo) {
		int i = this.getWidth();
		Random r = new Random();
		int x = r.nextInt(i);// Para q los marcianos empiezen en posiciones
								// aleatorias sobre el eje x
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		marciano = new Marcianos(this, x, bmp, tipo, velocidad);
		int aux = x + marciano.getWidth();
		if (aux > this.getWidth()) {
			while (aux > this.getWidth()) {
				aux--;
			}
			marciano = new Marcianos(this, aux - marciano.getWidth(), bmp,
					tipo, velocidad);
		}
		marcianosList.add(marciano);

	}

	public void crearFondo(int resource) {
		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = getWidth();
		int height = getHeight();
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width, height, true);
		this.fondo = tmp;
	}

	public void crearCupula(int resource) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		int cupulaWidth = bmp.getWidth();
		int cupulaHeight = bmp.getHeight();
		cupula = new Cupula(bmp, cupulaWidth, cupulaHeight);
	}

	public void crearExplosion(int resource, int x, int y) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		explosion = new Explosion(bmp, x, y);
	}

	public void crearBomba(int resource, int x, int y) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		bomba = new Explosion(bmp, x, y);
	}

	protected void onDraw(Canvas canvas) {

		canvas.drawBitmap(fondo, 0, 0, null);
		cupula.onDraw(canvas, this.getWidth() / 2 - cupula.getWidth() / 2,
				this.getHeight() - cupula.getHeight());
		posVidas = 10;// Para q permanezcan en sus posiciones iniciales
		for (int j = 0; j < numeroVidas; j++) {// bucle para dibujar las vidas
			canvas.drawBitmap(vida, posVidas, 10, null);
			posVidas = posVidas + vida.getWidth() + 20;
		}
		for (int k = listaSangre.size(); k > 0; k--) {
			if (listaSangre.get(k - 1).getLoops() != 0) {
				listaSangre.get(k - 1).onDraw(canvas);
				listaSangre.get(k - 1).restarLoops();
			}

			else {
				listaSangre.remove(k - 1);

			}
		}
		for (int l = listaExplosion.size(); l > 0; l--) {
			if (listaExplosion.get(l - 1).getLoops() != 0) {
				listaExplosion.get(l - 1).onDraw(canvas);
				listaExplosion.get(l - 1).restarLoops();
			} else {
				listaExplosion.remove(l - 1);
			}

		}
		bomba.onDraw(canvas);
		for (int i = marcianosList.size(); i > 0; i--) {
			marcianosList.get(i - 1).onDraw(canvas);
		}
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		p.setTextSize(50);

		canvas.drawText(marcianosEliminados.toString(), this.getWidth() - 100,
				40, p);
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (bomba.isClick(event.getX(), event.getY())
				&& (bomba.getEstado() == false)) {
			for (int n = marcianosList.size() - 1; n >= 0; n--) {
				int s = marcianosList.get(n).getTipo();
				if (s == 1) {
					crearSangreMarciano(R.drawable.img_mj1_sangre1,
							marcianosList.get(n).getX(), marcianosList.get(n)
									.getY());
				}
				if (s == 2) {
					crearSangreMarciano(R.drawable.img_mj1_sangre2,
							marcianosList.get(n).getX(), marcianosList.get(n)
									.getY());
				}
				if (s == 3) {
					crearSangreMarciano(R.drawable.img_mj1_sangre2,
							marcianosList.get(n).getX(), marcianosList.get(n)
									.getY());
				}
				marcianosList.remove(n);
				marcianosEliminados++;
			}
			crearBomba(R.drawable.img_mj1_bomba2, posBomba, 0);
			bomba.cambiarEstado(true);

		}
		for (int i = 0; i < marcianosList.size(); i++) {
			if (marcianosList.get(i).isClick(event.getX(), event.getY())) {

				if (marcianosList.get(i).getTipo() == 1) {
					crearSangreMarciano(R.drawable.img_mj1_sangre1,
							marcianosList.get(i).getX(), marcianosList.get(i)
									.getY());
					marcianosList.remove(i);
					marcianosEliminados++;

				}

				else if (marcianosList.get(i).getTipo() == 2) {
					int v = marcianosList.get(i).getVidasMarciano();
					if (v == 3) {
						crearSangreMarciano(R.drawable.img_mj1_sangre2,
								marcianosList.get(i).getX(),
								marcianosList.get(i).getY());
						marcianosList.remove(i);
						marcianosEliminados++;
					} else {
						marcianosList.get(i).quitarVidaMarciano();
					}
				} else if (marcianosList.get(i).getTipo() == 3) {
					int v2 = marcianosList.get(i).getVidasMarciano();
					if (v2 == 0) {
						crearSangreMarciano(R.drawable.img_mj1_sangre3,
								marcianosList.get(i).getX(),
								marcianosList.get(i).getY());
						marcianosList.remove(i);
						marcianosEliminados++;

					} else {
						marcianosList.get(i).quitarVidaMarciano();
					}
				}
			}
		}

		return super.onTouchEvent(event);
	}

	public int getScore() {
		return this.marcianosEliminados;
	}

	public void reanudar() {
		loop.resumeLoop();

	}

	/**
	 * @return the activity
	 */
	public StartingMarcianos getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(StartingMarcianos activity) {
		this.activity = activity;
	}

}
