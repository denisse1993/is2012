//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
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

		public static final String MAPA = "com.cinnamon.is.game.MAPA";

		public static final String MOCHILA = "com.cinnamon.is.game.MOCHILA";

		public static final String LOGIN = "com.cinnamon.is.game.LOGIN";

		public static final String OPCIONES = "com.cinnamon.is.comun.OPCIONES";

		public static final String ASCENSORMJ = "com.cinnamon.is.minijuegos.ascensor.ASCENSORMJ";
		// prueba d juego
		public static final String REINAS = "com.cinnamon.is.minijuegos.reinas.REINAS";

		// este intent hay que poner lo de android asi por el tema de la
		// libreria zXing
		public static final String SCAN = "com.cinnamon.is.android.SCAN";

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
		 * String con la ruta base del proyecto
		 */
		public static final String BASE = "com.cinnamon.is.";
		/**
		 * String con la ruta base de los minijuegos del proyecto
		 */
		public static final String BASE_MINIJUEGOS = "com.cinnamon.is.minijuegos.";
		/**
		 * String para usar cuando se quiera lanzar desde ingame el scan
		 * directamente
		 */
		public static final String INGAME_SCAN = "ingame_scan";
		/**
		 * String para usar cuando se quiera pasar el jugador entre actividades
		 */
		public static final String JUGADOR = "jugador";

		private Comun() {
		}
	}
}
