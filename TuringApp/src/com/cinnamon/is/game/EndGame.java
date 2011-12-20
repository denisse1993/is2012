package com.cinnamon.is.game;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Intents;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EndGame extends Activity{
	
	private TextView tvPuntuacion ;
	private Jugador jugador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.endgame);
		
		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		
		String puntuacion = jugador.getNombre()+" tu puntuación final ha sido de "
					+ jugador.getScore(); 	
		
		tvPuntuacion = (TextView) findViewById(R.id.tVPuntuacion);
		tvPuntuacion.setText(puntuacion);
	}
	
	
	
}
