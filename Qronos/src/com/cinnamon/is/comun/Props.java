//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Qronos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import android.app.Activity;

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

		public static final String SELECMJ = "com.cinnamon.is.game.SELECMJ";

		public static final String SELECPISTA = "com.cinnamon.is.game.SELECPISTA";

		public static final String ELIGEMODOAVENTURA = "com.cinnamon.is.game.ELIGEMODOAVENTURA";

		public static final String LANZARAVENTURA = "com.cinnamon.is.game.LANZARAVENTURA";

		public static final String INGAMEAVENTURA = "com.cinnamon.is.game.INGAMEAVENTURA";

		public static final String INGAMEHOST = "com.cinnamon.is.game.INGAMEHOST";

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
		public static final String MJ7 = "com.cinnamon.is.minijuegos.MJ7";
		public static final String MJ7W = "com.cinnamon.is.minijuegos.MJ7W";
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

		public static final int CMJ[] = { cmj1, cmj2, cmj3, cmj4, cmj5, cmj6,
				cmj7, cmj8, cmj9, cmj10, cmj11, cmj12 };

		/**
		 * Controla si se debe esperar para seguir con la aplicacion por si
		 * necesitas informacion de un hilo que todavia no ha finalizado
		 */
		public static boolean ESPERA = false;
		/**
		 * Controla si esta habilitado el modo online, por defecto on
		 */
		public static boolean ONLINE = true;

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
		 * Array de ids para ImageButtons de minijuegos
		 */
		public static final int[] iDiBmj = { R.id.iBmj1, R.id.iBmj2,
				R.id.iBmj3, R.id.iBmj4, R.id.iBmj5, R.id.iBmj6 };

		/**
		 * Array de ids para EditText de minijuegos
		 */
		public static final int[] iDeTmj = { R.id.eTpista1, R.id.eTpista2,
				R.id.eTpista3, R.id.eTpista4, R.id.eTpista5, R.id.eTpista6 };

		/**
		 * Array de ids para ImageViews de scores de mj en arcade
		 */
		public static final int[] iDiVsC = { R.id.iVsc1, R.id.iVsc2,
				R.id.iVsc3, R.id.iVsc4, R.id.iVsc5, R.id.iVsc6 };

		/**
		 * Array de ids drawables para ImageViews de minijuegos. Tamaño 12
		 */
		public static final int[] iDiVmj = { R.drawable.ibmj1,
				R.drawable.ibmj2, R.drawable.ibmj3, R.drawable.ibmj4,
				R.drawable.ibmj5, R.drawable.ibmj6, R.drawable.ibmj7,
				R.drawable.ibmj8, R.drawable.ibmj0, R.drawable.ibmj0,
				R.drawable.ibmj0, R.drawable.ibmj0 };

		/**
		 * Array de ids drawables para ImageViews de estrellas en arcade
		 */
		public static final int[] iDiVstar = { R.drawable.starcero,
				R.drawable.staruna, R.drawable.stardos, R.drawable.startres,
				R.drawable.starnull };

		/**
		 * Array de ids drawables para ImageViews de selecMJ
		 */
		public static final int[] iDiVselec = { R.drawable.noselec,
				R.drawable.selec, R.drawable.starnull };

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
		public static int getStar(final int sc) {
			if (sc > STAR3) {
				return iDiVstar[3];
			} else if (sc > STAR2) {
				return iDiVstar[2];
			} else if (sc > STAR1) {
				return iDiVstar[1];
			} else {
				return iDiVstar[0];
			}
		}

		/**
		 * Comprueba si la imagen de minijuego es la vacia, en tal caso el boton
		 * no esta habilitado
		 * 
		 * @param iDimg
		 *            el id de la imagen que tiene ese mj
		 * @return true si esta habilitado false en caso contrario
		 */
		public static boolean bHabilitado(final int iDimg) {
			return R.drawable.ibmj0 == iDimg ? false : true;
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
		 * String para usar cuando se quiera pasar la aventura entre actividades
		 */
		public static final String AVENTURA = "aventura";

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

		/**
		 * Para pasar elementos json
		 */
		public static final String JSON = "json";

		/**
		 * Para indicar que viene del arcade, contiene la informacion del
		 * jugador
		 */
		public static final String ARCADE_DATA = "arcade";

		/**
		 * Para abrir selecPista en modo lectura
		 */
		public static final String READ = "read";
		/**
		 * Para elegir el modo en los dialogos
		 */
		public static final String MODO = "modo";

		/**
		 * Usar al lanzar opciones, pasandole el ACTION que usara para volver, a
		 * la actividad que lo llamo
		 */
		public static String RETORNO = "retorno";

		/**
		 * Para guardar una referencia a una actividad concreta
		 */
		public static Activity ACTIVIDAD = null;

		/**
		 * Para obtener el estado del sonido
		 */
		public static final String CB_SONIDO = "cbSonido";
		/**
		 * String para usar cuando se quiera pasar la aventura entre actividades
		 */
		public static final String CAMARABOMBA = "camara_bomba";
		/**
		 * String para cuando se quiera usar tokens
		 */
		public static final String TOKEN = "token";

		/**
		 * Constantes para limitar longitudes de strings
		 */
		public static final int MAX_CHAR_NICK = 11;
		public static final int MAX_CHAR_PASS = 11; // se encrypta md5 en el
													// server long = 32
		public static final int MAX_CHAR_PISTA = 30;
		public static final int MAX_CHAR_NAVENTURA = 20;
		public static final int MAX_CHAR_PAVENTURA = 11;

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
		public static final String[] mjNames = { "Marcianos", "Topos",
				"ZigZagQR", "CadenaQR", "EncuentraQR", "Bomba", "LetrasQR",
				"Slide Puzzle", "Name9", "Name10", "Name11", "Name12" };
		/**
		 * Array con explicaciones de los minijuegos
		 */
		public static final String[] mjExps = {
				"Elimina a todos los marcianos que puedas antes de morir",
				"Elimina cuantos topos puedas",
				"Lee los 10 códigos QR colocados en Zig-Zag lo más rápido que puedas",
				"Lee 5 QR en cadena lo más rápido que puedas",
				"Encuentra el código QR lo más rápido que puedas",
				"Trata de no hacer explotar a la bomba!!!",
				"Lee los códigos QR para encontrar las letras y formar con ellas una palabra",
				"Coloca todas las piezas en orden para formar el puzzle",
				"Exp9", "Exp10", "Exp11", "Exp12" };

		/**
		 * Info de subida al servidor
		 */
		public static final String upSc = "¿Deseas subir tus puntuaciones al servidor online?";
		/**
		 * Info estado de la conexion
		 */
		public static final String IONLINE = "Dispones de cobertura de red Movil/WIFI";
		public static final String IOFFLINE = "No dispones de cobertura de red Movil/WIFI";
		/**
		 * Informacion de actividades
		 */
		public static final String iMain = "Arcade: Juega en modo un jugador.\nAventura: Crea o œnete a una aventura";
		public static final String iArcade = "Toca en la imagen para lanzar el mj asociado.\nToca el icono de la flecha para subir tus puntuaciones al servidor.\nToca el icono de estadisticas para ver las estadisticas.\nToca los iconos de las flechas para cambiar la lista de MJ.";
		public static final String iSelecMJ = "Toca en la imagen para agregar el mj asociado a la lista de MJ.\nToca los iconos de las flechas para cambiar la lista de MJ.";
		public static final String iSelecPISTA = "Escribe la pista asociada a cada MJ.\nPara confirmar la pista, pincha en la imagen del MJ.\nToca los iconos de las flechas para cambiar la lista de MJ";
		public static final String iHost = "Comparte el código QR generado con los jugadores de la Aventura";
		public static final String iInGame = "Encuentra el c—digo QR asociado a la pista para lanzar el minijuego";
		public static final String iElige = "Crear: Establece una nueva aventura|"
				+ "\nEditar: cambia alguna aventura ya creada"
				+ "\nUsar: utiliza una aventura existente"
				+ "\nUnirse: unete a una aventura y empieza a jugar!";

		/**
		 * Para uso en avisos
		 */
		public static final String LOGIN_OK = "Logueado con éxito";
		public static final String MODO_OFFLINE = "Modo offline activado";
		public static final String ERROR_INET = "No se pudo conectar con el servidor. Revisa tu conexión.";
		public static final String USER_PASS_MAL = "User o pass incorrecta en BD local";
		public static final String USER_INET_NO_EXISTE = "El usuario introducido no existe";
		public static final String USER_YA_EXISTE = "Usuario existente local!";
		public static final String USER_YA_EXISTE_ONLINE = "Usuario existente online!";
		public static final String USER_CREADO_ONLINE = "Registro correcto!";
		public static final String USER_CREADO = "Usuario creado en local. Menu abierto";
		public static final String CAMPOS_VACIOS = "Alguno de los campos estan vacios";
		public static final String PASS_ERROR = "Password incorrecta";
		public static final String PISTA_MOD = "Pista modificada!";
		public static final String PISTAS_COMPLETO = "Asignadas todas las pistas";
		public static final String SCORE_SUBIDA = "Puntuacion subida al servidor";
		public static final String SCORE_SUBIDA_ERROR = "Problema al subir la puntuacion al servidor";
		public static final String AVENTURA_SUBIDA = "Aventura subida al servidor";
		public static final String AVENTURA_SUBIDA_ERROR = "Problema al subir la aventura al servidor";
		public static final String AVENTURA_NO_EXISTE = "No existe esa aventura!";
		public static final String VER_RANKING_ERROR = "Error de conexion viendo el ranking";
		public static final String FORMATO_ERROR = "Error en el formato de la aventura";
		public static final String DB_ABRIR_ERROR = "Error al abrir la BD";
		public static final String AVENTURA_BAJADA = "Aventura obtenida";
		public static final String AVENTURA_BAJADA_ERROR = "Error al obtener la aventura";
		public static final String RESULTADO_MJ_COMPLETO = "Minijuego Completado";
		public static final String RESULTADO_MJ_INCOMPLETO = "Minijuego No Completado";
		public static final String USER_UPDATE = "Usuario Actualizado desde Online";
		public static final String AVENTURA_YA_EXISTE = "Ya existe aventura con ese nombre";
		public static final String AVENTURA_UPDATED = "Aventura actualizada";
		public static final String AVENTURA_UPDATED_ERROR = "Aventura actualizada";
		public static final String ERROR_JSON = "Error obteniendo la informacion del servidor";
		public static final String VER_RANKING_NOPLAYERS = "No jugadores en la aventura";

		/**
		 * Avisos para longitudes incorrectas. Se puede simplificar si la long
		 * es la misma
		 */
		public static final String LONGITUD_NICK = "La longitud del nick debe ser menor que"
				+ (Props.Comun.MAX_CHAR_NICK + 1);
		public static final String LONGITUD_PASS = "La longitud de la contrasena debe ser menor que"
				+ (Props.Comun.MAX_CHAR_PASS + 1);
		public static final String LONGITUD_NICK_PASS = "La longitud del contrasena debe ser <"
				+ (Props.Comun.MAX_CHAR_PASS + 1)
				+ "y de la contrasena<"
				+ (Props.Comun.MAX_CHAR_PASS + 1);

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
			users {
				@Override
				public String toString() {
					return "users";
				}
			},
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
