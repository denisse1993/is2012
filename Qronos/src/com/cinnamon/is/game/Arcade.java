//
// Universidad Complutense de Madrid
// Ingenieria Inform�tica
//
// PROYECTO: QRonos
// ASIGNATURA : Ingenier�a del Software
//

package com.cinnamon.is.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;

/**
 * Pantalla principal del arcade de la aplicacion
 * 
 * @author Cinnamon Team
 * @version 0.3 12.03.2012
 */
public class Arcade extends Activity implements View.OnClickListener,
		DialogInterface.OnClickListener {

	// interfaz
	private ImageButton iBinfo, iBupSc, iBseeSc;
	private ImageButton[] iBmj;
	private ImageView[] iVsc;
	private TextView tVhello;
	// private ImageButton iBmj1,iBmj2,iBmj3,iBmj4,iBmj5,iBmj6;
	// private ImageView iBsc1,iBsc2,iBsc3,iBsc4,iBsc5,iBsc6;

	/**
	 * Vista pulsada en onClick para uso en dialog onclick
	 */
	private int vClicked;
	/**
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	/**
	 * Jugador actual en la aplicacion
	 */
	private Jugador jugador;

	/**
	 * Lanzador auxiliar
	 */
	private Launch l;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arcade);
		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		// SharedPreferences getData = PreferenceManager
		// .getDefaultSharedPreferences(getBaseContext());
		// sonido = getData.getBoolean("cbSonido", true);
		l = new Launch(this);
		inicializar();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mDbHelper.close();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mDbHelper.open(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Bundle b = data.getExtras();
			switch (requestCode) {
			case Props.Comun.cmj1:
				int score = b.getInt(Props.Comun.SCORE);
				jugador.setScore(score, Props.Comun.cmj1 - 1);
				break;
			case Props.Comun.cmj2:
				break;
			case Props.Comun.cmj3:
				break;
			case Props.Comun.cmj4:
				break;
			case Props.Comun.cmj5:
				break;
			case Props.Comun.cmj6:
				break;
			}
		}
	}

	/**
	 * Metodo de utilidad para inicializar la Actividad
	 */
	private void inicializar() {

		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		// genericos
		iBinfo = (ImageButton) findViewById(R.id.iBinfoArcade);
		iBupSc = (ImageButton) findViewById(R.id.iBupSc);
		iBseeSc = (ImageButton) findViewById(R.id.iBseeSc);
		tVhello = (TextView) findViewById(R.id.tVhello);

		// creo arrays para minijuegos
		iBmj = new ImageButton[Props.Comun.MAX_MJ];
		iVsc = new ImageView[Props.Comun.MAX_MJ];

		// inicializo arrays
		for (int i = 0; i < Props.Comun.MAX_MJ; i++) {
			iBmj[i] = (ImageButton) findViewById(Props.Comun.iDiBmj[i]);
			iVsc[i] = (ImageView) findViewById(Props.Comun.iDiVsC[i]);

			// pone imagen a imagebutton
			// iBmj[i].setImageResource(R.drawable.icon_information);
			iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[i]);

			// TODO CORRECTO
			// iVsc[i].setImageResource(Props.Comun.getStar(jugador.getScore(i)));
			// iVsc[i].setImageResource(R.drawable.icon_information);

			iBmj[i].setOnClickListener(this);
		}
		iBinfo.setOnClickListener(this);
		iBupSc.setOnClickListener(this);
		iBseeSc.setOnClickListener(this);
		tVhello.setText(tVhello.getText() + " " + jugador.getNombre() + "!");
	}

	@Override
	public void onClick(DialogInterface dialog, int boton) {
		if (aDactual == dialog) {
			aDactual = null;
			switch (boton) {
			case 0:
				tVhello.setText("pulsado continuar");
				break;
			case 1:
				tVhello.setText("pulsado reiniciar");
				break;
			case 2:
				tVhello.setText("pulsado salir");
				break;
			}
		} else
			switch (boton) {
			case -1:
				dialog.cancel();
				switch (vClicked) {
				case R.id.iBmj1:
					l.lanzaActivity(Props.Action.MJ1, Props.Comun.cmj1);
					break;
				case R.id.iBmj2:
					l.lanzaActivity(Props.Action.MJ2, Props.Comun.cmj2);
					break;
				case R.id.iBmj3:
					l.lanzaActivity(Props.Action.MJ3, Props.Comun.cmj3);
					break;
				case R.id.iBmj4:
					l.lanzaActivity(Props.Action.MJ4, Props.Comun.cmj4);
					break;
				case R.id.iBmj5:
					l.lanzaActivity(Props.Action.MJ5, Props.Comun.cmj6);
					break;
				case R.id.iBmj6:
					l.lanzaActivity(Props.Action.MJ6, Props.Comun.cmj6);
					break;
				case R.id.iBupSc:
					subirScores();
					break;
				}
			case -2:
				dialog.cancel();
				break;
			}
	}

	/**
	 * Metodo para subir puntuaciones al servidor
	 */
	private void subirScores() {
		// TODO Por hacer
	}

	AlertDialog aDactual;

	@Override
	public void onClick(View v) {
		switch (vClicked = v.getId()) {
		case R.id.iBinfoArcade:
			 Launch.lanzaAviso("Informaci�n de arcade",
			 Props.Strings.iArcade, this);
			//TODO lanzaOpciones prueba aDactual = Launch.lanzaOpciones(this);
			break;
		case R.id.iBseeSc:
			Bundle b = new Bundle();
			b.putSerializable(Props.Comun.JUGADOR, jugador);
			l.lanzaActivity(Props.Action.RANKING, b);
		case R.id.iBupSc:
			Launch.lanzaConfirmacion("Confirmacion para subir puntuaciones de "
					+ jugador.getNombre(), Props.Strings.upSc, this);
			break;
		case R.id.iBmj1:
			Launch.lanzaConfirmacion("Confirmacion para lanzar "
					+ Props.Strings.mjNames[0], Props.Strings.mjExps[0]
					+ "\n Score: " + jugador.getScore(0), this);
			break;
		case R.id.iBmj2:
			Launch.lanzaConfirmacion("Confirmacion para lanzar "
					+ Props.Strings.mjNames[1], Props.Strings.mjExps[1], this);
			break;
		case R.id.iBmj3:
			Launch.lanzaConfirmacion("Confirmacion para lanzar "
					+ Props.Strings.mjNames[2], Props.Strings.mjExps[2], this);
			break;
		case R.id.iBmj4:
			Launch.lanzaConfirmacion("Confirmacion para lanzar "
					+ Props.Strings.mjNames[3], Props.Strings.mjExps[3], this);
			break;
		case R.id.iBmj5:
			Launch.lanzaConfirmacion("Confirmacion para lanzar "
					+ Props.Strings.mjNames[4], Props.Strings.mjExps[4], this);
			break;
		case R.id.iBmj6:
			Launch.lanzaConfirmacion("Confirmacion para lanzar "
					+ Props.Strings.mjNames[5], Props.Strings.mjExps[5], this);
			break;
		}
	}
}
