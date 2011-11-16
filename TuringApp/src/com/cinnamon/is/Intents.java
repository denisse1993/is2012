package com.cinnamon.is;

/**
 * Actividad de utilidad para los distintos Intents
 * 
 * @author Cinnamon Team
 * 
 */
public final class Intents {

	public static final class Action {

		public static final String INTRO = "com.cinnamon.is.INTRO";

		public static final String INTRO2 = "com.cinnamon.is.INTRO2";

		public static final String MAINMENU = "com.cinnamon.is.MAINMENU";

		public static final String INGAME = "com.cinnamon.is.INGAME";

		public static final String MAPA = "com.cinnamon.is.MAPA";

		public static final String MOCHILA = "com.cinnamon.is.MOCHILA";

		public static final String ASCENSORMJ = "com.cinnamon.is.ASCENSORMJ";

		//prueba d juego
		public static final String REINAS = "com.cinnamon.is.REINAS";
		
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

		private Comun() {
		}
	}
}
