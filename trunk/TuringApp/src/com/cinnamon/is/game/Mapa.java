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

public class Mapa extends Activity implements OnClickListener {

	// Jugador creao en login
	private Jugador jugador;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		
		//obtiene el jugador serializado
		jugador= (Jugador)getIntent().getSerializableExtra(Intents.Comun.JUGADOR);
		//establecer mapa usando variables de jugador
		//jugador.getFase1();
		//jugador.getFase2();
		//jugador.getFase3();
		//jugador.getFase4();
	}

	@Override
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
		iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivity(iInGame);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.bCamara:
			Intent iInGame = new Intent(Intents.Action.INGAME);
			iInGame.putExtra(Intents.Comun.INGAME_SCAN, true);
			iInGame.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivity(iInGame);
			break;
		case R.id.bVolver:
			onBackPressed();
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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
