package com.cinnamon.is.comun;

import com.cinnamon.is.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Dialogos extends Dialog implements
		android.view.View.OnClickListener {

	String title;
	Button bRenaudar;
	Button bReiniciar;
	Button bSalir;

	boolean tipo;
	TextView tvDescription;

	public Dialogos(Context context, String title, boolean tipo, int theme) {
		super(context, theme);
		this.title = title;
		this.tipo = tipo;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_menu);

		bReiniciar = (Button) findViewById(R.id.ButtonRestartMenu);
		bRenaudar = (Button) findViewById(R.id.ButtonResumeMenu);
		bSalir = (Button) findViewById(R.id.ButtonExitMenu);
		//LinearLayout fondo = (LinearLayout) findViewById(R.id.LinearLayoutMain);

		if (!tipo) {
			ViewGroup vg = (ViewGroup) (bReiniciar.getParent());
			vg.removeView(bReiniciar);
		} else
			bReiniciar.setOnClickListener(this);

		this.setTitle(title);
		//fondo.getBackground().setAlpha(45);
		bReiniciar.getBackground().setAlpha(45);
		bRenaudar.getBackground().setAlpha(45);
		bSalir.getBackground().setAlpha(45);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ButtonResumeMenu:
		case R.id.ButtonRestartMenu:
		case R.id.ButtonExitMenu:
		}
	}

}
