//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Qronos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

/**
 * Clase de utilidad para los distintos Intents
 * 
 * @author Cinnamon Team
 * @version 1.4 25.11.2011
 */
public final class Intents {

	/**
	 * Clase interna con los distintos ACTION de la aplicacion
	 * 
	 * @author Cinnamon Team
	 * 
	 */
	public static final class Action {

		public static final String INTRO = "com.cinnamon.is.presentacion.INTRO";

		public static final String INTRO2 = "com.cinnamon.is.presentacion.INTRO2";

		public static final String MAINMENU = "com.cinnamon.is.game.MAINMENU";

		public static final String INGAME = "com.cinnamon.is.game.INGAME";

		public static final String LOGIN = "com.cinnamon.is.game.LOGIN";

		public static final String OPCIONES = "com.cinnamon.is.comun.OPCIONES";		

		// este intent hay que poner lo de android asi por el tema de la
		// libreria zXing
		public static final String SCAN = "com.cinnamon.is.SCAN";
		
		public static final String ENCODE = "com.google.zxing.client.android.ENCODE";

		private Action() {
		}
	}

	/**
	 * Clase interna con los String genericos
	 * 
	 * @author Cinnamon Team
	 * 
	 */
	public static final class Comun {

		/**
		 * <p>
		 * Array de String para guardar la ruta de los minijuegos, se usa para
		 * comprobar que el minijuego que se va a lanzar es correcto
		 * </p>
		 * <code><pre>
		 * </pre></code>
		 */
		public static final String[] minijuegos = {};
		/**
		 * String con la ruta base del proyecto
		 */
		public static final String BASE = "com.cinnamon.is.";
		/**
		 * String con la ruta base de los minijuegos del proyecto
		 */
		public static final String BASE_MINIJUEGOS = "com.cinnamon.is.minijuegos.";
		/**
		 * String para usar cuando se quiera pasar el jugador entre actividades
		 */
		public static final String JUGADOR = "jugador";

		/**
		 * String para usar cuando se quiera pasar el boolean superado del mj al
		 * ingame
		 */
		public static final String superado = "superado";

		private Comun() {
		}
	}
}
