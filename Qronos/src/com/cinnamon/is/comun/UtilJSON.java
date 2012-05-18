//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Qronos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.cinnamon.is.game.Aventura;
import com.cinnamon.is.game.Jugador;
import com.cinnamon.is.game.Ranking;

/**
 * Clase de ayuda para operaciones JSON
 * 
 * @author Cinnamon Team
 * @version 1.0 28.04.2012
 */
public final class UtilJSON {

	/**
	 * Actividad padre
	 */
	private final Activity a;

	// private static boolean yes;

	/**
	 * Metodo constructor
	 * 
	 * @param activity
	 *            la actividad padre
	 */
	public UtilJSON(final Activity activity) {
		this.a = activity;
	}

	/**
	 * @param json
	 *            la info con la aventura
	 * @param av
	 * @param a
	 *            la aventura a rellenar
	 * 
	 * @return boolean
	 */
	public boolean rellenaQuest(final String json, final Aventura av) {
		boolean retorno;
		String name, pass, mj1, mj2, mj3, mj4, mj5, mj6, mj7, mj8, mj9, mj10, mj11, mj12, pista1, pista2, pista3, pista4, pista5, pista6, pista7, pista8, pista9, pista10, pista11, pista12;
		JSONArray jArray;
		try {
			jArray = new JSONArray(json);
			JSONObject json_data = null;

			json_data = jArray.getJSONObject(0);
			name = json_data.getString("NOMBRE");
			pass = json_data.getString("PASS");
			mj1 = json_data.getString("MJ1");
			mj2 = json_data.getString("MJ2");
			mj3 = json_data.getString("MJ3");
			mj4 = json_data.getString("MJ4");
			mj5 = json_data.getString("MJ5");
			mj6 = json_data.getString("MJ6");
			mj7 = json_data.getString("MJ7");
			mj8 = json_data.getString("MJ8");
			mj9 = json_data.getString("MJ9");
			mj10 = json_data.getString("MJ10");
			mj11 = json_data.getString("MJ11");
			mj12 = json_data.getString("MJ12");
			pista1 = json_data.getString("PISTA1");
			pista2 = json_data.getString("PISTA2");
			pista3 = json_data.getString("PISTA3");
			pista4 = json_data.getString("PISTA4");
			pista5 = json_data.getString("PISTA5");
			pista6 = json_data.getString("PISTA6");
			pista7 = json_data.getString("PISTA7");
			pista8 = json_data.getString("PISTA8");
			pista9 = json_data.getString("PISTA9");
			pista10 = json_data.getString("PISTA10");
			pista11 = json_data.getString("PISTA11");
			pista12 = json_data.getString("PISTA12");

			String[] mjs = new String[] { mj1, mj2, mj3, mj4, mj5, mj6, mj7,
					mj8, mj9, mj10, mj11, mj12 };
			String[] pistas = new String[] { pista1, pista2, pista3, pista4,
					pista5, pista6, pista7, pista8, pista9, pista10, pista11,
					pista12 };
			// relleno
			av.setNombre(name);
			av.setPass(pass);
			av.setMinijuegos(mjs, pistas);
			retorno = true;
		} catch (JSONException e1) {
			Launch.lanzaToast(this.a, Props.Strings.ERROR_JSON);
			retorno = false;
		} catch (ParseException e1) {
			Launch.lanzaToast(this.a, Props.Strings.ERROR_JSON);
			retorno = false;
		}
		return retorno;

	}

	/**
	 * @param json
	 *            la info de la tabla arcade online
	 * @param nombre
	 *            del jugador a comprobar
	 * @return el array con los datos de arcade o null si no existe
	 */
	public int[] verSiJugadorExisteArcade(final String json, final String nombre) {
		String n;
		int[] d = new int[Props.Comun.MAX_MJ];
		JSONArray jArray;
		try {
			jArray = new JSONArray(json);
			JSONObject json_data = null;
			for (int i = 0; i < jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				n = json_data.getString("NICK");
				if (n.equals(nombre)) {// mismo nombre
					for (int j = 0; j < d.length; j++) {
						int code = j + 1;
						d[j] = Integer.parseInt(json_data
								.getString("MJ" + code));
					}
					break;
				}
			}
		} catch (JSONException e1) {
			d = null;
		} catch (ParseException e1) {
			d = null;
		}
		return d;
	}

