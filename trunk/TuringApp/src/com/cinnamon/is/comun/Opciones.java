//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import com.cinnamon.is.R;
import com.cinnamon.is.game.Jugador;
import com.cinnamon.is.game.MainMenu;

/**
 * Actividad abtracta extiende de PreferenceActivity para representar las
 * opciones del juego
 * 
 * @author Cinnamon Team
 * @version 1.0 24.11.2011
 */
public class Opciones extends PreferenceActivity implements
		OnPreferenceClickListener {

	/**
	 * Jugador actual del juego
	 */
	private Jugador jugador;
	/**
	 * Preference para la opcion de cambiar de usuario
	 */
	private Preference pLogin;

	/**
	 * Preference para la opcion de resetear el juego
	 */
	private Preference pReset;

	/**
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.opciones);
		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		pLogin = (Preference) findPreference("loginChange");
		pLogin.setOnPreferenceClickListener(this);
		pReset = (Preference) findPreference("resetGame");
		pReset.setOnPreferenceClickListener(this);
	}

	@Override
	public void onBackPressed() {
		finish();
		mDbHelper.close();
		Intent iMainMenu = new Intent(Intents.Action.MAINMENU);
		iMainMenu.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivity(iMainMenu);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		String key = preference.getKey();
		if (key.equals(pLogin.getKey())) {
			finish();
			mDbHelper.close();
			Intent openLogin = new Intent(Intents.Action.LOGIN);
			startActivity(openLogin);
		} else if (key.equals(pReset.getKey())) {
			resetJugador();
			lanzarAvisoReseteo();
		}
		return true;
	}

	/**
	 * Metodo que lanza el Dialog para avisar que la puntuacion se ha reseteado
	 */
	private void lanzarAvisoReseteo() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Juego Reseteado").setNegativeButton("Cerrar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show();
	}

	/**
	 * Metodo que resetea al jugador accediendo a la base de datos
	 */
	private void resetJugador() {
		jugador.setScore(0);
		jugador.setHoja(0);
		jugador.setMochila(0);
		jugador.setFase1(0);
		jugador.setFase2(0);
		jugador.setFase3(0);
		jugador.setFase4(0);
		mDbHelper.updateRow("'" + jugador.getNombre() + "'",
				jugador.getScore(), jugador.getHoja(), jugador.mochilaToInt(),
				jugador.getFase1(), jugador.getFase2(), jugador.getFase3(),
				jugador.getFase4());
	}
}
