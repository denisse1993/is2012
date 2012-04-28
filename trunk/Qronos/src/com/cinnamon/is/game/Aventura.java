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

import android.R.integer;

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
	 * Crea una aventura con parametros
	 * 
	 * @param nombre
	 *            de la aventura
	 * @param pass
	 *            de la aventura
	 * @param minijuegos
	 *            lista minijuegos
	 */
	public Aventura(String nombre, String pass, ArrayList<T> minijuegos) {
		this.nombre = nombre;
		this.pass = pass;
		this.minijuegos = minijuegos;
	}

	/**
	 * Crea una aventura con la lista de mj vacia
	 * 
	 * @param nombre
	 *            de la aventura
	 * @param pass
	 *            de la aventura
	 */
	public Aventura(String nombre, String pass) {
		this(nombre, pass, new ArrayList<T>(Props.Comun.MAX_MJ));
	}

	/**
	 * Introduce un minijuego en la aventura
	 * 
	 * @param mj
	 *            la posicion del minijuego
	 * @param pista
	 *            la pista asociada
	 */
	public void addMJ(int mj) {
		minijuegos.add(new T(mj));
	}

	/**
	 * Borra un minijuego en la aventura
	 * 
	 * @param mj
	 *            el id del minijuego
	 * @return false o true si se ha modificado
	 */
	public boolean delMJ(int mj) {
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
	public void modPistaByPos(int mj, String pista) {
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
	public void modPista(int mj, String pista) {
		for (T t : minijuegos)
			if (t.idMj == mj)
				t.pista = pista;
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
	 * @param la
	 *            key del mj
	 * @return si esta o no
	 */
	public boolean existe(int mj) {
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			if (it.next().idMj == mj)
				return true;
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
			if (t.pista != null && !t.equals(""))
				i++;
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
		if (minijuegos.size() == 0)
			return a;
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			T t = it.next();
			for (int i = 0; i < a.length; i++)
				if (i == (t.idMj - 1)) {
					a[i] = t.idMj;
					break;
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
		if (minijuegos.size() == 0)
			return a;
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			T t = it.next();
			for (int i = 0; i < a.length; i++)
				if (i == (t.idMj - 1)) {
					a[i] = String.valueOf(t.idMj);
					break;
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
		if (minijuegos.size() == 0)
			return a;
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			T t = it.next();
			for (int i = 0; i < a.length; i++)
				if (i == (t.idMj - 1)) {
					a[i] = String.valueOf(t.pista);
					break;
				}
		}
		return a;
	}

	// /**
	// * Guarda la clave de minijuego y la pista asociada
	// */
	// private HashMap<Integer, String> minijuegos;
	// /**
	// * Crea una aventura con parametros
	// *
	// * @param nombre
	// * de la aventura
	// * @param pass
	// * de la aventura
	// * @param minijuegos
	// * lista minijuegos
	// */
	// public Aventura(String nombre, String pass,
	// HashMap<Integer, String> minijuegos) {
	//
	// this.nombre = nombre;
	// this.pass = pass;
	// this.minijuegos = minijuegos;
	// }
	//
	// /**
	// * Crea una aventura con la lista de mj vacia
	// *
	// * @param nombre
	// * de la aventura
	// * @param pass
	// * de la aventura
	// */
	// public Aventura(String nombre, String pass) {
	// this(nombre, pass, new HashMap<Integer, String>());
	// }
	//
	// /**
	// * Introduce un minijuego en la aventura
	// *
	// * @param mj
	// * la key de minijuego
	// * @param pista
	// * la pista asociada
	// * @return null si no existe o la pista anterior
	// */
	// public String addMJ(int mj, String pista) {
	// return minijuegos.put(mj, pista);
	// }
	//
	// /**
	// * Borra un minijuego en la aventura
	// *
	// * @param mj
	// * la key de minijuego
	// * @return null si no existe o la pista asociada
	// */
	// public String delMJ(Integer mj) {
	// return minijuegos.remove(mj);
	// }
	//
	// /**
	// * @return un iterador sobre las claves
	// */
	// public Iterator<Integer> keys() {
	// return minijuegos.keySet().iterator();
	// }
	//
	// /**
	// * Comprueba si el mj ha sido add
	// *
	// * @param la
	// * key del mj
	// * @return si esta o no
	// */
	// public boolean existe(Integer mj) {
	// return minijuegos.containsKey(mj);
	// }
	//
	// /**
	// * @return el numero de minijuegos add
	// */
	// public int size() {
	// return minijuegos.size();
	// }
	//
	// /**
	// * @return el numero de pistas add
	// */
	// public int sizePista() {
	// int i = 0;
	// Iterator<String> it = minijuegos.values().iterator();
	// while (it.hasNext())
	// if (it.next() != null)
	// i++;
	// return i;
	// }
	//
	// public HashMap<Integer, String> getMinijuegos() {
	// return minijuegos;
	// }
	//
	// public void setMinijuegos(HashMap<Integer, String> minijuegos) {
	// this.minijuegos = minijuegos;
	// }

	// getters and setters

	public ArrayList<T> getMinijuegos() {
		return minijuegos;
	}

	public T getMinijuego(int index) {
		return minijuegos.get(index);
	}



	public void setMinijuegos(ArrayList<T> minijuegos) {
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
	public void setMinijuegos(String[] mjs, String[] pistas) {
		for (int i = 0; i < pistas.length; i++) {
			Integer a = Integer.parseInt(mjs[i]);
			if (!pistas[i].equals("") && a != 0)
				minijuegos.add(new T(a, pistas[i], false));
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
