package com.cinnamon.is.comun.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.game.Aventura;

public class TextDialog extends Dialogos {

	Button bOk;
	EditText etTexto, etPassword;
	TextView tvPassword;
	LinearLayout fondo;

	Aventura a;
	Launch launch;
	int tipo;
	String nombreJugador;

	public TextDialog(final Context _context, final String _title,
			final int _modo, final int _theme, final Launch _launch,
			final Aventura _a, String jugadorQueLaCrea) {
		super(_context, _title, _modo, _theme);
		a = _a;
		this.launch = _launch;
		this.tipo = _modo;
		this.nombreJugador=jugadorQueLaCrea;
		init();
	}

	@Override
	void init() {
		setContentView(R.layout.dialog_texto);
		this.bOk = (Button) findViewById(R.id.bOkTextDialog);
		this.etTexto = (EditText) findViewById(R.id.etTextDialog);
		this.etPassword = (EditText) findViewById(R.id.etPasswordTextDialog);
		this.tvPassword = (TextView) findViewById(R.id.tvPasswordTextDialog);
		this.fondo = (LinearLayout) findViewById(R.id.llTextoDialog);

		if (tipo == 2) {
			ViewGroup vg = (ViewGroup) (this.etPassword.getParent());
			vg.removeView(this.etPassword);
			vg = (ViewGroup) (this.tvPassword.getParent());
			vg.removeView(this.etPassword);
		}

		this.bOk.setOnClickListener(this);
		this.setTitle(title);

		fondo.getBackground().setAlpha(45);
		this.bOk.getBackground().setAlpha(45);
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.bOkTextDialog:
			String nombre = etTexto.getText().toString();
			String password = etPassword.getText().toString();
			a.setNombre(nombre);
			a.setPass(password);
			switch (tipo) {
			case 0:
				this.dismiss();
				launch.lanzaDialogoEsperaCreaQuest(a,nombreJugador);
				break;

			case 1:
				this.dismiss();
				launch.lanzaDialogoEsperaGetQuest(a);
				break;
			case 2:
				this.dismiss();
				launch.lanzaDialogoEsperaGetQuestPass(a);
				break;
			}
			break;
		}
	}

}
