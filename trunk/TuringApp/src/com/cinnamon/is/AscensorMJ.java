package com.cinnamon.is;

import android.os.Bundle;

/**
 * Actividad que representa el minijuego del Ascensor, consiste en...
 * 
 * @author Cinnamon Team
 * 
 */
public class AscensorMJ extends Minijuego {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ascensormj);
		setNombre("AscensorMJ");
	}

}
