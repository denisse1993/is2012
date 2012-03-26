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

		public static final String RANKING = "com.cinnamon.is.game.RANKING";

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
		public static final String MJ4G = "com.cinnamon.is.minijuegos.MJ4.Game";
		public static final String MJ4W = "com.cinnamon.is.minijuegos.MJ4.Win";
		// public static final String MJ4st =
		// "com.cinnamon.is.minijuegos.MJ1.Start";
		public static final String MJ5 = "com.cinnamon.is.minijuegos.MJ5";
		public static final String MJ6 = "com.cinnamon.is.minijuegos.MJ6";
		public static final String MJ7 = "com.cinnamon.is.minijuegos.MJ7";
		public static final String MJ8 = "com.cinnamon.is.minijuegos.MJ8";
		public static final String MJ9 = "com.cinnamon.is.minijuegos.MJ9";
		public static final String MJ10 = "com.cinnamon.is.minijuegos.MJ10";
		public static final String MJ11 = "com.cinnamon.is.minijuegos.MJ11";
		public static final String MJ12 = "com.cinnamon.is.minijuegos.MJ12";

		public static final String MJ[] = { MJ1, MJ2, MJ3, MJ4, MJ5, MJ6, MJ7,
				MJ8, MJ9, MJ10, MJ11, MJ12 };

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
		public static final int cmj7 = 7;
		public static final int cmj8 = 8;
		public static final int cmj9 = 9;
		public static final int cmj10 = 10;
		public static final int cmj11 = 11;
		public static final int cmj12 = 12;

		/**
		 * Minijuegos maximos
		 */
		public static final int MAX_MJ = 12;

		/**
		 * Minijuegos por pantalla maximas de minijuegos
		 */
		public static final int MAX_MJ_P = 6;

		/**
		 * Maximo de pantallas de minijuegos
		 */
		public static final int MAX_P = 2;

		/**
		 * Array de ids para ImageButtons de minijuegos en arcade
		 */
		public static final int[] iDiBmj = { R.id.iBmj1, R.id.iBmj2,
				R.id.iBmj3, R.id.iBmj4, R.id.iBmj5, R.id.iBmj6 };

		/**
		 * Array de ids drawables para ImageViews de minijuegos en arcade,
		 * tamaño 12
		 */
		public static final int[] iDiVmj = { R.drawable.ibmj1,
				R.drawable.ibmj2, R.drawable.ibmj3, R.drawable.ibmj3,
				R.drawable.ibmj0, R.drawable.ibmj0, R.drawable.ibmj3,
				R.drawable.ibmj1, R.drawable.ibmj0, R.drawable.ibmj0,
				R.drawable.ibmj0, R.drawable.ibmj0 };

		/**
		 * Array de ids drawables para ImageViews de estrellas en arcade
		 */
		public static final int[] iDiVstar = { R.drawable.starcero,
				R.drawable.staruna, R.drawable.stardos, R.drawable.startres,R.drawable.starnull};

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
				return iDiVstar[3];
			else if (sc > STAR2)
				return iDiVstar[2];
			else if (sc > STAR1)
				return iDiVstar[1];
			else
				return iDiVstar[0];
		}

		/**
		 * <p>
		 * Array de String para guardar la ruta de los minijuegos, se usa para
		 * comprobar que el minijuego que se va a lanzar es correcto
		 * </p>
		 * <code><pre>
		 * </pre></code>
		 */
		public static final String[] mjRuta = { "MJ1", "MJ2", "MJ3", "MJ4",
				"MJ5", "MJ6", "MJ7", "MJ8", "MJ9", "MJ10", "MJ11", "MJ12" };
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
	 * Clase interna con los strings genericos
	 * 
	 * @author Cinnamon Team
	 * 
	 */
	public static final class Strings {

		/**
		 * Array con nombres de los minijuegos
		 */
		public static final String[] mjNames = { "Marcianos", "Topos", "QR",
				"CadenaQR", "Name5", "Name6", "Name7", "Name8", "Name9",
				"Name10", "Name11", "Name12" };
		/**
		 * Array con explicaciones de los minijuegos
		 */
		public static final String[] mjExps = {
				"Elimina a todos los marcianos que puedas antes de morir",
				"Elimina cuantos topos puedas", "Exp3",
				"Lee 5 QR en cadena lo más rápido que puedas", "Exp5", "Exp6",
				"Exp7", "Exp8", "Exp9", "Exp10", "Exp11", "Exp12" };

		/**
		 * Info de subida al servidor
		 */
		public static final String upSc = "¿Deseas subir tus puntuaciones al servidor online?";
		/**
		 * Informacion de actividades
		 */
		public static final String iArcade = "Toca en la imagen para lanzar el mj asociado.\nToca el icono de la flecha para subir tus puntuaciones al servidor\nToca el icono de estadisticas para ver las estadisticas";

		private Strings() {
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

		private Enum() {
		}
	}
}
