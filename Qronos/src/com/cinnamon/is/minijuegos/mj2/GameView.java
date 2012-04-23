package com.cinnamon.is.minijuegos.mj2;

import java.util.ArrayList;
import java.util.Random;

import com.cinnamon.is.R;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class GameView extends SurfaceView {

	private ToposMJ activity;
	GameLoopTopos loop;
	SurfaceHolder ourHolder;
	Bitmap fondoTopo;
	Bitmap topo, topoSan, topof, topofSan, sangre, conejo, conejoTopo, est;
	int toposEliminados;
	boolean finJuego, arriba, topoSanb = false;
	int numTopos;
	public ArrayList<Hoyo> hoyoList;
	int pos;
	int fuerte;
	Canvas c;
	int cont;
	float hx, hy;
	int vidafuerte = 2;

	public GameView(Context context, ToposMJ ac) {
		super(context);
		activity = ac;
		loop = new GameLoopTopos(this, ac);
		ourHolder = getHolder();
		ourHolder.addCallback(new Callback() {

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub

			}

			public void surfaceCreated(SurfaceHolder holder) {
				toposEliminados = 0;
				numTopos = 50;
				arriba = false;
				cont = 10;
				hx = hy = 0;
				hoyoList = new ArrayList<Hoyo>();
				hoyoList.add(new Hoyo(3, 6));// hoyo1
				hoyoList.add(new Hoyo(27, 8));// hoyo2
				hoyoList.add(new Hoyo(15, 21));// hoyo3
				hoyoList.add(new Hoyo(29, 37));// hoyo4
				hoyoList.add(new Hoyo(3, 37));// hoyo5
				hoyoList.add(new Hoyo(14, 49));// hoyo6
				hoyoList.add(new Hoyo(1, 62));// hoyo7
				hoyoList.add(new Hoyo(28, 65));// hoyo8

				creaFondo(R.drawable.fondo);
				creaTopo(R.drawable.topo3);
				creaTopof(R.drawable.topocasco3);
				creaConejoTopo(R.drawable.topoconejo3);
				creaSan(R.drawable.sangre);
				creaCon(R.drawable.conejo3);
				creaEst(R.drawable.estrella);

				setClickable(true);

				loop.start();

			}

			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				/*finJuego = true;
				loop.stop();*/

			}

		});

	}

	private void creaFondo(int resource) {
		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = getWidth();
		int height = getHeight();
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width, height, true);
		this.fondoTopo = tmp;
	}

	private void creaTopo(int resource) {
		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = getWidth();
		int height = getHeight();
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width / 5, height / 8,
				true);
		this.topo = tmp;
	}

	private void creaTopof(int resource) {
		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = getWidth();
		int height = getHeight();
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width / 5, height / 8,
				true);
		this.topof = tmp;
	}

	private void creaSan(int resource) {
		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = getWidth();
		int height = getHeight();
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width / 2, height / 5,
				true);
		this.sangre = tmp;
	}

	private void creaCon(int resource) {
		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = getWidth();
		int height = getHeight();
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width / 5, height / 8,
				true);
		this.conejo = tmp;
	}

	private void creaConejoTopo(int resource) {
		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = getWidth();
		int height = getHeight();
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width / 5, height / 8,
				true);
		this.conejoTopo = tmp;
	}

	private void creaEst(int resource) {
		Bitmap pantalla = BitmapFactory
				.decodeResource(getResources(), resource);
		int width = getWidth();
		int height = getHeight();
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width / 5,
				height / 15, true);
		this.est = tmp;
	}

	protected void onDraw(Canvas canvas) {
		Paint pcom = new Paint();
		pcom.setColor(Color.YELLOW);
		pcom.setTextSize(25);
		canvas.drawBitmap(fondoTopo, 0, 0, null);
		if (!finJuego) {
			if (!arriba) {
				Random r = new Random();
				pos = r.nextInt(8);
				fuerte = r.nextInt(4);
				vidafuerte = 1;
				numTopos--;
				arriba = true;
				cont = 10;
				if (fuerte == 3)
					cont = 7;
				if (toposEliminados > 10) {
					cont = 7;
				} else if (toposEliminados > 25) {
					cont = 5;
				}
				topoSanb = false;
			}
			if (fuerte == 0) {
				if (numTopos == 1)
					finJuego = true;
				if (topoSanb) {
					canvas.drawBitmap(topo,
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 80,
							null);
					canvas.drawBitmap(sangre,
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 83,
							null);
					canvas.drawText("+1",
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 80
									- canvas.getWidth() / 16, pcom);
				} else {
					canvas.drawBitmap(topo,
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 80,
							null);
				}
			} else if (fuerte == 1) {
				if (numTopos == 1)
					finJuego = true;
				if (topoSanb) {
					canvas.drawBitmap(topof,
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 80,
							null);
					canvas.drawBitmap(sangre,
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 83,
							null);
					canvas.drawText("+2",
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 80
									- canvas.getWidth() / 16, pcom);
				} else {
					canvas.drawBitmap(topof,
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 80,
							null);
				}
			} else if (fuerte == 2) {
				if (numTopos == 1)
					finJuego = true;
				if (topoSanb) {
					canvas.drawBitmap(conejoTopo,
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 80,
							null);
					canvas.drawBitmap(sangre,
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 83,
							null);
					canvas.drawText("+3",
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 80
									- canvas.getWidth() / 16, pcom);
				} else {
					canvas.drawBitmap(conejoTopo,
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 80,
							null);
				}
			} else if (fuerte == 3) {
				if (numTopos == 1)
					finJuego = true;
				canvas.drawBitmap(conejo,
						canvas.getWidth() * (hoyoList.get(pos).x) / 40,
						canvas.getHeight() * (hoyoList.get(pos).y) / 80, null);
				if (topoSanb) {
					canvas.drawBitmap(sangre,
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 83,
							null);
					canvas.drawText("-2",
							canvas.getWidth() * (hoyoList.get(pos).x) / 40,
							canvas.getHeight() * (hoyoList.get(pos).y) / 80
									- canvas.getWidth() / 16, pcom);

				}
			}
			cont--;
			if (cont == 0 || cont < 0) {
				arriba = false;
			}
		}
		canvas.drawColor(color.black);
		String text = Integer.toString(toposEliminados);
		Paint p = new Paint();
		p.setColor(Color.RED);
		p.setTextSize(45);
		canvas.drawText(text, this.getWidth() * 5 / 6, this.getHeight() / 10, p);
		if (finJuego) {

			/*
			 * canvas.drawBitmap(est, canvas.getWidth()*1/5,
			 * canvas.getHeight()/2, null); if(toposEliminados>18){
			 * canvas.drawBitmap(est, canvas.getWidth()*2/5,
			 * canvas.getHeight()/2, null); } if(toposEliminados>30){
			 * canvas.drawBitmap(est, canvas.getWidth()*3/5,
			 * canvas.getHeight()/2, null); }
			 */
		}
	}

	public int getScore() {
		return this.toposEliminados;
	}

	public void loopStop() {
		loop.setRunning(false);

	}

}
