package com.cinnamon.is.game;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Conexion;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.UtilQR;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * 
 * @author Cinnamon Team
 * @version 1.0 19.04.2012
 * 
 */
public class InGameHost extends Activity implements OnClickListener {

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
	 * 
	 */
	private ImageButton bOpciones, bRanking;

	private ImageView ivQR;

	public Conexion conexion;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame_host);

		// mDbHelper = new DbAdapter(this);
		// mDbHelper.open(false);

		// TODO conexion.updateAventura();

		this.bOpciones = (ImageButton) findViewById(R.id.ib_opciones_ingame_host);
		this.bRanking = (ImageButton) findViewById(R.id.ib_ranking_ingame_host);

		this.bOpciones.setOnClickListener(this);
		this.bRanking.setOnClickListener(this);

		this.ivQR = (ImageView) findViewById(R.id.ivQR);
		this.conexion = new Conexion(this);
		this.launch = new Launch(this);
		this.q = new UtilQR(this);
		Bitmap b = this.q.getQR(this.quest.getNombre());
		this.ivQR.setImageBitmap(b);
		this.conexion = new Conexion(this);
		this.launch = new Launch(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
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
				dialog.cancel();
				this.launch.lanzaActivity(Props.Action.MAINMENU);
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
		case R.id.ll_ranking_ingame_host:
			this.launch.lanzaActivity(Props.Action.RANKING);
			break;
		case R.id.ib_opciones_ingame_host:
			this.launch.lanzaActivity(Props.Action.OPCIONES);
		}
	}

}
