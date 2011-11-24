package com.cinnamon.is.comun;

import android.app.Activity;
import android.os.Bundle;

/**
 * Actividad abtracta que representa un minijuego
 * 
 * @author Cinnamon Team
 * 
 */
public abstract class Minijuego extends Activity {

	/**
	 * El nombre del minijuego
	 */
	protected String nombre;

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
	 * Variables para pasar de nanosegundos a segundos y hombres mujeres y
	 * viceversa
	 */
	protected final static double tos = 0.000000001;
	protected final static double tons = 1000000000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	protected void startTime() {
		start = System.nanoTime();
	}

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
		if (elapsedS < 5)//5segundos
			return score;
		else if (elapsedS >= 5 && elapsedS < 10)
			return score - 200;
		else if (elapsedS >= 10 && elapsedS < 20)
			return score - 400;
		else if (elapsedS >= 120 && elapsedS < 180)
			return score - 600;
		else if (elapsedS >= 180 && elapsedS < 240)
			return score - 800;
		else
			return 0;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
