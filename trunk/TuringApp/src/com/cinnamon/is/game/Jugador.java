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
 * @version 1.3 12.12.2011
 */
public class Jugador implements Serializable {

	/**
	 * Nombre del jugador
	 */
	private String nombre;
	/**
	 * Puntuacion del jugador
	 */
	private int score;
	/**
	 * Posicion del jugador segun la fase
	 */
	private int fase1, fase2, fase3, fase4;
	/**
	 * Pagina en la que se encuentra el jugador
	 */
	private int hoja;

	/**
	 * Mochila del jugador
	 * <p>
	 * Sera array de 4 casillas, cuyo indice representa el papel 1, el papel 2,
	 * el papel 3 y la maquina
	 * </p>
	 * 
	 * El acceso a mochila[i] nos dira si tiene ese objeto o no en funcion de:
	 * <code><pre>
	 * Valor 0 - no tiene el objeto i
	 * Valor 1 - si tiene el objeto i
	 * </pre></code>
	 * <p>
	 */
	private int[] mochila;

	/**
	 * ID para la serializacion
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * Genera un jugador por parametros
	 * 
	 * @param nombre
	 *            del jugador
	 * @param score
	 *            del jugador
	 * @param fase1
	 * @param fase2
	 * @param fase3
	 * @param fase4
	 * @param hoja
	 *            actual del jugador
	 * @param mochila
	 *            del jugador representada como un array
	 */
	public Jugador(String nombre, int score, int fase1, int fase2, int fase3,
			int fase4, int hoja, int[] mochila) {
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
	 * Genera un Jugador por parametros
	 * 
	 * @param nombre
	 *            del jugador
	 * @param score
	 *            del jugador
	 * @param fase1
	 * @param fase2
	 * @param fase3
	 * @param fase4
	 * @param hoja
	 *            actual del jugador
	 * @param mochila
	 *            del jugador representada como un int
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
		intToMochila(mochila);
	}

	/**
	 * Genera un jugador por defecto con un nombre
	 */
	public Jugador(String nombre) {
		this(nombre, 0, 0, 0, 0, 0, 0, new int[] { 0, 0, 0, 0 });
	}

	// ARRAY DE 4 CASILLAS PARA GUARDAR LA INFO DEL OBJETO K TIENES Y K SE LE
	// PASE EL NUMERO DE LA BD Y EN FUNCION DE ESO HACES EL TRATAMIENTO DEL
	// NUEVO ESTADO DE OBJETO
	/**
	 * Metodo que devuelve la mochila en formato BD
	 * 
	 * @see DbAdapter
	 * @return el entero en formato BD
	 */
	public int mochilaToInt() {
		if (mochila[0] == 0 && mochila[1] == 0 && mochila[2] == 0
				&& mochila[3] == 0)
			return 0;
		else if (mochila[0] == 1 && mochila[1] == 0 && mochila[2] == 0
				&& mochila[3] == 0)
			return 1;
		else if (mochila[0] == 0 && mochila[1] == 1 && mochila[2] == 0
				&& mochila[3] == 0)
			return 2;
		else if (mochila[0] == 0 && mochila[1] == 0 && mochila[2] == 1
				&& mochila[3] == 0)
			return 3;
		else if (mochila[0] == 0 && mochila[1] == 0 && mochila[2] == 0
				&& mochila[3] == 1)
			return 4;
		else if (mochila[0] == 1 && mochila[1] == 1 && mochila[2] == 0
				&& mochila[3] == 0)
			return 5;
		else if (mochila[0] == 1 && mochila[1] == 0 && mochila[2] == 1
				&& mochila[3] == 0)
			return 6;
		else if (mochila[0] == 1 && mochila[1] == 0 && mochila[2] == 0
				&& mochila[3] == 1)
			return 7;
		else if (mochila[0] == 0 && mochila[1] == 1 && mochila[2] == 1
				&& mochila[3] == 0)
			return 8;
		else if (mochila[0] == 0 && mochila[1] == 1 && mochila[2] == 0
				&& mochila[3] == 1)
			return 9;
		else if (mochila[0] == 0 && mochila[1] == 0 && mochila[2] == 1
				&& mochila[3] == 1)
			return 10;
		else if (mochila[0] == 1 && mochila[1] == 1 && mochila[2] == 1
				&& mochila[3] == 0)
			return 11;
		else if (mochila[0] == 1 && mochila[1] == 1 && mochila[2] == 0
				&& mochila[3] == 1)
			return 12;
		else if (mochila[0] == 1 && mochila[1] == 0 && mochila[2] == 1
				&& mochila[3] == 1)
			return 13;
		else if (mochila[0] == 0 && mochila[1] == 1 && mochila[2] == 1
				&& mochila[3] == 1)
			return 14;
		else if (mochila[0] == 1 && mochila[1] == 1 && mochila[2] == 1
				&& mochila[3] == 1)
			return 15;
		// no deberia llegar aqui si mochila esta correcta
		return 0;
	}

