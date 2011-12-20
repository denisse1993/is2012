//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import com.cinnamon.is.R;
import com.cinnamon.is.game.Jugador;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Actividad abtracta que representa un minijuego
 * 
 * @author Cinnamon Team
 * @version 1.4 12.12.2011
 */
public abstract class Minijuego extends Activity implements OnClickListener {

	/**
	 * Jugador actual de la aplicacion
	 */
	protected Jugador jugador;

	/**
	 * El nombre del minijuego
	 */
	protected String nombre;

	/**
	 * Indica si el minijuego ha sido completado
	 */
	protected boolean superado;

	/**
	 * El objeto del juego que se desbloquea al superar el minijuego <code><pre>
	 * Valor 1 - papel 1
	 * Valor 2 - papel 2
	 * Valor 3 - papel 3
	 * Valor 4 - maquina
	 * </pre></code>
	 * 
	 * @see DbAdapter.INFO_KEY_MOCHILA
	 */
	protected int objeto;

	/**
	 * La fase que representa el minijuego * <code><pre>
	 * Valor 0 - inicio
	 * Valor 1 - fase 1
	 * Valor 2 - fase 2
	 * Valor 3 - fase 3
	 * Valor 4 - fase 4
	 * Valor 5 - fase 5
	 * </pre></code>
	 * 
	 * @see DbAdapter.INFO_KEY_MOCHILA
	 */
	protected int fase;

	/**
	 * Maxima puntuacion
	 */
	protected final int MAX_SCORE = 1000;

	/**
	 * Maximo tiempo de juego en segundos (5minutos)
	 */
	// podria funcionar ejecutandose el finishTime() cada x evento y que si es
	// mayor que este tiempo apareciera el boton de rendirse o un cuadro de
	// dialogo o algo asi
	protected final long MAX_TIME = 300;

	/**
	 * Variables para controlar en tiempo
	 */
	protected long start, elapsed;

	/**
	 * Variables para pasar de nanosegundos a segundos y viceversa
	 */
	protected final static double tos = 0.000000001;
	protected final static double tons = 1000000000;

	protected static final int DIALOG_MINIJUEGOS_INIT = 0;

	/**
	 * Inicia el contador de tiempo del minijuego
	 */
	protected void startTime() {
		start = System.nanoTime();
	}

	/**
	 * Detiene el contador de tiempo del minijuego y obtiene el tiempo
	 */
	protected void finishTime() {
		elapsed = System.nanoTime() - start;
	}

	/**
	 * Calcula la puntuacion del jugador
	 * 
	 * @return la puntuacion del jugador en base al tiempo que ha tardado
	 */
	protected int calcularPuntuacion() {
		// pasa el tiempo a segundos
		double elapsedS = elapsed * tos;
		int score = MAX_SCORE;
		// tiempos de prueba para probar la aplicacion, habría que mirar cuando
		// se tarda en cada uno, o dejarlo para todos igual
		if (elapsedS < 60)// 60segundos
			return score;
		else if (elapsedS >= 60 && elapsedS < 120)
			return score - 200;
		else if (elapsedS >= 120 && elapsedS < 240)
			return score - 400;
		else if (elapsedS >= 240 && elapsedS < 300)
			return score - 600;
		else if (elapsedS >= 300 && elapsedS < 360)
			return score - 800;
		else
			return 0;
	}

	@Override
	public void onBackPressed() {
		lanzaExitDialog();
	}

	/**
	 * Metodo que finaliza el minijuego con resultado superado o no
	 */
	protected void finalizar() {
		finishTime();
		int puntuacion = calcularPuntuacion();

		if (superado) {
			jugador.setScore(jugador.getScore() + puntuacion);
			jugador.addObjeto(objeto);
			jugador.superaFase(fase);
			jugador.actualFase(fase+1);
		} else
			jugador.actualFase(fase);

		Intent r = new Intent();
		r.putExtra(Intents.Comun.superado, superado);
		r.putExtra(Intents.Comun.objeto, objeto);
		r.putExtra(Intents.Comun.JUGADOR, jugador);
		setResult(RESULT_OK, r);
		finish();
	}

	/**
	 * Metodo que lanza el dialog para escoger si quieres salir del minijuego
	 */
	protected void lanzaExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"¿Quieres salir del minijuego sin completarlo?\n¡Deberás volver a escanear el QR para lanzarlo de nuevo!")
				.setCancelable(false)
				.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finalizar();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show();
	}

	// getters & setters
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isSuperado() {
		return superado;
	}

	public void setSuperado(boolean superado) {
		this.superado = superado;
	}

	public int getObjeto() {
		return objeto;
	}

	/**
	 * <p>
	 * Actualiza el objeto del minijuego
	 * </p>
	 * <code><pre>
	 * Valor 0 - final - FINAL
	 * Valor 1 - papel 1 - Ascensor
	 * Valor 2 - papel 2 - Reinas
	 * Valor 3 - papel 3 - Puzzle
	 * Valor 4 - maquina - Cuadrado</pre></code></pre>
	 * 
	 * @param objeto
	 *            a poner
	 */
	public void setObjeto(int objeto) {
		this.objeto = objeto;
	}

	public int getFase() {
		return fase;
	}

	/**
	 * La fase que representa el minijuego <code><pre>
	 * Valor 0 - fase 0 - inicio
	 * Valor 1 - fase 1 - cuadrado 
	 * Valor 2 - fase 2 - ascensor
	 * Valor 3 - fase 3 - reinas
	 * Valor 4 - fase 4 - puzzle
	 * Valor 5 - fase 5 - end
	 * </pre></code>
	 * 
	 * @see DbAdapter.INFO_KEY_MOCHILA
	 * @param fase
	 *            la fase a poner
	 */
	public void setFase(int fase) {
		this.fase = fase;
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		Dialog dialog = null;
		switch (id) {

		case DIALOG_MINIJUEGOS_INIT:
			// obtiene datos
			String textoDialog = bundle.getString("textoDialog");
			int idIvDialog = bundle.getInt("idIvDialog");
			boolean superado = bundle.getBoolean(Intents.Comun.superado);
			String title = bundle.getString("title");
			// crea dialog
			dialog = new Dialog(this);
			dialog.setContentView(R.layout.dialogimg);
			dialog.setTitle(title);
			// pone elementos
			TextView tvDialog = (TextView) dialog.findViewById(R.id.tvDialog);
			tvDialog.setText(textoDialog);
			ImageView ivDialog = (ImageView) dialog.findViewById(R.id.ivDialog);
			ivDialog.setImageResource(idIvDialog);
			if (!superado)
				ivDialog.setVisibility(View.GONE);
			// ivDialog.setImageResource(R.drawable.bonoff);
			Button bDialog = (Button) dialog.findViewById(R.id.bDialog);
			bDialog.setOnClickListener(this);
			break;
		}

		return dialog;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bDialog:
			dismissDialog(DIALOG_MINIJUEGOS_INIT);
			break;
		}

	}

	public void lanzarAvisoMJ(String texto, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(texto).setNegativeButton("Cerrar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show();
	}

}
