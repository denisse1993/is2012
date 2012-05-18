package com.cinnamon.is.game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.UtilJSON;

public class Ranking extends Activity {

	private String JSON;

	/**
	 * Tendra la información del jugador que viene desde arcade
	 */
	// es una apaño para no tener que cambiar todo el codigo
	private Jugador arcadeData;
	
	/**
	 * Tendra la información del jugador que viene desde aventura
	 */
	// otro apaño
	private Jugador aventuraData;

	private Jugador jugador;

	private TextView tvTitulo;

	private UtilJSON utilj;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking);
		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		JSON = b.getString(Props.Comun.JSON);
		arcadeData = (Jugador) b.getSerializable(Props.Comun.ARCADE_DATA);
		aventuraData = (Jugador) b.getSerializable(Props.Comun.AVENTURA_DATA);
		tvTitulo = (TextView) findViewById(R.id.textViewTitulo);
		utilj = new UtilJSON(this);
		boolean admin = b.getBoolean(Props.Comun.ADMIN, false);

		if (jugador == null) {
			if (arcadeData != null) {
				tvTitulo.setText("Ranking Arcade");
				utilj.rankingOnlineArcade(JSON, 5, arcadeData);
			} else {
				tvTitulo.setText("Ranking Aventura");
				utilj.rankingOnlineAventura(JSON, aventuraData, admin);
			}
		} else {
			rankingLocal();
		}
	}

	/**
	 * Muestra ranking local
	 */
	private void rankingLocal() {
		int[] a = jugador.getScore();
		String suma = String.valueOf(jugador.getScoreTotal());
		setFila(1, jugador.getNombre(), String.valueOf(a[0]),
				String.valueOf(a[1]), String.valueOf(a[2]),
				String.valueOf(a[3]), String.valueOf(a[4]),
				String.valueOf(a[5]), String.valueOf(a[6]),
				String.valueOf(a[7]), String.valueOf(a[8]),
				String.valueOf(a[9]), String.valueOf(a[10]),
				String.valueOf(a[11]), suma);
	}

	public void setFila(final int i, final String nick, final String mj1,
			final String mj2, final String mj3, final String mj4,
			final String mj5, final String mj6, final String mj7,
			final String mj8, final String mj9, final String mj10,
			final String mj11, final String mj12, final String total) {
		if (i == 1) {
			TextView text0 = (TextView) findViewById(R.id.textView0);
			text0.setText(nick);
			TextView text1 = (TextView) findViewById(R.id.textView1);
			text1.setText(mj1);
			TextView text2 = (TextView) findViewById(R.id.textView2);
			text2.setText(mj2);
			TextView text3 = (TextView) findViewById(R.id.textView3);
			text3.setText(mj3);
			TextView text4 = (TextView) findViewById(R.id.textView4);
			text4.setText(mj4);
			TextView text5 = (TextView) findViewById(R.id.textView5);
			text5.setText(mj5);
			TextView text6 = (TextView) findViewById(R.id.textView6);
			text6.setText(mj6);
			TextView text7 = (TextView) findViewById(R.id.textView7);
			text7.setText(mj7);
			TextView text8 = (TextView) findViewById(R.id.textView8);
			text8.setText(mj8);
			TextView text9 = (TextView) findViewById(R.id.textView9);
			text9.setText(mj9);
			TextView text10 = (TextView) findViewById(R.id.textView10);
			text10.setText(mj10);
			TextView text11 = (TextView) findViewById(R.id.textView11);
			text11.setText(mj11);
			TextView text12 = (TextView) findViewById(R.id.textView12);
			text12.setText(mj12);
			TextView text13 = (TextView) findViewById(R.id.textViewT1);
			text13.setText(total);
		}
		if (i == 2) {
			TextView text0 = (TextView) findViewById(R.id.textView13);
			text0.setText(nick);
			TextView text1 = (TextView) findViewById(R.id.textView14);
			text1.setText(mj1);
			TextView text2 = (TextView) findViewById(R.id.textView15);
			text2.setText(mj2);
			TextView text3 = (TextView) findViewById(R.id.textView16);
			text3.setText(mj3);
			TextView text4 = (TextView) findViewById(R.id.textView17);
			text4.setText(mj4);
			TextView text5 = (TextView) findViewById(R.id.textView18);
			text5.setText(mj5);
			TextView text6 = (TextView) findViewById(R.id.textView19);
			text6.setText(mj6);
			TextView text7 = (TextView) findViewById(R.id.textView20);
			text7.setText(mj7);
			TextView text8 = (TextView) findViewById(R.id.textView21);
			text8.setText(mj8);
			TextView text9 = (TextView) findViewById(R.id.textView22);
			text9.setText(mj9);
			TextView text10 = (TextView) findViewById(R.id.textView23);
			text10.setText(mj10);
			TextView text11 = (TextView) findViewById(R.id.textView24);
			text11.setText(mj11);
			TextView text12 = (TextView) findViewById(R.id.textView25);
			text12.setText(mj12);
			TextView text13 = (TextView) findViewById(R.id.textViewT2);
			text13.setText(total);
		}
		if (i == 3) {
			TextView text0 = (TextView) findViewById(R.id.textView26);
			text0.setText(nick);
			TextView text1 = (TextView) findViewById(R.id.textView27);
			text1.setText(mj1);
			TextView text2 = (TextView) findViewById(R.id.textView28);
			text2.setText(mj2);
			TextView text3 = (TextView) findViewById(R.id.textView29);
			text3.setText(mj3);
			TextView text4 = (TextView) findViewById(R.id.textView30);
			text4.setText(mj4);
			TextView text5 = (TextView) findViewById(R.id.textView31);
			text5.setText(mj5);
			TextView text6 = (TextView) findViewById(R.id.textView32);
			text6.setText(mj6);
			TextView text7 = (TextView) findViewById(R.id.textView33);
			text7.setText(mj7);
			TextView text8 = (TextView) findViewById(R.id.textView34);
			text8.setText(mj8);
			TextView text9 = (TextView) findViewById(R.id.textView35);
			text9.setText(mj9);
			TextView text10 = (TextView) findViewById(R.id.textView36);
			text10.setText(mj10);
			TextView text11 = (TextView) findViewById(R.id.textView37);
			text11.setText(mj11);
			TextView text12 = (TextView) findViewById(R.id.textView38);
			text12.setText(mj12);
			TextView text13 = (TextView) findViewById(R.id.textViewT3);
			text13.setText(total);
		}
		if (i == 4) {
			TextView text0 = (TextView) findViewById(R.id.textView39);
			text0.setText(nick);
			TextView text1 = (TextView) findViewById(R.id.textView40);
			text1.setText(mj1);
			TextView text2 = (TextView) findViewById(R.id.textView41);
			text2.setText(mj2);
			TextView text3 = (TextView) findViewById(R.id.textView42);
			text3.setText(mj3);
			TextView text4 = (TextView) findViewById(R.id.textView43);
			text4.setText(mj4);
			TextView text5 = (TextView) findViewById(R.id.textView44);
			text5.setText(mj5);
			TextView text6 = (TextView) findViewById(R.id.textView45);
			text6.setText(mj6);
			TextView text7 = (TextView) findViewById(R.id.textView46);
			text7.setText(mj7);
			TextView text8 = (TextView) findViewById(R.id.textView47);
			text8.setText(mj8);
			TextView text9 = (TextView) findViewById(R.id.textView48);
			text9.setText(mj9);
			TextView text10 = (TextView) findViewById(R.id.textView49);
			text10.setText(mj10);
			TextView text11 = (TextView) findViewById(R.id.textView50);
			text11.setText(mj11);
			TextView text12 = (TextView) findViewById(R.id.textView51);
			text12.setText(mj12);

			TextView text13 = (TextView) findViewById(R.id.textViewT4);
			text13.setText(total);
		}
		if (i == 5) {
			TextView text0 = (TextView) findViewById(R.id.textView52);
			text0.setText(nick);
			TextView text1 = (TextView) findViewById(R.id.textView53);
			text1.setText(mj1);
			TextView text2 = (TextView) findViewById(R.id.textView54);
			text2.setText(mj2);
			TextView text3 = (TextView) findViewById(R.id.textView55);
			text3.setText(mj3);
			TextView text4 = (TextView) findViewById(R.id.textView56);
			text4.setText(mj4);
			TextView text5 = (TextView) findViewById(R.id.textView57);
			text5.setText(mj5);
			TextView text6 = (TextView) findViewById(R.id.textView58);
			text6.setText(mj6);
			TextView text7 = (TextView) findViewById(R.id.textView59);
			text7.setText(mj7);
			TextView text8 = (TextView) findViewById(R.id.textView60);
			text8.setText(mj8);
			TextView text9 = (TextView) findViewById(R.id.textView61);
			text9.setText(mj9);
			TextView text10 = (TextView) findViewById(R.id.textView62);
			text10.setText(mj10);
			TextView text11 = (TextView) findViewById(R.id.textView63);
			text11.setText(mj11);
			TextView text12 = (TextView) findViewById(R.id.textView64);
			text12.setText(mj12);
			TextView text13 = (TextView) findViewById(R.id.textViewT5);
			text13.setText(total);
		}

		// handler=new Handler();

		// handler.postDelayed(r, 1000);
		// setContentView(R.layout.table);
	}

}
