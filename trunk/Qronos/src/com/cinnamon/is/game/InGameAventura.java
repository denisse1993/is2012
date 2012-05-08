// Universidad Complutense de Madrid
package com.cinnamon.is.game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Conexion;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Inet;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.UtilJSON;
import com.cinnamon.is.comun.UtilQR;
import com.cinnamon.is.comun.dialog.Dialogos;

/**
 * Clase del Modo Ingame de un Jugador
 * 
 * @author CinnamonTeam
 * @version 1.0 18.04.2012
 */
public class InGameAventura extends Activity implements OnClickListener, Inet,
		DialogInterface.OnClickListener {
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
	int superados = 0;
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
	 * Utilidad para lanzar el Scanner QR y traducir la respuesta del server
	 */
	private UtilQR q;
	private UtilJSON j;
	/**
	 * Interfaz
	 */
	private LinearLayout llInGame, llInGameActionBar, llInGameBottomBar;
	private ImageView bOpciones, bCamara, bRanking;
	private TextView title;

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
	/** timer background **/
	private Timer timer;
	private int start; // start en X sec.
	private int period; // se repite cada X sec.

	/** Para saber cual ha sido la ultima notificacion **/
	private int currentNotif;
	private TextView textFinal;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame_aventura);

		mDbHelper = new DbAdapter(this);
		this.mDbHelper.open(false);
		// Bundle
		Bundle b = getIntent().getExtras();
		this.jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		this.quest = (Aventura) b.getSerializable(Props.Comun.AVENTURA);
		this.jugador.setAventura(quest.getNombre());

		// FindViewByID
		this.bOpciones = (ImageView) findViewById(R.id.iv_opciones_ingame);
		this.bCamara = (ImageView) findViewById(R.id.iv_camara_ingame);
		this.bRanking = (ImageView) findViewById(R.id.iv_ranking_ingame);

		llInGame = (LinearLayout) findViewById(R.id.ll_ingame);
		llInGameActionBar = (LinearLayout) findViewById(R.id.ll_ingame_action_bar);
		llInGameBottomBar = (LinearLayout) findViewById(R.id.ll_ingame_bottom_bar);

		this.title = (TextView) findViewById(R.id.title_in_game);

		title.setText(quest.getNombre());

		// Opacidad
		llInGame.getBackground().setAlpha(75);
		llInGameActionBar.getBackground().setAlpha(175);
		llInGameBottomBar.getBackground().setAlpha(175);

		bOpciones.setAlpha(150);
		bCamara.setAlpha(150);
		bRanking.setAlpha(150);

		// Listeners
		this.bOpciones.setOnClickListener(this);
		this.bCamara.setOnClickListener(this);
		this.bRanking.setOnClickListener(this);

		// Conexion
		this.conexion = new Conexion(this);
		this.l = new Launch(this);

		// Generar Minijuegos
		this.mjActual = generaMinijuego();
		// this.mjActual = 1;
		if (this.mjActual == -2) {
			// TODO
			// launch.lanzaActivity(Props.Action.ENDGAME);
		} else if (this.mjActual != -1) {
			String dialogText = this.quest.getMinijuego(this.mjActual).pista;
			Launch.lanzaAviso(dialogText, this);
		}
		start = 10000;
		period = 50000;
		currentNotif = 0;
		programarTimer();

		j = new UtilJSON(this);
		// textFinal = (TextView) findViewById(R.id.tv_fin);
	}

	private int generaMinijuego() {
		if (this.quest == null) {
			return -1;
		} else {
			int n = this.quest.size();
			if (n == this.jugador.getFase() || superados == n) {
				return -2;
			} else {
				Random rand = new Random();
				int x;
				while (true) {
					x = rand.nextInt(n);
					if (!this.quest.getMinijuego(x).getSuperado()) {
						return x;
					}
				}
				// Peligro bucle infinito si no hemos marcado las fases
				// siguienteMinijuego();
			}
		}
	}

	/*
	 * private void siguienteMinijuego() { while (true) { x = rand.nextInt(n);
	 * if (!this.quest.getMinijuego(x).getSuperado()) { return x; } }
	 * 
	 * }
	 */

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		timer.cancel();
		this.aDactual = Launch
				.lanzaConfirmacion("Salir", "ÀDesea Salir?", this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.mDbHelper.close();
	}

	@Override
	protected void onResume() {

		super.onResume();
		if (!this.mDbHelper.isOpen()) {
			this.mDbHelper.open(false);
		}
		programarTimer();
		// l.lanzaAviso("prueba");
	}

	public void onClick(final DialogInterface dialog, final int boton) {
		if (this.aDactual == dialog) {
			switch (boton) {
			case -1:// yes
				dialog.cancel();
				super.onBackPressed();
				// this.l.lanzaActivity(Props.Action.MAINMENU);
				break;
			case -2:// no
				dialog.cancel();
				break;
			}
		}
	}

	@Override
	public void onClick(final View v) {
		switch (this.vClicked = v.getId()) {
		case R.id.iv_ranking_ingame:
			timer.cancel();
			this.l.lanzaDialogoGetPquest(quest.getNombre());
			break;
		case R.id.iv_camara_ingame:
			timer.cancel();
			this.q = new UtilQR(this);
			this.q.lanzarQR();
			break;
		case R.id.iv_opciones_ingame:
			/*
			 * timer.cancel(); Launch.lanzaActivity(this,
			 * Props.Action.OPCIONES);
			 */
			break;
		case R.id.iv_info_ingame:
			Launch.lanzaAviso("Informaci—n Aventura", Props.Strings.iHost, this);
		}
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		String contents = this.q.getRawQR(requestCode, resultCode, data);
		int nMJ = -5;// valor cualquiera no util
		if (contents != null) {
			try {
				nMJ = Integer.parseInt(contents);
			} catch (NumberFormatException e) {
				// Simplemente la capturamos para que no pete si no es un numero
				// de mj valido
			}
		}
		if (requestCode == UtilQR.REQUEST_CODE && nMJ == this.mjActual) {
			if (resultCode == RESULT_OK) {
				Launch.lanzaConfirmacion(this, nMJ, this.l,
						Dialogos.DIALOG_AVENTURA);
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancell
			} else if (nMJ != this.mjActual) {
				Toast.makeText(this, "Codigo erroneo, busca el correcto!!",
						3000).show();
			}

		} else if (resultCode == RESULT_OK) {
			Bundle b = data.getExtras();
			mDbHelper.open(false);
			int score = b.getInt(Props.Comun.SCORE);
			int fase = this.jugador.getFase();
			boolean superado = b.getBoolean(Props.Comun.SUPERADO);
			switch (requestCode) {
			case Props.Comun.cmj1:
				if (superado) {
					int indice1 = Props.Comun.cmj1 - 1;
					this.jugador.setScoreQuest(score, indice1);
					this.jugador.setFase(fase + 1);
					this.mDbHelper.updateRowPQuest(this.jugador.getNombre(),
							this.jugador.getScoreQuest(),
							this.jugador.getFase(), this.quest.getNombre());
					this.quest.getMinijuego(0).setSuperado(true);
					// subirScoreAventura();
				}
				break;
			case Props.Comun.cmj2:
				if (superado) {
					int indice2 = Props.Comun.cmj2 - 1;
					this.jugador.setScoreQuest(score, indice2);
					this.jugador.setFase(fase + 1);
					this.mDbHelper.updateRowPQuest(this.jugador.getNombre(),
							this.jugador.getScoreQuest(),
							this.jugador.getFase(), this.quest.getNombre());
					this.quest.getMinijuego(1).setSuperado(true);
					// subirScoreAventura();
				}

				break;
			case Props.Comun.cmj3:
				if (superado) {
					int indice3 = Props.Comun.cmj3 - 1;
					this.jugador.setScoreQuest(score, indice3);
					this.jugador.setFase(fase + 1);
					this.mDbHelper.updateRowPQuest(this.jugador.getNombre(),
							this.jugador.getScoreQuest(),
							this.jugador.getFase(), this.quest.getNombre());
					this.quest.getMinijuego(2).setSuperado(true);
				}
				break;
			case Props.Comun.cmj4:
				if (superado) {
					int indice4 = Props.Comun.cmj4 - 1;
					this.jugador.setScoreQuest(score, indice4);
					this.jugador.setFase(fase + 1);
					this.mDbHelper.updateRowPQuest(this.jugador.getNombre(),
							this.jugador.getScoreQuest(),
							this.jugador.getFase(), this.quest.getNombre());
					this.quest.getMinijuego(3).setSuperado(true);
				}
				break;
			case Props.Comun.cmj5:
				if (superado) {
					int indice5 = Props.Comun.cmj5 - 1;
					this.jugador.setScoreQuest(score, indice5);
					this.jugador.setFase(fase + 1);
					this.mDbHelper.updateRowPQuest(this.jugador.getNombre(),
							this.jugador.getScoreQuest(),
							this.jugador.getFase(), this.quest.getNombre());
					this.quest.getMinijuego(4).setSuperado(true);
				}
				break;
			case Props.Comun.cmj6:
				if (superado) {
					int indice6 = Props.Comun.cmj6 - 1;
					this.jugador.setScoreQuest(score, indice6);
					this.jugador.setFase(fase + 1);
					this.mDbHelper.updateRowPQuest(this.jugador.getNombre(),
							this.jugador.getScoreQuest(),
							this.jugador.getFase(), this.quest.getNombre());
					this.quest.getMinijuego(5).setSuperado(true);
				}
				break;
			case Props.Comun.cmj7:
				if (superado) {
					int indice7 = Props.Comun.cmj7 - 1;
					this.jugador.setScoreQuest(score, indice7);
					this.jugador.setFase(fase + 1);
					this.mDbHelper.updateRowPQuest(this.jugador.getNombre(),
							this.jugador.getScoreQuest(),
							this.jugador.getFase(), this.quest.getNombre());
					this.quest.getMinijuego(6).setSuperado(true);
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
			if (superado) {
				this.l.lanzaAviso(Props.Strings.RESULTADO_MJ_COMPLETO,
						"Puntuacion obtenida: " + score);
				this.l.lanzaDialogoUpdatePquest(this.jugador);
				superados++;
				this.mjActual = generaMinijuego();
			} else {
				this.l.lanzaAviso(Props.Strings.RESULTADO_MJ_INCOMPLETO,
						"No has completado el MJ, no se guardara tu puntuacion.");
			}

			// this.mjActual = 1;
			if (this.mjActual == -2) {
				// TODO
				textFinal
						.setText("se acabó el juego para ti, espera a que terminen los demás");
			} else if (this.mjActual != -1) {
				String dialogText = this.quest.getMinijuego(this.mjActual).pista;
				Launch.lanzaAviso(dialogText, this);
			}

		}

	}

	public void programarTimer() {
		timer = new Timer();

		timer.schedule/* AtFixedRate */(new TimerTask() {

			public void run() {
				funcionTimer();
			}

		}, start, period);

	}

	protected void funcionTimer() {
		this.runOnUiThread(mostrarMensaje);

	}

	final Activity a = this;
	private Runnable mostrarMensaje = new Runnable() {
		public void run() {
			if (conexion.getNotif(jugador.getNombre())) {
				int respuesta = Integer.parseInt(j.jsonToString(conexion
						.getRespuesta()));
				if (currentNotif != respuesta) {
					currentNotif = respuesta;
					Launch.lanzaAviso("Notificaciones pendientes",
							"Click en Ranking para mas detalles", a);
				}
			}
		}
	};

	@Override
	public Launch l() {
		return l;
	}

	@Override
	public Conexion c() {
		return conexion;
	}

	public void subirScoreAventura() {
		l.lanzaDialogoUpdatePquest(jugador);
	}
}
