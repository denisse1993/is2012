package com.cinnamon.is.comun;

/**
 * Actividad de utilidad para los distintos Intents
 * 
 * @author Cinnamon Team
 * 
 */
public final class Intents {

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

	public static final class Comun {

		public static final String BASE = "com.cinnamon.is.";
		// para usar cuando se quiera lanzar desde ingame el scan directamente
		public static final String INGAME_SCAN = "ingame_scan";
		// para usar cuando se quiera pasar el jugador entre actividades
		public static final String JUGADOR = "jugador";

		private Comun() {
		}
	}
}
