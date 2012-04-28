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
	 * Jugador
	 */
	Jugador jugador;

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

		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		 quest = (Aventura) b.getSerializable(Props.Comun.AVENTURA);

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
			String dialogText = quest.getMinijuego(mjActual).pista;
			Launch.lanzaAviso(dialogText, this);
		}

	}

	private int generaMinijuego() {
		if (quest == null)
			return -1;
		else {
			int n = quest.size();
			if (n == jugador.getFase())
				return -2;
			else {
				Random rand = new Random();
				int x;
				// Peligro bucle infinito si no hemos marcado las fases
				while (true) {
					x = rand.nextInt(n);
					if (!quest.getMinijuego(x).getSuperado())
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
				Launch.lanzaConfirmacion(this,nMJ,
						launch, Dialogos.DIALOG_AVENTURA);
			else if (resultCode == RESULT_CANCELED) {
				// Handle cancell
			}
		} else if (resultCode == RESULT_OK) {
			Bundle b = data.getExtras();
			mDbHelper.open(false);
			int score = b.getInt(Props.Comun.SCORE);
			int fase = jugador.getFase();
			boolean superado = b.getBoolean(Props.Comun.SUPERADO);
			switch (requestCode) {
			case Props.Comun.cmj1:
				if (superado) {
					int indice1 = Props.Comun.cmj1 - 1;
					jugador.setScoreQuest(score, indice1);
					jugador.setFase(fase + 1);
					mDbHelper.updateRowPQuest(jugador.getNombre(),
							jugador.getScoreQuest(), jugador.getFase(),
							quest.getNombre());
				}
				break;
			case Props.Comun.cmj2:
				if (superado) {
					int indice2 = Props.Comun.cmj2 - 1;
					jugador.setScoreQuest(score, indice2);
					jugador.setFase(fase + 1);
					mDbHelper.updateRowPQuest(jugador.getNombre(),
							jugador.getScoreQuest(), jugador.getFase(),
							quest.getNombre());
				}

				break;
			case Props.Comun.cmj3:
				if (superado) {
					int indice3 = Props.Comun.cmj3 - 1;
					jugador.setScoreQuest(score, indice3);
					jugador.setFase(fase + 1);
					mDbHelper.updateRowPQuest(jugador.getNombre(),
							jugador.getScoreQuest(), jugador.getFase(),
							quest.getNombre());
				}
				break;
			case Props.Comun.cmj4:
				if (superado) {
					int indice4 = Props.Comun.cmj4 - 1;
					jugador.setScoreQuest(score, indice4);
					jugador.setFase(fase + 1);
					mDbHelper.updateRowPQuest(jugador.getNombre(),
							jugador.getScoreQuest(), jugador.getFase(),
							quest.getNombre());
				}
				break;
			case Props.Comun.cmj5:
				if (superado) {
					int indice5 = Props.Comun.cmj5 - 1;
					jugador.setScoreQuest(score, indice5);
					jugador.setFase(fase + 1);
					mDbHelper.updateRowPQuest(jugador.getNombre(),
							jugador.getScoreQuest(), jugador.getFase(),
							quest.getNombre());
				}
				break;
			case Props.Comun.cmj6:
				if (superado) {
					int indice6 = Props.Comun.cmj6 - 1;
					jugador.setScoreQuest(score, indice6);
					jugador.setFase(fase + 1);
					mDbHelper.updateRowPQuest(jugador.getNombre(),
							jugador.getScoreQuest(), jugador.getFase(),
							quest.getNombre());
				}
				break;
			case Props.Comun.cmj7:
				if (superado) {
					int indice7 = Props.Comun.cmj7 - 1;
					jugador.setScoreQuest(score, indice7);
					jugador.setFase(fase + 1);
					mDbHelper.updateRowPQuest(jugador.getNombre(),
							jugador.getScoreQuest(), jugador.getFase(),
							quest.getNombre());
				}
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
			mDbHelper.close();
			if (superado){
				l.lanzaAviso(Props.Strings.RESULTADO_MJ_COMPLETO,
						"Puntuacion obtenida: " + score);
				launch.lanzaDialogoUpdatePquest(jugador);
			}
			else
				l.lanzaAviso(Props.Strings.RESULTADO_MJ_INCOMPLETO,
						"No has completado el MJ, no se guardara tu puntuacion.");

			mjActual = generaMinijuego();
			if (mjActual == -2) {
				// TODO
				// launch.lanzaActivity(Props.Action.ENDGAME);
			} else if (mjActual != -1) {
				String dialogText = quest.getMinijuego(mjActual).pista;
				Launch.lanzaAviso(dialogText, this);
			}

		}
	}

}
