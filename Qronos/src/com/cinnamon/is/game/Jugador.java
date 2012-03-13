//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: QRonos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import java.io.Serializable;

import com.cinnamon.is.comun.Props;

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
	private static final long serialVersionUID = 1L;

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
	 * Genera un jugador por parametros
	 * 
	 * @param nombre
	 *            del jugador
	 * @param score
	 *            del jugador
	 */
	public Jugador(String nombre, int[] score) {
		this(nombre, score, 0);
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
		this(nombre, new int[Props.Comun.MAX_MJ], 0);
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
		StringBuffer sb = new StringBuffer("[");
		for (int i = 0; i < score.length; i++)
			sb.append(score[i] + ",");
		sb.setLength(sb.length() - 1);
		sb.append("]");
		return nombre + " " + sb.toString() + " " + faseActual;
	}
}
