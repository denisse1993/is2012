//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: QRonos
// ASIGNATURA : Ingeniería del Software
//

package com.cinnamon.is.game;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Dialogs;
import com.cinnamon.is.comun.Props;

/**
 * Pantalla principal del arcade de la aplicacion
 * 
 * @author Cinnamon Team
 * @version 0.3 12.03.2012
 */
public class Arcade extends Activity implements OnClickListener {

	// interfaz
	private ImageButton iBinfo, iBupSc, iBseeSc;
	private ImageButton[] iBmj;
	private ImageView[] iVsc;
	// private ImageButton iBmj1,iBmj2,iBmj3,iBmj4,iBmj5,iBmj6;
	// private ImageView iBsc1,iBsc2,iBsc3,iBsc4,iBsc5,iBsc6;

	/**
	 * Cursor para tratar las consultas en la BD
	 */
	private Cursor mCursor;

	/**
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	/**
	 * Jugador actual en la aplicacion
	 */
	private Jugador jugador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arcade);
		jugador = (Jugador) getIntent().getSerializableExtra(Props.Comun.J);
		// SharedPreferences getData = PreferenceManager
		// .getDefaultSharedPreferences(getBaseContext());
		// sonido = getData.getBoolean("cbSonido", true);
		inicializar();
	}

	/**
	 * Metodo de utilidad para inicializar la Actividad
	 */
	private void inicializar() {

		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		// ib genericos
		iBinfo = (ImageButton) findViewById(R.id.iBinfo);
		iBupSc = (ImageButton) findViewById(R.id.iBupSc);
		iBseeSc = (ImageButton) findViewById(R.id.iBseeSc);

		// creo arrays para minijuegos
		iBmj = new ImageButton[Props.Comun.MAX_MJ];
		iVsc = new ImageButton[Props.Comun.MAX_MJ];

		// inicializo arrays
		for (int i = 0; i < Props.Comun.MAX_MJ; i++) {
			iBmj[i] = (ImageButton) findViewById(Props.Comun.iDiBmj[i]);
			iVsc[i] = (ImageView) findViewById(Props.Comun.iDiVsC[i]);
			// pone imagen a imagebutton
			iBmj[i].setImageResource(R.drawable.icon_information);// temp xa
																	// prueba
			// iBmj[i].setImageResource(Props.Comun.iDiVmj[i]); CORRECTO

			// iVsc[i].setImageResource(Props.Comun.getStar(jugador.getScore(i)));
			// CORRECTO
			iVsc[i].setImageResource(R.drawable.icon_information);

			iBmj[i].setOnClickListener(this);
		}
		iBinfo.setOnClickListener(this);
		iBupSc.setOnClickListener(this);
		iBseeSc.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent iMinijuego = new Intent();
		iMinijuego.putExtra(Props.Comun.J, jugador);
		boolean si = false;
		switch (v.getId()) {
		case R.id.iBmj1:
			si = Dialogs.lanzaConfirmacion("Confirmacion para lanzar "
					+ Props.Comun.minijuegos[0], Props.Comun.expmjs[0], this);
			if (si) {
				iMinijuego.setAction(Props.Action.MJ1);
				startActivityForResult(iMinijuego,
						Props.Comun.cmj1);
			}
			break;
		case R.id.iBmj2:
			si = Dialogs.lanzaConfirmacion("Confirmacion para lanzar "
					+ Props.Comun.minijuegos[1], Props.Comun.expmjs[1], this);
			if (si) {
				iMinijuego.setAction(Props.Action.MJ2);
				startActivityForResult(iMinijuego,
						Props.Comun.cmj2);
			}
			break;
		case R.id.bSalir:
			finish();
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case Props.Comun.cmj1:
				break;

			case Props.Comun.cmj2:
				break;
			}
		}
	}
}
