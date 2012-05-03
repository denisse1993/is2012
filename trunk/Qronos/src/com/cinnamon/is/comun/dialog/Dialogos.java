package com.cinnamon.is.comun.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

/**
 * Clase abstracta que implementa todos los tipos de dialog de nuestra
 * aplicacion
 * 
 * Menu - Dialogos para mostrar el menu de juego Ayuda - Dialogos para mostra la
 * informacion de ayuda
 * 
 * @author Cinnamon Team
 * @version 1.0 15.03.2012
 * 
 */

public abstract class Dialogos extends Dialog implements
		android.view.View.OnClickListener {

	/**
	 * Indica el titulo que se mostrar‡ en la cabezera
	 */
	String title;

	/**
	 * Constantes que asignan el tipo de dialogo Aventura o Arcade
	 */
	public static final int DIALOG_ARCADE = 0;
	public static final int DIALOG_AVENTURA = 1;

	/**
	 * Selecciona el tipo de dialogo segun el modo de juego Modo == True =>
	 * Dialogo Arcade Modo == False => Dialogo Aventura
	 */
	boolean modo;

	public Dialogos(final Context _context, final String _title,
			final int _modo, final int _theme) {
		super(_context, _theme);
		this.title = _title;
		if (DIALOG_ARCADE == _modo) {
			this.modo = true;
		} else if (DIALOG_AVENTURA == _modo) {
			this.modo = false;
		}
	}

	abstract void init();

	@Override
	public abstract void onClick(View v);

	@Override
	public void onBackPressed() {
		this.dismiss();
	}

}
