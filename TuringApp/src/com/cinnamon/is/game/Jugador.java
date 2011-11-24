package com.cinnamon.is.game;

import java.io.Serializable;

/**
 * Clase que representa a un jugador en el juego. implementa serializable para
 * poder ser pasada en un intent entre activities
 * 
 * @author JUser
 * 
 */
public class Jugador implements Serializable {

	private String nombre;
	private int score;
	private int fase1;
	private int fase2;
	private int fase3;
	private int fase4;
	private int hoja;
	private int mochila;

	/**
	 * ID para la serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Genera un jugador por parametros
	 * 
	 * @param nombre
	 * @param score
	 * @param fase1
	 * @param fase2
	 * @param fase3
	 * @param fase4
	 * @param hoja
	 * @param mochila
	 */
	public Jugador(String nombre, int score, int fase1, int fase2, int fase3,
			int fase4, int hoja, int mochila) {
		this.nombre = nombre;
		this.score = score;
		this.fase1 = fase1;
		this.fase2 = fase2;
		this.fase3 = fase3;
		this.fase4 = fase4;
		this.hoja = hoja;
		this.mochila = mochila;
	}

	/**
	 * Genera un jugador por defecto con un nombre
	 */
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.score = 0;
		this.fase1 = 0;
		this.fase2 = 0;
		this.fase3 = 0;
		this.fase4 = 0;
		this.hoja = 0;
		this.mochila = 0;
	}

	public Jugador() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getHoja() {
		return hoja;
	}

	public void setHoja(int hoja) {
		this.hoja = hoja;
	}

	public int getFase1() {
		return fase1;
	}

	public void setFase1(int fase1) {
		this.fase1 = fase1;
	}

	public int getFase2() {
		return fase2;
	}

	public void setFase2(int fase2) {
		this.fase2 = fase2;
	}

	public int getFase3() {
		return fase3;
	}

	public void setFase3(int fase3) {
		this.fase3 = fase3;
	}

	public int getFase4() {
		return fase4;
	}

	public void setFase4(int fase4) {
		this.fase4 = fase4;
	}

	public int getMochila() {
		return mochila;
	}

	public void setMochila(int mochila) {
		this.mochila = mochila;
	}

}
