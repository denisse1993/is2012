//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Qronos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;


import com.cinnamon.is.R;
import com.cinnamon.is.comun.dialog.AyudaDialog;
import com.cinnamon.is.comun.dialog.MenuDialog;
import com.cinnamon.is.comun.dialog.TextDialog;
import com.cinnamon.is.game.Arcade;
import com.cinnamon.is.game.Aventura;
import com.cinnamon.is.game.EligeModoAventura;
import com.cinnamon.is.game.InGameAventura;
import com.cinnamon.is.game.Jugador;
import com.cinnamon.is.game.Login;

/**
 * <p>
 * Clase de utilidad para lanzamientos de actividades y dialogs. Se puede usar
 * tanto con metodos estaticos como creando un objeto Launch.
 * </p>
 * 
 * @author Cinnamon Team
 * @version 1.2 19.04.2012
 */
public final class Launch {

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
	public Launch(final Activity activity) {
		this.a = activity;
		/*
		 * this.conexion =new Conexion(a); esto era para no tener que pasarle
		 * CONEXION al lanzaDialogoEspera
		 */
	}

	/**
	 * Lanza una actividad
	 * 
	 * @param ACTION
	 *            la actividad a lanzar
	 */
	public void lanzaActivity(final String ACTION) {
		Launch.lanzaActivity(this.a, ACTION);
	}

	/**
	 * Lanza una actividad desde static
	 * 
	 * @param a
	 *            la actividad que lo lanza
	 * @param ACTION
	 *            la actividad a lanzar
	 */
	public static void lanzaActivity(final Activity a, final String ACTION) {
		Intent i = new Intent(ACTION);
		a.startActivity(i);
	}

	/**
	 * Lanza una actividad con parametros para el intent
	 * 
	 * @param ACTION
	 *            la actividad a lanzar
	 * @param b
	 *            el bundle con la info extra
	 */
	public void lanzaActivity(final String ACTION, final Bundle b) {
		Launch.lanzaActivity(this.a, ACTION, b);
	}

	/**
	 * Lanza una actividad desde static con parametros para el intent
	 * 
	 * @param a
	 *            la actividad que lo lanza
	 * @param ACTION
	 *            la actividad a lanzar
	 * @param b
	 *            el bundle con la info extra
	 */
	public static void lanzaActivity(final Activity a, final String ACTION,
			final Bundle b) {
		Intent i = new Intent(ACTION);
		i.putExtras(b);
		a.startActivity(i);
	}

	/**
	 * Lanza una actividad for result
	 * 
	 * @param ACTION
	 *            la actividad a lanzar
	 * 
	 * @param rq
	 *            el codigo de lanzamiento
	 */
	public void lanzaActivity(final String ACTION, final int rq) {
		Launch.lanzaActivity(this.a, ACTION, rq);
	}

	/**
	 * Lanza una actividad for result desde static
	 * 
	 * @param a
	 *            la actividad que lo lanza
	 * @param ACTION
	 *            la actividad a lanzar
	 * @param rq
	 *            el codigo de lanzamiento
	 */
	public static void lanzaActivity(final Activity a, final String ACTION,
			final int rq) {
		Intent i = new Intent(ACTION);
		a.startActivityForResult(i, rq);
	}

	/**
	 * Lanza una actividad for result con parametros para el intent
	 * 
	 * @param ACTION
	 *            la actividad a lanzar
	 * @param b
	 *            el bundle con la info extra
	 * @param rq
	 *            el codigo de lanzamiento
	 */
	public void lanzaActivity(final String ACTION, final Bundle b, final int rq) {
		Launch.lanzaActivity(this.a, ACTION, b, rq);
	}

	/**
	 * Lanza una actividad for result desde static con parametros para el intent
	 * 
	 * @param a
	 *            la actividad que lo lanza
	 * @param ACTION
	 *            la actividad a lanzar
	 * @param b
	 *            el bundle con la info extra
	 * @param rq
	 *            el codigo de lanzamiento
	 */
	public static void lanzaActivity(final Activity a, final String ACTION,
			final Bundle b, final int rq) {
		Intent i = new Intent(ACTION);
		i.putExtras(b);
		a.startActivityForResult(i, rq);
	}

