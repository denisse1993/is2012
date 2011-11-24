package com.cinnamon.is.comun;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import com.cinnamon.is.R;
import com.cinnamon.is.game.Jugador;

public class Opciones extends PreferenceActivity {

	Jugador jugador;
	Preference pIntent;

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
