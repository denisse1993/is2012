package com.cinnamon.is.minijuegos.ascensor;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 3;
	private int x;
	private int y;
	private GameView gameView;
	private Bitmap bmp;
	private int width;
	private int height;
	private int currentFrame;
	private Rect rect_char;
	private Rect rect_png;
	private boolean moveElevator;
	private boolean enAscensor;
	private boolean onMovementUp;
	private boolean onMovementDown;
	private int nombre;
	private boolean toPlanta1;
	private int xInicial;
	private boolean toPlanta2;

	public Sprite(GameView gameView, Bitmap bmp, int x1, int id) {

		this.x = x1;
		this.y = 650;
		this.nombre = id;
		this.enAscensor = false;
		this.gameView = gameView;
		this.bmp = bmp;
		this.width = (bmp.getWidth() / BMP_COLUMNS);
		this.height = (bmp.getHeight() / BMP_ROWS);
		this.nombre = id;
		this.xInicial = x1;
		this.toPlanta1 = false;
		this.toPlanta2 = false;

	}

	public int getY() {
		return this.y;
	}

	public int getX() {
		return this.x;
	}

	public int getNombre() {
		return this.nombre;
	}

	public void setNombre(int nomb) {
		this.nombre = nomb;
	}

	public boolean getEnAscensor() {
		return this.enAscensor;
	}

	private void update() {

		currentFrame = ((++currentFrame) % BMP_COLUMNS); // alterna imagen
	}

	public void onDraw(Canvas canvas) {
		update();
		int currentX = (currentFrame * this.width);
		int currentY = 0;
		// Primer personaje al ascensor
		if (this.gameView.getOcupantes() == 0) {
			if ((moveElevator == true)
					&& (this.x < (this.gameView.getWidth() - 140))) {
				currentY = 2 * height;
				this.x = this.x + 10;

			} else {
				if (this.x >= (this.gameView.getWidth() - 140)) {
					this.enAscensor = true;
					moveElevator = false;
					// this.gameView.añadirAscensor(this);
					this.gameView.setOcupantes();
					if (this.y > 270) {
						this.gameView.quitarPlanta1(this);
						this.gameView.quitarCaminantes();
					} else {
						this.gameView.quitarPlanta2(this);
						this.gameView.quitarCaminantes();
					}
				}

				currentY = 0;

			}
		}
		// Segundo personaje al ascensor
		else {
			if (this.gameView.getOcupantes() == 1) {
				if ((moveElevator == true)
						&& (this.x < (this.gameView.getWidth() - 90))) {
					currentY = 2 * height;
					this.x = this.x + 10;

				} else {
					if ((this.x >= (this.gameView.getWidth() - 90))
							&& (moveElevator == true)) {
						this.enAscensor = true;
						moveElevator = false;
						// this.gameView.añadirAscensor(this);
						this.gameView.setOcupantes();
						if (this.y > 270) {
							this.gameView.quitarPlanta1(this);
							this.gameView.quitarCaminantes();
						} else {
							this.gameView.quitarPlanta2(this);
							this.gameView.quitarCaminantes();
						}
					}

					currentY = 0;

				}
			}
		}
		// Del ascensor a planta 1
		if ((this.toPlanta1) && (this.x > this.xInicial)
				&& (onMovementUp == false)) {
			currentY = 1 * height;
			this.x = this.x - 10;
			if (this.x == this.xInicial) {
				this.toPlanta1 = false;
				this.gameView.añadirPlanta1(this);
				// this.gameView.quitarAscensor(this);
				this.gameView.quitarOcupantes();
				this.gameView.quitarCaminantes();
				if (gameView.getOcupantes() > 0) {
					ArrayList<Sprite> list = gameView.getListaSprites();
					for (int i = list.size() - 1; i >= 0; i--) {
						if (list.get(i).getX() > 200) {
							list.get(i).x = gameView.getWidth() - 140;
						}
					}
				}
			}
		}
		// Del ascensor a planta 2
		if ((this.toPlanta2) && (this.x > this.xInicial)
				&& (onMovementDown == false)) {
			currentY = 1 * height;
			this.x = this.x - 10;
			if (this.x == this.xInicial) {
				this.toPlanta2 = false;
				this.gameView.añadirPlanta2(this);
				// this.gameView.quitarAscensor(this);
				this.gameView.quitarOcupantes();
				this.gameView.quitarCaminantes();
				if (gameView.getOcupantes() > 0) {
					ArrayList<Sprite> list = gameView.getListaSprites();
					for (int i = list.size() - 1; i >= 0; i--) {
						if (list.get(i).getX() > 200) {
							list.get(i).x = gameView.getWidth() - 140;
						}
					}
				}
			}
		}
		// Subir ascensor
		if ((onMovementUp == true)
				&& (this.x >= (this.gameView.getWidth() - 140))
				&& (this.y > 270)) {
			this.y = this.y - 10;
		}
		if ((this.y == 270) && (onMovementUp == true)) {
			onMovementUp = false;
			this.gameView.setPlanta2(true);
			this.gameView.setMoving(false);
		}
		// Bajando ascensor
		if ((onMovementDown == true)
				&& (this.x >= (this.gameView.getWidth() - 140) && this.y < 650)) {
			this.y = this.y + 10;
		}
		if ((this.y == 650) && (onMovementDown == true)) {
			onMovementDown = false;
			this.gameView.setPlanta2(false);
			this.gameView.setMoving(false);
		}
		rect_char = new Rect(currentX, currentY, currentX + width, currentY
				+ height);
		rect_png = new Rect(x, y, x + width, y + width);
		canvas.drawBitmap(bmp, rect_char, rect_png, null);
	}

	public boolean isClick(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;

	}

	public void toElevator() {
		moveElevator = true;

	}

	public void toPlanta1() {
		toPlanta1 = true;
	}

	public void toPlanta2() {
		toPlanta2 = true;
	}

	public int getId() {
		return this.nombre;
	}

	public void onMovementUp() {
		onMovementUp = true;

	}

	public void onMovementDown() {
		onMovementDown = true;

	}
}
