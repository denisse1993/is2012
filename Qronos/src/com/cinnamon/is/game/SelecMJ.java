//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: QRonos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
public class SelecMJ extends Activity implements OnClickListener {
	// interfaz
	private LinearLayout llselecmj;
	private ImageButton iBinfo, iBleft, iBright;
	private ImageButton[] iBmj;
	private ImageView[] iVsc;
	private TextView tVhello;
	private Button bDoneSelecMJ;
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
		llselecmj = (LinearLayout) findViewById(R.id.llselecmj);
		iBinfo = (ImageButton) findViewById(R.id.iBinfoSelecMJ);
		iBleft = (ImageButton) findViewById(R.id.iBleft);
		iBright = (ImageButton) findViewById(R.id.iBright);
		tVhello = (TextView) findViewById(R.id.tVhello);
		bDoneSelecMJ = (Button) findViewById(R.id.bDoneSelecMJ);
		// establezco Listeners
		// arcade.setOnTouchListener(this);
		iBinfo.setOnClickListener(this);
		iBleft.setOnClickListener(this);
		iBright.setOnClickListener(this);
		bDoneSelecMJ.setOnClickListener(this);

		// rellena texto de bienvenida
		tVhello.setText(tVhello.getText() + " para " + aventura.getNombre()
				+ "!");

		// creo arrays para minijuegos
		iBmj = new ImageButton[Props.Comun.MAX_MJ_P];
		iVsc = new ImageView[Props.Comun.MAX_MJ_P];

		// inicializo arrays
		for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
			iBmj[i] = (ImageButton) findViewById(Props.Comun.iDiBmj[i]);
			iVsc[i] = (ImageView) findViewById(Props.Comun.iDiVsC[0]);

			// imagenes de mj
			iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[i]);
			if (Props.Comun.bHabilitado(Props.Comun.iDiVmj[i])) {
				iBmj[i].setOnClickListener(this);
			} else
				iVsc[i].setImageResource(Props.Comun.iDiVselec[2]);

		}
		// habilito flechas
		iBleft.setEnabled(false);
		iBright.setEnabled(true);
		der = true;
		izq = false;
		// deshabilito hasta que se elija al menos 1
		bDoneSelecMJ.setEnabled(false);
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
				if (Props.Comun.bHabilitado(Props.Comun.iDiVmj[i])) {
					iBmj[i].setOnClickListener(this);
					if (aventura.existe(Props.Comun.CMJ[i]))
						iVsc[i].setImageResource(Props.Comun.iDiVmj[1]);
					else
						iVsc[i].setImageResource(Props.Comun.iDiVmj[0]);
				} else {
					iBmj[i].setOnClickListener(null);
					iVsc[i].setImageResource(Props.Comun.iDiVselec[0]);
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
					if (aventura.existe(Props.Comun.CMJ[j]))
						iVsc[j].setImageResource(Props.Comun.iDiVmj[1]);
					else
						iVsc[j].setImageResource(Props.Comun.iDiVmj[0]);
				} else {
					iBmj[j].setOnClickListener(null);
					iVsc[j].setImageResource(Props.Comun.iDiVselec[0]);
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

	public void onClick(View v) {
		// habilita acabar
		if (aventura.size() == 0)
			bDoneSelecMJ.setEnabled(false);
		else
			bDoneSelecMJ.setEnabled(true);

		switch (v.getId()) {
		// Botones
		case R.id.iBleft:
			habilitarGrupoMJ(grupoMJ - 1);
			break;
		case R.id.iBright:
			habilitarGrupoMJ(grupoMJ + 1);
			break;
		case R.id.bDoneSelecMJ:
			Bundle b = new Bundle();
			b.putSerializable(Props.Comun.JUGADOR, jugador);
			b.putSerializable(Props.Comun.AVENTURA, aventura);
			Launch.lanzaActivity(this, Props.Action.SELECPISTA, b);
			break;
		case R.id.iBinfoSelecMJ:
			Launch.lanzaAviso("Información", Props.Strings.iSelecMJ, this);
			break;
		// Botones de mjs
		case R.id.iBmj1:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[0])) {
					aventura.addMJ(Props.Comun.CMJ[0], null);
					iVsc[0].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[0]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[0].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[6])) {
					aventura.addMJ(Props.Comun.CMJ[6], null);
					iVsc[0].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[6]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[0].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		case R.id.iBmj2:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[1])) {
					aventura.addMJ(Props.Comun.CMJ[1], null);
					iVsc[1].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[1]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[1].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[7])) {
					aventura.addMJ(Props.Comun.CMJ[7], null);
					iVsc[1].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[7]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[1].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		case R.id.iBmj3:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[2])) {
					aventura.addMJ(Props.Comun.CMJ[2], null);
					iVsc[2].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[2]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[2].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[8])) {
					aventura.addMJ(Props.Comun.CMJ[8], null);
					iVsc[2].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[8]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[2].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		case R.id.iBmj4:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[3])) {
					aventura.addMJ(Props.Comun.CMJ[3], null);
					iVsc[3].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[3]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[3].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[9])) {
					aventura.addMJ(Props.Comun.CMJ[9], null);
					iVsc[3].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[9]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[3].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		case R.id.iBmj5:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[4])) {
					aventura.addMJ(Props.Comun.CMJ[4], null);
					iVsc[4].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[4]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[4].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[10])) {
					aventura.addMJ(Props.Comun.CMJ[10], null);
					iVsc[4].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[10]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[4].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		case R.id.iBmj6:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[5])) {
					aventura.addMJ(Props.Comun.CMJ[5], null);
					iVsc[5].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[5]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[5].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[11])) {
					aventura.addMJ(Props.Comun.CMJ[11], null);
					iVsc[5].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					// aventura.delMJ(Props.Comun.CMJ[11]);
					aventura.delMJ(Props.Comun.CMJ[0], null);
					iVsc[5].setImageResource(Props.Comun.iDiVselec[0]);
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
			lim = llselecmj.getWidth() / 4;

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
