//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//

/**
 * Paquete para clases del juego
 */
package com.cinnamon.is.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Intents;

/**
 * Actividad del juego principal, se mantiene en ejecución durante el desarrollo
 * del juego, llamando al resto de Actividades
 * 
 * @author Cinnamon Team
 * @version 1.5 25.11.2011
 */
public class InGame extends Activity implements OnClickListener {

	/**
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	/**
	 * String para guardar el raw del codigo QR leido
	 */
	private String qrLeido;

	/**
	 * Array de String para guardar la ruta de los minijuegos, se usa para
	 * comprobar que el minijuego que se va a lanzar es correcto
	 */
	private String[] minijuegos = { "ascensor.ASCENSORMJ",
			"cuadrado.CUADRADOMJ", "puzzle.PUZZLEMJ", "reinas.REINASMJ" };

	/**
	 * Constantes para controlar que actividad tratar en onActivityResult() en
	 * función de la actividad lanzada.
	 */
	private static final int cMapa = 0;
	private static final int cCamara = 1;
	private static final int cMochila = 2;
	private static final int cMinijuego = 3;

	/**
	 * Jugador actual del juego
	 */
	private Jugador jugador;

	private Button bOpciones;
	private Button bReinas;

	// texview de prueba para comprobar que funciona lo de la puntuacion
	private TextView tvScoreActual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame);
		inicializar();

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);

		// pone el textview la puntuacion del jugador
		tvScoreActual.setText(String.valueOf(jugador.getScore()));

		// establecer hoja de libro usar la variable hoja de jugador
		// jugador.getHoja();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mDbHelper.close();
	}

	// este metodo se ejecuta al volver de la aplicacion lanzada con forresult
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		boolean scanDirecto, mochilaDirecto, mapaDirecto;
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case cMapa:
				scanDirecto = data.getBooleanExtra(Intents.Comun.INGAME_SCAN,
						false);
				mochilaDirecto = data.getBooleanExtra(Intents.Action.MOCHILA,
						false);
				jugador = (Jugador) data
						.getSerializableExtra(Intents.Comun.JUGADOR);
				updateJugador();
				if (scanDirecto)
					lanzaScan();
				else if (mochilaDirecto)
					lanzaMochila();
				else {
					// hacer nada
				}
				break;

			case cCamara:
				qrLeido = data.getStringExtra("SCAN_RESULT");
				lanzaMinijuego(qrLeido);
				break;
			case cMochila:
				scanDirecto = data.getBooleanExtra(Intents.Comun.INGAME_SCAN,
						false);
				mapaDirecto = data.getBooleanExtra(Intents.Action.MAPA, false);
				jugador = (Jugador) data
						.getSerializableExtra(Intents.Comun.JUGADOR);
				updateJugador();
				if (scanDirecto)
					lanzaScan();
				else if (mapaDirecto)
					lanzaMapa();
				else {
					// hacer nada
				}
				break;
			case cMinijuego:
				jugador = (Jugador) data
						.getSerializableExtra(Intents.Comun.JUGADOR);
				updateJugador();
				break;
			}

		} else if (resultCode == RESULT_CANCELED) {
			// si no ha hecho nada
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menuingame, menu);
		return true;// debe devolver true para que el menu se muestre
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.bCamara:
			lanzaScan();
			break;
		case R.id.bMapa:
			lanzaMapa();
			break;
		case R.id.bMochila:
			lanzaMochila();
			break;
		}
		return false;// devuelve falso para proceso normal
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("¿Quieres salir al menu principal?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								InGame.this.finish();
								Intent iMainMenu = new Intent(
										Intents.Action.MAINMENU);
								iMainMenu.putExtra(Intents.Comun.JUGADOR,
										jugador);
								startActivity(iMainMenu);
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bOpciones:
			// El boton bOpciones se comporta como el boton fisico MENU del
			// dispositivo
			this.getWindow().openPanel(Window.FEATURE_OPTIONS_PANEL,
					new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MENU));
			break;
		case R.id.bReinas:
			Intent iReinas = new Intent(Intents.Action.ASCENSORMJ);
			iReinas.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivityForResult(iReinas, cMinijuego);
			break;
		default:
			break;
		}
	}

	/**
	 * Metodo auxiliar para inicializar la actividad
	 */
	private void inicializar() {
		tvScoreActual = (TextView) findViewById(R.id.tVscoreActual);
		bOpciones = (Button) findViewById(R.id.bOpciones);
		bOpciones.setOnClickListener(this);

		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		// prueba lanzar juego
		bReinas = (Button) findViewById(R.id.bReinas);
		bReinas.setOnClickListener(this);
	}

	/**
	 * Metodo que actualiza al jugador accediendo a la base de datos
	 */
	private void updateJugador() {
		mDbHelper.updateRow("'" + jugador.getNombre() + "'",
				jugador.getScore(), jugador.getHoja(), jugador.getMochila(),
				jugador.getFase1(), jugador.getFase2(), jugador.getFase3(),
				jugador.getFase4());
		// pone el textview la puntuacion del jugador
		tvScoreActual.setText(String.valueOf(jugador.getScore()));
	}

	/**
	 * Metodo que lanza la mochila
	 */
	private void lanzaMochila() {
		Intent iMochila = new Intent(Intents.Action.MOCHILA);
		iMochila.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivityForResult(iMochila, cMochila);
	}

	/**
	 * Metodo que lanza el mapa
	 */
	private void lanzaMapa() {
		Intent iMapa = new Intent(Intents.Action.MAPA);
		iMapa.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivityForResult(iMapa, cMapa);
	}

	/**
	 * Metodo que lanza el scaneo de codigos QR
	 */
	private void lanzaScan() {
		Intent iScan = new Intent(Intents.Action.SCAN);
		iScan.putExtra("SCAN_MODE", "QR_CODE_MODE");
		iScan.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivityForResult(iScan, cCamara);
	}

	/**
	 * Método que lanza el minijuego del "qrMinijuego", comprobando que el
	 * codigoQR leido es correcto para lanzar la actividad asociada
	 * 
	 * @param qrMiniJuego
	 *            el nombre del minijuego a lanzar
	 */
	private void lanzaMinijuego(String qrMiniJuego) {

		for (int i = 0; i < minijuegos.length; i++) {
			// comprobacion de que existe el minijuego leido
			if (minijuegos[i].equals(qrMiniJuego)) {
				Intent iMinijuego = new Intent(Intents.Comun.BASE_MINIJUEGOS
						+ qrMiniJuego);
				iMinijuego.putExtra(Intents.Comun.JUGADOR, jugador);
				startActivityForResult(iMinijuego, cMinijuego);
			}
		}
		// no se lanza el minijuego porque no se corresponde con uno que exista
	}

}
