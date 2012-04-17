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
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	/**
	 * Jugador actual en la aplicacion
	 */
	private Jugador jugador;

	/**
	 * Aventura actual en la aplicacion
	 */
	private Aventura aventura;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selecmj);
		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		aventura = (Aventura) b.getSerializable(Props.Comun.AVENTURA);

		grupoMJ = 0;
		inicializar();
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
		Iterator<Pair<Integer, String>> j = aventura.iterator();
		for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
			iBmj[i] = (ImageButton) findViewById(Props.Comun.iDiBmj[i]);
			eTpista[i] = (EditText) findViewById(Props.Comun.iDeTmj[i]);
			if (j.hasNext()) {
				iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[j.next().first - 1]);
			} else {
				iBmj[i].setBackgroundResource(R.drawable.ibmj0);
				eTpista[i].setVisibility(View.INVISIBLE);
				eTpista[i].setEnabled(false);
			}
			iBmj[i].setOnClickListener(null);
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
		Iterator<Pair<Integer, String>> it = aventura.iterator();
		switch (grupoMJ) {
		case 0:

			for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
				// imagenes de mj
				if (it.hasNext()) {
					iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[it.next().first - 1]);
					eTpista[i].setVisibility(View.VISIBLE);
					eTpista[i].setEnabled(true);
				} else {
					iBmj[i].setBackgroundResource(R.drawable.ibmj0);
					eTpista[i].setVisibility(View.INVISIBLE);
					eTpista[i].setEnabled(false);
				}
			}
			// habilito transicion derecha
			iBleft.setEnabled(false);
			izq = false;
			iBright.setEnabled(true);
			der = true;
			break;
		case 1:
			// desplazo hasta sexto mj, se podria acceder directamente pero pss
			for (int c = 0; c < 6; c++)
				it.next();

			for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
				if (it.hasNext()) {
					iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[it.next().first - 1]);
					eTpista[i].setVisibility(View.VISIBLE);
					eTpista[i].setEnabled(true);
				} else {
					iBmj[i].setBackgroundResource(R.drawable.ibmj0);
					eTpista[i].setVisibility(View.INVISIBLE);
					eTpista[i].setEnabled(false);
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

	public void onClick(View v) {
		// habilita acabar
		if (aventura.sizePista() == aventura.size())
			bDoneSelecPISTA.setEnabled(false);
		else
			bDoneSelecPISTA.setEnabled(true);
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
			// TODO Launch.lanzaActivity(this, Props.Action.SELECPISTA, b);
			break;
		case R.id.iBinfoSelecMJ:
			Launch.lanzaAviso("Información", Props.Strings.iSelecMJ, this);
			break;
		// Botones de mjs
		case R.id.iBmj1:
			pista = eTpista[0].getText().toString();
			if (pista != null && !pista.equals("")) {
				if (grupoMJ == 0) {
					aventura.modMJ(0, pista);
				} else if (grupoMJ == 1) {
					aventura.modMJ(6, pista);
				}
			}
			break;
		case R.id.iBmj2:
			pista = eTpista[1].getText().toString();
			if (pista != null && !pista.equals("")) {
				if (grupoMJ == 0) {
					aventura.modMJ(1, pista);
				} else if (grupoMJ == 1) {
					aventura.modMJ(7, pista);
				}
			}
			break;
		case R.id.iBmj3:
			pista = eTpista[2].getText().toString();
			if (pista != null && !pista.equals("")) {
				if (grupoMJ == 0) {
					aventura.modMJ(2, pista);
				} else if (grupoMJ == 1) {
					aventura.modMJ(8, pista);
				}
			}
			break;
		case R.id.iBmj4:
			pista = eTpista[3].getText().toString();
			if (pista != null && !pista.equals("")) {
				if (grupoMJ == 0) {
					aventura.modMJ(3, pista);
				} else if (grupoMJ == 1) {
					aventura.modMJ(9, pista);
				}
			}
			break;
		case R.id.iBmj5:
			pista = eTpista[4].getText().toString();
			if (pista != null && !pista.equals("")) {
				if (grupoMJ == 0) {
					aventura.modMJ(4, pista);
				} else if (grupoMJ == 1) {
					aventura.modMJ(10, pista);
				}
			}
			break;
		case R.id.iBmj6:
			pista = eTpista[5].getText().toString();
			if (pista != null && !pista.equals("")) {
				if (grupoMJ == 0) {
					aventura.modMJ(5, pista);
				} else if (grupoMJ == 1) {
					aventura.modMJ(11, pista);
				}
			}
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
