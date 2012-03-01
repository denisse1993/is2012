//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Qronos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Clase de utilidad para los distintos Dialogs
 * 
 * @author Cinnamon Team
 * @version 1.0 01.03.2012
 */
public final class Dialogs {
	
	
	/**
	 * Indica si se quiere salir de la actividad que llame a lanzaExit o no
	 */
	public static boolean exit;
	
	/**
	 * Lanza el Dialog con un texto y un titulo
	 */
	public static void lanzarOneButton(String texto, String title, Context c) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
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
	 * Lanza el dialog para escoger si quieres cerrar la actividad actual o no
	 */
	public static boolean lanzaExit(Context c) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setMessage("¿Quieres salir?")
				.setCancelable(false)
				.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						exit=true;
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						exit=false;
						dialog.cancel();
					}
				});
		builder.show();
		return exit;
	}
}
