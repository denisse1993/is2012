package com.cinnamon.is;

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

/**
 * Actividad del juego principal
 * 
 * @author Cinnamon Team
 * 
 */
public class InGame extends Activity implements OnClickListener {

	// base de datos
	private DbAdapter mDbHelper;
	private Cursor mNotesCursor;

	private Button bOpciones;
	private Button bReinas;

	// guarda el qr leido
	private String qrLeido;

	// lista minijuegos
	private String[] minijuegos = { "ASCENSORMJ" };

	// Constantes para controlar que actividad tratar en onActivityResult
	private static final int cMapa = 0;
	private static final int cCamara = 1;
	private static final int cMochila = 2;
	private static final int cMinijuego = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame);

		//abre base de datos
		//mDbHelper = new DbAdapter(this);
        //mDbHelper.open(true);
        
		bOpciones = (Button) findViewById(R.id.bOpciones);
		bOpciones.setOnClickListener(this);

		boolean scanDirecto = getIntent().getBooleanExtra(
				Intents.Comun.INGAME_SCAN, false);

		if (scanDirecto)
			lanzaScan();

		///prueba lanzar juego
		bReinas = (Button) findViewById(R.id.bReinas);
		bReinas.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menuingame, menu);
		return true;// debe devolver true para que el menu se muestre
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// metodo para que hacer cuando se pulsa el boton de atras
		// ahora mismo no hace nada
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.bCamara:
			lanzaScan();
			break;
		case R.id.bMapa:
			// para la prueba del boton Mapa
			// prMapa.setText("He pulsado Mapa");
			Intent iMapa = new Intent(Intents.Action.MAPA);
			startActivity(iMapa);
			break;
		case R.id.bMochila:
			Intent iMochila = new Intent(Intents.Action.MOCHILA);
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
        	//para la prueba del boton Mapa
        	//prMapa.setText("He pulsado Mapa");
        	//
        	Intent iReinas = new Intent(Intents.Action.REINAS);
			startActivity(iReinas);
			break;
		default:
			break;
		}
	}

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
				startActivityForResult(iMinijuego, cMinijuego);
			}
		}
		// no se lanza el minijuego porque no se corresponde con uno que exista
	}

}