	/**
	 * 
	 * Lanza un toast con el texto indicado
	 * 
	 * @param a
	 *            la actividad de lanzamiento
	 * @param msg
	 *            el mensaje
	 */
	public void lanzaToast(final String msg) {
		lanzaToast(this.a, msg);
	}

	/**
	 * 
	 * Lanza un toast con el texto indicado
	 * 
	 * @param a
	 *            la actividad de lanzamiento
	 * @param msg
	 *            el mensaje
	 */
	public static void lanzaToast(final Activity a, final String msg) {
		Toast.makeText(a, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Devuelve una actividad desde static con parametros para el intent
	 * 
	 * @param b
	 *            el bundle con la info extra
	 * @param rq
	 *            el codigo de retorno RESULT_OK or RESULT_CANCELED
	 */
	public void returnActivity(final Bundle b, final int rq) {
		returnActivity(this.a, b, rq);
	}

	/**
	 * Devuelve una actividad desde static con parametros para el intent
	 * 
	 * @param a
	 *            la actividad que lo lanza
	 * @param b
	 *            el bundle con la info extra
	 * @param rq
	 *            el codigo de retorno RESULT_OK or RESULT_CANCELED
	 */
	public static void returnActivity(final Activity a, final Bundle b,
			final int rq) {
		a.setResult(rq, new Intent().putExtras(b));
		a.finish();
	}

	/**
	 * Lanza el Dialog con un texto y un titulo
	 * 
	 * @param title
	 *            el titulo del dialog
	 * @param texto
	 *            el texto del dialog
	 * @return el alertdialog
	 */
	public AlertDialog lanzaAviso(final String title, final String texto) {
		return lanzaAviso(title, texto, this.a);
	}

	/**
	 * Lanza el Dialog con un texto y un titulo
	 * 
	 * @param title
	 *            el titulo del dialog
	 * @param texto
	 *            el texto del dialog
	 * @param a
	 *            la actividad de lanzamiento
	 * @return el alertdialog
	 */
	public static AlertDialog lanzaAviso(final String title,
			final String texto, final Activity a) {
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setTitle(title);
		builder.setMessage(texto).setNegativeButton("Cerrar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.cancel();
					}
				});
		return builder.show();
	}

	/**
	 * Lanza el Dialog con un texto y un titulo
	 * 
	 * @param title
	 *            el titulo del dialog
	 * @param texto
	 *            el texto del dialog
	 * @param idIcono
	 *            el id del icono para el dialog
	 * @return el alertdialog
	 */
	public AlertDialog lanzaAviso(final String title, final String texto,
			final int idIcono) {
		return lanzaAviso(title, texto, idIcono, this.a);
	}

