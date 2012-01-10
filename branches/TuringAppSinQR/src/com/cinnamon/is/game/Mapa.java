package com.cinnamon.is.game;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

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
	private LinearLayout fase1;
	private LinearLayout fase2;
	private LinearLayout fase3;
	private LinearLayout fase4;
	private LinearLayout estadofin;
	
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
		//fase=0 entonces estado a rojo
		//fase=1 entonces estado a verde
		//fase=2 entonces estado a azul
		
		switch (jugador.getFase1()) {
			case 0:
				fase1 = (LinearLayout)findViewById(R.id.fase1);
				fase1.setBackgroundColor(R.drawable.boton_check_on);
				break;
			case 1:
				fase1 = (LinearLayout)findViewById(R.id.fase1);
				fase1.setBackgroundColor(R.drawable.boton_verde);
				break;
			case 2:
				fase1 = (LinearLayout)findViewById(R.id.fase1);
				fase1.setBackgroundColor(R.drawable.boton_check_off);
				break;
		}
		switch (jugador.getFase2()) {
		case 0:
			fase2 = (LinearLayout)findViewById(R.id.fase2);
			fase2.setBackgroundColor(R.drawable.boton_check_on);
			break;
		case 1:
			fase2 = (LinearLayout)findViewById(R.id.fase2);
			fase2.setBackgroundColor(R.drawable.boton_verde);
			break;
		case 2:
			fase2 = (LinearLayout)findViewById(R.id.fase2);
			fase2.setBackgroundColor(R.drawable.boton_check_off);
			break;
		}
		switch (jugador.getFase3()) {
		case 0:
			fase3 = (LinearLayout)findViewById(R.id.fase3);
			fase3.setBackgroundColor(R.drawable.boton_check_on);
			break;
		case 1:
			fase3 = (LinearLayout)findViewById(R.id.fase3);
			fase3.setBackgroundColor(R.drawable.boton_verde);
			break;
		case 2:
			fase3 = (LinearLayout)findViewById(R.id.fase3);
			fase3.setBackgroundColor(R.drawable.boton_check_off);
			break;
		}
		switch (jugador.getFase4()) {
		case 0:
			fase4 = (LinearLayout)findViewById(R.id.fase4);
			fase4.setBackgroundColor(R.drawable.boton_check_on);
			break;
		case 1:
			fase4 = (LinearLayout)findViewById(R.id.fase4);
			fase4.setBackgroundColor(R.drawable.boton_verde);
			break;
		case 2:
			fase4 = (LinearLayout)findViewById(R.id.fase4);
			fase4.setBackgroundColor(R.drawable.boton_check_off);
			break;
		}
		if(jugador.getFase1()==1 && jugador.getFase2()==1 &&
				jugador.getFase3()==1 && jugador.getFase4()==1){
			//cambiar el estado final a azul
			estadofin = (LinearLayout)findViewById(R.id.estadofin);
			estadofin.setBackgroundColor(R.drawable.boton_check_off);
		}
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
