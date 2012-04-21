//
// Universidad Complutense de Madrid
// Ingenieria Inform�tica
//
// PROYECTO: QRonos
// ASIGNATURA : Ingenier�a del Software
//

package com.cinnamon.is.game;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Conexion;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.dialog.Dialogos;

/**
 * Pantalla principal del arcade de la aplicacion
 * 
 * @author Cinnamon Team
 * @version 0.6 16.04.2012
 */
public class Arcade extends Activity implements View.OnClickListener,
		DialogInterface.OnClickListener {

	// interfaz
	private LinearLayout llarcade;
	private ImageButton iBinfo, iBupSc, iBseeSc, iBleft, iBright;
	private ImageButton[] iBmj;
	private ImageView[] iVsc;
	private TextView tVhello;
	// private ImageButton iBmj1,iBmj2,iBmj3,iBmj4,iBmj5,iBmj6;
	// private ImageView iBsc1,iBsc2,iBsc3,iBsc4,iBsc5,iBsc6;

	/**
	 * Indica grupo de mjs mostrado
	 */
	private int grupoMJ;

	/**
	 * Controla la habilitacion de transicion a izquierda y derecha
	 */
	private boolean der, izq;

	/**
	 * Variable auxiliar referencia a ultimo dialog usado
	 */
	private AlertDialog aDactual;

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
	private Conexion conexion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arcade);
		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		// SharedPreferences getData = PreferenceManager
		// .getDefaultSharedPreferences(getBaseContext());
		// sonido = getData.getBoolean("cbSonido", true);
		grupoMJ = 0;
		conexion = new Conexion(this);
		l = new Launch(this);
		inicializar();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mDbHelper.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!mDbHelper.isOpen())
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
				mDbHelper.updateRowParcade(jugador.getNombre(),
						jugador.getScore());
				break;
			case Props.Comun.cmj2:
				break;
			case Props.Comun.cmj3:
				int score4 = b.getInt(Props.Comun.SCORE);
				jugador.setScore(score4, Props.Comun.cmj3 - 1);
				// esto da problemas
				mDbHelper.updateRowParcade(jugador.getNombre(),
						jugador.getScore());
				// creo que habr�a que recargar esta actividad para que
				// actualizara los datos
				break;
			case Props.Comun.cmj4:
				int score2 = b.getInt(Props.Comun.SCORE);
				jugador.setScore(score2, Props.Comun.cmj4 - 1);
				// esto da problemas
				mDbHelper.updateRowParcade(jugador.getNombre(),
						jugador.getScore());
				// creo que habr�a que recargar esta actividad para que
				// actualizara los datos
				break;
			case Props.Comun.cmj5:
				int score3 = b.getInt(Props.Comun.SCORE);
				jugador.setScore(score3, Props.Comun.cmj5 - 1);
				// esto da problemas
				mDbHelper.updateRowParcade(jugador.getNombre(),
						jugador.getScore());
				// creo que habr�a que recargar esta actividad para que
				// actualizara los datos
				break;
			case Props.Comun.cmj6:
				break;
			case Props.Comun.cmj7:
				break;
			case Props.Comun.cmj8:
				break;
			case Props.Comun.cmj9:
				break;
			case Props.Comun.cmj10:
				break;
			case Props.Comun.cmj11:
				break;
			case Props.Comun.cmj12:
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
		llarcade = (LinearLayout) findViewById(R.id.llarcade);
		iBinfo = (ImageButton) findViewById(R.id.iBinfoArcade);
		iBupSc = (ImageButton) findViewById(R.id.iBupSc);
		iBseeSc = (ImageButton) findViewById(R.id.iBseeSc);
		iBleft = (ImageButton) findViewById(R.id.iBleft);
		iBright = (ImageButton) findViewById(R.id.iBright);
		tVhello = (TextView) findViewById(R.id.tVhello);

		// establezco Listeners
		// arcade.setOnTouchListener(this);
		iBinfo.setOnClickListener(this);
		iBleft.setOnClickListener(this);
		iBright.setOnClickListener(this);
		iBupSc.setOnClickListener(this);
		iBseeSc.setOnClickListener(this);
		// rellena texto de bienvenida
		tVhello.setText(tVhello.getText() + " " + jugador.getNombre() + "!"
				+ "\nPuntuacion total: " + jugador.getScoreTotal());

		// creo arrays para minijuegos
		iBmj = new ImageButton[Props.Comun.MAX_MJ_P];
		iVsc = new ImageView[Props.Comun.MAX_MJ_P];

		// inicializo arrays
		for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
			iBmj[i] = (ImageButton) findViewById(Props.Comun.iDiBmj[i]);
			iVsc[i] = (ImageView) findViewById(Props.Comun.iDiVsC[i]);

			// imagenes de mj
			iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[i]);
			if (Props.Comun.bHabilitado(Props.Comun.iDiVmj[i])) {
				iBmj[i].setOnClickListener(this);
				iVsc[i].setImageResource(Props.Comun.getStar(jugador
						.getScore(i)));
			} else
				iVsc[i].setImageResource(Props.Comun.iDiVstar[4]);

		}
		// habilito flechas
		iBleft.setEnabled(false);
		iBright.setEnabled(true);
		der = true;
		izq = false;

	}

	/**
	 * Establece el grupo de minijuegos a mostrar en la pantalla de arcade
	 * 
	 * @param actual
	 *            el grupo a activar
	 */
	private void habilitarGrupoMJ(int actual) {
		grupoMJ = actual;
		switch (grupoMJ) {
		case 0:
			for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
				// imagenes de mj
				iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[i]);
				// iVsc[i].setImageResource(Props.Comun.getStar(jugador.getScore(i)));
				if (Props.Comun.bHabilitado(Props.Comun.iDiVmj[i])) {
					iBmj[i].setOnClickListener(this);
					iVsc[i].setImageResource(Props.Comun.getStar(jugador
							.getScore(i)));
				} else {
					iBmj[i].setOnClickListener(null);
					iVsc[i].setImageResource(Props.Comun.iDiVstar[4]);
				}
			}
			// habilito transicion derecha
			iBleft.setEnabled(false);
			izq = false;
			iBright.setEnabled(true);
			der = true;
			break;
		case 1:
			for (int j = 0, i = Props.Comun.MAX_MJ_P; i < Props.Comun.MAX_MJ_P * 2; i++) {
				// imagenes de mj
				iBmj[j].setBackgroundResource(Props.Comun.iDiVmj[i]);
				if (Props.Comun.bHabilitado(Props.Comun.iDiVmj[i])) {
					iBmj[j].setOnClickListener(this);
					iVsc[j].setImageResource(Props.Comun.getStar(jugador
							.getScore(i)));
				} else {
					iBmj[j].setOnClickListener(null);
					iVsc[j].setImageResource(Props.Comun.iDiVstar[4]);
				}
				if (j < 6)
					j++;
			}
			// habilito transicion izquierda
			iBleft.setEnabled(true);
			izq = true;
			iBright.setEnabled(false);
			der = false;
			break;
		}

	}

	/**
	 * Metodo para subir puntuaciones al servidor
	 */
	private void subirScores() {
		int[] arraySc = jugador.getScore();
		try {
			conexion.updateArcade(arraySc, jugador.getNombre());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
			case -1:// yes
				dialog.cancel();
				switch (vClicked) {
				case R.id.iBupSc:
					subirScores();
					break;
				}
			case -2:// no
				dialog.cancel();
				break;
			}

		/*
		 * else switch (boton) { case -1: dialog.cancel(); switch (vClicked) {
		 * case R.id.iBmj1: if (grupoMJ == 0) l.lanzaActivity(Props.Action.MJ1,
		 * Props.Comun.cmj1); else if (grupoMJ == 1)
		 * l.lanzaActivity(Props.Action.MJ7, Props.Comun.cmj7); break; case
		 * R.id.iBmj2: if (grupoMJ == 0) l.lanzaActivity(Props.Action.MJ2,
		 * Props.Comun.cmj2); else if (grupoMJ == 1)
		 * l.lanzaActivity(Props.Action.MJ8, Props.Comun.cmj8); break; case
		 * R.id.iBmj3: if (grupoMJ == 0) l.lanzaActivity(Props.Action.MJ3,
		 * Props.Comun.cmj3); else if (grupoMJ == 1)
		 * l.lanzaActivity(Props.Action.MJ9, Props.Comun.cmj9); break; case
		 * R.id.iBmj4: if (grupoMJ == 0) l.lanzaActivity(Props.Action.MJ4,
		 * Props.Comun.cmj4); else if (grupoMJ == 1)
		 * l.lanzaActivity(Props.Action.MJ10, Props.Comun.cmj10); break; case
		 * R.id.iBmj5: if (grupoMJ == 0) l.lanzaActivity(Props.Action.MJ5,
		 * Props.Comun.cmj6); else if (grupoMJ == 1)
		 * l.lanzaActivity(Props.Action.MJ11, Props.Comun.cmj11); break; case
		 * R.id.iBmj6: if (grupoMJ == 0) l.lanzaActivity(Props.Action.MJ6,
		 * Props.Comun.cmj6); else if (grupoMJ == 1)
		 * l.lanzaActivity(Props.Action.MJ12, Props.Comun.cmj12); break; case
		 * R.id.iBupSc: subirScores(); break; } case -2: dialog.cancel(); break;
		 * }
		 */
	}

	public void onClick(View v) {
		switch (vClicked = v.getId()) {
		// Botones
		case R.id.iBleft:
			habilitarGrupoMJ(grupoMJ - 1);
			break;
		case R.id.iBright:
			habilitarGrupoMJ(grupoMJ + 1);
			break;
		case R.id.iBinfoArcade:
			Launch.lanzaAviso("Informaci�n de arcade", Props.Strings.iArcade,
					this);
			// TODO lanzaOpciones prueba aDactual = Launch.lanzaOpciones(this);
			break;
		case R.id.iBseeSc:
			Bundle b = new Bundle();
			if (Props.Comun.ONLINE) {
				try {
					String json = conexion.dameOnlineArcade();
					b.putSerializable(Props.Comun.JSON, json);
				} catch (IOException e) {
					l.lanzaToast(Props.Strings.ERROR_INET);
					Props.Comun.ONLINE = false;
				}
			} else {
				b.putSerializable(Props.Comun.JUGADOR, jugador);
			}
			l.lanzaActivity(Props.Action.RANKING, b);
		case R.id.iBupSc:
			Launch.lanzaConfirmacion("Confirmacion para subir puntuaciones de "
					+ jugador.getNombre(), Props.Strings.upSc, this);
			break;
		// Botones de mjs
		case R.id.iBmj1:
			if (grupoMJ == 0)
				Launch.lanzaConfirmacion(this, 0, l, Dialogos.DIALOG_ARCADE);
			else if (grupoMJ == 1)
				Launch.lanzaConfirmacion(this, 6, l, Dialogos.DIALOG_ARCADE);
			break;
		case R.id.iBmj2:
			if (grupoMJ == 0)
				Launch.lanzaConfirmacion(this, 1, l, Dialogos.DIALOG_ARCADE);
			else if (grupoMJ == 1)
				Launch.lanzaConfirmacion(this, 7, l, Dialogos.DIALOG_ARCADE);
			break;
		case R.id.iBmj3:
			if (grupoMJ == 0)
				Launch.lanzaConfirmacion(this, 2, l, Dialogos.DIALOG_ARCADE);
			else if (grupoMJ == 1)
				Launch.lanzaConfirmacion(this, 8, l, Dialogos.DIALOG_ARCADE);
			break;
		case R.id.iBmj4:
			if (grupoMJ == 0)
				Launch.lanzaConfirmacion(this, 3, l, Dialogos.DIALOG_ARCADE);
			else if (grupoMJ == 1)
				Launch.lanzaConfirmacion(this, 9, l, Dialogos.DIALOG_ARCADE);
			break;
		case R.id.iBmj5:
			if (grupoMJ == 0)
				Launch.lanzaConfirmacion(this, 4, l, Dialogos.DIALOG_ARCADE);
			else if (grupoMJ == 1)
				Launch.lanzaConfirmacion(this, 10, l, Dialogos.DIALOG_ARCADE);
			break;
		case R.id.iBmj6:
			if (grupoMJ == 0)
				Launch.lanzaConfirmacion(this, 5, l, Dialogos.DIALOG_ARCADE);
			else if (grupoMJ == 1)
				Launch.lanzaConfirmacion(this, 11, l, Dialogos.DIALOG_ARCADE);
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// funciona pero si lo haces por el centro de la pantalla, donde esta el
		// layout arcade, y no siempre
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float x1,
			x2,
			dif,
			lim = llarcade.getWidth() / 4;
			;

			x1 = event.getHistoricalX(0);
			x2 = event.getX();
			dif = x1 - x2;
			if (!izq && der && dif > 0 && dif > lim)
				habilitarGrupoMJ(grupoMJ + 1);
			else if (izq && !der && dif < 0 && Math.abs(dif) > lim)
				habilitarGrupoMJ(grupoMJ - 1);
			break;
		}
		/*
		 * switch (event.getAction()) { case MotionEvent.ACTION_DOWN: x1 =
		 * event.getX(); break; case MotionEvent.ACTION_UP: x2 = event.getX();
		 * dif=x1-x2; lim = arcade.getWidth() / 3; if (!izq && der && dif > 0 &&
		 * dif > lim) { habilitarGrupoMJ(grupoMJ + 1); } else if (izq && !der &&
		 * dif < 0 && Math.abs(dif) > lim) { habilitarGrupoMJ(grupoMJ - 1); } }
		 */
		return true;
	}
}