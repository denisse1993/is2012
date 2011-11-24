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
		// if (!savedInstanceState.isEmpty()) {
		// jugador = (Jugador) savedInstanceState.getSerializable("jugador");
		// }
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

	/*
	 * @Override protected void onPause() { Bundle b = new Bundle();
	 * b.putSerializable("jugador", jugador); onSaveInstanceState(b); }
	 */

	@Override
	public void onBackPressed() {
		finish();
		Intent iInGame = new Intent(Intents.Action.INGAME);
		iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivity(iInGame);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.bCamara:
			Intent iInGame = new Intent(Intents.Action.INGAME);
			iInGame.putExtra(Intents.Comun.INGAME_SCAN, true);
			iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivity(iInGame);
			break;
		case R.id.bMapa:
			Intent iMapa = new Intent(Intents.Action.MAPA);
			iMapa.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivity(iMapa);
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
