package com.cinnamon.is.minijuegos.mj7;

import java.util.HashMap;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.comun.Props;

//este Intent permite lanzar el lector de c�digos QR (cambiar interfaz)

public class Game extends Minijuego {

	private HashMap<String, Boolean> leidas;
	private int todas;
	private Button btnQR;
	private TextView texto;
	private long start;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.inicioletqr);

		start=System.nanoTime();

		this.todas = 0;
		this.leidas = new HashMap<String, Boolean>();
		this.texto = (TextView) findViewById(R.id.textView);
		this.btnQR = (Button) findViewById(R.id.leer);
		this.btnQR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				try {

					// lanzamos el lector del c�digoQR
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					startActivityForResult(intent, 0);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}
			}

		});
	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode,
			final Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				// Handle successful scan
				// Si la lectura se realiza correctamente se llega aqu�
				String contents = intent.getStringExtra("SCAN_RESULT");

				// Si es alguna de las letras que estamos buscando
				if ((contents.equals("R")) || (contents.equals("A"))
						|| (contents.equals("P")) || (contents.equals("I"))
						|| (contents.equals("D")) || (contents.equals("O"))) {

					// si aun no hemos le�do todos los QR
					if (!repetida(contents)) {
						// si no leemos uno repetido
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
						almacenarLetra(contents);

						if (!estanTodas()) {
							// si es una letra nueva y no es la �ltima
							this.texto.setTextColor(Color.GREEN);
							this.texto.setText("�BIEN! Acabas de encontrar una nueva letra, sigue leyendo c�digos para conseguir el resto");
						}
						if (estanTodas()) {
							Bundle b= new Bundle();
							b.putLong("inicio", start);
							Launch.lanzaActivity(this, Props.Action.MJ7W, b, 7);
						}

					} else {
						// si leemos uno repetido
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
						this.texto.setTextColor(Color.RED);
						this.texto.setText("Esta letra ya ha sido le�da anteriormente.");
					}

				}// if de si es una letra valida

				// si el c�digo que hemos le�do no es ninguna de las letras que
				// buscamos
				if ((!contents.equals("R")) && (!contents.equals("A"))
						&& (!contents.equals("P")) && (!contents.equals("I"))
						&& (!contents.equals("D")) && (!contents.equals("O"))) {

					this.texto.setTextColor(Color.RED);
					this.texto.setText("El c�digo QR que est�s leyendo no pertenece a este juego.");
				}// if de cuando no es ninguna

			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				// Si se cancela la lectura se llega aqu�
				this.texto.setText("Lee de nuevo el c�digo QR");
			}// resultcanceled
		}// requestcode
		if(requestCode== 7){ //Cuando ya se haya calculado la puntuaci�n
			//Hace lo mismo que finalizar(true)
			if (resultCode == RESULT_OK){
				this.superado = true;
				int puntuacion=intent.getIntExtra("puntuacion", 0);
				Bundle b = new Bundle();
				b.putInt(Props.Comun.SCORE, puntuacion);
				b.putBoolean(Props.Comun.SUPERADO, this.superado);
				Launch.returnActivity(this, b, RESULT_OK);
			}
		}
	}// fin del metodo

	/******************************* METODOS AUXILIARES *********************************/

	private void almacenarLetra(final String contents) {
		// asignandole un �ndice a cada letra, cambiar su booleano asociado en
		// el
		// array leidas. Valorar si es m�s �til usar un HashMap
		this.leidas.put(contents, true);
		this.todas++;
	}

	private boolean repetida(final String contents) {
		// recorrer la estructura "leidas" comprobando si el nuevo QR ya estaba
		// le�do.
		if (this.leidas.containsKey(contents)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean estanTodas() {
		// si al recorrer la estructura "leidas" todas las letras tienen el
		// booleano
		// asociado al mismo valor es porque ya no quedan m�s por leer o bien...
		if (this.todas == 6) {
			return true;
		} else {
			return false;
		}
	}
	


}
