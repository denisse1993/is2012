package com.cinnamon.is.minijuegos.ascensor;

import com.cinnamon.is.comun.Intents;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.game.Jugador;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Actividad que representa el minijuego del Ascensor
 * 
 * @author Cinnamon Team
 * 
 */

public class AscensorMJ extends Minijuego {
    /** Called when the activity is first created. */
    int contador;
	Button s,r;
	TextView display;
	 GameView game;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        game = new GameView(this);
        setContentView(game);    
        
     // Pone valores del minijuego
		startTime();
		setNombre(Intents.Comun.minijuegos[1]);
		setObjeto(1);
		setFase(2);
		setSuperado(false);

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		
		//String textoDialog = "Hola",
		/*"Enhorabuena has desbloqueado el minijuego de las 8 reinas." +
		"/nPara conseguir el siguiente objeto necesitaras completar el siguiente tablero" +
		"en el que se colocan ocho reinas sin que se amenacen. Una reina amenaza a otra si" +
		" se encuentren en su misma fila, columna o diagonal." +
		"/nSuerte.",*/
		/*title = "8 Reinas";
		int idIvDialog = R.drawable.tablero8reinas;

		Bundle dialogBundle = new Bundle();
		dialogBundle.putString("textoDialog", textoDialog);
		dialogBundle.putInt("idIvDialog", idIvDialog);
		//	dialogBundle.putBoolean(Intents.Comun.superado, superado);
		dialogBundle.putString("title", title);
		showDialog(DIALOG_MINIJUEGOS_INIT,dialogBundle);*/
    }
	
	public void restartActivity(){
		super.onRestart();
	}
}