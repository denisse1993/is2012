package com.cinnamon.is.comun.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.game.Aventura;

public class TextDialog extends Dialogos {

	Button bOk;
	EditText etTexto, etPassword;
	LinearLayout fondo;

	Aventura a;
	Launch launch;
	int tipo;

	public TextDialog(final Context _context, final String _title,
			final int _modo, final int _theme, final Launch _launch) {
		super(_context, _title, _modo, _theme);
		this.launch = _launch;
		this.tipo = _modo;
	}

	@Override
	void init() {
//		setContentView(R.layout.dialog_texto);
//		this.bOk = (Button) findViewById(R.id.bOkTextDialog);
//		this.etTexto = (EditText) findViewById(R.id.etTextDialog);
//		this.etTexto = (EditText) findViewById(R.id.etPasswordTextDialog);
//		this.fondo = (LinearLayout) findViewById(R.id.llTextoDialog);
//
//		if (!this.modo) {
//			ViewGroup vg = (ViewGroup) (this.etPassword.getParent());
//			vg.removeView(this.etPassword);
//		}
//
//		this.bOk.setOnClickListener(this);
//
//		fondo.getBackground().setAlpha(45);
//		this.bOk.getBackground().setAlpha(45);
	}

	@Override
	public void onClick(final View v) {
//		switch (v.getId()) {
//		case R.id.bOkTextDialog:
//			String nombre = etTexto.getText().toString();
//			String password = etPassword.getText().toString();
//			a = new Aventura(nombre, password);
//			switch (tipo) {
//			case 0:
//				this.dismiss();
//				launch.lanzaDialogoEsperaCreaQuest(a);
//				break;
//
//			case 1:
//				this.dismiss();
//				launch.lanzaDialogoEsperaGetQuest(a);
//				break;
//			case 2:
//				this.dismiss();
//				launch.lanzaDialogoEsperaGetQuestPass(a);
//				break;
//			}
//			break;
//		}
	}

}
