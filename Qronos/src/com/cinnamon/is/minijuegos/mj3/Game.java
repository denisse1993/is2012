package com.cinnamon.is.minijuegos.mj3;

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
 * Minijuego Zig Zag QR
 * 
 * @author Cinnamon Team
 * @version 1.0 24.11.2011
 */

public class Game extends Minijuego {

	private Button btnQR;
	private TextView texto;
	// private UtilQR QR;
	// para saber hasta donde lleva le�dos en orden (0-10)
	private int cuentaValida;
	private boolean ordenCorrecto = false;
	private int tiempo;

	/**
	 * @param savedInstanceState
	 * @param args
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_mj3_iniciozigzagqr);

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
				// Si la lectura se realiza correctamente se llega aqui�
				// Si el c�digo no es el c�digo objetivo, seguir intentandolo

				switch (valorInt(contents)) {
				case 1:
					if (cuentaValida == 0) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("�BIEN!\nYa has conseguido el primer c�digo, �corre al segundo!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("�MAL!\nEse no es el orden correcto, vuelve a empezar");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
					}
					break;
				case 2:
					if (cuentaValida == 1) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("�BIEN!\nYa has conseguido el segundo c�digo, �corre al tercero!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("�MAL!\nEse no es el orden correcto, vuelve a empezar");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
					}
					break;
				case 3:
					if (cuentaValida == 2) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("�BIEN!\nYa has conseguido el tercer c�digo, �corre al cuarto!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("�MAL!\nEse no es el orden correcto, vuelve a empezar");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
					}
					break;
				case 4:
					if (cuentaValida == 3) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("�BIEN!\nYa has conseguido el cuarto c�digo, �corre al quinto!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("�MAL!\nEse no es el orden correcto, vuelve a empezar");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
					}
					break;
				case 5:
					if (cuentaValida == 4) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("�BIEN!\nYa has conseguido el quinto c�digo, �corre al sexto!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("�MAL!\nEse no es el orden correcto, vuelve a empezar");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
					}
					break;
				case 6:
					if (cuentaValida == 5) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("�BIEN!\nYa has conseguido el sexto c�digo, �corre al s�ptimo!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("�MAL!\nEse no es el orden correcto, vuelve a empezar");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
					}
					break;
				case 7:
					if (cuentaValida == 6) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("�BIEN!\nYa has conseguido el s�ptimo c�digo, �corre al octavo!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("�MAL!\nEse no es el orden correcto, vuelve a empezar");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
					}
					break;
				case 8:
					if (cuentaValida == 7) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("�BIEN!\nYa has conseguido el octavo c�digo, �corre al noveno!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("�MAL!\nEse no es el orden correcto, vuelve a empezar");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
					}
					break;
				case 9:
					if (cuentaValida == 8) {
						cuentaValida++;
						texto.setTextColor(Color.rgb(0, 221, 0));
						texto.setText("�BIEN!\nYa has conseguido el noveno c�digo, �corre al �ltimo!");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(300);
					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("�MAL!\nEse no es el orden correcto, vuelve a empezar");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
					}
					break;
				case 10:
					if (cuentaValida == 9) {
						cuentaValida++;
						ordenCorrecto = true;

					} else {
						cuentaValida = 0;
						texto.setTextColor(Color.rgb(221, 0, 0));
						texto.setText("�MAL!\nEse no es el orden correcto, vuelve a empezar");
						Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
						vibr.vibrate(500);
					}
					break;
				default:
					texto.setTextColor(Color.RED);
					texto.setText("El c�digo QR que est�s leyendo no pertenece a este juego.");
				}

				if (ordenCorrecto) {
					Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
					vibr.vibrate(300);
					// poner aqui un mensaje que pueda leer el usuario con el
					// tiempo
					// que ha tardado
					texto.setTextColor(Color.rgb(0, 221, 0));
					texto.setText("�BIEN!\nYa has conseguido el noveno c�digo, �corre al �ltimo!");
					finalizar(true);
				}

			}

		} else if (resultCode == RESULT_CANCELED) {
			// Handle cancel
			// Si se cancela la lectura se llega aqu�
			texto.setText("Lee de nuevo el c�digo QR");
		}
	}

	private int valorInt(final String contents) {
		// TODO Auto-generated method stub
		if (contents.equals("primero")) {
			return 1;
		}
		if (contents.equals("segundo")) {
			return 2;
		}
		if (contents.equals("tercero")) {
			return 3;
		}
		if (contents.equals("cuarto")) {
			return 4;
		}
		if (contents.equals("quinto")) {
			return 5;
		}
		if (contents.equals("sexto")) {
			return 6;
		}
		if (contents.equals("septimo")) {
			return 7;
		}
		if (contents.equals("octavo")) {
			return 8;
		}
		if (contents.equals("noveno")) {
			return 9;
		}
		if (contents.equals("decimo")) {
			return 10;
		}
		return -1;
	}

	// protected void finishTime() {
	// elapsed = System.nanoTime() - start;
	// }

	@Override
	protected int calcularPuntuacion() {
		int score = MAX_SCORE;
		tiempo = (int) (elapsed * tos);
		
		if (tiempo < 40) {
			return score;
		} else if (tiempo >= 40 && tiempo < 50) {
			return score - 200;
		} else if (tiempo >= 50 && tiempo < 60) {
			return score - 400;
		} else if (tiempo >= 60 && tiempo < 70) {
			return score - 600;
		} else if (tiempo >= 70 && tiempo < 80) {
			return score - 800;
		} else {
			return 0;
		}
	}
}
