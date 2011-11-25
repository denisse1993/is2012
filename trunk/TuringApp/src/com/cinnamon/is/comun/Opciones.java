//
// Universidad Complutense de Madrid
// Ingenieria Inform�tica
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingenier�a del Software
//
package com.cinnamon.is.comun;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import com.cinnamon.is.R;
import com.cinnamon.is.game.Jugador;

/**
 * Actividad abtracta extiende de PreferenceActivity para representar las
 * opciones del juego
 * 
 * @author Cinnamon Team
 * @version 1.0 24.11.2011
 */
public class Opciones extends PreferenceActivity {

	/**
	 * Jugador actual del juego
	 */
	private Jugador jugador;
	/**
	 * Preference para la opcion de cambiar de usuario
	 */
	private Preference pIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.opciones);
		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);

		pIntent = (Preference) findPreference("loginChange");
		pIntent.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			public boolean onPreferenceClick(Preference preference) {
				finish();
				Intent openLogin = new Intent(Intents.Action.LOGIN);
				startActivity(openLogin);
				return true;
			}
		});

	}

	@Override
	public void onBackPressed() {
		finish();
		Intent iMainMenu = new Intent(Intents.Action.MAINMENU);
		iMainMenu.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivity(iMainMenu);
	}

}
