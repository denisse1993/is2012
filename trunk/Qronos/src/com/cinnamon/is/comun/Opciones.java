//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Qronos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

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
 * @version 1.1 16.03.2012
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
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.opciones);
		Bundle b = getIntent().getExtras();
		this.jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		// abre base de datos
		this.mDbHelper = new DbAdapter(this);
		this.mDbHelper.open(false);

		this.pLogin = findPreference("loginChange");
		this.pLogin.setOnPreferenceClickListener(this);
		this.pReset = findPreference("resetGame");
		this.pReset.setOnPreferenceClickListener(this);
	}

	@Override
	public void onBackPressed() {
		this.mDbHelper.close();
		Bundle b = new Bundle();
		b.putSerializable(Props.Comun.JUGADOR, this.jugador);
		finish();
		Launch.lanzaActivity(this, Props.Action.MAINMENU, b);
	}

	@Override
	public boolean onPreferenceClick(final Preference preference) {
		String key = preference.getKey();
		if (key.equals(this.pLogin.getKey())) {
			this.mDbHelper.close();
			finish();
			Launch.lanzaActivity(this, Props.Action.LOGIN);
		} else if (key.equals(this.pReset.getKey())) {
			resetJugadorArcade();
			Launch.lanzaAviso("Arcade Reseteado!", this);
		}
		return true;
	}

	/**
	 * Metodo que resetea al jugador accediendo a la base de datos
	 */
	private void resetJugadorArcade() {
		this.jugador.resetArcade();
		this.mDbHelper.updateRowParcade(this.jugador.getNombre(),
				this.jugador.getScore());

	}
}
