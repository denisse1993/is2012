package com.cinnamon.is.comun;

/**
 * Toda aquella clase que haga uso de lanzadores de dialogos de espera
 * 
 * @author Cinnamon Team
 * @version 1.0 26.12.2012
 */
public interface Inet {

	/**
	 * @return el lanzador de la actividad
	 */
	public Launch l();

	/**
	 * @return la conexion de la actividad
	 */
	public Conexion c();
}
