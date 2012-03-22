package com.cinnamon.is.comun.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cinnamon.is.R;


/**
 * Clase que implementa los dialogs de menu
 * 
 * @author Cinnamon Team
 * @version 1.0 15.03.2012
 * 
 */
public class MenuDialog extends Dialogos {

	/**
	 * Botones y Views de los xml
	 */
	Button bRenaudar;
	Button bReiniciar;
	Button bSalir;

	/**
	 * Constructora
	 * 
	 * @param _context
	 *            Contexto de la actividad sobre la que se lanza el dialog
	 * @param _title
	 *            Cabezera del dialogo
	 * @param _modo
	 *            Modo de juego elegido ( DIALOG_ARCADE , DIALOG_AVENTURA)
	 * @param _theme
	 *            Tema elegido para el dialog
	 */
	public MenuDialog(Context _context, String _title, int _modo, int _theme) {
		super(_context, _title, _modo, _theme);
		this.title = _title;
	}

	/**
	 * Called when the activity is first created.
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	/**
	 * Inicializa el xml del dialog y todas sus views
	 */
	void init() {
		setContentView(R.layout.dialog_menu);
		bReiniciar = (Button) findViewById(R.id.ButtonRestartMenu);
		bRenaudar = (Button) findViewById(R.id.ButtonResumeMenu);
		bSalir = (Button) findViewById(R.id.ButtonExitMenu);
		LinearLayout fondo = (LinearLayout) findViewById(R.id.LinearLayoutMainMenu);

		if (!modo) {
			ViewGroup vg = (ViewGroup) (bReiniciar.getParent());
			vg.removeView(bReiniciar);
		} else
			bReiniciar.setOnClickListener(this);

		this.setTitle(title);
		fondo.getBackground().setAlpha(45);
		bReiniciar.getBackground().setAlpha(45);
		bRenaudar.getBackground().setAlpha(45);
		bSalir.getBackground().setAlpha(45);
	}

	/**
	 * Asigna una accion a cada uno de los botones
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ButtonResumeMenu:
		case R.id.ButtonRestartMenu:
		case R.id.ButtonExitMenu:
		}
	}
}