	/**
	 * @param json
	 *            la info de la tabla pquest online
	 * @param jug
	 *            del jugador a comprobar
	 * @return true o false segun existe o no
	 */
	public Boolean[] getPquestJugadorSiExiste(final String json,
			final Jugador jug) {
		String n;
		boolean retorno = false;
		boolean uploadServer = false;
		int[] d = new int[Props.Comun.MAX_MJ];
		JSONArray jArray;
		try {
			jArray = new JSONArray(json);
			JSONObject json_data = null;
			for (int i = 0; i < jArray.length(); i++) {
				json_data = jArray.getJSONObject(i);
				n = json_data.getString("NICK");
				if (n.equals(jug.getNombre())) {// mismo nombre
					retorno = true;
					// Se comprueba que la partida(aventura;host) que se esta
					// actualizando, sea
					// la misma que la que se va a lanzar en ese momento
					String dif = json_data.getString("QUEST");
					if (jug.getDiferenciador().equals(dif)) {
						for (int j = 0; j < d.length; j++) {
							int code = j + 1;
							d[j] = Integer.parseInt(json_data.getString("MJ"
									+ code));
						}
						int actual = Integer.parseInt(json_data
								.getString("ACTUAL"));
						jug.setScoreQuest(d);
						jug.setFase(actual);
						uploadServer = false;
					} else {
						uploadServer = true;
						jug.resetQuest();
					}
					break;
				}
			}
		} catch (JSONException e1) {
			Launch.lanzaToast(this.a, Props.Strings.ERROR_JSON);
			retorno = false;
		} catch (ParseException e1) {
			Launch.lanzaToast(this.a, Props.Strings.ERROR_JSON);
			retorno = false;
		}
		return new Boolean[] { retorno, uploadServer };
	}

