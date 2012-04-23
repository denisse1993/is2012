package com.cinnamon.is.minijuegos.mj1;

import java.util.ArrayList;
import java.util.Random;

import com.cinnamon.is.R;

import android.R.color;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class GameView extends SurfaceView {
	private GameLoop loop;
	private SurfaceHolder holder;
	private Marcianos marciano;
	private ArrayList<Marcianos> marcianosList = new ArrayList<Marcianos>();
	private ArrayList<SangreMarciano> listaSangre = new ArrayList<SangreMarciano>();
	private ArrayList<Explosion> listaExplosion = new ArrayList<Explosion>();
	private int numeroVidas;
	// private Fondo fondo;//no tiene por que ser una clase(mirar)
	private int screenWidth;// ancho pantalla
	private int screenHeight;// altura pantalla
	private Bitmap fondo;
	private Cupula cupula;
	private boolean finJuego;
	private int posVidas;
	private Bitmap vida;
	private SangreMarciano blood;
	private Explosion explosion;
	private int velocidad;
	private MediaPlayer music;
	private MediaPlayer ruido;
	private Integer marcianosEliminados;
	private StartingMarcianos activity;
	private Thread timer;
	private Explosion bomba;
	private int posBomba;
	// Setters y getters
	public int getNumVidas() {
		return numeroVidas;
	}

	public void musicaOff() {
		music.pause();
	}
	public Explosion getBomba(){
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

	public void a�adirExplosion(int x, int y) {
		crearExplosion(R.drawable.explosion1, x, y);
		listaExplosion.add(explosion);
	}
	public void a�adirBomba(int x,int y){
		crearBomba(R.drawable.bomba1, posBomba, 0);
		listaExplosion.add(bomba);
	}
	public void acelerar() {
		this.velocidad = this.velocidad + 5;
	}

	public void loopStop(){
		 this.loop.setRunning();
	}

	public GameView(Context context, StartingMarcianos ac) {
		super(context);
		activity = ac;
		loop = new GameLoop(this,ac);
		holder = getHolder();
		numeroVidas = 3;
		posVidas = 10;
		velocidad = 10;
		posBomba = 120;
		marcianosEliminados = 0;
		holder.addCallback(new Callback() {

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub

			}

			public void surfaceCreated(SurfaceHolder holder) {
				temazo(R.raw.music);
				crearBomba(R.drawable.bomba1,posBomba, 0);
				crearFondo(R.drawable.espacio4);
				crearVida(R.drawable.vidas);
				crearCupula(R.drawable.cupulacristal);
				loop.start();
				
			}

			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub

			}

		});
	}

	private void ruido(int resource) {
		ruido = MediaPlayer.create(getContext(), resource);
		Thread timer = new Thread(){
			public void run() {
				// TODO Auto-generated method stub
				try{
					ruido.start();
					sleep(1000);
					
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				finally {
					ruido.pause();
			}        	
			}
			};    
		timer.start();
		timer.stop();
		//ruido.setLooping(true);
		
		//ruido.start();
		
	}

	private void temazo(int resource) {
		music = MediaPlayer.create(getContext(), resource);
		music.setVolume(1000, 1000);
		music.start();
		music.setLooping(true);
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
	
	public void crearBomba(int resource, int x , int y) {
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
		if(bomba.isClick(event.getX(), event.getY())&&(bomba.getEstado() == false)){
			for(int n =marcianosList.size() - 1;n>=0;n--){
				int s = marcianosList.get(n).getTipo();
				if(s==1){
					crearSangreMarciano(R.drawable.greenblood, marcianosList
							.get(n).getX(), marcianosList.get(n).getY());
				}
				if(s==2){
					crearSangreMarciano(R.drawable.sangrelili, marcianosList
							.get(n).getX(), marcianosList.get(n).getY());
				}
				if(s==3){
					crearSangreMarciano(R.drawable.redblood, marcianosList
							.get(n).getX(), marcianosList.get(n).getY());
				}
				marcianosList.remove(n);
				marcianosEliminados++;
			}
			crearBomba(R.drawable.bomba2,posBomba,0);
			bomba.cambiarEstado(true);
			
		}
		for (int i = 0; i < marcianosList.size(); i++) {
			if (marcianosList.get(i).isClick(event.getX(), event.getY())) {
				//ruido(R.raw.ruido1);
				if (marcianosList.get(i).getTipo() == 1) {
					crearSangreMarciano(R.drawable.greenblood, marcianosList
							.get(i).getX(), marcianosList.get(i).getY());
					marcianosList.remove(i);
					marcianosEliminados++;

				}

				else if (marcianosList.get(i).getTipo() == 2) {
					int v = marcianosList.get(i).getVidasMarciano();
					if (v == 3) {
						crearSangreMarciano(R.drawable.sangrelili,
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
						crearSangreMarciano(R.drawable.redblood, marcianosList
								.get(i).getX(), marcianosList.get(i).getY());
						marcianosList.remove(i);
						marcianosEliminados++;

					} else {
						marcianosList.get(i).quitarVidaMarciano();
					}
				}
			}
		}
		// Comprobar si se ha pulsado algun marcianito,si asi ha sido
		// eliminarlo
		
		return super.onTouchEvent(event);
	}
	
	public int getScore(){
		return this.marcianosEliminados;
	}

	public void reanudar() {
		loop.setRunning(true);
		
	}

}