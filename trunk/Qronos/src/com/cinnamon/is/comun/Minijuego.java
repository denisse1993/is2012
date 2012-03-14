//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.SurfaceView;

/**
 * Actividad abtracta que representa un minijuego
 * 
 * @author Cinnamon Team
 * @version 1.5 14.03.2012
 */
public abstract class Minijuego extends Activity implements
		DialogInterface.OnClickListener {

	/**
	 * GameView del MJ
	 */
	protected SurfaceView game;
	/**
	 * GameLoop del MJ
	 */
	protected Thread run;
	/**
	 * El nombre del minijuego
	 */
	protected String nombre;

	/**
	 * Indica si el minijuego ha sido completado
	 */
	protected boolean superado;

	/**
	 * Maxima puntuacion
	 */
	protected final int MAX_SCORE = 1000;

	/**
	 * Maximo tiempo de juego en segundos (5minutos)
	 */
	protected final long MAX_TIME = 300;

	/**
	 * Variables para controlar el tiempo
	 */
	protected long start, elapsed;

	/**
	 * Variable que indica la fase del minijuego
	 */
	protected int fase;

	/**
	 * Variables para pasar de nanosegundos a segundos y viceversa
	 */
	protected final static double tos = 0.000000001;
	protected final static double tons = 1000000000;

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
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/**
	 * Metodo que finaliza el minijuego
	 */
	protected void finalizar(boolean s) {
		//TODO los que hereden probablemente tengan que implementar esto
		finishTime();
		int puntuacion = calcularPuntuacion();
		superado = s;
		Bundle b = new Bundle();
		b.putInt(Props.Comun.SCORE, puntuacion);
		b.putBoolean(Props.Comun.SUPERADO, superado);
		Launch.returnActivity(this, b, RESULT_OK);
	}

	/**
	 * Metodo que lanza el dialog para escoger si quieres salir del minijuego
	 */
	protected void lanzaExitDialog() {
		//TODO Debe pararse el minijuego de alguna  manera antes de lanzar el dialog
		Launch.lanzaConfirmacion("Salir del minijuego",
				"¿Quieres salir del minijuego sin completarlo?", this);
	}

	@Override
	public void onClick(DialogInterface dialog, int boton) {
		switch (boton) {
		case -1:
			dialog.cancel();
			finalizar(false);
		case -2:
			dialog.cancel();
			break;
		}
	}

	@Override
	public void onBackPressed() {
		lanzaExitDialog();
	}

	public void guardarTemp() {
	};

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

	public int getFase() {
		return fase;
	}

	public void setFase(int fase) {
		this.fase = fase;
	}

	public void setSV(SurfaceView sv) {
		this.game = sv;
	}

}
