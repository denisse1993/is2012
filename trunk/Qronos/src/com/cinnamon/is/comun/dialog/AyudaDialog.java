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

/**
 * Clase que implementa los dialogos de ayuda
 * 
 * @author Cinnamon Team
 * @version 1.0 15.03.2012
 * 
 */
public class AyudaDialog extends Dialogos {

	int descripction;
	int image;
	
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
	 * @param _description
	 *            Descripcion de la ayuda solicitada
	 * @param _image
	 *            Imagen que se mostrara en el dialog 
	 * @param _modo
	 *            Modo de juego elegido ( DIALOG_ARCADE , DIALOG_AVENTURA)
	 * @param _theme
	 *            Tema elegido para el dialog
	 */
	public AyudaDialog(Context _context, String _title,int _description, 
			int _image, int _modo, int _theme) {
		super(_context, _title, _modo, _theme);
		this.title = _title;
		this.descripction = _description;
		this.image =_image;
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
		setContentView(R.layout.dialog_ayuda);
		bEmpezar = (Button) findViewById(R.id.ButtonStartAyuda);
		bCancelar = (Button) findViewById(R.id.ButtonCancelAyuda);
		LinearLayout fondo = (LinearLayout) findViewById(R.id.LinearLayoutMainAyuda);
		ivBanner = (ImageView) findViewById(R.id.ImageViewBannerAyuda);
		tvDescripcion = (TextView) findViewById(R.id.TextViewDescriptionAyuda); 

		if (!modo) {
			ViewGroup vg = (ViewGroup) (bEmpezar.getParent());
			vg.removeView(bEmpezar);
		} else
			bEmpezar.setOnClickListener(this);

		this.setTitle(title);
		ivBanner.setBackgroundResource(image);
		tvDescripcion.setText(descripction);
		fondo.getBackground().setAlpha(45);
		bEmpezar.getBackground().setAlpha(45);
		bCancelar.getBackground().setAlpha(45);
	}

	/**
	 * Asigna una accion a cada uno de los botones
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ButtonStartAyuda:
		case R.id.ButtonCancelAyuda:
		}
	}
}