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

import android.util.Pair;

/**
 * Clase que representa a una aventura implementa serializable para poder ser
 * pasada en un intent entre activities
 * 
 * @author Cinnamon Team
 * @version 1.0 16.04.2012
 */
public class Aventura implements Serializable {

	/**
	 * ID para la serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Nombre de la aventura
	 */
	private String nombre;

	/**
	 * Password de la aventura
	 */
	private String pass;

	/**
	 * Guarda la clave de minijuego y la pista asociada
	 */
	private ArrayList<Pair<Integer, String>> minijuegos;

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
	public Aventura(String nombre, String pass,
			ArrayList<Pair<Integer, String>> minijuegos) {

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
		this(nombre, pass, new ArrayList<Pair<Integer, String>>());
	}

	/**
	 * Introduce un minijuego en la aventura
	 * 
	 * @param mj
	 *            la posicion del minijuego
	 * @param pista
	 *            la pista asociada
	 */
	public void addMJ(int mj, String pista) {
		minijuegos.add(new Pair<Integer, String>(mj, pista));
	}

	/**
	 * Introduce un minijuego en la aventura
	 * 
	 * @param mj
	 *            la pos del minijuego en el array
	 * @param pista
	 *            la pista asociada
	 */
	public void modMJ(int mj, String pista) {
		int saveCodeMJ=minijuegos.get(mj).first;
		delMJ(saveCodeMJ,pista);
		minijuegos.add(mj, new Pair<Integer, String>(saveCodeMJ, pista));
	}

	/**
	 * Borra un minijuego en la aventura
	 * 
	 * @param mj
	 *            la key de minijuego
	 * @return false o true si se ha modificado
	 */
	public boolean delMJ(Integer mj, String pista) {
		return minijuegos.remove(new Pair<Integer, String>(mj, pista));
	}

	/**
	 * @return un iterador sobre las claves
	 */
	public Iterator<Pair<Integer, String>> iterator() {
		return minijuegos.iterator();
	}

	/**
	 * Comprueba si el mj ha sido add
	 * 
	 * @param la
	 *            key del mj
	 * @return si esta o no
	 */
	public boolean existe(Integer mj) {
		Iterator<Pair<Integer, String>> it = iterator();
		while (it.hasNext()) {
			if (it.next().first == mj)
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
		Iterator<Pair<Integer, String>> it = minijuegos.iterator();
		while (it.hasNext())
			if (it.next().second != null)
				i++;
		return i;
	}

	public ArrayList<Pair<Integer, String>> getMinijuegos() {
		return minijuegos;
	}

	public void setMinijuegos(ArrayList<Pair<Integer, String>> minijuegos) {
		this.minijuegos = minijuegos;
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
