package com.cinnamon.is.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Conexion;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Inet;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;

public class EndGame extends Activity implements OnClickListener, Inet,
		DialogInterface.OnClickListener {

	/**
	 * Vista pulsada en onClick para uso en dialog onclick
	 */
	private int vClicked;

	/**
	 * Jugador
	 */
	Jugador jugador;
	// int superados = 0;Lo metio en aventura

	/**
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	private AlertDialog aDactual;

	/**
	 * Interfaz
	 */
	private LinearLayout llInGame, llInGameActionBar, llInGameBottomBar;
	private ImageView bExit, bRanking;
	private TextView title;

	/**
	 * Launch y conexion
	 */
	private Launch l;
	private Conexion conexion;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_game);

		mDbHelper = new DbAdapter(this);
		this.mDbHelper.open(false);
		// Bundle
		Bundle b = getIntent().getExtras();
		this.jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);

		// FindViewByID

		this.bRanking = (ImageView) findViewById(R.id.iv_end_game_ranking);
		this.bExit = (ImageView) findViewById(R.id.iv_end_game_exit);

		llInGame = (LinearLayout) findViewById(R.id.ll_medio_end_game);
		llInGameActionBar = (LinearLayout) findViewById(R.id.ll_end_game_action_bar);
		llInGameBottomBar = (LinearLayout) findViewById(R.id.ll_end_game_bottom_bar);

		this.title = (TextView) findViewById(R.id.title_end_game);

		title.setText("Fin del juego");

		// Opacidad
		llInGameActionBar.getBackground().setAlpha(175);
		llInGameBottomBar.getBackground().setAlpha(175);

		bExit.setAlpha(150);
		bRanking.setAlpha(150);

		// Listeners
		this.bRanking.setOnClickListener(this);
		this.llInGame.setOnClickListener(this);
		this.bExit.setOnClickListener(this);

		this.l = new Launch(this);
		this.conexion = new Conexion(this);
	}

	@Override
	public void onClick(final View v) {
		switch (this.vClicked = v.getId()) {
		case R.id.iv_end_game_ranking:
			this.l.lanzaDialogoEsperaGetPquest(jugador.getDiferenciador(),
					false, jugador);
			break;
		case R.id.iv_end_game_exit:
			this.aDactual = Launch.lanzaConfirmacion("Salir", "¿Desea Salir?",
					this);
			break;
		case R.id.ll_medio_end_game:
			this.l.lanzaAviso("ÁEnhorabuena!",
					Props.Strings.iEndGame + jugador.getScoreQuestTotal());
			break;

		}

	}

	@Override
	public void onBackPressed() {
		this.aDactual = Launch
				.lanzaConfirmacion("Salir", "¿Desea Salir?", this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.mDbHelper.close();
	}

	public void onClick(final DialogInterface dialog, final int boton) {
		if (this.aDactual == dialog) {
			switch (boton) {
			case -1:// yes
				if (Props.Comun.ACTIVIDAD != null) {
					Props.Comun.ACTIVIDAD.finish();// cierra SelecMJ
					Props.Comun.ACTIVIDAD = null;// resetea
				}
				dialog.cancel();
				finish();
				Bundle b = new Bundle();
				b.putSerializable(Props.Comun.JUGADOR, jugador);
				this.l.lanzaActivity(Props.Action.MAINMENU, b);
				break;
			case -2:// no
				dialog.cancel();
				break;
			}
		}
	}

	@Override
	public Launch l() {

		return l;
	}

	@Override
	public Conexion c() {

		return conexion;
	}

}
