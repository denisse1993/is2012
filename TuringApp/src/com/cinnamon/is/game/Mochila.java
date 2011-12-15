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
import android.widget.ImageView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Intents;

/**
 * Actividad que representa la mochila del jugador con los distintos objetos que
 * puede tener
 * 
 * @author Cinnamon Team
 * @version 1.2 24.11.2011
 */
public class Mochila extends Activity implements OnClickListener {

	/**
	 * Jugador actual en la aplicacion
	 */ 
	private Jugador jugador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mochila);
		
		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		
		ImageView papel1 = (ImageView)findViewById(R.id.ivPapel1);
		ImageView papel2 = (ImageView)findViewById(R.id.ivPapel2);
		ImageView papel3 = (ImageView)findViewById(R.id.ivPapel3);
		ImageView enigma = (ImageView)findViewById(R.id.ivEnigma);
		
		int[] mochila = jugador.getMochila();
		if (mochila[0]== 0)	papel1.setVisibility(View.INVISIBLE); else papel1.setVisibility(View.VISIBLE);
		if (mochila[1]== 0) papel2.setVisibility(View.INVISIBLE); else papel2.setVisibility(View.VISIBLE);
		if (mochila[2]== 0)	papel3.setVisibility(View.INVISIBLE); else papel3.setVisibility(View.VISIBLE);
		if (mochila[3]== 0) enigma.setVisibility(View.INVISIBLE); else enigma.setVisibility(View.VISIBLE);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menumochila, menu);
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