	public boolean rankingOnlineAventura(final String jSON,
			final Jugador jugador, final boolean admin) {
		boolean retorno;
		String nick, mj1, mj2, mj3, mj4, mj5, mj6, mj7, mj8, mj9, mj10, mj11, mj12, total;
		boolean entre5primeros = false;
		JSONArray jArray;
		try {

			jArray = new JSONArray(jSON);
			JSONObject json_data = null;
			int limite = jArray.length() < 5 ? jArray.length() : 5;
			for (int i = 0; i < limite; i++) {
				int j = i + 1;
				json_data = jArray.getJSONObject(i);
				nick = json_data.getString("NICK");
				mj1 = json_data.getString("MJ1");
				mj2 = json_data.getString("MJ2");
				mj3 = json_data.getString("MJ3");
				mj4 = json_data.getString("MJ4");
				mj5 = json_data.getString("MJ5");
				mj6 = json_data.getString("MJ6");
				mj7 = json_data.getString("MJ7");
				mj8 = json_data.getString("MJ8");
				mj9 = json_data.getString("MJ9");
				mj10 = json_data.getString("MJ10");
				mj11 = json_data.getString("MJ11");
				mj12 = json_data.getString("MJ12");
				total = json_data.getString("TOTAL");
				if (nick == jugador.getNombre()) {
					entre5primeros = true;
				}
				((Ranking) this.a).setFila(j, nick, mj1, mj2, mj3, mj4, mj5,
						mj6, mj7, mj8, mj9, mj10, mj11, mj12, total);
			}
			if (!entre5primeros && !admin) {
				int[] b = jugador.getScore();
				String suma = String.valueOf(jugador.getScoreTotal());
				((Ranking) this.a).setFila(0, jugador.getNombre(),
						String.valueOf(b[0]), String.valueOf(b[1]),
						String.valueOf(b[2]), String.valueOf(b[3]),
						String.valueOf(b[4]), String.valueOf(b[5]),
						String.valueOf(b[6]), String.valueOf(b[7]),
						String.valueOf(b[8]), String.valueOf(b[9]),
						String.valueOf(b[10]), String.valueOf(b[11]), suma);
			}
			retorno = true;
		} catch (JSONException e1) {
			Launch.lanzaToast(this.a, Props.Strings.ERROR_JSON);
			retorno = false;
		} catch (ParseException e1) {
			Launch.lanzaToast(this.a, Props.Strings.ERROR_JSON);
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Rellena el layout de ranking con el string jSON
	 * 
	 * @param jSON
	 *            a parsear, contiene la info de la tabla arcade en formato json
	 * @param limite
	 *            debe ser entre 0 y 5 inclusive
	 * @param jugador
	 *            el jugador que ha lanzado el ranking
	 * @return si ha sido correcto o no
	 */
	public boolean rankingOnlineArcade(final String jSON, final int limite,
			final Jugador jugador) {
		boolean retorno;
		String nick, mj1, mj2, mj3, mj4, mj5, mj6, mj7, mj8, mj9, mj10, mj11, mj12, total;
		boolean entre5primeros = false;
		JSONArray jArray;
		try {
			jArray = new JSONArray(jSON);
			JSONObject json_data = null;
			for (int i = 0; i < limite; i++) {// solo muestra limite
				int j = i + 1;
				json_data = jArray.getJSONObject(i);
				nick = json_data.getString("NICK");
				mj1 = json_data.getString("MJ1");
				mj2 = json_data.getString("MJ2");
				mj3 = json_data.getString("MJ3");
				mj4 = json_data.getString("MJ4");
				mj5 = json_data.getString("MJ5");
				mj6 = json_data.getString("MJ6");
				mj7 = json_data.getString("MJ7");
				mj8 = json_data.getString("MJ8");
				mj9 = json_data.getString("MJ9");
				mj10 = json_data.getString("MJ10");
				mj11 = json_data.getString("MJ11");
				mj12 = json_data.getString("MJ12");
				total = json_data.getString("TOTAL");
				if (nick == jugador.getNombre()) {
					entre5primeros = true;
				}
				((Ranking) this.a).setFila(j, nick, mj1, mj2, mj3, mj4, mj5,
						mj6, mj7, mj8, mj9, mj10, mj11, mj12, total);
			}
			if (!entre5primeros) {
				int[] b = jugador.getScore();
				String suma = String.valueOf(jugador.getScoreTotal());
				((Ranking) this.a).setFila(0, jugador.getNombre(),
						String.valueOf(b[0]), String.valueOf(b[1]),
						String.valueOf(b[2]), String.valueOf(b[3]),
						String.valueOf(b[4]), String.valueOf(b[5]),
						String.valueOf(b[6]), String.valueOf(b[7]),
						String.valueOf(b[8]), String.valueOf(b[9]),
						String.valueOf(b[10]), String.valueOf(b[11]), suma);
			}
			retorno = true;
		} catch (JSONException e1) {
			Launch.lanzaToast(this.a, Props.Strings.ERROR_JSON);
			retorno = false;
		} catch (ParseException e1) {
			Launch.lanzaToast(this.a, Props.Strings.ERROR_JSON);
			retorno = false;
		}
		return retorno;
	}

	public String jsonToString(final String jSON) {
		JSONArray jArray;
		String notif = "";
		try {
			jArray = new JSONArray(jSON);
			JSONObject json_data = null;
			json_data = jArray.getJSONObject(0);
			notif = json_data.getString("NOTIF");
		} catch (Exception e) {
			Launch.lanzaToast(this.a, Props.Strings.ERROR_JSON);

		}
		return notif;
	}

	public String jsonToStringLogin(final String jSON) {
		JSONArray jArray;
		String notif = "";
		try {
			jArray = new JSONArray(jSON);
			JSONObject json_data = null;
			json_data = jArray.getJSONObject(0);
			notif = json_data.getString("SD");
		} catch (Exception e) {
			Launch.lanzaToast(this.a, Props.Strings.ERROR_JSON);

		}
		return notif;
	}
}
