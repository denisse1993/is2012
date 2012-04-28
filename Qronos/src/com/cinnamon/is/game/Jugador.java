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
 * @version 1.3 26.04.2012
 */
public class Jugador implements Serializable {

	/**
	 * Nombre del jugador
	 */
	private String nombre;

	/**
	 * Nombre de la aventura a la que esta jugando el jugador
	 */
	private String aventura;

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
	private static final long serialVersionUID = 3L;

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
	 * @param aventura
	 *            nombre de la aventura
	 */
	public Jugador(String nombre, String pass, int[] score, int[] scoreQuest,
			int faseActual, String aventura) {
		this.nombre = nombre;
		this.pass = pass;
		this.score = score;
		this.scoreQuest = scoreQuest;
		this.faseActual = faseActual;
		this.aventura = aventura;
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

	public String getAventura() {
		return aventura;
	}

	public void setAventura(String aventura) {
		this.aventura = aventura;
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

	public int getFase(){
		return this.faseActual;
	}
	
	public void setFase(int fase){
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
		return nombre + " " + faseActual + "\nScore Arcade: " + sb.toString()
				+ "\nScore Quest: " + sbQ.toString();
	}
}
