// Universidad Complutense de Madrid
package com.cinnamon.is.game;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Conexion;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.UtilQR;
import com.cinnamon.is.comun.dialog.Dialogos;

/**
 * Clase del Modo Ingame de un Jugador
 * 
 * @author CinnamonTeam
 * @version 1.0 18.04.2012
 */
public class InGameAventura extends Activity implements OnClickListener {
	/**
	 * Conexion de la actividad
	 */
	Conexion conex;

	/**
	 * Aventura
	 */
	Aventura quest;

	/**
	 * Lauch de la actividad
	 */
	public Launch launch;

	/**
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	private AlertDialog aDactual;

	/**
	 * Vista pulsada en onClick para uso en dialog onclick
	 */
	private int vClicked;
	/**
	 * Utilidad para lanzar el Scanner QR
	 */
	private UtilQR q;

	/**
	 * 
	 */
	private ImageButton bOpciones, bCamara, bRanking;

	/**
	 * Minijuego Actual -1 NO existe la aventura -2 Todos los minijuegos han
	 * sido completados
	 */
	private int mjActual;

	/**
	 * Conectar con server
	 */
	public Conexion conexion;

	public Launch l;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame_aventura);

		bOpciones = (ImageButton) findViewById(R.id.ib_opciones_ingame);
		bCamara = (ImageButton) findViewById(R.id.ib_camara_ingame);
		bRanking = (ImageButton) findViewById(R.id.ib_ranking_ingame);

		bOpciones.setOnClickListener(this);
		bCamara.setOnClickListener(this);
		bRanking.setOnClickListener(this);

		conexion = new Conexion(this);
		l = new Launch(this);
		
		mjActual = generaMinijuego();
		if (mjActual == -2) {
			// TODO
			// launch.lanzaActivity(Props.Action.ENDGAME);
		} else if (mjActual != -1) {
			String dialogText = quest.getMinijuego(mjActual).toString();
			Launch.lanzaAviso(dialogText, this);
		}

	}

	private int generaMinijuego() {
		if (quest == null)
			return -1;
		else {
			int n = quest.size();
			if (n == quest.getNSuperados())
				return -2;
			else {
				Random rand = new Random();
				int x;
				// Peligro bucle infinito si no hemos marcado bien los superados
				while (true) {
					x = rand.nextInt(n);
					if (quest.getMinijuego(x).getSuperado())
						return x;
				}
			}
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		aDactual = Launch.lanzaConfirmacion("Salir", "ÀDesea Salir?", this);
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

	public void onClick(DialogInterface dialog, int boton) {
		if (aDactual == dialog) {
			switch (boton) {
			case -1:// yes
				dialog.cancel();
				launch.lanzaActivity(Props.Action.MAINMENU);
				break;
			case -2:// no
				dialog.cancel();
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (vClicked = v.getId()) {
		case R.id.ll_ranking_ingame:
			launch.lanzaActivity(Props.Action.RANKING);
		case R.id.ll_camara_ingame:
			q = new UtilQR(this);
			q.lanzarQR();
		case R.id.ll_opciones_ingame:
			launch.lanzaActivity(Props.Action.OPCIONES);
		case R.id.ib_info_ingame:
			String dialogText = quest.getMinijuego(mjActual).toString();
			Launch.lanzaAviso(dialogText, this);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String contents = q.getRawQR(requestCode, resultCode, data);
		int nMJ = Integer.parseInt(contents);
		if (requestCode == UtilQR.REQUEST_CODE && nMJ == mjActual) {
			if (resultCode == RESULT_OK)
				Launch.lanzaConfirmacion(this, Integer.parseInt(contents),
						launch, Dialogos.DIALOG_AVENTURA);
			else if (resultCode == RESULT_CANCELED) {
				// Handle cancell
			}
		}
	}

}
