package com.cinnamon.is.comun.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;

/**
 * Clase que implementa los dialogos de ayuda
 * 
 * @author Cinnamon Team
 * @version 1.0 15.03.2012
 * 
 */
public class AyudaDialog extends Dialogos {

	String descripction;
	int image;

	/**
	 * Intent de la actividad a lanzar
	 */
	String intent;

	/**
	 * Nœmero del Minijuego
	 */
	int numMJ;

	/**
	 * Launch de la actividad Padre
	 */
	Launch launch;

	/**
	 * Botones y Views de los xml
	 */
	Button bEmpezar;
	Button bCancelar;
	ImageView ivBanner;
	TextView tvDescripcion;

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
	 * @param _numMJ
	 *            Nœmero del Minijuego a lanzar
	 * @param _intent
	 *            Intent de la actividad a lanzar
	 * @param _description
	 *            Descripcion de la ayuda solicitada
	 * @param _image
	 *            Imagen que se mostrara en el dialog
	 * @param _launch
	 *            Launch de la clase padre
	 */
	public AyudaDialog(final Context _context, final String _title,
			final int _modo, final int _theme, final int _numMJ,
			final String _intent, final String _description, final int _image,
			final Launch _launch) {
		super(_context, _title, _modo, _theme);
		this.intent = _intent;
		this.numMJ = _numMJ;
		this.launch = _launch;
		this.descripction = _description;
		this.image = _image;
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

	public void setLaunch(final Launch _launch) {
		this.launch = _launch;
	}

	/**
	 * Inicializa el xml del dialog y todas sus views
	 */
	@Override
	void init() {
		setContentView(R.layout.dialog_ayuda);
		this.bEmpezar = (Button) findViewById(R.id.ButtonStartAyuda);
		this.bCancelar = (Button) findViewById(R.id.ButtonCancelAyuda);
		LinearLayout fondo = (LinearLayout) findViewById(R.id.LinearLayoutMainAyuda);
		this.ivBanner = (ImageView) findViewById(R.id.ImageViewBannerAyuda);
		this.tvDescripcion = (TextView) findViewById(R.id.TextViewDescriptionAyuda);

		if (!this.modo) {
			ViewGroup vg = (ViewGroup) (this.bCancelar.getParent());
			vg.removeView(this.bCancelar);
		} else {
			this.bCancelar.setOnClickListener(this);
		}

		this.bEmpezar.setOnClickListener(this);
		this.setTitle(this.title);
		this.ivBanner.setBackgroundResource(this.image);
		this.tvDescripcion.setText(this.descripction);
		fondo.getBackground().setAlpha(145);
		// this.bEmpezar.getBackground().setAlpha(45);
		// this.bCancelar.getBackground().setAlpha(45);
	}

	/**
	 * Asigna una accion a cada uno de los botones
	 * 
	 * @param v
	 */
	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.ButtonStartAyuda:
			Bundle b = new Bundle();
			b.putBoolean(Props.Comun.MODO, this.modo);
			this.dismiss();
			this.launch.lanzaActivity(Props.Action.MJ[this.numMJ], b,
					Props.Comun.CMJ[this.numMJ]);
			break;
		case R.id.ButtonCancelAyuda:
			this.dismiss();
		}
	}
}