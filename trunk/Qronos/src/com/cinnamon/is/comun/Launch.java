//
// Universidad Complutense de Madrid
// Ingenieria Inform�tica
//
// PROYECTO: Qronos
// ASIGNATURA : Ingenier�a del Software
//
package com.cinnamon.is.comun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.dialog.AyudaDialog;
import com.cinnamon.is.comun.dialog.MenuDialog;
import com.cinnamon.is.game.Arcade;
import com.cinnamon.is.game.Aventura;
import com.cinnamon.is.game.EligeModoAventura;
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
	public Launch(Activity activity) {
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
	public void lanzaActivity(String ACTION) {
		Launch.lanzaActivity(a, ACTION);
	}

	/**
	 * Lanza una actividad desde static
	 * 
	 * @param a
	 *            la actividad que lo lanza
	 * @param ACTION
	 *            la actividad a lanzar
	 */
	public static void lanzaActivity(Activity a, String ACTION) {
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
	public void lanzaActivity(String ACTION, Bundle b) {
		Launch.lanzaActivity(a, ACTION, b);
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
	public static void lanzaActivity(Activity a, String ACTION, Bundle b) {
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
	public void lanzaActivity(String ACTION, int rq) {
		Launch.lanzaActivity(a, ACTION, rq);
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
	public static void lanzaActivity(Activity a, String ACTION, int rq) {
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
	public void lanzaActivity(String ACTION, Bundle b, int rq) {
		Launch.lanzaActivity(a, ACTION, b, rq);
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
	public static void lanzaActivity(Activity a, String ACTION, Bundle b, int rq) {
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
	public void lanzaToast(String msg) {
		lanzaToast(a, msg);
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
	public static void lanzaToast(Activity a, String msg) {
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
	public void returnActivity(Bundle b, int rq) {
		returnActivity(a, b, rq);
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
	public static void returnActivity(Activity a, Bundle b, int rq) {
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
	public AlertDialog lanzaAviso(String title, String texto) {
		return lanzaAviso(title, texto, a);
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
	public static AlertDialog lanzaAviso(String title, String texto, Activity a) {
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setTitle(title);
		builder.setMessage(texto).setNegativeButton("Cerrar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
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
	public AlertDialog lanzaAviso(String texto) {
		return lanzaAviso(texto, a);
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
	public static AlertDialog lanzaAviso(String texto, Activity a) {
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setMessage(texto).setNegativeButton("Cerrar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
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
	public AlertDialog lanzaConfirmacion(String title, String texto) {
		return lanzaConfirmacion(title, texto, a);
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
	public static AlertDialog lanzaConfirmacion(String title, String texto,
			Activity a) {

		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setTitle(title);
		builder.setMessage(texto).setCancelable(false)
				.setPositiveButton("S�", (OnClickListener) a)
				.setNegativeButton("No", (OnClickListener) a);
		return builder.show();
	}

	/**
	 * 
	 * @param context
	 *            Contexto de la actividad
	 * @param nMJ
	 *            N�mero de Minijuego
	 */
	public static void lanzaConfirmacion(Context context, int nMJ,
			Launch launch, int modo) {
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
		return lanzaOpciones(a);
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
	public static AlertDialog lanzaOpciones(Activity a) {

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
	public static void lanzaOpciones(Context context, String title, int modo,
			Minijuego mj) {
		MenuDialog dialogo = new MenuDialog(context, title, modo,
				R.style.CenterDialog, mj);
		dialogo.show();
	}

	/**
	 * Sirve para loguear un jugador
	 * 
	 * @param nick
	 * @param pass
	 */
	public void lanzaDialogoEsperaLogin(String nick, String pass) { //
		// valor 0 activa login
		new ConexionServerTask().execute(new Object[] { 0, nick, pass });
	}

	/**
	 * Sirve para registrar un jugador
	 * 
	 * @param nick
	 * @param pass
	 */
	public void lanzaDialogoEsperaRegister(String nick, String pass) { //
		// valor 1 activa register
		new ConexionServerTask().execute(new Object[] { 1, nick, pass });
	}

	/**
	 * Sirve para subir las puntuaciones en el modo arcade (tabla parcade)
	 * 
	 * @param nick
	 * @param pass
	 */
	public void lanzaDialogoEsperaUpdateScoreArcade(String nick, int[] a) { //
		// valor 2 activa subir scores
		new ConexionServerTask().execute(new Object[] { 2, nick, a });
	}

	/**
	 * Sirve para subir una aventura al servidor (tabla quest)
	 * 
	 * @param a
	 */
	public void lanzaDialogoEsperaCreaQuest(Aventura a) { //
		// valor 3 activa crear aventura
		new ConexionServerTask().execute(new Object[] { 3, a });
	}

	/**
	 * Sirve para ver ranking arcade online (tabla arcade)
	 * 
	 */
	public void lanzaDialogoEsperaVerRankingArcade() { //
		// valor 4 activa ver ranking online
		new ConexionServerTask().execute(new Object[] { 4 });
	}

	/**
	 * Sirve para obtener una aventura (tabla quest)
	 * 
	 */
	public void lanzaDialogoEsperaGetQuest(Aventura av) { //
		// valor 5 activa get aventura
		new ConexionServerTask().execute(new Object[] { 5, av });
	}

	/**
	 * Sirve para actualizar una aventura (tabla quest)
	 * 
	 */
	public void lanzaDialogoEsperaUpdateQuest(Aventura av) { //
		// valor 6 activa update aventura
		new ConexionServerTask().execute(new Object[] { 6, av });
	}

	/**
	 * Sirve para obtener una aventura y ver si concuerda su pass (tabla quest)
	 * 
	 */
	public void lanzaDialogoEsperaGetQuestPass(Aventura av) { //
		// valor 7 activa get aventura con pass
		new ConexionServerTask().execute(new Object[] { 7, av });
	}

	/**
	 * Sirve para subir puntuaciones de pquest
	 * 
	 */
	public void lanzaDialogoUpdatePquest(Jugador j) { //
		// valor 8 activa updatePquest
		new ConexionServerTask().execute(new Object[] { 8, j });
	}

	/**
	 * Sirve para obtener las puntuaciones de pquest en base a quest
	 * 
	 */
	public void lanzaDialogoGetPquest(String quest) { //
		// valor 9 activa getPquest
		new ConexionServerTask().execute(new Object[] { 9, quest });
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
			dialog = ProgressDialog.show(a, "Conectando...",
					"Por favor, espera...", true, false);
		}

		@Override
		protected Object[] doInBackground(Object... datos) {
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
				Login loginA = (Login) a;
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
				} else if (respuestaSave.equals("1")) {
					// esta en la bd online, comprobar si existe en bd local,
					// si no existe crearlo cogiendo sus datos de la bd online
					if (!loginA.loginLocal()) {
						loginA.conexion.dameOnlineArcade();
						String json = loginA.conexion.getRespuesta();
						u = new UtilJSON(a);
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
				Login register = (Login) a;
				nick = (String) datos[1];
				pass = (String) datos[2];
				// hace registro y obtiene return
				ret[1] = register.conexion.register(nick, pass);
				ret[2] = register.conexion.getRespuesta();
				break;
			case 2:
				// Upload Score Arcade
				Arcade upScore = (Arcade) a;
				nick = (String) datos[1];
				int[] b = (int[]) datos[2];
				ret[1] = upScore.conexion.updateArcade(b, nick);
				break;
			case 3:
				// Upload Aventura (tabla quest)
				inet = (Inet) a;
				Aventura quest = (Aventura) datos[1];
				ret[1] = inet.c().creaOnlineAventura(quest.getMJArrayString(),
						quest.getPistasArrayString(), quest.getNombre(),
						quest.getPass(), false);
				break;
			case 4:
				// Ver ranking arcade
				Arcade seeRanking = (Arcade) a;
				ret[1] = seeRanking.conexion.dameOnlineArcade();
				break;
			case 5:
				// Get aventura
				inet = (Inet) a;
				av = (Aventura) datos[1];
				ret[2] = av;
				ret[1] = inet.c().dameOnlineAventura(av.getNombre(), null);
				break;
			case 6:
				// Update Aventura (tabla quest)
				inet = (Inet) a;
				Aventura quest2 = (Aventura) datos[1];
				ret[1] = inet.c().creaOnlineAventura(quest2.getMJArrayString(),
						quest2.getPistasArrayString(), quest2.getNombre(),
						quest2.getPass(), true);
				break;
			case 7:
				// Get aventura con pass
				inet = (Inet) a;
				av = (Aventura) datos[1];
				ret[2] = av;
				ret[1] = inet.c().dameOnlineAventura(av.getNombre(),
						av.getPass());
				break;
			case 8:
				// Update Pquest (tabla pquest)
				inet = (Inet) a;
				Jugador j = (Jugador) datos[1];
				ret[1] = inet.c().updatePquest(j.getScoreQuest(),
						j.getNombre(), j.getAventura(), j.getActual());
				break;
			case 9:
				// Ver ranking pquest
				inet = (Inet) a;
				qStr = (String) datos[1];
				ret[1] = inet.c().getPquest(qStr);
				break;
			}
			return ret;
		}

		@Override
		protected void onPostExecute(Object[] result) {
			dialog.dismiss();
			int tarea = (Integer) result[0];
			String respuestaSave;
			Inet inet;
			boolean conex;
			UtilJSON u;
			Aventura av;
			switch (tarea) {
			case 0:
				// login
				Props.Comun.ONLINE = (Boolean) result[1];
				respuestaSave = (String) result[2];
				Login login = (Login) a;
				if (Props.Comun.ONLINE == false) {
					login.l.lanzaToast(Props.Strings.ERROR_INET);
					if (login.loginLocal())
						login.lanzaMenuPrincipal();
					else if (!Props.Comun.ONLINE)
						login.l.lanzaToast(Props.Strings.USER_PASS_MAL);
				} else {
					if (respuestaSave.equals("1")) {
						login.l.lanzaToast(Props.Strings.LOGIN_OK);
						if (login.loginLocal())
							login.lanzaMenuPrincipal();
					} else if (respuestaSave.equals("2")) {
						login.l.lanzaToast(Props.Strings.PASS_ERROR);
					} else if (respuestaSave.equals("3")) {
						login.l.lanzaToast(Props.Strings.USER_INET_NO_EXISTE);
					} else if (respuestaSave.equals("4")) {
						login.l.lanzaToast(Props.Strings.USER_UPDATE);
						login.lanzaMenuPrincipal();
					}
				}
				break;
			case 1:
				// Register
				Props.Comun.ONLINE = (Boolean) result[1];
				Login register = (Login) a;
				respuestaSave = (String) result[2];
				if (Props.Comun.ONLINE == false) {
					register.l.lanzaToast(Props.Strings.ERROR_INET);
				} else {
					if (respuestaSave.equals("1")) {
						// TODO solo registra offline si ha registrado online
						register.l.lanzaToast(Props.Strings.USER_CREADO_ONLINE);
						if (!register.creaJugadorLocal())
							register.lanzaMenuPrincipal();
						// else if (!Props.Comun.ONLINE) {
						// register.l.lanzaToast(Props.Strings.USER_YA_EXISTE);
						// login.l.lanzaToast(Props.Strings.USER_CREADO);
						// }
					} else if (respuestaSave.equals("2")) {
						register.l
								.lanzaToast(Props.Strings.USER_YA_EXISTE_ONLINE);
					}
				}
				break;
			case 2:
				// Upload Score Arcade
				Arcade upScore = (Arcade) a;
				conex = (Boolean) result[1];
				if (conex)
					upScore.l.lanzaToast(Props.Strings.SCORE_SUBIDA);
				else
					upScore.l.lanzaToast(Props.Strings.SCORE_SUBIDA_ERROR);
				break;
			case 3:
				// Upload Aventura (tabla quest)
				conex = (Boolean) result[1];
				inet = (Inet) a;
				if (conex) {
					if (inet.c().getRespuesta().equals("1")) {
						inet.l().lanzaToast(Props.Strings.AVENTURA_SUBIDA);
						EligeModoAventura eli = (EligeModoAventura) a;
						eli.creaAventuraLocal();
						eli.lanzaSelecMJ();
					} else if (inet.c().getRespuesta().equals("2")) {
						inet.l().lanzaToast(Props.Strings.AVENTURA_YA_EXISTE);
					} else if (inet.c().getRespuesta().equals("3")) {
						inet.l().lanzaToast(Props.Strings.DB_ABRIR_ERROR);
					}
				} else
					inet.l().lanzaToast(Props.Strings.AVENTURA_SUBIDA_ERROR);
				break;
			case 4:
				// Ver ranking arcade
				conex = (Boolean) result[1];
				Arcade seeRanking = (Arcade) a;
				if (conex) {
					String json = seeRanking.conexion.getRespuesta();
					Bundle b = new Bundle();
					b.putSerializable(Props.Comun.JSON, json);
					b.putSerializable(Props.Comun.ARCADE, "Arcade");
					seeRanking.l.lanzaActivity(Props.Action.RANKING, b);
				} else {
					seeRanking.l.lanzaToast(Props.Strings.VER_RANKING_ERROR);
				}
				break;
			case 5:
				// Obtener Aventura
				conex = (Boolean) result[1];
				inet = (Inet) a;
				if (conex) {
					String aventura = inet.c().getRespuesta();
					if (aventura.equals("3"))
						inet.l().lanzaToast(Props.Strings.AVENTURA_NO_EXISTE);
					else {
						av = (Aventura) result[2];
						u = new UtilJSON(a);
						if (u.rellenaQuest(aventura, av)) {
							inet.l().lanzaToast(Props.Strings.AVENTURA_BAJADA);
							EligeModoAventura eli = (EligeModoAventura) a;
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
				inet = (Inet) a;
				if (conex) {
					if (inet.c().getRespuesta().equals("1")) {
						inet.l().lanzaToast(Props.Strings.AVENTURA_UPDATED);
						Props.Comun.ACTIVIDAD.finish();// cierra SelecMJ
						Props.Comun.ACTIVIDAD = null;// resetea
						// TODO Lanzar Siguiente Actividad
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
				inet = (Inet) a;
				if (conex) {
					String aventura = inet.c().getRespuesta();
					u = new UtilJSON(a);
					if (aventura.equals("3"))
						inet.l().lanzaToast(Props.Strings.AVENTURA_NO_EXISTE);
					else if (aventura.equals("2"))
						inet.l().lanzaToast(Props.Strings.PASS_ERROR);
					else {
						av = (Aventura) result[2];
						u = new UtilJSON(a);
						if (u.rellenaQuest(aventura, av)) {
							inet.l().lanzaToast(Props.Strings.AVENTURA_BAJADA);
							EligeModoAventura eli = (EligeModoAventura) a;
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
				inet = (Inet) a;
				conex = (Boolean) result[1];
				if (conex)
					inet.l().lanzaToast(Props.Strings.SCORE_SUBIDA);
				else
					inet.l().lanzaToast(Props.Strings.SCORE_SUBIDA_ERROR);
				break;
			case 9:
				// Ver ranking pquest
				conex = (Boolean) result[1];
				inet = (Inet) a;
				if (conex) {
					String json = inet.c().getRespuesta();
					Bundle b = new Bundle();
					b.putSerializable(Props.Comun.JSON, json);
					inet.l().lanzaActivity(Props.Action.RANKING, b);
				} else {
					inet.l().lanzaToast(Props.Strings.VER_RANKING_ERROR);
				}
				break;
			}
		}
	}

}
