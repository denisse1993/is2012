package com.cinnamon.is.comun.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Minijuego;

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

	Minijuego mj;

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
	 * @param _mj
	 *            Minijuego relacionado con el menu
	 */
	public MenuDialog(final Context _context, final String _title,
			final int _modo, final int _theme, final Minijuego _mj) {
		super(_context, _title, _modo, _theme);
		this.mj = _mj;
	}

	/**
	 * Called when the activity is first created.
	 * 
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	/**
	 * Inicializa el xml del dialog y todas sus views
	 */
	@Override
	void init() {
		setContentView(R.layout.dialog_menu);
		this.bReiniciar = (Button) findViewById(R.id.ButtonRestartMenu);
		this.bRenaudar = (Button) findViewById(R.id.ButtonResumeMenu);
		this.bSalir = (Button) findViewById(R.id.ButtonExitMenu);
		LinearLayout fondo = (LinearLayout) findViewById(R.id.LinearLayoutMainMenu);

		if (!this.modo) {
			ViewGroup vg = (ViewGroup) (this.bReiniciar.getParent());
			vg.removeView(this.bReiniciar);
		} else {
			this.bReiniciar.setOnClickListener(this);
		}

		this.bRenaudar.setOnClickListener(this);
		this.bSalir.setOnClickListener(this);
		this.setTitle(this.title);
		fondo.getBackground().setAlpha(45);
		this.bReiniciar.getBackground().setAlpha(45);
		this.bRenaudar.getBackground().setAlpha(45);
		this.bSalir.getBackground().setAlpha(45);
	}

	/**
	 * Asigna una accion a cada uno de los botones
	 * 
	 * @param v
	 */
	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.ButtonResumeMenu:
			MenuDialog.this.dismiss();
			this.mj.resumir();
			break;
		case R.id.ButtonRestartMenu:
			MenuDialog.this.dismiss();
			this.mj.reiniciar();
			break;
		case R.id.ButtonExitMenu:
			MenuDialog.this.dismiss();
			this.mj.finalizar(false);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		this.dismiss();
		this.mj.resumir();
	}

}
