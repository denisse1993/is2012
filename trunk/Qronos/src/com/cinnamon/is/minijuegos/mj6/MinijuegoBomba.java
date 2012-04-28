package com.cinnamon.is.minijuegos.mj6;

import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.UtilQR;
import com.cinnamon.is.comun.dialog.Dialogos;
import com.cinnamon.is.presentacion.Intro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

public class MinijuegoBomba extends Minijuego implements SensorEventListener {
	/** Called when the activity is first created. */

	private float explosion = 8, peligro = 6;
	private float curX = 0, curY = 0, curZ = 0;
	private Canvas canvas;
	private Sensor SensorOrientacion;
	private SensorManager sm;
	private GameView vista;
	private int idMJ;
	protected boolean superado;
	protected int modo = Dialogos.DIALOG_ARCADE;
	private UtilQR q;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		vista = new GameView(this, display.getWidth(), display.getHeight(),
				this);
		setContentView(vista);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		SensorOrientacion = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

	protected void onDestroy() {
		finalizar(true);
		super.onDestroy();

	}

	protected void lanzaOpcionesDialog() {
		// TODO Ahora mismo muestra 3 opciones, continuar, reiniciar o salir
		// para el mj y luego lanza las opciones
		// Launch.lanzaConfirmacion("Salir del minijuego",
		// "¿Quieres salir del minijuego sin completarlo?", this);
		Launch.lanzaOpciones(this, "Juego Pausado", modo, this);
	}

	@Override
	public void onBackPressed() {
		this.onStop();
		lanzaOpcionesDialog();
	}

	/*
	 * public void onBackPressed() { onDestroy(); //TODO METER dialogo, quiere
	 * salir? si (pausar)/ no(reiniciar) AlertDialog LDialog = new
	 * AlertDialog.Builder(this) .setTitle("Salir")
	 * .setMessage("¿Está seguro de que desea salir?")
	 * //.setPositiveButton("ok", null) .setPositiveButton("Si", new
	 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
	 * dialog, int id) { finalizar(true); } }) .setNegativeButton("No", new
	 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
	 * dialog, int id) { onResume(); }}) .create(); LDialog.show();
	 * 
	 * }
	 */
	public void onHomePressed() {

	}

	@Override
	protected void onResume() {
		super.onResume();
		sm.registerListener(this, (Sensor) SensorOrientacion,
				SensorManager.SENSOR_DELAY_GAME);
		// el sensormanager.sensor... es el rate para ver cada cuanto actualiza
		// la captura del sensor
	}

	@Override
	protected void onStop() {
		sm.unregisterListener(this);
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		sm.unregisterListener(this);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {

		curX = event.values[0];
		curY = event.values[1];
		curZ = event.values[2];
		float absY = Math.abs(curY);
		float absZ = Math.abs(curZ);
		vista.setY(curY);
		vista.setZ(curZ);
		Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		if (((absY > peligro) && (absY < explosion))
				|| ((absZ > peligro) && ((absZ < explosion)))) {
			v.vibrate(100);
			vista.setEstado(2); // estado amarillo
			vista.invalidate();
			vista.restaPuntuacion();
		} else if ((absY > explosion) || (absZ > explosion)) {
			vista.setEstado(3);
			vista.invalidate();
			vista.setExplosion(true);
			this.onStop();
			v.cancel();
			// setContentView(R.layout.leerqr);
		} else {
			vista.setEstado(1);
			vista.invalidate();
			v.cancel();
		}

	}

	public boolean onTouchEvent(MotionEvent event) {

		if (vista.getExplosion() == true) {
			vista.setExplosion(false);
			this.onResume();
		} else {
			// loop.stop();
		}
		return true;
	}

	public void finalizar(boolean s) {
		// Para tiempo
		superado = s;
		// Creo el bundle con la info usando strings genericos de clase
		// Props.Comun
		Bundle b = new Bundle();
		b.putInt(Props.Comun.SCORE, vista.getScore());
		b.putBoolean(Props.Comun.SUPERADO, superado);
		// Devuelvo resultado a actividad padre
		Launch.returnActivity(this, b, RESULT_OK);

		if (s) {
			setResult(vista.getScore());
		}
		this.finish();
	}

	public void pulsarCamara() {

		q = new UtilQR(this);
		q.lanzarQRTiempo();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String contents = q.getRawQR(requestCode, resultCode, data);
		if (requestCode == UtilQR.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				if (contents.equals("finbomba")) {
					superado = true;
					finalizar(superado);
					Toast.makeText(this, "Enhorabuena te has pasado el juego",
							5);
				}
			} else if (resultCode == RESULT_CANCELED) {
					superado = false;
				// Handle cancell
			}
		}

	}

}
