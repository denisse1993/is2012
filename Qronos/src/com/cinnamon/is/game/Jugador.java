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
 * @version 1.5 15.05.2012
 */
public class Jugador implements Serializable {

	/**
	 * Nombre del jugador
	 */
	private String nombre;

	/**
	 * Nombre de la aventura y del host que la ha creado. Es asi para permitir
	 * diferenciar una partida concreta en la tabla pquest, que la conforman un
	 * nombre aventura y el host.
	 * <p>
	 * Formato: aventura;host
	 * </p>
	 */
	private String diferenciador;

	/**
	 * Pass del jugador
	 */
	private String pass;

	/**
	 * Puntuacion del jugador en los distintos minijuegos de arcade
	 */
	private int[] score;

	/**
	 * Puntuacion del jugador en los distintos minijuegos de la aventura
	 */
	private int[] scoreQuest;

	/**
	 * Fase en la que se encuentra el jugador
	 */
	private int faseActual;

	/**
	 * ID para la serializacion
	 */
	private static final long serialVersionUID = 5L;

	/**
	 * Genera un jugador por parametros
	 * 
	 * @param nombre
	 *            del jugador
	 * @param pass
	 *            del jugador
	 * @param score
	 *            del jugador
	 * @param scoreQuest
	 *            del jugador
	 * @param faseActual
	 *            del jugador
	 * @param diferenciador
	 *            nombre del creador de partida
	 */
	public Jugador(String nombre, String pass, int[] score, int[] scoreQuest,
			int faseActual, String diferenciador) {
		this.nombre = nombre;
		this.pass = pass;
		this.score = score;
		this.scoreQuest = scoreQuest;
		this.faseActual = faseActual;
		this.diferenciador = diferenciador;
	}

	/**
	 * Genera un jugador por parametros con scores
	 * 
	 * @param nombre
	 *            del jugador
	 * @param pass
	 *            del jugador
	 * @param score
	 *            del jugador
	 * @param scoreQuest
	 *            del jugador
	 * 
	 */
	public Jugador(String nombre, String pass, int[] score, int[] scoreQuest) {
		this(nombre, pass, score, scoreQuest, 0, null);
	}

	/**
	 * Genera un jugador por parametros con score arcade
	 * 
	 * @param nombre
	 *            del jugador
	 * @param pass
	 *            del jugador
	 * @param score
	 *            del jugador
	 */
	public Jugador(String nombre, String pass, int[] score) {
		this(nombre, pass, score, new int[Props.Comun.MAX_MJ], 0, null);
	}

	/**
	 * Genera un Jugador por parametros
	 * 
	 * @param nombre
	 *            del jugador
	 * @param pass
	 *            del jugador
	 */
	public Jugador(String nombre, String pass) {
		this(nombre, pass, new int[Props.Comun.MAX_MJ],
				new int[Props.Comun.MAX_MJ], 0, null);
	}

	/**
	 * Obtiene la puntuacion total del jugador en arcade
	 * 
	 * @return la puntuacion total del jugador en arcade
	 */
	public int getScoreTotal() {
		int suma = 0;
		for (Integer i : score)
			suma += i;
		return suma;
	}

	/**
	 * Obtiene la puntuacion total del jugador en la aventura
	 * 
	 * @return la puntuacion total del jugador en la aventura
	 */
	public int getScoreQuestTotal() {
		int suma = 0;
		for (Integer i : scoreQuest)
			suma += i;
		return suma;
	}

	/**
	 * Resetea jugador
	 */
	public void reset() {
		for (int i = 0; i < score.length; i++)
			score[i] = scoreQuest[i] = 0;
		faseActual = 0;
	}

	/**
	 * Resetea jugador en arcade
	 */
	public void resetArcade() {
		for (int i = 0; i < score.length; i++)
			score[i] = 0;
	}

	/**
	 * Resetea jugador en aventura
	 */
	public void resetQuest() {
		for (int i = 0; i < score.length; i++)
			scoreQuest[i] = 0;
		faseActual = 0;
	}

	public int getScore(int i) {
		return score[i];
	}

	public void setScore(int score, int i) {
		this.score[i] = score;
	}

	public int getScoreQuest(int i) {
		return scoreQuest[i];
	}

	public void setScoreQuest(int score, int i) {
		this.scoreQuest[i] = score;
	}

	// getter & setter
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDiferenciador() {
		return diferenciador;
	}

	public void setDiferenciador(String host) {
		this.diferenciador = host;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPass() {
		return pass;
	}

	public int[] getScore() {
		return score;
	}

	public void setScore(int[] score) {
		this.score = score;
	}

	public int[] getScoreQuest() {
		return scoreQuest;
	}

	public void setScoreQuest(int[] scoreQuest) {
		this.scoreQuest = scoreQuest;
	}

	public int getFase() {
		return this.faseActual;
	}

	public void setFase(int fase) {
		this.faseActual = fase;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[");
		StringBuffer sbQ = new StringBuffer("[");
		for (int i = 0; i < score.length; i++) {
			sb.append(score[i] + ",");
			sbQ.append(scoreQuest[i] + ",");
		}
		sb.setLength(sb.length() - 1);
		sb.append("]");
		sbQ.setLength(sb.length() - 1);
		sbQ.append("]");
		return nombre + " " + faseActual + " " + diferenciador
				+ "\nScore Arcade: " + sb.toString() + "\nScore Quest: "
				+ sbQ.toString();
	}
}
