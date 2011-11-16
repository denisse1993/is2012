package com.cinnamon.is;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Mochila extends Activity implements OnClickListener {

	// Constantes para controlar que actividad tratar en onActivityResult
	private static final int cMapa = 0;
	private static final int cCamara = 1;
	private static final int cMochila = 2;
	private static final int cMinijuego = 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mochila);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menumochila, menu);
		return true;// debe devolver true para que el menu se muestre
	}

	@Override
	public void onBackPressed() {
		finish();
		Intent iInGame = new Intent(Intents.Action.INGAME);
		startActivity(iInGame);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.bCamara:
			Intent iInGame = new Intent(Intents.Action.INGAME);
			iInGame.putExtra(Intents.Comun.INGAME_SCAN, true);
			startActivity(iInGame);
			break;
		case R.id.bMapa:
			Intent iMapa = new Intent(Intents.Action.MAPA);
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
