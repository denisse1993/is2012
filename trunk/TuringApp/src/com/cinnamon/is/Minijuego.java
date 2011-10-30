package com.cinnamon.is;

import android.app.Activity;
import android.os.Bundle;

/**
 * Actividad abtracta que representa un minijuego
 * 
 * @author Cinnamon Team
 * 
 */
public abstract class Minijuego extends Activity {

	/**
	 * El nombre del minijuego
	 */
	protected String nombre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
