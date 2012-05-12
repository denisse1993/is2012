package com.cinnamon.is.minijuegos.mj5;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.comun.Props;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Minijuego Encuentra QR
 * 
 * @author Cinnamon Team
 * @version 1.0 24.11.2011
 */
public class Game extends Minijuego {

	private Button btnQR;
	private TextView texto;
	// private UtilQR QR;
	private int tiempo;

	/**
	 * @param savedInstanceState
	 * @param args
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_mj5_inicioencqr);

		startTime();

		texto = (TextView) findViewById(R.id.textView);
		btnQR = (Button) findViewById(R.id.leer);
		// QR= new UtilQR(this);
		btnQR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				try {
					// QR.lanzarQR();
					// lanza el scan del Barcode Scanner
					Intent intent = new Intent(Props.Action.SCAN);
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
				String contents = intent.getStringExtra("SCAN_RESULT");
				// Handle successful scan
				// Si la lectura se realiza correctamente se llega aquií
				// Si el código no es el código objetivo, seguir intentandolo
			  if (contents.equals("objetivo")||contents.equals("objñ")||contents.equals("objsad")
				  ||contents.equals("kasidaj")||contents.equals("objeti")||contents.equals("objetivoi")
				  ||contents.equals("objetivp")||contents.equals("objetiva")||contents.equals("objetivas")
				  ||contents.equals("odsfgvd")){
				  
				if (!contents.equals("objetivo")) {
					texto.setText("¡MAL!\nEse no es el código que tienes que encontrar\n¡SIGUE BUSCANDO!");
					Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
					vibr.vibrate(500);
				}
				if (contents.equals("objetivo")) {
					Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
					vibr.vibrate(300);
					// poner aqui un mensaje que pueda leer el usuario con el
					// tiempo
					// que ha tardado
					texto.setTextColor(Color.rgb(0, 221, 0));
					texto.setText("¡BIEN!\nYa has conseguido el noveno código, ¡corre al último!");

					finalizar(true);
				}
			 }
			  else{
				  texto.setTextColor(Color.RED);
				  texto.setText("El código QR que has leído no pertenece a este juego");
			  }
				// Comprobar que el código QR sea de este juego
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				// Si se cancela la lectura se llega aquí
				texto.setTextColor(Color.RED);
				texto.setText("Lee de nuevo el código QR");
			}
		}
	}

	@Override
	protected int calcularPuntuacion() {
		int score = MAX_SCORE;
		tiempo = (int) (elapsed * tos);
		//25 segundos para 1000 puntos, con 10 segundos de aumento
		if (tiempo < 25) {
			return score;
		} else if (tiempo >= 25 && tiempo < 35) {
			return score - 200;
		} else if (tiempo >= 35 && tiempo < 45) {
			return score - 400;
		} else if (tiempo >= 45 && tiempo < 55) {
			return score - 600;
		} else if (tiempo >= 55 && tiempo < 65) {
			return score - 800;
		} else {
			return 0;
		}
	}
}
