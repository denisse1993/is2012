//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: QRonos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.T;

/**
 * Pantalla principal de seleccion de MJ para aventura
 * 
 * @author Cinnamon Team
 * @version 1.0 23.03.2012
 */
public class SelecPista extends Activity implements OnClickListener {
	// interfaz
	private LinearLayout llselecpista;
	private ImageButton iBinfo, iBleft, iBright;
	private ImageButton[] iBmj;
	private EditText[] eTpista;
	private TextView tVhello;
	private Button bDoneSelecPISTA;
	/**
	 * Indica grupo de mjs mostrado
	 */
	private int grupoMJ;

	/**
	 * Controla la habilitacion de transicion a izquierda y derecha
	 */
	private boolean der, izq;

	/**
	 * Jugador actual en la aplicacion
	 */
	private Jugador jugador;

	/**
	 * Aventura actual en la aplicacion
	 */
	private Aventura aventura;

	/**
	 * Launch de utilidad
	 */
	private Launch l;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selecpista);
		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		aventura = (Aventura) b.getSerializable(Props.Comun.AVENTURA);
		l = new Launch(this);
		grupoMJ = 0;
		inicializar();
	}

	@Override
	protected void onResume() {
		super.onResume();
		habilitarGrupoMJ(0);
	}

	/**
	 * Metodo de utilidad para inicializar la Actividad
	 */
	private void inicializar() {

		// abre base de datos
		// mDbHelper = new DbAdapter(this);
		// mDbHelper.open(false);

		// genericos
		llselecpista = (LinearLayout) findViewById(R.id.llselecpista);
		iBinfo = (ImageButton) findViewById(R.id.iBinfoSelecPISTA);
		iBleft = (ImageButton) findViewById(R.id.iBleft);
		iBright = (ImageButton) findViewById(R.id.iBright);
		tVhello = (TextView) findViewById(R.id.tVhello);
		bDoneSelecPISTA = (Button) findViewById(R.id.bDoneSelecPISTA);
		// establezco Listeners
		// arcade.setOnTouchListener(this);
		iBinfo.setOnClickListener(this);
		iBleft.setOnClickListener(this);
		iBright.setOnClickListener(this);
		bDoneSelecPISTA.setOnClickListener(this);

		// rellena texto de bienvenida
		tVhello.setText(tVhello.getText() + " para " + aventura.getNombre()
				+ "!");

		// creo arrays para minijuegos
		iBmj = new ImageButton[Props.Comun.MAX_MJ_P];
		eTpista = new EditText[Props.Comun.MAX_MJ_P];

		// inicializo arrays
		// Iterator<Integer> j = aventura.iterator();
		Iterator<T> j = aventura.iterator();
		for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
			iBmj[i] = (ImageButton) findViewById(Props.Comun.iDiBmj[i]);
			eTpista[i] = (EditText) findViewById(Props.Comun.iDeTmj[i]);
			if (j.hasNext()) {
				T t = j.next();
				iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[t.idMj - 1]);
				iBmj[i].setOnClickListener(this);
				eTpista[i].setText(t.pista);
			} else {
				iBmj[i].setBackgroundResource(R.drawable.ibmj0);
				eTpista[i].setVisibility(View.INVISIBLE);
				eTpista[i].setEnabled(false);
				iBmj[i].setOnClickListener(null);
			}
		}

		// habilito flechas
		iBleft.setEnabled(false);
		izq = false;
		// si se han selec mas de 6 mj
		if (aventura.size() > 6) {
			iBright.setEnabled(true);
			der = true;
		} else {
			iBright.setEnabled(false);
			der = false;
		}
		// deshabilito hasta que se escriban todas las pistas
		bDoneSelecPISTA.setEnabled(false);
	}

	/**
	 * Establece el grupo de minijuegos a mostrar en la pantalla de arcade
	 * 
	 * @param actual
	 *            el grupo a activar
	 */
	private void habilitarGrupoMJ(int actual) {
		grupoMJ = actual;
		Iterator<T> it = aventura.iterator();
		switch (grupoMJ) {
		case 0:

			for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
				// imagenes de mj
				if (it.hasNext()) {
					T t = it.next();
					iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[t.idMj - 1]);
					iBmj[i].setOnClickListener(this);
					// pone pista
					eTpista[i].setVisibility(View.VISIBLE);
					eTpista[i].setEnabled(true);
					eTpista[i].setText(t.pista);
				} else {
					iBmj[i].setBackgroundResource(R.drawable.ibmj0);
					eTpista[i].setVisibility(View.INVISIBLE);
					eTpista[i].setEnabled(false);
					iBmj[i].setOnClickListener(null);
				}
			}
			// habilito flechas
			iBleft.setEnabled(false);
			izq = false;
			// si se han selec mas de 6 mj
			if (aventura.size() > 6) {
				iBright.setEnabled(true);
				der = true;
			} else {
				iBright.setEnabled(false);
				der = false;
			}
			break;
		case 1:
			// desplazo hasta sexto mj, se podria acceder directamente pero pss
			for (int c = 0; c < 6; c++)
				it.next();

			for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
				if (it.hasNext()) {
					T t = it.next();
					iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[t.idMj - 1]);
					iBmj[i].setOnClickListener(this);
					eTpista[i].setVisibility(View.VISIBLE);
					eTpista[i].setEnabled(true);
					eTpista[i].setText(t.pista);
				} else {
					iBmj[i].setBackgroundResource(R.drawable.ibmj0);
					eTpista[i].setVisibility(View.INVISIBLE);
					eTpista[i].setEnabled(false);
					iBmj[i].setOnClickListener(null);
				}
			}
			// habilito transicion izquierda
			iBleft.setEnabled(true);
			izq = true;
			iBright.setEnabled(false);
			der = false;
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// if (l.lanzaConfirmacionSimple(
		// "¿Deseas volver a selección de Minijuegos?",
		// "Aviso: Se resetearán las pistas"))
		// finish();
		// TODO temporal hasta tener un dialog en condiciones
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("¿Deseas volver a selección de Minijuegos?")
				.setMessage("Aviso: Se resetearán las pistas")
				.setCancelable(false)
				.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						SelecPista.this.finish();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show();
	}

	public void onClick(View v) {
		// habilita acabar
		String pista = null;
		switch (v.getId()) {
		// Botones
		case R.id.iBleft:
			habilitarGrupoMJ(grupoMJ - 1);
			break;
		case R.id.iBright:
			habilitarGrupoMJ(grupoMJ + 1);
			break;
		case R.id.bDoneSelecPISTA:
			Bundle b = new Bundle();
			b.putSerializable(Props.Comun.JUGADOR, jugador);
			b.putSerializable(Props.Comun.AVENTURA, aventura);
			// TODO Launch.lanzaActivity(this,Props.Action.SELECPISTA, b);
			break;
		case R.id.iBinfoSelecPISTA:
			Launch.lanzaAviso("Información", Props.Strings.iSelecPISTA, this);
			break;
		// Botones de mjs
		case R.id.iBmj1:
			pista = eTpista[0].getText().toString();
			if (pista != null && !pista.equals("")) {
				l.lanzaToast(Props.Strings.PISTA_MOD);
				if (grupoMJ == 0) {
					aventura.modPistaByPos(0, pista);
				} else if (grupoMJ == 1) {
					aventura.modPistaByPos(6, pista);
				}
			}
			break;
		case R.id.iBmj2:
			pista = eTpista[1].getText().toString();
			if (pista != null && !pista.equals("")) {
				l.lanzaToast(Props.Strings.PISTA_MOD);
				if (grupoMJ == 0) {
					aventura.modPistaByPos(1, pista);
				} else if (grupoMJ == 1) {
					aventura.modPistaByPos(7, pista);
				}
			}

			break;
		case R.id.iBmj3:
			pista = eTpista[2].getText().toString();
			if (pista != null && !pista.equals("")) {
				l.lanzaToast(Props.Strings.PISTA_MOD);
				if (grupoMJ == 0) {
					aventura.modPistaByPos(2, pista);
				} else if (grupoMJ == 1) {
					aventura.modPistaByPos(8, pista);
				}
			}

			break;
		case R.id.iBmj4:
			pista = eTpista[3].getText().toString();
			if (pista != null && !pista.equals("")) {
				l.lanzaToast(Props.Strings.PISTA_MOD);
				if (grupoMJ == 0) {
					aventura.modPistaByPos(3, pista);
				} else if (grupoMJ == 1) {
					aventura.modPistaByPos(9, pista);
				}
			}

			break;
		case R.id.iBmj5:
			pista = eTpista[4].getText().toString();
			if (pista != null && !pista.equals("")) {
				l.lanzaToast(Props.Strings.PISTA_MOD);
				if (grupoMJ == 0) {
					aventura.modPistaByPos(4, pista);
				} else if (grupoMJ == 1) {
					aventura.modPistaByPos(10, pista);
				}
			}

			break;
		case R.id.iBmj6:
			pista = eTpista[5].getText().toString();
			if (pista != null && !pista.equals("")) {
				l.lanzaToast(Props.Strings.PISTA_MOD);
				if (grupoMJ == 0) {
					aventura.modPistaByPos(5, pista);
				} else if (grupoMJ == 1) {
					aventura.modPistaByPos(11, pista);
				}
			}

			break;
		}
		if (aventura.sizePista() == aventura.size()) {
			bDoneSelecPISTA.setEnabled(true);
			l.lanzaToast(Props.Strings.PISTAS_COMPLETO);
		} else
			bDoneSelecPISTA.setEnabled(false);
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
			lim = llselecpista.getWidth() / 4;

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
