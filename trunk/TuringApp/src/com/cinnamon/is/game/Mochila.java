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

public class Mochila extends Activity implements OnClickListener {

	// Jugador creao en login
	private Jugador jugador;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mochila);
	
		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		// controla objetos escogidos en base a
		// jugador.getMochila();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menumochila, menu);
		return true;// debe devolver true para que el menu se muestre
	}

	@Override
	public void onBackPressed() {
		Intent iInGame = new Intent();
		iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
		iInGame.putExtra(Intents.Comun.INGAME_SCAN, false);
		setResult(RESULT_OK, iInGame);
		finish();
		/*Intent iInGame = new Intent(Intents.Action.INGAME);
		iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivity(iInGame);
		finish();*/
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.bCamara:
			Intent iInGame = new Intent();
			iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
			iInGame.putExtra(Intents.Comun.INGAME_SCAN, true);
			setResult(RESULT_OK, iInGame);
			finish();
			/*Intent iInGame = new Intent(Intents.Action.INGAME);
			iInGame.putExtra(Intents.Comun.INGAME_SCAN, true);
			iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivity(iInGame);*/
			break;
		case R.id.bMapa:
			Intent iMapa = new Intent();
			iMapa.putExtra(Intents.Comun.JUGADOR, jugador);
			iMapa.putExtra(Intents.Action.MAPA, true);
			setResult(RESULT_OK, iMapa);
			finish();
			break;
		case R.id.bVolver:
			onBackPressed();
			break;
		}
		return false;// devuelve falso para proceso normal
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
