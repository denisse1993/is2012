package com.cinnamon.is;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {

	Button bNuevaPartida, bOpciones, bAyuda, bSalir;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		inicializar();
	}

	private void inicializar() {
		bNuevaPartida = (Button) findViewById(R.id.bNuevaPartida);
		bOpciones = (Button) findViewById(R.id.bOpciones);
		bAyuda = (Button) findViewById(R.id.bAyuda);
		bSalir = (Button) findViewById(R.id.bSalir);

		bNuevaPartida.setOnClickListener(this);
		bOpciones.setOnClickListener(this);
		bAyuda.setOnClickListener(this);
		bSalir.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bNuevaPartida:
			Intent iInGame = new Intent(Intents.Action.INGAME);
			startActivity(iInGame);
			break;

		case R.id.bOpciones:

			break;

		case R.id.bAyuda:

			break;

		case R.id.bSalir:
			finish();
			break;
		}

	}

}
