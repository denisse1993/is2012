package com.cinnamon.is.minijuegos.mj4;

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

public class Game extends Minijuego {

	private Button btnQR;
	private TextView texto;
	// private UtilQR QR;
	private int tiempo;
	// para saber hasta donde lleva leídos en orden (0-5)
	private int cuentaValida;
	private boolean ordenCorrecto = false;

	/**
	 * @param savedInstanceState
	 * @param args
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_mj4_iniciocadqr);

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
					// Intent intent = new Intent(Props.Action.SCAN);
					// esto no funciona con el nuevo ZXing(117)
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

				switch (valorInt(contents)) {
				case 1:
					if (cuentaValida == 0) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("¡BIEN!\nYa has conseguido el primer código, ¡busca el segundo!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("¡MAL!\nEse no es el orden correcto, vuelve a empezar");
					}
					break;
				case 2:
					if (cuentaValida == 1) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("¡BIEN!\nYa has conseguido el segundo código, ¡busca el tercero!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("¡MAL!\nEse no es el orden correcto, vuelve a empezar");
					}
					break;
				case 3:
					if (cuentaValida == 2) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("¡BIEN!\nYa has conseguido el tercer código, ¡busca el cuarto!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("¡MAL!\nEse no es el orden correcto, vuelve a empezar");
					}
					break;
				case 4:
					if (cuentaValida == 3) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("¡BIEN!\nYa has conseguido el cuarto código, ¡busca el último!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("¡MAL!\nEse no es el orden correcto, vuelve a empezar");
					}
					break;
				case 5:
					if (cuentaValida == 4) {
						cuentaValida++;
						ordenCorrecto = true;
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);

					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("¡MAL!\nEse no es el orden correcto, vuelve a empezar");
					}
					break;
				default:
					texto.setTextColor(Color.RED);
					texto.setText("El código QR que estás leyendo no pertenece a este juego.");
				}

				if (ordenCorrecto) {

					Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
					vibr.vibrate(300);

					// poner aqui un mensaje que pueda leer el usuario con el
					// tiempo
					// que ha tardado
					texto.setTextColor(Color.rgb(0, 221, 0));
					texto.setText("¡BIEN!\nYa has conseguido el noveno código, ¡corre al último!");

					finalizar(true);

				}

			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				// Si se cancela la lectura se llega aquí
				texto.setText("Lee de nuevo el código QR");
			}
		}
	}

	private int valorInt(final String contents) {
		// TODO Auto-generated method stub
		if (contents.equals("cadena1")) {
			return 1;
		}
		if (contents.equals("cadena2")) {
			return 2;
		}
		if (contents.equals("cadena3")) {
			return 3;
		}
		if (contents.equals("cadena4")) {
			return 4;
		}
		if (contents.equals("cadena5")) {
			return 5;
		}
		return -1;
	}

	@Override
	protected int calcularPuntuacion() {
		int score = MAX_SCORE;
		tiempo = (int) (elapsed * tos);
		// tiempos de prueba para probar la aplicacion, habría que mirar cuando
		// se tarda en cada uno, o dejarlo para todos igual
		if (tiempo < 30) {
			return score;
		} else if (tiempo >= 30 && tiempo < 45) {
			return score - 200;
		} else if (tiempo >= 45 && tiempo < 60) {
			return score - 400;
		} else if (tiempo >= 60 && tiempo < 75) {
			return score - 600;
		} else if (tiempo >= 90 && tiempo < 105) {
			return score - 800;
		} else {
			return 0;
		}
	}
}