	/**
	 * Añade el objeto en la posicion pos a la mochila
	 * 
	 * @see Jugador.mochila
	 * @param objeto
	 *            el objeto a añadir
	 */
	public void addObjeto(int objeto) {
		mochila[objeto - 1] = 1;
	}

	/**
	 * Elimina el objeto en la posicion pos de la mochila
	 * 
	 * @see Jugador.mochila
	 * @param objeto
	 *            el objeto a eliminar
	 */
	public void delObjeto(int objeto) {
		mochila[objeto - 1] = 0;
	}

	/**
	 * Metodo que transforma un entero en formato BD en el array de mochila,
	 * modifica mochila
	 * 
	 * @param keyMochila
	 *            el entero en formato BD
	 * @see DbAdapter
	 */
	private void intToMochila(int keyMochila) {

		// int[] a = { 0, 0, 0, 0 };
		switch (keyMochila) {
		case 0:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = 0;
			break;
		case 1:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 1 ? 1 : 0);
			break;
		case 2:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 2 ? 1 : 0);
			break;
		case 3:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 3 ? 1 : 0);
			break;
		case 4:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 4 ? 1 : 0);
			break;
		case 5:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 1 || i + 1 == 2 ? 1 : 0);
			break;
		case 6:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 1 || i + 1 == 3 ? 1 : 0);
			break;
		case 7:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 1 || i + 1 == 4 ? 1 : 0);
			break;
		case 8:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 2 || i + 1 == 4 ? 1 : 0);
			break;
		case 9:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 3 || i + 1 == 4 ? 1 : 0);
			break;
		case 10:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 3 || i + 1 == 4 ? 1 : 0);
			break;
		case 11:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 1 || i + 1 == 2 || i + 1 == 3 ? 1 : 0);
			break;
		case 12:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 1 || i + 1 == 2 || i + 1 == 4 ? 1 : 0);
			break;
		case 13:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 1 || i + 1 == 3 || i + 1 == 4 ? 1 : 0);
			break;
		case 14:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = (i + 1 == 2 || i + 1 == 3 || i + 1 == 4 ? 1 : 0);
			break;
		case 15:
			for (int i = 0; i < mochila.length; i++)
				mochila[i] = 1;
			break;
		}
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
		modificaFase(fase,2);
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
			fase1 = estado;
			break;
		case 2:
			fase2 = estado;
			break;
		case 3:
			fase3 = estado;
			break;
		case 4:
			fase4 = estado;
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

	public int[] getMochila() {
		return mochila;
	}

	public void setMochila(int[] mochila) {
		this.mochila = mochila;
	}

	public void setMochila(int mochila) {
		intToMochila(mochila);
	}

	@Override
	public String toString() {
		return nombre + " " + score + " " + fase1 + " " + fase2 + " " + fase3
				+ " " + fase4 + " " + mochila;
	}
}
