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

		// actions para minijuegos
		// TODO ASCENSOR
		public static final String ASCENSORMJ = "com.cinnamon.is.minijuegos.ascensor.ASCENSORMJ";

		// CUADRADO
		public static final String CUADRADOMJINTRO = "com.cinnamon.is.minijuegos.cuadrado.CUADRADOMAGICOMJINTRO";

		public static final String CUADRADOMJ = "com.cinnamon.is.minijuegos.cuadrado.CUADRADOMAGICOMJACTIVITY";

		// TODO PUZZLE
		public static final String PUZZLEMJ = "com.cinnamon.is.minijuegos.puzzle.PuzzleMJ";

		// TODO REINAS
		public static final String REINASMJ = "com.cinnamon.is.minijuegos.reinas.REINASMJ";

		// TODO END
		public static final String ENDMJ = "com.cinnamon.is.minijuegos.end.ENDMJ";

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
		 * Array de String para guardar la ruta de los minijuegos, se usa para
		 * comprobar que el minijuego que se va a lanzar es correcto
		 */
		public static final String[] minijuegos = {
				"cuadrado.CUADRADOMAGICOMJINTRO", "ascensor.ASCENSORMJ",
				"reinas.REINASMJ", "puzzle.PUZZLEMJ", "end.ENDMJ" };
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

		/**
		 * String para usar cuando se quiera pasar el boolean superado del mj al ingame
		 */
		public static final String superado = "superado";
		/**
		 * String para usar cuando se quiera pasar el objeto del mj al ingame
		 */
		public static final String objeto = "objeto";

		private Comun() {
		}
	}
}