	/**
	 * Lanza el Dialog con un texto y un titulo
	 * 
	 * @param title
	 *            el titulo del dialog
	 * @param texto
	 *            el texto del dialog
	 * @param idIcono
	 *            el id del icono para el dialog
	 * @param a
	 *            la actividad de lanzamiento
	 * @return el alertdialog
	 */
	public static AlertDialog lanzaAviso(final String title,
			final String texto, final int idIcono, final Activity a) {
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setTitle(title);
		builder.setIcon(idIcono);
		builder.setMessage(texto).setNegativeButton("Cerrar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.cancel();
					}
				});
		return builder.show();
	}

	/**
	 * Lanza el Dialog con un texto y un titulo
	 * 
	 * @param texto
	 *            el texto del dialog
	 * @return el alertdialog
	 */
	public AlertDialog lanzaAviso(final String texto) {
		return lanzaAviso(texto, this.a);
	}

	/**
	 * Lanza el Dialog con un texto y un titulo
	 * 
	 * @param texto
	 *            el texto del dialog
	 * @param a
	 *            la actividad de lanzamiento
	 * @return el alertdialog
	 */
	public static AlertDialog lanzaAviso(final String texto, final Activity a) {
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setMessage(texto).setNegativeButton("Cerrar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.cancel();
					}
				});
		return builder.show();
	}

	/**
	 * <p>
	 * Lanza el dialog para escoger si quieres hacer algo o no. La actividad a
	 * debe implementar DialogInterface.OnClickListener, el boton si es
	 * representado con un -1 y el no con un -2
	 * </p>
	 * 
	 * @param title
	 *            titulo del dialog
	 * @param texto
	 *            texto del dialog
	 * @return el alertdialog
	 */
	public AlertDialog lanzaConfirmacion(final String title, final String texto) {
		return lanzaConfirmacion(title, texto, this.a);
	}

	/**
	 * <p>
	 * Lanza el dialog para escoger si quieres hacer algo o no. La actividad a
	 * debe implementar DialogInterface.OnClickListener, el boton si es
	 * representado con un -1 y el no con un -2
	 * </p>
	 * 
	 * @param title
	 *            titulo del dialog
	 * @param texto
	 *            texto del dialog
	 * @param a
	 *            la actividad de lanzamiento
	 * @return el alertdialog
	 */
	public static AlertDialog lanzaConfirmacion(final String title,
			final String texto, final Activity a) {

		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setTitle(title);
		builder.setMessage(texto).setCancelable(false)
				.setPositiveButton("Sí", (OnClickListener) a)
				.setNegativeButton("No", (OnClickListener) a);
		return builder.show();
	}

	/**
	 * 
	 * @param context
	 *            Contexto de la actividad
	 * @param nMJ
	 *            Nœmero de Minijuego
	 * @param launch
	 * @param modo
	 */
	public static void lanzaConfirmacion(final Context context, final int nMJ,
			final Launch launch, final int modo) {
		AyudaDialog dialogo = new AyudaDialog(context,
				Props.Strings.mjNames[nMJ], modo, R.style.CenterDialog, nMJ,
				Props.Action.MJ[nMJ], Props.Strings.mjExps[nMJ],
				Props.Comun.iDiVmj[nMJ], launch);
		dialogo.show();

	}

	/**
	 * <p>
	 * Lanza el dialog para opciones, eligiendo entre continuar,reiniciar y
	 * salir
	 * </p>
	 * 
	 * @return si o no
	 */
	public AlertDialog lanzaOpciones() {
		return lanzaOpciones(this.a);
	}

	/**
	 * <p>
	 * Lanza el dialog para opciones, eligiendo entre continuar,reiniciar y
	 * salir
	 * </p>
	 * 
	 * @param a
	 *            la actividad de lanzamiento
	 * @return si o no
	 */
	public static AlertDialog lanzaOpciones(final Activity a) {

		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setItems(
				new CharSequence[] { "Continuar", "Reiniciar", "Salir" },
				(OnClickListener) a);
		builder.setTitle("Elige opcion");
		builder.setCancelable(false);
		return builder.show();
	}

	/**
	 * @param context
	 * @param title
	 * @param modo
	 * @param mj
	 */
	public static void lanzaOpciones(final Context context, final String title,
			final int modo, final Minijuego mj) {
		MenuDialog dialogo = new MenuDialog(context, title, modo,
				R.style.CenterDialog, mj);
		dialogo.show();
	}

	/**
	 * Lanza un dialogo con
	 * 
	 * @param context
	 * @param title
	 * @param modo
	 *            0 Crear 1 Editar 2 Usar
	 * @param launch
	 * @param a
	 */
	public void lanzaTextoDialogo(final Context context, final int modo,
			final String title, final Launch launch, final Aventura a, String nombre) {
		TextDialog dialogo = new TextDialog(context, title, modo,
				R.style.CenterDialog, launch, a,nombre);
		dialogo.show();
	}


	/**
	 * Sirve para loguear un jugador
	 * 
	 * @param nick
	 * @param pass
	 */
	public void lanzaDialogoEsperaLogin(final String nick, final String pass) { //
		// valor 0 activa login
		new ConexionServerTask().execute(new Object[] { 0, nick, pass });
	}

	/**
	 * Sirve para registrar un jugador
	 * 
	 * @param nick
	 * @param pass
	 */
	public void lanzaDialogoEsperaRegister(final String nick, final String pass) { //
		// valor 1 activa register
		new ConexionServerTask().execute(new Object[] { 1, nick, pass });
	}

	/**
	 * Sirve para subir las puntuaciones en el modo arcade (tabla parcade)
	 * 
	 * @param nick
	 * @param a1
	 * @param pass
	 */
	public void lanzaDialogoEsperaUpdateScoreArcade(final String nick,
			final int[] a1) { //
		// valor 2 activa subir scores
		new ConexionServerTask().execute(new Object[] { 2, nick, a1 });
	}

	/**
	 * Sirve para subir una aventura al servidor (tabla quest)
	 * 
	 * @param a1
	 */
	public void lanzaDialogoEsperaCreaQuest(final Aventura a1,final String nick) { //
		// valor 3 activa crear aventura
		new ConexionServerTask().execute(new Object[] { 3, a1, nick });
	}

	/**
	 * Sirve para ver ranking arcade online (tabla arcade)
	 * 
	 * @param jugador
	 * 
	 */
	public void lanzaDialogoEsperaVerRankingArcade(final Jugador jugador) { //
		// valor 4 activa ver ranking online
		new ConexionServerTask().execute(new Object[] { 4, jugador });
	}

	/**
	 * Sirve para obtener una aventura (tabla quest)
	 * 
	 * @param av
	 * 
	 */
	public void lanzaDialogoEsperaGetQuest(final Aventura av) { //
		// valor 5 activa get aventura
		new ConexionServerTask().execute(new Object[] { 5, av });
	}

	/**
	 * Sirve para actualizar una aventura (tabla quest)
	 * 
	 * @param av
	 * 
	 */
	public void lanzaDialogoEsperaUpdateQuest(final Aventura av,String nickJugador) { //
		// valor 6 activa update aventura
		new ConexionServerTask().execute(new Object[] { 6, av, nickJugador });
	}

	/**
	 * Sirve para obtener una aventura y ver si concuerda su pass (tabla quest)
	 * 
	 * @param av
	 * 
	 */
	public void lanzaDialogoEsperaGetQuestPass(final Aventura av) { //
		// valor 7 activa get aventura con pass
		new ConexionServerTask().execute(new Object[] { 7, av });
	}

	/**
	 * Sirve para subir puntuaciones de pquest
	 * 
	 * @param j
	 * 
	 */
	public void lanzaDialogoUpdatePquest(final Jugador j) { //
		// valor 8 activa updatePquest
		new ConexionServerTask().execute(new Object[] { 8, j });
	}

	/**
	 * Sirve para obtener las puntuaciones de pquest en base a quest
	 * 
	 * @param quest
	 * 
	 */
	public void lanzaDialogoGetPquest(final String quest) { //
		// valor 9 activa getPquest
		new ConexionServerTask().execute(new Object[] { 9, quest });
	}

	/**
	 * Sirve para obtener una aventura (tabla quest) y pasar a unirse
	 * 
	 * @param av
	 * 
	 */
	public void lanzaDialogoEsperaGetQuestUnirse(final Aventura av) { //
		// valor 10 activa get aventura para unirse a partida
		new ConexionServerTask().execute(new Object[] { 10, av });
	}

	/**
	 * <p>
	 * Clase asincrona para realizar conexiones con el server
	 * </p>
	 * <p>
	 * Debe usarse siempre con un Launch creado como objeto e implementar la
	 * interfaz Inet para las opciones: 3 y 5
	 * </p>
	 * 
	 * @author Cinnamon Team
	 * @version 1.0 23.04.2012
	 */
	public class ConexionServerTask extends
			AsyncTask<Object, Boolean, Object[]> {

		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			this.dialog = ProgressDialog.show(Launch.this.a, "Conectando...",
					"Por favor, espera...", true, true);
		}

		@Override
		protected Object[] doInBackground(final Object... datos) {
			String nick, pass, qStr;
			UtilJSON u;
			Inet inet;
			Aventura av;
			Object[] ret = new Object[3];
			// tarea a realizar
			int tarea = (Integer) datos[0];
			// rellena tarea
			ret[0] = tarea;
			switch (tarea) {
			case 0:
				// login
				Login loginA = (Login) Launch.this.a;
				nick = (String) datos[1];
				pass = (String) datos[2];
				// hace login y obtiene return
				ret[1] = loginA.conexion.login(nick, pass);
				String respuestaSave = loginA.conexion.getRespuesta();
				if (respuestaSave.equals("3")) {
					// TODO Desactivado Solo permitimos registro con inet previo
					// devuelve Usuario Inexistente, comprobar que existe en la
					// BDlocal, y entonces registras online directametne
					// if (loginA.loginLocal())
					// loginA.conexion.register(nick, pass);
				}else if (respuestaSave.equals("2")){
					//nose
				} else if (respuestaSave!= "") {
					SharedPreferences.Editor editor = Login.prefs.edit();
					
					editor.putString("token", respuestaSave);
						
					editor.commit();
					// esta en la bd online, comprobar si existe en bd local,
					// si no existe crearlo cogiendo sus datos de la bd online
					if (!loginA.loginLocal()) {
						u = new UtilJSON(Launch.this.a);
						//String s = u.jsonToStringLogin(respuestaSave);
						
						//TODO
						//este es el token
						//guardo el string respuestaSave en las prefs
						
						/////////////////////////////////
						loginA.conexion.dameOnlineArcade();
						String json = loginA.conexion.getRespuesta();
						
						int[] d = u.verSiJugadorExisteArcade(json,
								loginA.nombre);
						if (d != null) {
							loginA.creaJugadorLocalActualizado(d);
							respuestaSave = "4";
						}
					}
				}
				ret[2] = respuestaSave;
				break;
			case 1:
				// Register
				Login register = (Login) Launch.this.a;
				nick = (String) datos[1];
				pass = (String) datos[2];
				// hace registro y obtiene return
				ret[1] = register.conexion.register(nick, pass);
				ret[2] = register.conexion.getRespuesta();
				break;
			case 2:
				// Upload Score Arcade
				Arcade upScore = (Arcade) Launch.this.a;
				nick = (String) datos[1];
				int[] b = (int[]) datos[2];
				String s = Login.prefs.getString("token", "");
				ret[1] = upScore.conexion.updateArcade(b, nick,s);
				break;
			case 3:
				// Upload Aventura (tabla quest)
				inet = (Inet) Launch.this.a;
				Aventura quest = (Aventura) datos[1];
				String creadorAventura = (String) datos[2];
				ret[1] = inet.c().creaOnlineAventura(quest.getMJArrayString(),
						quest.getPistasArrayString(), quest.getNombre(),
						quest.getPass(), false,creadorAventura,Login.prefs.getString("token", ""));
				break;
			case 4:
				// Ver ranking arcade
				Arcade seeRanking = (Arcade) Launch.this.a;
				ret[2] = datos[1];
				ret[1] = seeRanking.conexion.dameOnlineArcade();
				break;
			case 5:
				// Get aventura
				inet = (Inet) Launch.this.a;
				av = (Aventura) datos[1];
				ret[2] = av;
				ret[1] = inet.c().dameOnlineAventura(av.getNombre(), null);
				break;
			case 6:
				// Update Aventura (tabla quest)
				inet = (Inet) Launch.this.a;
				Aventura quest2 = (Aventura) datos[1];
				String nombreJugador = (String) datos[3];
				ret[1] = inet.c().creaOnlineAventura(quest2.getMJArrayString(),
						quest2.getPistasArrayString(), quest2.getNombre(),
						quest2.getPass(), true, nombreJugador, Login.prefs.getString("token", ""));
				ret[2] = quest2;
				break;
			case 7:
				// Get aventura con pass
				inet = (Inet) Launch.this.a;
				av = (Aventura) datos[1];
				ret[2] = av;
				ret[1] = inet.c().dameOnlineAventura(av.getNombre(),
						av.getPass());
				break;
			case 8:
				// Update Pquest (tabla pquest)
				inet = (Inet) Launch.this.a;
				Jugador j = (Jugador) datos[1];
				ret[1] = inet.c().updatePquest(j.getScoreQuest(),
						j.getNombre(), j.getAventura(), j.getFase(),Login.prefs.getString("token", ""));
				break;
			case 9:
				// Ver ranking pquest
				inet = (Inet) Launch.this.a;
				qStr = (String) datos[1];
				ret[1] = inet.c().getPquest(qStr);

				break;
			case 10:
				// Get aventura
				inet = (Inet) Launch.this.a;
				av = (Aventura) datos[1];
				ret[2] = av;
				ret[1] = inet.c().dameOnlineAventura(av.getNombre(), null);
				break;
			}

			return ret;
		}

		@Override
		protected void onPostExecute(final Object[] result) {
			this.dialog.dismiss();
			int tarea = (Integer) result[0];
			String respuestaSave;
			Inet inet;
			boolean conex;
			UtilJSON u;
			Aventura av;
			Jugador jugador;
			switch (tarea) {
			case 0:
				// login
				Props.Comun.ONLINE = (Boolean) result[1];
				respuestaSave = (String) result[2];
				Login login = (Login) Launch.this.a;
				if (Props.Comun.ONLINE == false) {
					login.l.lanzaToast(Props.Strings.ERROR_INET);
					if (login.loginLocal()) {
						login.l.lanzaToast(Props.Strings.MODO_OFFLINE);
						login.lanzaMenuPrincipal();
					} else if (!Props.Comun.ONLINE) {
						login.l.lanzaToast(Props.Strings.USER_PASS_MAL);
					}
				} else {
					if (respuestaSave.equals("2")) {
						login.l.lanzaToast(Props.Strings.PASS_ERROR);
					} else if (respuestaSave.equals("3")) {
						login.l.lanzaToast(Props.Strings.USER_INET_NO_EXISTE);
					} else if (respuestaSave.equals("4")) {
						login.l.lanzaToast(Props.Strings.USER_UPDATE);
						login.lanzaMenuPrincipal();
					}
					else {
						login.l.lanzaToast(Props.Strings.LOGIN_OK);
						if (login.loginLocal()) {
							login.lanzaMenuPrincipal();
						}
					}
				}
				break;
			case 1:
				// Register
				Props.Comun.ONLINE = (Boolean) result[1];
				Login register = (Login) Launch.this.a;
				respuestaSave = (String) result[2];
				if (Props.Comun.ONLINE == false) {
					register.l.lanzaToast(Props.Strings.ERROR_INET);
				} else {
					if (respuestaSave.equals("1")) {
						// TODO solo registra offline si ha registrado online
						register.l.lanzaToast(Props.Strings.USER_CREADO_ONLINE);
						if (!register.creaJugadorLocal()) {
							register.lanzaMenuPrincipal();
							// else if (!Props.Comun.ONLINE) {
							// register.l.lanzaToast(Props.Strings.USER_YA_EXISTE);
							// login.l.lanzaToast(Props.Strings.USER_CREADO);
							// }
						}
					} else if (respuestaSave.equals("2")) {
						register.l
								.lanzaToast(Props.Strings.USER_YA_EXISTE_ONLINE);
					}
				}
				break;
			case 2:
				// Upload Score Arcade
				Arcade upScore = (Arcade) Launch.this.a;
				conex = (Boolean) result[1];
				if (conex) {
					upScore.l.lanzaToast(Props.Strings.SCORE_SUBIDA);
				} else {
					upScore.l.lanzaToast(Props.Strings.SCORE_SUBIDA_ERROR);
				}
				break;
			case 3:
				// Upload Aventura (tabla quest)
				conex = (Boolean) result[1];
				inet = (Inet) Launch.this.a;
				if (conex) {
					if (inet.c().getRespuesta().equals("1")) {
						inet.l().lanzaToast(Props.Strings.AVENTURA_SUBIDA);
						EligeModoAventura eli = (EligeModoAventura) Launch.this.a;
						eli.creaAventuraLocal();
						eli.lanzaSelecMJ();
					} else if (inet.c().getRespuesta().equals("2")) {
						inet.l().lanzaToast(Props.Strings.AVENTURA_YA_EXISTE);
					} else if (inet.c().getRespuesta().equals("3")) {
						inet.l().lanzaToast(Props.Strings.DB_ABRIR_ERROR);
					}
				} else {
					inet.l().lanzaToast(Props.Strings.AVENTURA_SUBIDA_ERROR);
				}
				break;
			case 4:
				// Ver ranking arcade
				conex = (Boolean) result[1];
				jugador = (Jugador) result[2];
				Arcade seeRanking = (Arcade) Launch.this.a;
				if (conex) {
					String json = seeRanking.conexion.getRespuesta();
					Bundle b = new Bundle();
					b.putSerializable(Props.Comun.JSON, json);
					b.putSerializable(Props.Comun.ARCADE_DATA, jugador);
					seeRanking.l.lanzaActivity(Props.Action.RANKING, b);
				} else {
					seeRanking.l.lanzaToast(Props.Strings.VER_RANKING_ERROR);
				}
				break;
			case 5:
				// Obtener Aventura
				conex = (Boolean) result[1];
				inet = (Inet) Launch.this.a;
				if (conex) {
					String aventura = inet.c().getRespuesta();
					if (aventura.equals("3")) {
						inet.l().lanzaToast(Props.Strings.AVENTURA_NO_EXISTE);
					} else {
						av = (Aventura) result[2];
						u = new UtilJSON(Launch.this.a);
						if (u.rellenaQuest(aventura, av)) {
							inet.l().lanzaToast(Props.Strings.AVENTURA_BAJADA);
							EligeModoAventura eli = (EligeModoAventura) Launch.this.a;
							eli.creaAventuraLocalActualizada();
							eli.lanzaSelecMJ();
						}
					}
				} else {
					inet.l().lanzaToast(Props.Strings.AVENTURA_BAJADA_ERROR);
				}
				break;
			case 6:
				// Update Aventura
				conex = (Boolean) result[1];
				inet = (Inet) Launch.this.a;
				av = (Aventura) result[2];
				if (conex) {
					if (inet.c().getRespuesta().equals("1")) {
						inet.l().lanzaToast(Props.Strings.AVENTURA_UPDATED);
						if (Props.Comun.ACTIVIDAD != null) {
							Props.Comun.ACTIVIDAD.finish();// cierra SelecMJ
							Props.Comun.ACTIVIDAD = null;// resetea
						}
						Bundle b = new Bundle();
						b.putSerializable(Props.Comun.AVENTURA, av);
						inet.l().lanzaActivity(Props.Action.INGAMEHOST, b);
						a.finish();// cerrara selecPista
					} else if (inet.c().getRespuesta().equals("3")) {
						inet.l().lanzaToast(Props.Strings.DB_ABRIR_ERROR);
					}
				} else {
					inet.l().lanzaToast(Props.Strings.AVENTURA_UPDATED_ERROR);
				}
				break;

			case 7:
				// Obtener Aventura con pass
				conex = (Boolean) result[1];
				inet = (Inet) Launch.this.a;
				if (conex) {
					String aventura = inet.c().getRespuesta();
					u = new UtilJSON(Launch.this.a);
					if (aventura.equals("3")) {
						inet.l().lanzaToast(Props.Strings.AVENTURA_NO_EXISTE);
					} else if (aventura.equals("2")) {
						inet.l().lanzaToast(Props.Strings.PASS_ERROR);
					} else {
						av = (Aventura) result[2];
						u = new UtilJSON(Launch.this.a);
						if (u.rellenaQuest(aventura, av)) {
							inet.l().lanzaToast(Props.Strings.AVENTURA_BAJADA);
							EligeModoAventura eli = (EligeModoAventura) Launch.this.a;
							eli.creaAventuraLocalActualizada();
							eli.lanzaSelecPISTA();
						}
					}
				} else {
					inet.l().lanzaToast(Props.Strings.AVENTURA_BAJADA_ERROR);
				}
				break;
			case 8:
				// Upload Score pquest
				inet = (Inet) Launch.this.a;
				conex = (Boolean) result[1];
				if (conex) {
					inet.l().lanzaToast(Props.Strings.SCORE_SUBIDA);
				} else {
					inet.l().lanzaToast(Props.Strings.SCORE_SUBIDA_ERROR);
				}
				break;
			case 9:
				// Ver ranking pquest
				conex = (Boolean) result[1];
				inet = (Inet) Launch.this.a;
				if (conex) {
					String json = inet.c().getRespuesta();
					if (json != null) {
						Bundle b = new Bundle();
						b.putSerializable(Props.Comun.JSON, json);
						inet.l().lanzaActivity(Props.Action.RANKING, b);
					} else {
						inet.l()
								.lanzaToast(Props.Strings.VER_RANKING_NOPLAYERS);
					}
				} else {
					inet.l().lanzaToast(Props.Strings.VER_RANKING_ERROR);
				}
				break;
			case 10:
				// Obtener Aventura
				conex = (Boolean) result[1];
				inet = (Inet) Launch.this.a;
				if (conex) {
					String aventura = inet.c().getRespuesta();
					if (aventura.equals("3")) {
						inet.l().lanzaToast(Props.Strings.AVENTURA_NO_EXISTE);
					} else {
						av = (Aventura) result[2];
						u = new UtilJSON(Launch.this.a);
						if (u.rellenaQuest(aventura, av)) {
							inet.l().lanzaToast(Props.Strings.AVENTURA_BAJADA);
							EligeModoAventura eli = (EligeModoAventura) Launch.this.a;
							eli.lanzaInGameAventura();
						}
					}
				} else {
					inet.l().lanzaToast(Props.Strings.AVENTURA_BAJADA_ERROR);
				}
				break;
			}
		}
	}



}
