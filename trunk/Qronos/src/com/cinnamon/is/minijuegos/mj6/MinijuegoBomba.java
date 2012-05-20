package com.cinnamon.is.minijuegos.mj6;

import com.cinnamon.is.R;
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
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MinijuegoBomba extends Minijuego implements SensorEventListener {
	/** Called when the activity is first created. */

	private final float explosion = 8, peligro = 6;
	private float curX = 0, curY = 0, curZ = 0;
	private Canvas canvas;
	private Sensor SensorOrientacion;
	private SensorManager sm;
	private GameView vista;
	private int idMJ;
	protected boolean superado;
	protected int modo = Dialogos.DIALOG_ARCADE;
	private UtilQR q;

	/** PARA LA CUENTA ATRAS **/
	private CuentaAtras cuenta;
	private TextView text;
	private final long startTime = 10000;
	private final long interval = 1000;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** contador **/
		/*
		 * cuenta = new CuentaAtras(startTime, interval); text= (TextView)
		 * findViewById(R.id.textVCuenta); text.setText(text.getText() +
		 * String.valueOf(startTime));
		 */
		// TODO 1¼ Cambio
		Toast.makeText(this, "Coloca el movil en posici—n vertical", 4000)
				.show();
		//
		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		vista = new GameView(this, display.getWidth(), display.getHeight(),
				this);
		// setContentView(R.layout.cuentaatrasbomba);
		setContentView(vista);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		SensorOrientacion = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// cuenta.start();
	}

	@Override
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
	protected void onDestroy() {
		finalizar(true);
		super.onDestroy();

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
		return;
	}

	@Override
	protected void onResume() {
		super.onResume();
		sm.registerListener(this, SensorOrientacion,
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

	public void onAccuracyChanged(final Sensor sensor, final int accuracy) {
		return;
	}

	public void onSensorChanged(final SensorEvent event) {

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

	@Override
	public boolean onTouchEvent(final MotionEvent event) {

		if (vista.getExplosion() == true) {
			vista.setExplosion(false);
			this.onResume();
		} else {
			// loop.stop();
		}
		return true;
	}

	@Override
	public void finalizar(final boolean s) {
		// Para tiempo
		superado = s;
		if (s) {
			setResult(vista.getScore());
		}
		// Creo el bundle con la info usando strings genericos de clase
		// Props.Comun
		Bundle b = new Bundle();
		b.putInt(Props.Comun.SCORE, vista.getScore());
		b.putBoolean(Props.Comun.SUPERADO, superado);
		// Devuelvo resultado a actividad padre
		Launch.returnActivity(this, b, RESULT_OK);

		this.finish();
	}

	public void pulsarCamara() {
		q = new UtilQR(this);
		q.lanzarQRTiempo();
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		String contents = q.getRawQR(requestCode, resultCode, data);
		if (requestCode == UtilQR.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				if (contents.equals("finbomba")) {
					superado = true;
					finalizar(superado);
					Toast.makeText(this, "Enhorabuena te has pasado el juego",
							5).show();
				}
			} else if (resultCode == RESULT_CANCELED) {
				superado = false;
				vista.setExplosion(true);
				// Handle cancell
			}
		}

	}

	// cuenta atras inicial class
	public class CuentaAtras extends CountDownTimer {

		public CuentaAtras(final long startTime, final long intervalo) {
			super(startTime, intervalo);
		}

		@Override
		public void onFinish() {
			text.setText("Time's up!");
			/*
			 * timeElapsedView.setText("Time Elapsed: " +
			 * String.valueOf(startTime));
			 */
		}

		@Override
		public void onTick(final long millisRestantes) {
			/*
			 * text.setText("Time remain:" + millisRestantes); timeElapsed =
			 * startTime - millisRestantes;
			 * timeElapsedView.setText("Time Elapsed: " +
			 * String.valueOf(timeElapsed));
			 */
		}
	}

}
