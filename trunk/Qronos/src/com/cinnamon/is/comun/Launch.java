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
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.dialog.AyudaDialog;
import com.cinnamon.is.comun.dialog.MenuDialog;

/**
 * <p>
 * Clase de utilidad para lanzamientos de actividades y dialogs. Se puede usar
 * tanto con metodos estaticos como creando un objeto Launch.
 * </p>
 * 
 * @author Cinnamon Team
 * @version 1.1 15.03.2012
 */
public final class Launch {

	/**
	 * Actividad padre
	 */
	private final Activity a;

	/**
	 * Metodo constructor
	 * 
	 * @param activity
	 *            la actividad padre
	 */
	public Launch(Activity activity) {
		this.a = activity;
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
		Toast.makeText(a.getApplicationContext(), msg, Toast.LENGTH_LONG)
				.show();
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
	 * @param a
	 *            la actividad de lanzamiento
	 */
	public static void lanzaAviso(String title, String texto, Activity a) {
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setTitle(title);
		builder.setMessage(texto).setNegativeButton("Cerrar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show();
	}

	/**
	 * Lanza el Dialog con un texto y un titulo
	 * 
	 * @param texto
	 *            el texto del dialog
	 * @param a
	 *            la actividad de lanzamiento
	 * @return
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
	 * @param a
	 *            la actividad de lanzamiento
	 * @return
	 */
	public static AlertDialog lanzaConfirmacion(String title, String texto,
			Activity a) {

		/*
		 * AlertDialog.Builder builder = new AlertDialog.Builder(a); //
		 * builder.setTitle(title); //
		 * builder.setMessage(texto).setCancelable(false) //
		 * .setPositiveButton("Sí", new DialogInterface.OnClickListener() { //
		 * public void onClick(DialogInterface dialog, int id) { // yes = true;
		 * // dialog.cancel(); // } // }) // .setNegativeButton("No", new
		 * DialogInterface.OnClickListener() { // public void
		 * onClick(DialogInterface dialog, int id) { // yes = false; //
		 * dialog.cancel(); // } // }); // builder.show(); return yes;
		 */
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
	 * @param a
	 *            la actividad de lanzamiento
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
}
