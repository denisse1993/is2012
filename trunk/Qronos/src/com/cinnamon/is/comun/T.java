//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: QRonos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import java.io.Serializable;

/**
 * Clase de utilidad para aventura
 * 
 * @author Cinnamon Team
 * @version 1.0 18.04.2012
 */
public class T implements Serializable {

	/**
	 * ID para la serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Codigo de MJ
	 */
	public int idMj;
	public String pista;

	/**
	 * Minijuego asociado se ha superado o no
	 */
	public boolean superado;

	/**
	 * Constructor solo con el id
	 * 
	 * @param idMj
	 *            el id de mj
	 */
	public T(int idMj) {
		this.idMj = idMj;
		this.pista = null;
		this.superado = false;
	}

	/**
	 * Constructor completo
	 * 
	 * @param idMj
	 * @param pista
	 * @param superado
	 */
	public T(int idMj, String pista, boolean superado) {
		this.idMj = idMj;
		this.pista = pista;
		this.superado = superado;
	}

	@Override
	public boolean equals(Object o) {
		T t = (T) o;
		if (!(t instanceof T))
			return false;
		// seran iguales si tienen la misma pista
		if (t.idMj != this.idMj)
			return false;

		return true;
	}

	@Override
	public String toString() {
		return idMj + " " + pista + " " + (superado ? "si" : "no");
	}
}
