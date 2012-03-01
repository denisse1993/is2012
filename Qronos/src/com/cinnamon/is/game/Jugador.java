//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import java.io.Serializable;

/**
 * Clase que representa a un jugador en el juego. implementa serializable para
 * poder ser pasada en un intent entre activities
 * 
 * @author Cinnamon Team
 * @version 1.0 18.02.2012
 */
public class Jugador implements Serializable {

	/**
	 * Nombre del jugador
	 */
	private String nombre;

	/**
	 * Puntuacion del jugador en los distintos minijuegos
	 */
	private int[] score;

	/**
	 * Fase en la que se encuentra el jugador
	 */
	private int faseActual;

	/**
	 * ID para la serializacion
	 */
	private static final long serialVersionUID = 4L;

	private static final int MAX_MJ = 5;

	/**
	 * Genera un jugador por parametros
	 * 
	 * @param nombre
	 *            del jugador
	 * @param score
	 *            del jugador
	 * @param faseActual
	 *            del jugador
	 */
	public Jugador(String nombre, int[] score, int faseActual) {
		this.nombre = nombre;
		this.score = score;
		this.faseActual = faseActual;
	}

	/**
	 * Genera un Jugador por parametros
	 * 
	 * @param nombre
	 *            del jugador
	 * @param score
	 *            del jugador
	 * @param fase
	 *            del jugador
	 */
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.score = new int[MAX_MJ];
		this.faseActual = 0;
	}

	/**
	 * Metodo que actualiza la fase superada
	 * 
	 * @param fase
	 *            la fase superada
	 */
	public void superaFase(int fase) {
		modificaFase(fase, 1);
	}

	/**
	 * Metodo que pone la fase actual
	 * 
	 * @param fase
	 *            la fase actual
	 */
	public void actualFase(int fase) {
		modificaFase(fase, 2);
	}

	/**
	 * Metodo que modifica la fase <code>fase</code> poniendola a
	 * <code>estado</code>
	 * 
	 * @param fase
	 *            la fase a modificar
	 * @param estado
	 *            el nuevo estado de la fase
	 */
	private void modificaFase(int fase, int estado) {
		switch (fase) {
		case 1:
			fase = estado;
			break;
		case 2:
			fase = estado;
			break;
		case 3:
			fase = estado;
			break;
		case 4:
			fase = estado;
			break;
		}
	}

	// getter & setter
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int[] getScore() {
		return score;
	}

	public void setScore(int[] score) {
		this.score = score;
	}

	/**
	 * Obtiene la puntuacion total del jugador
	 * 
	 * @return la puntuacion total del jugador
	 */
	public int getScoreTotal() {
		int suma = 0;
		for (Integer i : score)
			suma += i;
		return suma;
	}

	public int getScore(int i) {
		return score[i];
	}

	public void setScore(int score, int i) {
		this.score[i] = score;
	}

	@Override
	public String toString() {
		return nombre + " " + score + " " + " " + faseActual;
	}
}
