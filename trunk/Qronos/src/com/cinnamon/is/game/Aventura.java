//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.T;

/**
 * Clase que representa a una aventura implementa serializable para poder ser
 * pasada en un intent entre activities
 * 
 * @author Cinnamon Team
 * @version 1.2 19.04.2012
 */
public class Aventura implements Serializable {

	/**
	 * ID para la serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Nombre de la aventura
	 */
	private String nombre;// lim a 20

	/**
	 * Password de la aventura
	 */
	private String pass;

	/**
	 * Guarda la clave de minijuego y la pista asociada
	 */
	private ArrayList<T> minijuegos;

	/**
	 * Guarda el numero de mj superados
	 */
	public int superados;

	/**
	 * Crea una aventura con parametros
	 * 
	 * @param nombre
	 *            de la aventura
	 * @param pass
	 *            de la aventura
	 * @param minijuegos
	 *            lista minijuegos
	 * @param superados
	 *            numero de superados
	 */
	public Aventura(final String nombre, final String pass,
			final ArrayList<T> minijuegos, final int superados) {
		this.nombre = nombre;
		this.pass = pass;
		this.minijuegos = minijuegos;
		this.superados = superados;
	}

	/**
	 * Crea una aventura con la lista de mj vacia
	 * 
	 * @param nombre
	 *            de la aventura
	 * @param pass
	 *            de la aventura
	 */
	public Aventura(final String nombre, final String pass) {
		this(nombre, pass, new ArrayList<T>(Props.Comun.MAX_MJ), 0);
	}

	/**
	 * Introduce un minijuego en la aventura
	 * 
	 * @param mj
	 *            la posicion del minijuego
	 * @param pista
	 *            la pista asociada
	 */
	public void addMJ(final int mj) {
		minijuegos.add(new T(mj));
	}

	/**
	 * Borra un minijuego en la aventura
	 * 
	 * @param mj
	 *            el id del minijuego
	 * @return false o true si se ha modificado
	 */
	public boolean delMJ(final int mj) {
		return minijuegos.remove(new T(mj));
	}

	/**
	 * Modifica la pista del mj en base a la posicion en el array
	 * 
	 * @param mj
	 *            la pos del mj en el array
	 * @param pista
	 *            la pista a poner
	 */
	public void modPistaByPos(final int mj, final String pista) {
		minijuegos.get(mj).pista = pista;
	}

	/**
	 * Modifica la pista del mj
	 * 
	 * @param mj
	 *            el cod del mj
	 * @param pista
	 *            la pista a poner
	 */
	public void modPista(final int mj, final String pista) {
		for (T t : minijuegos) {
			if (t.idMj == mj) {
				t.pista = pista;
			}
		}
	}

	/**
	 * @return un iterador sobre las claves
	 */
	public Iterator<T> iterator() {
		return minijuegos.iterator();
	}

	/**
	 * Comprueba si el mj ha sido add
	 * 
	 * @param mj
	 * 
	 * @param la
	 *            key del mj
	 * @return si esta o no
	 */
	public boolean existe(final int mj) {
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			if (it.next().idMj == mj) {
				return true;
			}
		}
		return false;

	}

	/**
	 * @return el numero de minijuegos add
	 */
	public int size() {
		return minijuegos.size();
	}

	/**
	 * @return el numero de pistas add
	 */
	public int sizePista() {
		int i = 0;
		Iterator<T> it = minijuegos.iterator();
		while (it.hasNext()) {
			T t = it.next();
			if (t.pista != null && !t.equals("")) {
				i++;
			}
		}
		return i;
	}

	/**
	 * Crea un array de MAX_MJ y devuelve ese array con valores NULL excepto
	 * donde coincida ese mj, que contendra su id ( a[0] estara el codigo de
	 * mj1)
	 * 
	 * @return un array con los mj en formato integer
	 */
	public Integer[] getMJArrayInteger() {
		Integer[] a = new Integer[Props.Comun.MAX_MJ];
		if (minijuegos.size() == 0) {
			return a;
		}
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			T t = it.next();
			for (int i = 0; i < a.length; i++) {
				if (i == (t.idMj - 1)) {
					a[i] = t.idMj;
					break;
				}
			}
		}
		return a;
	}

	/**
	 * Crea un array de MAX_MJ y devuelve ese array con valores NULL excepto
	 * donde coincida ese mj, que contendra su id ( a[0] estara el codigo de
	 * mj1)
	 * 
	 * @return un array con los mj en formato String
	 */
	public String[] getMJArrayString() {
		String[] a = new String[Props.Comun.MAX_MJ];
		if (minijuegos.size() == 0) {
			return a;
		}
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			T t = it.next();
			for (int i = 0; i < a.length; i++) {
				if (i == (t.idMj - 1)) {
					a[i] = String.valueOf(t.idMj);
					break;
				}
			}
		}
		return a;
	}

	/**
	 * Crea un array de MAX_MJ y devuelve ese array con valores NULL excepto
	 * donde coincida ese mj, que contendra su pista asociada ( a[0] estara la
	 * pista de mj1)
	 * 
	 * @return un array con la pista de cada mj en formato String
	 */
	public String[] getPistasArrayString() {
		String[] a = new String[Props.Comun.MAX_MJ];
		if (minijuegos.size() == 0) {
			return a;
		}
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			T t = it.next();
			for (int i = 0; i < a.length; i++) {
				if (i == (t.idMj - 1)) {
					a[i] = String.valueOf(t.pista);
					break;
				}
			}
		}
		return a;
	}

	// getters and setters

	public ArrayList<T> getMinijuegos() {
		return minijuegos;
	}

	public T getMinijuego(final int index) {
		return minijuegos.get(index);
	}

	public void setMinijuegos(final ArrayList<T> minijuegos) {
		this.minijuegos = minijuegos;
	}

	/**
	 * Rellena minijuegos a partir de 2 arrays de strings
	 * 
	 * @param mjs
	 *            los mj
	 * @param pistas
	 *            las pistas
	 */
	public void setMinijuegos(final String[] mjs, final String[] pistas) {
		for (int i = 0; i < pistas.length; i++) {
			Integer a = Integer.parseInt(mjs[i]);
			if (!pistas[i].equals("") && a != 0) {
				minijuegos.add(new T(a, pistas[i], false));
			}
		}
	}

	/**
	 * Pone el minijuego idMj a superado si la puntuacion que se encuentra en
	 * idMj-1 es mayor que 0, es decir, si el minijuego se ha jugado y se ha
	 * superado, e incrementa el numero de superados
	 * 
	 * @param scoreQuest
	 *            las puntuaciones de un jugador en la aventura
	 */
	public void setSuperadosIfScoreNo0(final int[] scoreQuest) {
		for (T mj : minijuegos) {
			if (scoreQuest[mj.idMj - 1] > 0) {
				mj.setSuperado(true);
				superados++;
			}
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(final String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[");
		for (T mj : minijuegos) {
			sb.append(mj + ",");
		}
		sb.setLength(sb.length() - 1);
		sb.append("]");
		return nombre + " " + pass + "\nMinijuegos: " + sb.toString();
	}
}
