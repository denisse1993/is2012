package com.cinnamon.is.minijuegos.ascensor;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.cinnamon.is.R;

public class GameView extends SurfaceView {
	GameLoop loop;
	private SurfaceHolder holder;
	private ArrayList<Sprite> listaPlanta1 = new ArrayList<Sprite>();
	private ArrayList<Sprite> listaPlanta2 = new ArrayList<Sprite>();
	// private ArrayList<Sprite> listaAscensor = new ArrayList<Sprite>();
	private ArrayList<Sprite> listaSprites = new ArrayList<Sprite>();
	private ArrayList<Sprite> listaAscenso = new ArrayList<Sprite>();
	private Ascensor ascensor;
	private Botones botones;
	private Fondo fondo1;
	private Fondo fondo2;
	private int xInicio;
	private boolean planta2;
	private boolean moving = false;
	private boolean finJuego;
	boolean winner;
	private int ocupantes;
	private int caminantes;
	private MediaPlayer music;
	

	public GameView(Context context) {
		super(context);
		loop = new GameLoop(this);
		holder = getHolder();
		xInicio = 50;
		caminantes = 0;
		holder.addCallback(new Callback() {

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub

			}

			public void surfaceCreated(SurfaceHolder holder) {
				temazo(R.raw.hearthbeat_sound);
				creaFondo1(R.drawable.piso);
				creaFondo2(R.drawable.piso);
				creaSprites(R.drawable.gobierno, 3);
				creaSprites(R.drawable.novio, 2);
				creaSprites(R.drawable.turing, 1);
				creaAscensor(R.drawable.ascensor);
				crearBotones(R.drawable.boton_ascensor);
				loop.setRunning(true);
				loop.start();

			}

			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub

			}

		});

	}

	public boolean getWinner() {
		return winner;
	}

	public ArrayList<Sprite> getListaSprites() {
		return listaSprites;
	}

	public void quitarCaminantes() {
		caminantes--;
	}

	public void setOcupantes() {
		this.ocupantes++;
	}

	public void quitarOcupantes() {
		this.ocupantes--;
	}

	public int getOcupantes() {
		return this.ocupantes;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean getFinJuego() {
		return finJuego;
	}

	public void setFInJUego(boolean b) {
		this.finJuego = b;
	}

	public boolean getMoving() {
		return this.moving;
	}

	public void setPlanta2(boolean planta) {
		this.planta2 = planta;
	}

	public int getTamPlanta1() {
		return listaPlanta1.size();
	}

	public int getTamPlanta2() {
		return listaPlanta2.size();
	}

	public int getTamAscensor() {
		return listaAscenso.size();
	}

	public void añadirAscensor(Sprite sprite) {
		listaAscenso.add(sprite);
	}

	public void añadirPlanta1(Sprite sprite) {
		listaPlanta1.add(sprite);
	}

	public void añadirPlanta2(Sprite sprite) {
		listaPlanta2.add(sprite);
	}

	public void quitarPlanta1(Sprite sprite) {
		listaPlanta1.remove(sprite);
	}

	public void quitarPlanta2(Sprite sprite) {
		listaPlanta2.remove(sprite);
	}

	public void quitarAscensor(Sprite sprite) {
		if (listaAscenso.size() == 1) {
			listaAscenso = new ArrayList<Sprite>();
			listaAscenso.clear();
		} else
			listaAscenso.remove(sprite);
	}

	private void temazo(int resource) {
		music = MediaPlayer.create(getContext(), resource);
		music.start();
	}

	private void creaFondo1(int resource) {
		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = getWidth();
		int height = getHeight();
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width, height / 2,
				true);
		this.fondo1 = new Fondo(tmp, this);
	}

	private void creaFondo2(int resource) {
		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = getWidth();
		int height = getHeight();
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width, height / 2,
				true);
		this.fondo2 = new Fondo(tmp, this);
	}

	private void creaSprites(int resource, int id) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		Sprite sprite = new Sprite(this, bmp, xInicio, id);

		listaSprites.add(sprite);
		listaPlanta1.add(sprite);
		xInicio = xInicio + 50;

	}

	private void creaAscensor(int resource) {
		Bitmap bmpA = BitmapFactory.decodeResource(getResources(), resource);
		this.ascensor = new Ascensor(this, bmpA);
	}

	private void crearBotones(int resource) {
		Bitmap bmpBoton = BitmapFactory
				.decodeResource(getResources(), resource);
		this.botones = new Botones(this, bmpBoton);
	}

	public boolean problema(int id1, int id2) {
		boolean aux = false;
		if ((id1 == 1) || (id2 == 1)) {
			aux = true;
		}
		return aux;
	}

	public void reinicio() {

		finJuego = true;
		xInicio = 20;
		ocupantes = 0;
		caminantes = 0;
		// listaAscensor.clear();
		listaPlanta1 = new ArrayList<Sprite>();
		listaPlanta2 = new ArrayList<Sprite>();
		// listaAscenso = new ArrayList<Sprite>();
		listaSprites = new ArrayList<Sprite>();
		creaSprites(R.drawable.gobierno, 3);
		creaSprites(R.drawable.novio, 2);
		creaSprites(R.drawable.turing, 1);
		creaAscensor(R.drawable.ascensor);
		crearBotones(R.drawable.boton_ascensor);

	}

	public void conseguido() {
		winner = true;
	}

	public int conflicto() {
		int aux = 0;
		// Comprobar planta1
		if (listaPlanta1.size() == 2) {
			Sprite Sprite1 = listaPlanta1.get(0);
			Sprite Sprite2 = listaPlanta1.get(1);
			int id1 = Sprite1.getNombre();
			int id2 = Sprite2.getNombre();
			if (problema(id1, id2)) {
				aux = 1;
			}
		}
		// Comporbar planta2
		if (listaPlanta2.size() == 2) {
			Sprite Sprite1 = listaPlanta2.get(0);
			Sprite Sprite2 = listaPlanta2.get(1);
			int id1 = Sprite1.getNombre();
			int id2 = Sprite2.getNombre();
			if (problema(id1, id2)) {
				aux = 1;
			}
		}
		// Comprobar ascensor
		if (ocupantes == 2) {
			if (listaPlanta1.size() != 0) {
				Sprite Sprite1 = listaPlanta1.get(0);
				if (Sprite1.getNombre() != 1) {
					aux = 1;
				}
			} else {
				Sprite Sprite2 = listaPlanta2.get(0);
				if (Sprite2.getNombre() != 1) {
					aux = 1;
				}

			}

		}
		// Los 3 arriba
		if (listaPlanta2.size() == 3) {
			aux = 2;
		}

		return aux;
	}

	protected void onDraw(Canvas canvas) {
		if (listaPlanta2.size() == 3) {
			loop.setRunning(false);
			winner = true;
		}
		if (finJuego == false && winner == false) {

			fondo1.onDraw(canvas, 0);
			fondo2.onDraw(canvas, getHeight() / 2);
			if (moving) {
				for (int i = 0; i <= 2; i++) {
					listaSprites.get(i).onDraw(canvas);
				}

			}
			if (ascensor != null) {
				ascensor.onDraw(canvas);
			}
			if (moving == false) {
				for (int i = 0; i <= 2; i++) {
					listaSprites.get(i).onDraw(canvas);
				}
			}
			if (botones != null) {
				botones.onDraw(canvas);
			}

		}
	}


	protected void onDraw2(Canvas canvas) {
		canvas.drawColor(Color.RED);
	}

	protected void onDraw3(Canvas canvas) {
		canvas.drawColor(Color.CYAN);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (caminantes != 1) {
			if ((planta2 == false) && (moving == false)) {
				for (int i = listaPlanta1.size() - 1; i >= 0; i--) {
					Sprite sprite = listaPlanta1.get(i);
					if (sprite.isClick(event.getX(), event.getY())) {
						if (ocupantes != 2) {
							sprite.toElevator();
							caminantes++;

						}

					}
				}
			}
			if ((planta2 == true) && (moving == false)) {
				for (int i = listaPlanta2.size() - 1; i >= 0; i--) {
					Sprite sprite = listaPlanta2.get(i);
					if (sprite.isClick(event.getX(), event.getY())) {
						if (ocupantes != 2) {
							sprite.toElevator();
							caminantes++;

						}

					}
				}
			}
			if (moving == false) {
				for (int i = listaSprites.size() - 1; i >= 0; i--) {
					Sprite sprite = listaSprites.get(i);
					if (sprite.isClick(event.getX(), event.getY())
							&& sprite.getX() > 200) {
						if (sprite.getY() > 270) {
							sprite.toPlanta1();
							// ocupantes--;
							caminantes++;
						} else {
							sprite.toPlanta2();
							// ocupantes--;
							caminantes++;
						}

					}
				}
			}
			if (botones.isClick(event.getX(), event.getY())) {
				if (conflicto() == 0) {
					if (planta2 == false) {
						for (int i1 = listaSprites.size() - 1; i1 >= 0; i1--) {
							Sprite sprite1 = listaSprites.get(i1);
							if (sprite1.getX() > 200) {
								sprite1.onMovementUp();
								ascensor.onMovementUp();
								moving = true;
							}
						}
					} else {
						for (int i1 = listaSprites.size() - 1; i1 >= 0; i1--) {
							Sprite sprite1 = listaSprites.get(i1);
							if (sprite1.getX() > 200) {
								sprite1.onMovementDown();
								ascensor.onMovementDown();
								moving = true;
							}
						}

					}
				} else {
					if (conflicto() == 1) {
						reinicio();
					} else
						conseguido();
				}
			}
		}
		return super.onTouchEvent(event);
	}
}

