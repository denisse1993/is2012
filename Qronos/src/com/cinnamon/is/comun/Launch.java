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
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.dialog.AyudaDialog;
import com.cinnamon.is.comun.dialog.MenuDialog;
import com.cinnamon.is.game.Arcade;
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
	 * @param nick
	 * @param pass
	 */
	public void lanzaDialogoEsperaLogin(String nick, String pass) { //
		// valor 0 activa login
		new ConexionServerTask().execute(new Object[] { 0, nick, pass });
	}

	/**
	 * @param nick
	 * @param pass
	 */
	public void lanzaDialogoEsperaRegister(String nick, String pass) { //
		// valor 1 activa register
		new ConexionServerTask().execute(new Object[] { 1, nick, pass });
	}

	/**
	 * @param nick
	 * @param pass
	 */
	public void lanzaDialogoEsperaUpdateScore(String nick, int[] a) { //
		// valor 2 activa subir scores
		new ConexionServerTask().execute(new Object[] { 2, nick, a });
	}

	/**
	 * Clase asincrona para realizar conexiones con el server Debe usarse
	 * siempre con un Launch creado como objeto
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
			String nick, pass;
			Object[] ret = new Object[2];
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
				if (loginA.conexion.getRespuesta().equals("3")) {
					// devuelve Usuario Inexistente, comprobar que existe en la
					// BDlocal, y entonces registras online directametne
					if (loginA.loginLocal())
						loginA.conexion.register(nick, pass);
				}
				break;
			case 1:
				// Register
				Login register = (Login) a;
				nick = (String) datos[1];
				pass = (String) datos[2];
				// hace login y obtiene return
				ret[1] = register.conexion.register(nick, pass);
				break;
			case 2:
				// Upload Score
				Arcade upScore = (Arcade) a;
				nick = (String) datos[1];
				int[] a = (int[]) datos[2];
				//TODO actualizar cuando updateArcade se adapte
				// ret[1]=upScore.conexion.updateArcade(a, nick);
				break;
			}
			return ret;
		}

		@Override
		protected void onPostExecute(Object[] result) {
			dialog.dismiss();
			int tarea = (Integer) result[0];
			switch (tarea) {
			case 0:
				// login
				Props.Comun.ONLINE = (Boolean) result[1];
				Login login = (Login) a;
				if (Props.Comun.ONLINE == false) {
					login.l.lanzaToast(Props.Strings.ERROR_INET);
				} else {
					if (login.conexion.getRespuesta().equals("1")) {
						login.l.lanzaToast(Props.Strings.LOGIN_OK);
					} else if (login.conexion.getRespuesta().equals("2")) {
						login.l.lanzaToast(Props.Strings.PASS_ERROR);
					} else if (login.conexion.getRespuesta().equals("3")) {
						login.l.lanzaToast(Props.Strings.USER_INET_NO_EXISTE);
					}
				}
				if (login.loginLocal())
					login.lanzaMenuPrincipal();
				else if (!Props.Comun.ONLINE)
					login.l.lanzaToast(Props.Strings.USER_PASS_MAL);
				break;
			case 1:
				// Register
				Props.Comun.ONLINE = (Boolean) result[1];
				Login register = (Login) a;
				if (Props.Comun.ONLINE == false) {
					register.l.lanzaToast(Props.Strings.ERROR_INET);
				} else {
					// TODO a filtar Toast con valores devueltos por online
				}
				if (!register.creaJugadorLocal())
					register.lanzaMenuPrincipal();
				else if (!Props.Comun.ONLINE) {
					register.l.lanzaToast(Props.Strings.USER_YA_EXISTE);
					// login.l.lanzaToast(Props.Strings.USER_CREADO);
				}
				break;
			case 2:
				// Upload Score
				Arcade upScore = (Arcade) a;
				boolean conex = (Boolean) result[1];
				if (conex)
					upScore.l.lanzaToast(Props.Strings.SCORE_SUBIDA);
				else
					upScore.l.lanzaToast(Props.Strings.SCORE_ERROR_SUBIDA);
				break;
			}

		}
	}

}
