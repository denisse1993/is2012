package com.cinnamon.is;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Actividad del juego principal
 * 
 * @author Cinnamon Team
 * 
 */
public class InGame extends Activity implements OnClickListener {

	// base de datos?¿

	private Button bMapa, bCamara, bMochila;

	private String qrLeido;

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
		//inicializar();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.menuingame, menu);		    
	     return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.bCamara:
	            
	            return true;
	        case R.id.bMapa:
	        
	            return true;
	        case R.id.bMochila:
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	/*private void inicializar() {
		bMapa = (Button) findViewById(R.id.bMapa);
		bCamara = (Button) findViewById(R.id.bCamara);
		bMochila = (Button) findViewById(R.id.bMochila);

		bMapa.setOnClickListener(this);
		bCamara.setOnClickListener(this);
		bMochila.setOnClickListener(this);
	}*/
	/*public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater= getMenuInflater();
		inflater.inflate(R.layout.ingame, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.bCamara:
			return true;
		case R.id.bMapa:
			return true;
		case R.id.bMochila:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
		
	}*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bMapa:
			break;
		case R.id.bCamara:
			Intent iScan = new Intent(Intents.Action.SCAN);
			iScan.putExtra("SCAN_MODE", "QR_CODE_MODE"); //a–ade informacion extra al intent
			startActivityForResult(iScan, cCamara);
			break;
		case R.id.bMochila:
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
				qrLeido = data.getStringExtra("SCAN_RESULT");//coge la informacion extra del intent
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
