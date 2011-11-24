package com.cinnamon.is.game;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
 * Actividad del juego principal
 * 
 * @author Cinnamon Team
 * 
 */
public class InGame extends Activity implements OnClickListener {

	// base de datos
	private DbAdapter mDbHelper;
	private Cursor mCursor;

	private Button bOpciones;
	private Button bReinas;

	// texview de prueba para comprobar que funciona lo de la puntuacion
	private TextView tvScoreActual;

	// guarda el qr leido
	private String qrLeido;

	// lista minijuegos
	private String[] minijuegos = { "ASCENSORMJ" };

	// Constantes para controlar que actividad tratar en onActivityResult
	private static final int cMapa = 0;
	private static final int cCamara = 1;
	private static final int cMochila = 2;
	private static final int cMinijuego = 3;

	// Jugador creao en login
	private Jugador jugador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame);

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);

		tvScoreActual = (TextView) findViewById(R.id.tVscoreActual);
		bOpciones = (Button) findViewById(R.id.bOpciones);
		bOpciones.setOnClickListener(this);

		boolean scanDirecto = getIntent().getBooleanExtra(
				Intents.Comun.INGAME_SCAN, false);

		if (scanDirecto)
			lanzaScan();

		// pone el textview la puntuacion del jugador
		tvScoreActual.setText(String.valueOf(jugador.getScore()));

		// establecer hoja de libro usar la variable hoja de jugador
		// jugador.getHoja();

		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		// prueba lanzar juego
		bReinas = (Button) findViewById(R.id.bReinas);
		bReinas.setOnClickListener(this);
	}

	/*@Override
	protected void onPause() {
		super.onPause();
	}*/

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mDbHelper.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menuingame, menu);
		return true;// debe devolver true para que el menu se muestre
	}

	// @Override
	// public void onBackPressed() {
	// metodo para que hacer cuando se pulsa el
	// boton de atras ahoramismo no hace nada
	// finish();
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.bCamara:
			lanzaScan();
			break;
		case R.id.bMapa:
			Intent iMapa = new Intent(Intents.Action.MAPA);
			iMapa.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivity(iMapa);
			break;
		case R.id.bMochila:
			Intent iMochila = new Intent(Intents.Action.MOCHILA);
			iMochila.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivity(iMochila);
			break;
		}
		return false;// devuelve falso para proceso normal
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

	// este metodo se ejecuta al volver de la aplicacion
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case cMapa:
				break;
			case cCamara:
				qrLeido = data.getStringExtra("SCAN_RESULT");
				lanzaMinijuego(qrLeido);
				break;
			case cMochila:
				break;
			case cMinijuego:
				jugador = (Jugador) data
						.getSerializableExtra(Intents.Comun.JUGADOR);
				mDbHelper.updateRow("'" + jugador.getNombre() + "'",
						jugador.getScore(), jugador.getHoja(),
						jugador.getMochila(), jugador.getFase1(),
						jugador.getFase2(), jugador.getFase3(),
						jugador.getFase4());
				// pone el textview la puntuacion del jugador
				tvScoreActual.setText(String.valueOf(jugador.getScore()));
				break;
			}

		} else if (resultCode == RESULT_CANCELED) {
			// si no ha hecho nada
		}
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
				Intent iMinijuego = new Intent(Intents.Comun.BASE + qrMiniJuego);
				iMinijuego.putExtra(Intents.Comun.JUGADOR, jugador);
				startActivityForResult(iMinijuego, cMinijuego);
			}
		}
		// no se lanza el minijuego porque no se corresponde con uno que exista
	}

}
