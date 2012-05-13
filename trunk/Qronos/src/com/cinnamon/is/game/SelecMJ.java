//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: QRonos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	private LinearLayout llselecmj,llArcadeActionBar, llArcadeBottomBar;
	private ImageView iBinfo, iBleft, iBright;
	private ImageButton[] iBmj;
	private ImageView[] iVselec;
	private TextView tVhello;
	private ImageView bDoneSelecMJ;
	/**
	 * Indica grupo de mjs mostrado
	 */
	private int grupoMJ;

	/**
	 * Controla la habilitacion de transicion a izquierda y derecha
	 */
	private boolean der, izq;

	/**
	 * Aventura actual en la aplicacion
	 */
	private Aventura aventura;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selecmj);
		Bundle b = getIntent().getExtras();
		aventura = (Aventura) b.getSerializable(Props.Comun.AVENTURA);

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
		llselecmj = (LinearLayout) findViewById(R.id.llselecmj);
		llArcadeActionBar = (LinearLayout) findViewById(R.id.ll_action_bar);
		llArcadeBottomBar = (LinearLayout) findViewById(R.id.ll_arcade_bottom_bar);
		iBinfo = (ImageView) findViewById(R.id.iBinfoSelecMJ);
		iBleft = (ImageView) findViewById(R.id.iBleft);
		iBright = (ImageView) findViewById(R.id.iBright);
		tVhello = (TextView) findViewById(R.id.tVhello);
		bDoneSelecMJ = (ImageView) findViewById(R.id.iVDone);
		// establezco Listeners
		// arcade.setOnTouchListener(this);
		iBinfo.setOnClickListener(this);
		iBleft.setOnClickListener(this);
		iBright.setOnClickListener(this);
		bDoneSelecMJ.setOnClickListener(this);

		// Opacidad
		llselecmj.getBackground().setAlpha(75);
		llArcadeActionBar.getBackground().setAlpha(175);
		llArcadeBottomBar.getBackground().setAlpha(175);

		iBinfo.setAlpha(150);
		iBleft.setAlpha(150);
		iBright.setAlpha(150);
		bDoneSelecMJ.setAlpha(150);
		
		// rellena texto de bienvenida
		tVhello.setText(tVhello.getText() + " para " + aventura.getNombre()
				+ "!");

		// creo arrays para minijuegos
		iBmj = new ImageButton[Props.Comun.MAX_MJ_P];
		iVselec = new ImageView[Props.Comun.MAX_MJ_P];

		// inicializo arrays
		for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
			iBmj[i] = (ImageButton) findViewById(Props.Comun.iDiBmj[i]);
			iVselec[i] = (ImageView) findViewById(Props.Comun.iDiVsC[i]);
		}
		habilitarGrupoMJ(0);
		if (aventura.size() > 0)
			bDoneSelecMJ.setEnabled(true);
		else
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
						iVselec[i].setImageResource(Props.Comun.iDiVselec[1]);
					else
						iVselec[i].setImageResource(Props.Comun.iDiVselec[0]);
				} else {
					iBmj[i].setOnClickListener(null);
					iVselec[i].setImageResource(Props.Comun.iDiVselec[2]);
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
					if (aventura.existe(Props.Comun.CMJ[i]))
						iVselec[j].setImageResource(Props.Comun.iDiVselec[1]);
					else
						iVselec[j].setImageResource(Props.Comun.iDiVselec[0]);
				} else {
					iBmj[j].setOnClickListener(null);
					iVselec[j].setImageResource(Props.Comun.iDiVselec[2]);
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

	@Override
	public void onBackPressed() {
		// if
		// (Launch.lanzaConfirmacionSimple("¿Deseas volver a Menu Principal?",
		// "Aviso: Se resetearán los Minijuegos", this))
		// finish();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("¿Deseas volver al Menu Principal?")
				.setMessage("Aviso: Se resetearán los Minijuegos")
				.setCancelable(false)
				.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						SelecMJ.this.finish();
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
		switch (v.getId()) {
		// Botones
		case R.id.iBleft:
			habilitarGrupoMJ(grupoMJ - 1);
			break;
		case R.id.iBright:
			habilitarGrupoMJ(grupoMJ + 1);
			break;
		case R.id.iVDone:
			Bundle b = new Bundle();
			b.putSerializable(Props.Comun.AVENTURA, aventura);
			Props.Comun.ACTIVIDAD = SelecMJ.this;
			Launch.lanzaActivity(this, Props.Action.SELECPISTA, b);
			break;
		case R.id.iBinfoSelecMJ:
			Launch.lanzaAviso("Información", Props.Strings.iSelecMJ, this);
			break;
		// Botones de mjs
		case R.id.iBmj1:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[0])) {
					aventura.addMJ(Props.Comun.CMJ[0]);
					iVselec[0].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[0]);
					iVselec[0].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[6])) {
					aventura.addMJ(Props.Comun.CMJ[6]);
					iVselec[0].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[6]);
					iVselec[0].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		case R.id.iBmj2:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[1])) {
					aventura.addMJ(Props.Comun.CMJ[1]);
					iVselec[1].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[1]);
					iVselec[1].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[7])) {
					aventura.addMJ(Props.Comun.CMJ[7]);
					iVselec[1].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[7]);
					iVselec[1].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		case R.id.iBmj3:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[2])) {
					aventura.addMJ(Props.Comun.CMJ[2]);
					iVselec[2].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[2]);
					iVselec[2].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[8])) {
					aventura.addMJ(Props.Comun.CMJ[8]);
					iVselec[2].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[8]);
					iVselec[2].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		case R.id.iBmj4:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[3])) {
					aventura.addMJ(Props.Comun.CMJ[3]);
					iVselec[3].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[3]);
					iVselec[3].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[9])) {
					aventura.addMJ(Props.Comun.CMJ[9]);
					iVselec[3].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[9]);
					iVselec[3].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		case R.id.iBmj5:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[4])) {
					aventura.addMJ(Props.Comun.CMJ[4]);
					iVselec[4].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[4]);
					iVselec[4].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[10])) {
					aventura.addMJ(Props.Comun.CMJ[10]);
					iVselec[4].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[10]);
					iVselec[4].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		case R.id.iBmj6:
			if (grupoMJ == 0) {
				if (!aventura.existe(Props.Comun.CMJ[5])) {
					aventura.addMJ(Props.Comun.CMJ[5]);
					iVselec[5].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[0]);
					iVselec[5].setImageResource(Props.Comun.iDiVselec[0]);
				}
			} else if (grupoMJ == 1) {
				if (!aventura.existe(Props.Comun.CMJ[11])) {
					aventura.addMJ(Props.Comun.CMJ[11]);
					iVselec[5].setImageResource(Props.Comun.iDiVselec[1]);
				} else {
					aventura.delMJ(Props.Comun.CMJ[11]);
					iVselec[5].setImageResource(Props.Comun.iDiVselec[0]);
				}
			}
			break;
		}
		// habilita acabar
		if (aventura.size() == 0)
			bDoneSelecMJ.setEnabled(false);
		else
			bDoneSelecMJ.setEnabled(true);
	}

	// TODO ontouch desactivado
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // funciona pero si lo haces por el centro de la pantalla, donde esta el
	// // layout arcade, y no siempre
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_MOVE:
	// float x1,
	// x2,
	// dif,
	// lim = llselecmj.getWidth() / 4;
	//
	// x1 = event.getHistoricalX(0);
	// x2 = event.getX();
	// dif = x1 - x2;
	// if (!izq && der && dif > 0 && dif > lim)
	// habilitarGrupoMJ(grupoMJ + 1);
	// else if (izq && !der && dif < 0 && Math.abs(dif) > lim)
	// habilitarGrupoMJ(grupoMJ - 1);
	// break;
	// }
	// /*
	// * switch (event.getAction()) { case MotionEvent.ACTION_DOWN: x1 =
	// * event.getX(); break; case MotionEvent.ACTION_UP: x2 = event.getX();
	// * dif=x1-x2; lim = arcade.getWidth() / 3; if (!izq && der && dif > 0 &&
	// * dif > lim) { habilitarGrupoMJ(grupoMJ + 1); } else if (izq && !der &&
	// * dif < 0 && Math.abs(dif) > lim) { habilitarGrupoMJ(grupoMJ - 1); } }
	// */
	// return true;
	// }
}
