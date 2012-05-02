package com.cinnamon.is.minijuegos.mj2;

import java.io.IOException;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.comun.Props;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ToposMJ extends Minijuego implements OnTouchListener {

	MediaPlayer introTheme, efec, efec2;
	GameView ourSurfaceView;
	boolean vi;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ourSurfaceView = new GameView(this, this);
		ourSurfaceView.setOnTouchListener(this);
		introTheme = MediaPlayer.create(ToposMJ.this, R.raw.musicatopo);
		efec = MediaPlayer.create(ToposMJ.this, R.raw.bip);
		efec2 = MediaPlayer.create(ToposMJ.this, R.raw.aplasta);
		//lanzaExitDialog();
	}

	public void restartActivity() {
		super.onRestart();
	}

	private void lanzarAvisoMJ(String texto, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(texto).setNegativeButton("Empezar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						ourSurfaceView.finJuego = false;
						ourSurfaceView.numTopos = 30;
						introTheme.setVolume(0.4f, 0.4f);
						introTheme.start();
						setContentView(ourSurfaceView);

						dialog.cancel();
					}
				});
		builder.show();
	}


	/*
	 * @Override protected void onPause() { // TODO Auto-generated method stub
	 * super.onPause(); introTheme.release(); ourSurfaceView.loop.stop(); }
	 */

	public void onHomePressed() {
		onHomePressed();
		ourSurfaceView.finJuego = true;

	}

	/*
	 * public void onBackPressed() { onDestroy(); ourSurfaceView.finJuego=true;
	 * lanzarAvisoMJ2("�Desea salir?" ,"QRonos Topos");
	 * 
	 * }
	 */
	/*
	 * protected void onDestroy(){ super.onDestroy(); introTheme.release();
	 * ourSurfaceView.loop.stop(); }
	 */
	@Override
	protected void onResume() {
		introTheme.start();
		ourSurfaceView.loop.setRunning(true);
		//ourSurfaceView.loop.start();
		super.onResume();
	}

	private void lanzaExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("�Quieres activar la vibraci�n?")
				.setCancelable(false)
				.setPositiveButton("S�", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						vi = true;
						dialog.cancel();
						lanzarAvisoMJ(
								"Aplasta todos los topos que puedas, algunos son m�s fuertes y no aplastes a los conejos.",
								"QRonos Topos");
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						vi = false;
						dialog.cancel();
						lanzarAvisoMJ(
								"Aplasta todos los topos que puedas, algunos son m�s fuertes y no aplastes a los conejos.",
								"QRonos Topos");
					}
				});
		builder.show();
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		GameView va = (GameView) v;
		float tx, ty;
		tx = v.getWidth() * (va.hoyoList.get(va.pos).x) / 40;
		ty = v.getHeight() * (va.hoyoList.get(va.pos).y) / 80;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			float x = event.getX();
			float y = event.getY();

			if (x >= tx && x <= tx + v.getWidth() / 5 && y >= ty
					&& y <= ty + v.getHeight() / 7) {
				if (va.fuerte == 1 && va.vidafuerte > 0) {
					va.vidafuerte--;
				} else {
					if (va.topoSanb == false) {
						if (va.fuerte == 0) {
							va.toposEliminados++;
							efec.start();
						} else if (va.fuerte == 1) {
							va.toposEliminados += 2;
							efec.start();
						} else if (va.fuerte == 2) {
							va.toposEliminados += 3;
							efec.start();
						} else {
							va.toposEliminados -= 2;
							efec2.start();
						}
						va.cont = 2;
						Vibrator vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						if (vi) {
							vib.vibrate(60);
						}

					}
					va.topoSanb = true;
				}
			}
			// huevo de pascua
			if (x >= v.getWidth() * 4 / 5 && x <= v.getWidth() && y >= 0
					&& y <= v.getHeight() / 10 && (va.fuerte == 3)) {
				va.toposEliminados += 10;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			/*
			 * if (va.finJuego) { this.onCreate(new Bundle()); va.finJuego =
			 * false; va.numTopos = 50; }
			 */
			break;
		}
		return false;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void reiniciar() {
		this.onResume();
	}

	public void terminar() {
		this.finish();
	}

	public void pausar() {
		this.onPause();
	}

	@Override
	public void onBackPressed() {
		pausar();
		lanzaOpcionesDialog();
	}

	public void finalizar(boolean s) {
		// Para tiempo
		superado = s;
		introTheme.stop();
		// Creo el bundle con la info usando strings genericos de clase
		// Props.Comun
		int puntuacion = ourSurfaceView.getScore() * 10;
		Bundle b = new Bundle();
		b.putInt(Props.Comun.SCORE, puntuacion);
		b.putBoolean(Props.Comun.SUPERADO, superado);
		// Devuelvo resultado a actividad padre
		Launch.returnActivity(this, b, RESULT_OK);

		setResult(puntuacion);

		this.finish();
	}

	public void parar() {
		introTheme.pause();
		efec.stop();
		efec2.stop();
		
		// ourSurfaceView.musicaOff();
		ourSurfaceView.loopStop();
		this.onPause();

	}

	protected void onDestroy() {
		finalizar(true);
		super.onDestroy();

	}
}