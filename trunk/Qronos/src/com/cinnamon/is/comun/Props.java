//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Qronos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import com.cinnamon.is.R;

/**
 * Clase de utilidad para los distintas propiedades
 * 
 * @author Cinnamon Team
 * @version 1.4 25.11.2011
 */
public final class Props {

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

		public static final String ARCADE = "com.cinnamon.is.game.ARCADE";

		public static final String AVENTURA = "com.cinnamon.is.game.AVENTURA";

		public static final String LOGIN = "com.cinnamon.is.game.LOGIN";

		public static final String OPCIONES = "com.cinnamon.is.comun.OPCIONES";

		// intents de zXing
		public static final String SCAN = "com.cinnamon.is.SCAN";

		public static final String ENCODE = "com.cinnamon.is.ENCODE";

		// MINIJUEGOS
		// mj1
		public static final String MJ1 = "com.cinnamon.is.minijuegos.MJ1";
		public static final String MJ1st = "com.cinnamon.is.minijuegos.MJ1.Start";
		public static final String MJ2 = "com.cinnamon.is.minijuegos.MJ2";
		public static final String MJ3 = "com.cinnamon.is.minijuegos.MJ3";
		public static final String MJ4 = "com.cinnamon.is.minijuegos.MJ4";
		public static final String MJ5 = "com.cinnamon.is.minijuegos.MJ5";
		public static final String MJ6 = "com.cinnamon.is.minijuegos.MJ6";
		public static final String MJ[] = { MJ1, MJ2, MJ3, MJ4, MJ5, MJ6 };

		private Action() {
		}
	}

	/**
	 * Clase interna con las vars genericas
	 * 
	 * @author Cinnamon Team
	 * 
	 */
	public static final class Comun {

		public static final int cmj1 = 1;
		public static final int cmj2 = 2;
		public static final int cmj3 = 3;
		public static final int cmj4 = 4;
		public static final int cmj5 = 5;
		public static final int cmj6 = 6;
		/**
		 * Minijuegos maximos
		 */
		public static final int MAX_MJ = 6;

		/**
		 * Array de ids para ImageButtons de minijuegos en arcade
		 */
		public static final int[] iDiBmj = { R.id.iBmj1, R.id.iBmj2,
				R.id.iBmj3, R.id.iBmj4, R.id.iBmj5, R.id.iBmj6 };

		/**
		 * Array de ids drawables para ImageViews de minijuegos en arcade
		 */
		public static final int[] iDiVmj = {};

		/**
		 * Array de ids drawables para ImageViews de estrellas en arcade
		 */
		public static final int[] iDiVstar = {};

		/**
		 * Array de ids para ImageViews de scores de mj en arcade
		 */
		public static final int[] iDiVsC = { R.id.iVsc1, R.id.iVsc2,
				R.id.iVsc3, R.id.iVsc4, R.id.iVsc5, R.id.iVsc6 };

		/**
		 * Limites estrellas
		 */
		public static final int STAR1 = 200;
		public static final int STAR2 = 500;
		public static final int STAR3 = 800;

		/**
		 * Obtiene la estrella correspondiente
		 * 
		 * @param sc
		 *            la puntuacion a comparar
		 * @return la estrella a usar
		 */
		public static int getStar(int sc) {
			if (sc > STAR3)
				return iDiVstar[2];
			else if (sc > STAR2)
				return iDiVstar[1];
			else
				return iDiVstar[0];
		}

		/**
		 * Array con explicaciones de los minijuegos
		 */
		public static final String[] expmjs = { "Mata tos lo ke puedas", "",
				"", "", "", "" };
		/**
		 * <p>
		 * Array de String para guardar la ruta de los minijuegos, se usa para
		 * comprobar que el minijuego que se va a lanzar es correcto
		 * </p>
		 * <code><pre>
		 * </pre></code>
		 */
		public static final String[] minijuegos = { "Marcianos", "", "", "",
				"", "" };
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
		public static final String JUGADOR = "j";

		/**
		 * String para usar cuando se quiera pasar el puntuacion entre
		 * actividades
		 */
		public static final String SCORE = "S";

		/**
		 * String para usar cuando se quiera pasar el boolean superado del mj al
		 * ingame
		 */
		public static final String SUPERADO = "s";

		private Comun() {
		}
	}

	/**
	 * Clase interna con los enum genericos
	 * 
	 * @author Cinnamon Team
	 * 
	 */
	public static final class Enum {

		public enum Tabla {
			parcade {
				@Override
				public String toString() {
					return "parcade";
				}
			},
			pquest {
				@Override
				public String toString() {
					return "pquest";
				}
			},
			quest {
				@Override
				public String toString() {
					return "quest";
				}
			},
		}
	}
}
