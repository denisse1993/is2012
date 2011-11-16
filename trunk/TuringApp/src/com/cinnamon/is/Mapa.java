package com.cinnamon.is;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Mapa extends Activity implements OnClickListener {

	// Constantes para controlar que actividad tratar en onActivityResult
	private static final int cMapa = 0;
	private static final int cCamara = 1;
	private static final int cMochila = 2;
	private static final int cMinijuego = 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menumapa, menu);
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
		case R.id.bVolver:
			onBackPressed();
			break;
		case R.id.bMochila:
			Intent iMochila = new Intent(Intents.Action.MOCHILA);
			startActivity(iMochila);
			break;
		}
		return false;// devuelve falso para proceso normal
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
