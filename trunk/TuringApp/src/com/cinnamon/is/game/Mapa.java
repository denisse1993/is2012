//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Intents;

/**
 * Actividad que representa el mapa del juego
 * 
 * @author Cinnamon Team
 * @version 1.2 24.11.2011
 */
public class Mapa extends Activity implements OnClickListener {

	/**
	 * Jugador actual en la aplicacion
	 */
	private Jugador jugador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);

		// obtiene el jugador serializado
		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		// establecer mapa usando variables de jugador
		// jugador.getFase1();
		// jugador.getFase2();
		// jugador.getFase3();
		// jugador.getFase4();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menumapa, menu);
		return true;// debe devolver true para que el menu se muestre
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.bCamara:
			Intent iInGame = new Intent();
			iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
			iInGame.putExtra(Intents.Comun.INGAME_SCAN, true);
			setResult(RESULT_OK, iInGame);
			finish();
			break;
		case R.id.bVolver:
			onBackPressed();
			break;
		case R.id.bMochila:
			Intent iMochila = new Intent();
			iMochila.putExtra(Intents.Comun.JUGADOR, jugador);
			iMochila.putExtra(Intents.Action.MOCHILA, true);
			setResult(RESULT_OK, iMochila);
			finish();
			break;
		}
		return false;// devuelve falso para proceso normal
	}

	@Override
	public void onBackPressed() {
		Intent iInGame = new Intent();
		iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
		iInGame.putExtra(Intents.Comun.INGAME_SCAN, false);
		setResult(RESULT_OK, iInGame);
		finish();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
