package com.cinnamon.is.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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
import com.cinnamon.is.comun.UtilQR;

/**
 * 
 * @author Cinnamon Team
 * @version 1.0 19.04.2012
 * 
 */
public class InGameHost extends Activity implements Inet, OnClickListener,
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
	@SuppressWarnings("unused")
	private int vClicked;

	/**
	 * Utilidad para lanzar el Scanner QR
	 */
	private UtilQR q;

	/**
	 * Interfaz
	 */
	private ImageView ivOpciones, ivRanking, ivInfo;
	private ImageView ivQR;
	private TextView title;
	private LinearLayout llInGameHost, llInGameHostActionBar,
			llInGameHostBottomBar;

	/**
	 * Conexion
	 */
	public Conexion conexion;

	/**
	 * Jugador
	 */
	Jugador jugador;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame_host);

		// Bundle
		Bundle bundle = getIntent().getExtras();
		this.jugador = (Jugador) bundle.getSerializable(Props.Comun.JUGADOR);
		this.quest = (Aventura) bundle.getSerializable(Props.Comun.AVENTURA);

		// Launch
		this.launch = new Launch(this);
		conexion = new Conexion(this);

		// Iniciar Interfaz
		this.ivOpciones = (ImageView) findViewById(R.id.iv_in_game_host_opciones);
		this.ivRanking = (ImageView) findViewById(R.id.iv_in_game_host_ranking);
		this.ivInfo = (ImageView) findViewById(R.id.iv_in_game_host_info);

		this.ivQR = (ImageView) findViewById(R.id.iv_in_game_host_qr);
		this.title = (TextView) findViewById(R.id.title_in_game_host);

		llInGameHost = (LinearLayout) findViewById(R.id.ll_in_game_host);
		llInGameHostActionBar = (LinearLayout) findViewById(R.id.ll_in_game_host_action_bar);
		llInGameHostBottomBar = (LinearLayout) findViewById(R.id.ll_in_game_host_bottom_bar);

		// Opacidad
		llInGameHost.getBackground().setAlpha(75);
		llInGameHostActionBar.getBackground().setAlpha(175);
		llInGameHostBottomBar.getBackground().setAlpha(175);

		ivOpciones.setAlpha(150);
		ivRanking.setAlpha(150);

		// Listeners
		this.ivOpciones.setOnClickListener(this);
		this.ivRanking.setOnClickListener(this);
		this.ivInfo.setOnClickListener(this);

		// Titulo
		this.title.setText(quest.getNombre());

		// QR
		//Contiene nombreAventura y nombreHost
		this.q = new UtilQR(this);
		String infoQR=this.quest.getNombre()+";"+this.jugador.getNombre();
		Bitmap b = this.q.getQR(infoQR);
		this.ivQR.setImageBitmap(b);
		// BBDD
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
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
				this.launch.lanzaActivity(Props.Action.MAINMENU, b);
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
		case R.id.iv_in_game_host_ranking:
			//TODO ANTES this.launch.lanzaDialogoGetPquest(quest.getNombre());
			this.launch.lanzaDialogoGetPquest(jugador.getNombre());
			break;
		case R.id.iv_in_game_host_opciones:
			if (Props.Comun.ACTIVIDAD != null) {
				Props.Comun.ACTIVIDAD.finish();// cierra SelecMJ
				Props.Comun.ACTIVIDAD = null;// resetea
			}
			Bundle b = new Bundle();
			b.putSerializable(Props.Comun.AVENTURA, quest);
			b.putSerializable(Props.Comun.JUGADOR, jugador);
			b.putString(Props.Comun.RETORNO, Props.Action.INGAMEHOST);
			finish();
			launch.lanzaActivity(Props.Action.OPCIONES, b);
			break;
		case R.id.iv_in_game_host_info:
			Launch.lanzaAviso("Info Aventura", Props.Strings.iHost, this);
			break;
		}
	}

	@Override
	public Launch l() {
		return launch;
	}

	@Override
	public Conexion c() {
		return conexion;
	}

}
