//
// Universidad Complutense de Madrid
// Ingenieria Inform�tica
//
// PROYECTO: Qronos
// ASIGNATURA : Ingenier�a del Software
//
package com.cinnamon.is.comun;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.cinnamon.is.game.Aventura;
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
	public UtilJSON(Activity activity) {
		this.a = activity;
	}

	/**
	 * @param json
	 *            la info con la aventura
	 * @param a
	 *            la aventura a rellenar
	 */
	public boolean rellenaQuest(String json, Aventura av) {
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
			Launch.lanzaToast(a, Props.Strings.ERROR_JSON);
			retorno = false;
		} catch (ParseException e1) {
			Launch.lanzaToast(a, Props.Strings.ERROR_JSON);
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
	public int[] verSiJugadorExisteArcade(String json, String nombre) {
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

	public boolean rankingOnlineAventura(String jSON) {
		boolean retorno;
		String nick, mj1, mj2, mj3, mj4, mj5, mj6, mj7, mj8, mj9, mj10, mj11, mj12, total;

		JSONArray jArray;
		try {
			jArray = new JSONArray(jSON);
			JSONObject json_data = null;
			for (int i = 0; i < jArray.length(); i++) {
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
				((Ranking) a).setFila(j, nick, mj1, mj2, mj3, mj4, mj5, mj6,
						mj7, mj8, mj9, mj10, mj11, mj12, total);
			}
			retorno = true;
		} catch (JSONException e1) {
			Launch.lanzaToast(a, Props.Strings.ERROR_JSON);
			retorno = false;
		} catch (ParseException e1) {
			Launch.lanzaToast(a, Props.Strings.ERROR_JSON);
			retorno = false;
		}
		return retorno;
	}

	public boolean rankingOnlineArcade(String jSON, int limite) {
		boolean retorno;
		String nick, mj1, mj2, mj3, mj4, mj5, mj6, mj7, mj8, mj9, mj10, mj11, mj12, total;

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
				((Ranking) a).setFila(j, nick, mj1, mj2, mj3, mj4, mj5, mj6,
						mj7, mj8, mj9, mj10, mj11, mj12, total);
			}
			retorno = true;
		} catch (JSONException e1) {
			Launch.lanzaToast(a, Props.Strings.ERROR_JSON);
			retorno = false;
		} catch (ParseException e1) {
			Launch.lanzaToast(a, Props.Strings.ERROR_JSON);
			retorno = false;
		}
		return retorno;
	}
}