package com.cinnamon.is.minijuegos.reinas;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Actividad que representa el minijuego del Ascensor, consiste en...
 * 
 * @author Cinnamon Team
 * 
 */
public class Reinas extends Activity {
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new GameView(this));
		//setNombre("EightReinas");
		
	}

}
