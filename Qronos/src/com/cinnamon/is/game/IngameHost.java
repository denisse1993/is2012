package com.cinnamon.is.game;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Conexion;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.UtilQR;
import com.cinnamon.is.comun.dialog.Dialogos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
public class InGameHost extends Activity implements OnClickListener{

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
	Launch launch;

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
	private ImageButton bOpciones, bRanking;
	
	private ImageView ivQR;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame_host);

		bOpciones = (ImageButton) findViewById(R.id.ib_opciones_ingame);
		bRanking = (ImageButton) findViewById(R.id.ib_ranking_ingame);

		bOpciones.setOnClickListener(this);
		bRanking.setOnClickListener(this);
		
		ivQR = (ImageView) findViewById(R.id.ivQR);
		
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
		case R.id.ll_opciones_ingame:
			launch.lanzaActivity(Props.Action.OPCIONES);
		}
	}


}
