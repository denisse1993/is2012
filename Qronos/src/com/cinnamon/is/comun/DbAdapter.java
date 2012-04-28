//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: QRonos
// ASIGNATURA : Ingeniería del Software
//

// Paquete para clases comunes y de utilidad

package com.cinnamon.is.comun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cinnamon.is.comun.Props.Enum.Tabla;

/**
 * Este adaptador nos permite acceder a la base de datos y modificarla
 * 
 * @author Cinnamon Team
 * @version 1.8 26.04.2012
 */
public class DbAdapter {

	/**
	 * Clase de ayuda para la creacion y gestion de la base de datos
	 */
	private DbHelper mDbHelper;

	/**
	 * Base de datos
	 */
	private SQLiteDatabase mDb;

	/**
	 * Contexto de la base de datos
	 */
	private final Context mCtx;

	/**
	 * Controla si la bd se ha abierto ya o no
	 */
	private boolean open;

	/**
	 * Nombre de la base de datos
	 */
	private static final String DATABASE_NAME = "data";

	/**
	 * Version de la base de datos
	 */
	private static final int DATABASE_VERSION = 8;

	/**
	 * Nombre de las tablas de la BD
	 */
	private static final String TABLE_USERS = "users";
	private static final String TABLE_PARCADE = "parcade";
	private static final String TABLE_PQUEST = "pquest";
	private static final String TABLE_QUEST = "quest";

	//
	// Campos de la tabla users
	//
	/**
	 * Guarda el nombre del jugador y actua como clave de la tabla
	 */
	public static final String USERS_KEY_PLAYER = "player";
	/**
	 * Guarda la pass del jugador
	 */
	public static final String USERS_KEY_PASS = "pass";

	//
	// Campos de la tabla parcade
	//

	/**
	 * Guarda el nombre del jugador y actua como clave de la tabla
	 */
	public static final String PARCADE_KEY_PLAYER = "player";

	/**
	 * Guarda la puntuacion del jugador para el minijuego 1
	 **/
	public static final String PARCADE_KEY_SCORE1 = "score1";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 2
	 **/
	public static final String PARCADE_KEY_SCORE2 = "score2";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 3
	 **/
	public static final String PARCADE_KEY_SCORE3 = "score3";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 4
	 **/
	public static final String PARCADE_KEY_SCORE4 = "score4";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 5
	 **/
	public static final String PARCADE_KEY_SCORE5 = "score5";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 6
	 **/
	public static final String PARCADE_KEY_SCORE6 = "score6";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 7
	 **/
	public static final String PARCADE_KEY_SCORE7 = "score7";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 8
	 **/
	public static final String PARCADE_KEY_SCORE8 = "score8";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 9
	 **/
	public static final String PARCADE_KEY_SCORE9 = "score9";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 10
	 **/
	public static final String PARCADE_KEY_SCORE10 = "score10";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 11
	 **/
	public static final String PARCADE_KEY_SCORE11 = "score11";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 12
	 **/
	public static final String PARCADE_KEY_SCORE12 = "score12";

	//
	// Campos de la tabla pquest
	//
	/**
	 * Guarda el nombre del jugador y actua como clave de la tabla
	 */
	public static final String PQUEST_KEY_PLAYER = "player";

	/**
	 * Guarda la puntuacion del jugador para el minijuego 1
	 **/
	public static final String PQUEST_KEY_SCORE1 = "score1";

	/**
	 * Guarda la puntuacion del jugador para el minijuego 2
	 **/
	public static final String PQUEST_KEY_SCORE2 = "score2";

	/**
	 * Guarda la puntuacion del jugador para el minijuego 3
	 **/
	public static final String PQUEST_KEY_SCORE3 = "score3";

	/**
	 * Guarda la puntuacion del jugador para el minijuego 4
	 **/
	public static final String PQUEST_KEY_SCORE4 = "score4";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 5
	 **/
	public static final String PQUEST_KEY_SCORE5 = "score5";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 6
	 **/
	public static final String PQUEST_KEY_SCORE6 = "score6";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 7
	 **/
	public static final String PQUEST_KEY_SCORE7 = "score7";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 8
	 **/
	public static final String PQUEST_KEY_SCORE8 = "score8";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 9
	 **/
	public static final String PQUEST_KEY_SCORE9 = "score9";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 10
	 **/
	public static final String PQUEST_KEY_SCORE10 = "score10";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 11
	 **/
	public static final String PQUEST_KEY_SCORE11 = "score11";
	/**
	 * Guarda la puntuacion del jugador para el minijuego 12
	 **/
	public static final String PQUEST_KEY_SCORE12 = "score12";
	/**
	 * Guarda la fase actual del jugador
	 **/
	public static final String PQUEST_KEY_ACTUAL = "actual";
	/**
	 * Guarda el nombre de la aventura asociada
	 **/
	public static final String PQUEST_KEY_QUEST = "name";

	//
	// Campos de la tabla quest
	//

	/**
	 * Guarda nombre de la aventura y es la clave de la tabla
	 **/
	public static final String QUEST_KEY_NAME = "name";

	/**
	 * Guarda la pass de la aventura
	 **/
	public static final String QUEST_KEY_PASS = "pass";

	/**
	 * Guarda el minijuego 1
	 **/
	public static final String QUEST_KEY_MJ1 = "mj1";

	/**
	 * Guarda el minijuego 2
	 **/
	public static final String QUEST_KEY_MJ2 = "mj2";

	/**
	 * Guarda el minijuego 3
	 **/
	public static final String QUEST_KEY_MJ3 = "mj3";

	/**
	 * Guarda el minijuego 4
	 **/
	public static final String QUEST_KEY_MJ4 = "mj4";

	/**
	 * Guarda el minijuego 5
	 **/
	public static final String QUEST_KEY_MJ5 = "mj5";

	/**
	 * Guarda el minijuego 6
	 **/
	public static final String QUEST_KEY_MJ6 = "mj6";

	/**
	 * Guarda el minijuego 7
	 **/
	public static final String QUEST_KEY_MJ7 = "mj7";

	/**
	 * Guarda el minijuego 8
	 **/
	public static final String QUEST_KEY_MJ8 = "mj8";

	/**
	 * Guarda el minijuego 9
	 **/
	public static final String QUEST_KEY_MJ9 = "mj9";

	/**
	 * Guarda el minijuego 10
	 **/
	public static final String QUEST_KEY_MJ10 = "mj10";

	/**
	 * Guarda el minijuego 11
	 **/
	public static final String QUEST_KEY_MJ11 = "mj11";

	/**
	 * Guarda el minijuego 12
	 **/
	public static final String QUEST_KEY_MJ12 = "mj12";

	/**
	 * Guarda la pista 1 asociada con el minijuego 1
	 **/
	public static final String QUEST_KEY_PISTA1 = "pista1";

	/**
	 * Guarda la pista 2 asociada con el minijuego 2
	 **/
	public static final String QUEST_KEY_PISTA2 = "pista2";

	/**
	 * Guarda la pista 3 asociada con el minijuego 3
	 **/
	public static final String QUEST_KEY_PISTA3 = "pista3";

	/**
	 * Guarda la pista 3 asociada con el minijuego 4
	 **/
	public static final String QUEST_KEY_PISTA4 = "pista4";

	/**
	 * Guarda la pista 3 asociada con el minijuego 5
	 **/
	public static final String QUEST_KEY_PISTA5 = "pista5";

	/**
	 * Guarda la pista 3 asociada con el minijuego 6
	 **/
	public static final String QUEST_KEY_PISTA6 = "pista6";

	/**
	 * Guarda la pista 3 asociada con el minijuego 7
	 **/
	public static final String QUEST_KEY_PISTA7 = "pista7";

	/**
	 * Guarda la pista 3 asociada con el minijuego 8
	 **/
	public static final String QUEST_KEY_PISTA8 = "pista8";

	/**
	 * Guarda la pista 3 asociada con el minijuego 9
	 **/
	public static final String QUEST_KEY_PISTA9 = "pista9";

	/**
	 * Guarda la pista 3 asociada con el minijuego 10
	 **/
	public static final String QUEST_KEY_PISTA10 = "pista10";

	/**
	 * Guarda la pista 3 asociada con el minijuego 3
	 **/
	public static final String QUEST_KEY_PISTA11 = "pista11";

	/**
	 * Guarda la pista 3 asociada con el minijuego 3
	 **/
	public static final String QUEST_KEY_PISTA12 = "pista12";

	/**
	 * Array con las variables de la tabla users
	 */
	private static final String[] usersCampos = new String[] {
			USERS_KEY_PLAYER, USERS_KEY_PASS };

	/**
	 * Array con las variables de la tabla parcade
	 */
	private static final String[] parcadeCampos = new String[] {
			PARCADE_KEY_PLAYER, PARCADE_KEY_SCORE1, PARCADE_KEY_SCORE2,
			PARCADE_KEY_SCORE3, PARCADE_KEY_SCORE4, PARCADE_KEY_SCORE5,
			PARCADE_KEY_SCORE6, PARCADE_KEY_SCORE7, PARCADE_KEY_SCORE8,
			PARCADE_KEY_SCORE9, PARCADE_KEY_SCORE10, PARCADE_KEY_SCORE11,
			PARCADE_KEY_SCORE12 };

	/**
	 * Array con las variables de la tabla pquest
	 */
	private static final String[] pquestCampos = new String[] {
			PQUEST_KEY_PLAYER, PQUEST_KEY_SCORE1, PQUEST_KEY_SCORE2,
			PQUEST_KEY_SCORE3, PQUEST_KEY_SCORE4, PQUEST_KEY_SCORE5,
			PQUEST_KEY_SCORE6, PQUEST_KEY_SCORE7, PQUEST_KEY_SCORE8,
			PQUEST_KEY_SCORE9, PQUEST_KEY_SCORE10, PQUEST_KEY_SCORE11,
			PQUEST_KEY_SCORE12, PQUEST_KEY_ACTUAL, PQUEST_KEY_QUEST };

	/**
	 * Array con las variables de la tabla quest
	 */
	private static final String[] questCampos = new String[] { QUEST_KEY_NAME,
			QUEST_KEY_PASS, QUEST_KEY_MJ1, QUEST_KEY_MJ2, QUEST_KEY_MJ3,
			QUEST_KEY_MJ4, QUEST_KEY_MJ5, QUEST_KEY_MJ6, QUEST_KEY_MJ7,
			QUEST_KEY_MJ8, QUEST_KEY_MJ9, QUEST_KEY_MJ10, QUEST_KEY_MJ11,
			QUEST_KEY_MJ12, QUEST_KEY_PISTA1, QUEST_KEY_PISTA2,
			QUEST_KEY_PISTA3, QUEST_KEY_PISTA4, QUEST_KEY_PISTA5,
			QUEST_KEY_PISTA6, QUEST_KEY_PISTA7, QUEST_KEY_PISTA8,
			QUEST_KEY_PISTA9, QUEST_KEY_PISTA10, QUEST_KEY_PISTA11,
			QUEST_KEY_PISTA12 };

	/**
	 * Tag para identificar la base de datos en el log
	 */
	private static final String TAG = "DbAdapter";

	// Strings para clarificar SQL info
	/**
	 * Conjunto de Strings para clarificar la creacion de las tablas
	 */

	// tabla users
	public static final String SQLplayer = USERS_KEY_PLAYER
			+ " text primary key,";
	public static final String SQLpass = USERS_KEY_PASS + " text";

	// tabla parcade-pquest
	public static final String SQLscore1 = PARCADE_KEY_SCORE1 + " integer,";
	public static final String SQLscore2 = PARCADE_KEY_SCORE2 + " integer,";
	public static final String SQLscore3 = PARCADE_KEY_SCORE3 + " integer,";
	public static final String SQLscore4 = PARCADE_KEY_SCORE4 + " integer,";
	public static final String SQLscore5 = PARCADE_KEY_SCORE5 + " integer,";
	public static final String SQLscore6 = PARCADE_KEY_SCORE6 + " integer,";
	public static final String SQLscore7 = PARCADE_KEY_SCORE7 + " integer,";
	public static final String SQLscore8 = PARCADE_KEY_SCORE8 + " integer,";
	public static final String SQLscore9 = PARCADE_KEY_SCORE9 + " integer,";
	public static final String SQLscore10 = PARCADE_KEY_SCORE10 + " integer,";
	public static final String SQLscore11 = PARCADE_KEY_SCORE11 + " integer,";
	public static final String SQLscore12 = PARCADE_KEY_SCORE12 + " integer";
	public static final String SQLscore12coma = PARCADE_KEY_SCORE12
			+ " integer,";
	public static final String SQLactual = PQUEST_KEY_ACTUAL + " integer,";
	public static final String SQLnameQuest = PQUEST_KEY_QUEST + " text";
	// tabla quest
	public static final String SQLname = QUEST_KEY_NAME + " text primary key,";
	public static final String SQLpasscoma = QUEST_KEY_PASS + " text,";
	public static final String SQLmj1 = QUEST_KEY_MJ1 + " integer,";
	public static final String SQLmj2 = QUEST_KEY_MJ2 + " integer,";
	public static final String SQLmj3 = QUEST_KEY_MJ3 + " integer,";
	public static final String SQLmj4 = QUEST_KEY_MJ4 + " integer,";
	public static final String SQLmj5 = QUEST_KEY_MJ5 + " integer,";
	public static final String SQLmj6 = QUEST_KEY_MJ6 + " integer,";
	public static final String SQLmj7 = QUEST_KEY_MJ7 + " integer,";
	public static final String SQLmj8 = QUEST_KEY_MJ8 + " integer,";
	public static final String SQLmj9 = QUEST_KEY_MJ9 + " integer,";
	public static final String SQLmj10 = QUEST_KEY_MJ10 + " integer,";
	public static final String SQLmj11 = QUEST_KEY_MJ11 + " integer,";
	public static final String SQLmj12 = QUEST_KEY_MJ12 + " integer,";
	public static final String SQLpista1 = QUEST_KEY_PISTA1 + " text,";
	public static final String SQLpista2 = QUEST_KEY_PISTA2 + " text,";
	public static final String SQLpista3 = QUEST_KEY_PISTA3 + " text,";
	public static final String SQLpista4 = QUEST_KEY_PISTA4 + " text,";
	public static final String SQLpista5 = QUEST_KEY_PISTA5 + " text,";
	public static final String SQLpista6 = QUEST_KEY_PISTA6 + " text,";
	public static final String SQLpista7 = QUEST_KEY_PISTA7 + " text,";
	public static final String SQLpista8 = QUEST_KEY_PISTA8 + " text,";
	public static final String SQLpista9 = QUEST_KEY_PISTA9 + " text,";
	public static final String SQLpista10 = QUEST_KEY_PISTA10 + " text,";
	public static final String SQLpista11 = QUEST_KEY_PISTA11 + " text,";
	public static final String SQLpista12 = QUEST_KEY_PISTA12 + " text";

	/**
	 * Indice de las columnas de la tabla users
	 */
	public static final int USERS_IDCOL_PLAYER = 0;
	public static final int USERS_IDCOL_PASS = 1;

	/**
	 * Indice de las columnas de la tabla parcade
	 */
	public static final int PARCADE_IDCOL_PLAYER = 0;
	public static final int PARCADE_IDCOL_SCORE1 = 1;
	public static final int PARCADE_IDCOL_SCORE2 = 2;
	public static final int PARCADE_IDCOL_SCORE3 = 3;
	public static final int PARCADE_IDCOL_SCORE4 = 4;
	public static final int PARCADE_IDCOL_SCORE5 = 5;
	public static final int PARCADE_IDCOL_SCORE6 = 6;
	public static final int PARCADE_IDCOL_SCORE7 = 7;
	public static final int PARCADE_IDCOL_SCORE8 = 8;
	public static final int PARCADE_IDCOL_SCORE9 = 9;
	public static final int PARCADE_IDCOL_SCORE10 = 10;
	public static final int PARCADE_IDCOL_SCORE11 = 11;
	public static final int PARCADE_IDCOL_SCORE12 = 12;
	/**
	 * Indice de las columnas de la tabla pquest
	 */
	public static final int PQUEST_IDCOL_PLAYER = 0;
	public static final int PQUEST_IDCOL_SCORE1 = 1;
	public static final int PQUEST_IDCOL_SCORE2 = 2;
	public static final int PQUEST_IDCOL_SCORE3 = 3;
	public static final int PQUEST_IDCOL_SCORE4 = 4;
	public static final int PQUEST_IDCOL_SCORE5 = 5;
	public static final int PQUEST_IDCOL_SCORE6 = 6;
	public static final int PQUEST_IDCOL_SCORE7 = 7;
	public static final int PQUEST_IDCOL_SCORE8 = 8;
	public static final int PQUEST_IDCOL_SCORE9 = 9;
	public static final int PQUEST_IDCOL_SCORE10 = 10;
	public static final int PQUEST_IDCOL_SCORE11 = 11;
	public static final int PQUEST_IDCOL_SCORE12 = 12;
	public static final int PQUEST_IDCOL_ACTUAL = 13;
	public static final int PQUEST_IDCOL_NAME = 14;
	/**
	 * Indice de las columnas de la tabla quest
	 */
	public static final int QUEST_IDCOL_NAME = 0;
	public static final int QUEST_IDCOL_PASS = 1;
	public static final int QUEST_IDCOL_MJ1 = 2;
	public static final int QUEST_IDCOL_MJ2 = 3;
	public static final int QUEST_IDCOL_MJ3 = 4;
	public static final int QUEST_IDCOL_MJ4 = 5;
	public static final int QUEST_IDCOL_MJ5 = 6;
	public static final int QUEST_IDCOL_MJ6 = 7;
	public static final int QUEST_IDCOL_MJ7 = 8;
	public static final int QUEST_IDCOL_MJ8 = 9;
	public static final int QUEST_IDCOL_MJ9 = 10;
	public static final int QUEST_IDCOL_MJ10 = 11;
	public static final int QUEST_IDCOL_MJ11 = 12;
	public static final int QUEST_IDCOL_MJ12 = 13;
	public static final int QUEST_IDCOL_PISTA1 = 14;
	public static final int QUEST_IDCOL_PISTA2 = 15;
	public static final int QUEST_IDCOL_PISTA3 = 16;
	public static final int QUEST_IDCOL_PISTA4 = 17;
	public static final int QUEST_IDCOL_PISTA5 = 18;
	public static final int QUEST_IDCOL_PISTA6 = 19;
	public static final int QUEST_IDCOL_PISTA7 = 20;
	public static final int QUEST_IDCOL_PISTA8 = 21;
	public static final int QUEST_IDCOL_PISTA9 = 22;
	public static final int QUEST_IDCOL_PISTA10 = 23;
	public static final int QUEST_IDCOL_PISTA11 = 24;
	public static final int QUEST_IDCOL_PISTA12 = 25;

	/**
	 * Sentencia SQL para crear la tabla users
	 */
	private static final String TABLE_USERS_CREATE = "create table if not exists "
			+ TABLE_USERS + "( " + SQLplayer + SQLpass + ")";

	/**
	 * Sentencia SQL para crear la tabla parcade
	 */
	private static final String TABLE_PARCADE_CREATE = "create table if not exists "
			+ TABLE_PARCADE
			+ "( "
			+ SQLplayer
			+ SQLscore1
			+ SQLscore2
			+ SQLscore3
			+ SQLscore4
			+ SQLscore5
			+ SQLscore6
			+ SQLscore7
			+ SQLscore8
			+ SQLscore9
			+ SQLscore10
			+ SQLscore11
			+ SQLscore12
			+ ")";
	/**
	 * Sentencia SQL para crear la tabla pquest
	 */
	private static final String TABLE_PQUEST_CREATE = "create table if not exists "
			+ TABLE_PQUEST
			+ "( "
			+ SQLplayer
			+ SQLscore1
			+ SQLscore2
			+ SQLscore3
			+ SQLscore4
			+ SQLscore5
			+ SQLscore6
			+ SQLscore7
			+ SQLscore8
			+ SQLscore9
			+ SQLscore10
			+ SQLscore11
			+ SQLscore12coma
			+ SQLactual + SQLnameQuest + ")";

	/**
	 * Sentencia SQL para crear la tabla quest
	 */
	private static final String TABLE_QUEST_CREATE = "create table if not exists "
			+ TABLE_QUEST
			+ "( "
			+ SQLname
			+ SQLpasscoma
			+ SQLmj1
			+ SQLmj2
			+ SQLmj3
			+ SQLmj4
			+ SQLmj5
			+ SQLmj6
			+ SQLmj7
			+ SQLmj8
			+ SQLmj9
			+ SQLmj10
			+ SQLmj11
			+ SQLmj12
			+ SQLpista1
			+ SQLpista2
			+ SQLpista3
			+ SQLpista4
			+ SQLpista5
			+ SQLpista6
			+ SQLpista7
			+ SQLpista8
			+ SQLpista9
			+ SQLpista10 + SQLpista11 + SQLpista12 + ")";

	/**
	 * Clase interna de utilidad para creacion y gestion de la base de datos
	 */
	private static class DbHelper extends SQLiteOpenHelper {

		DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_USERS_CREATE);
			db.execSQL(TABLE_PARCADE_CREATE);
			db.execSQL(TABLE_PQUEST_CREATE);
			db.execSQL(TABLE_QUEST_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARCADE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PQUEST);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
			onCreate(db);
		}
	}

	/**
	 * Constructor - pasa el contexto para poder abrir o crear la DB
	 * 
	 * @param ctx
	 *            Contexto en el que estamos trabajando
	 */
	public DbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	/**
	 * Abre la base de datos. Si no puede abrirla, la crea. Si no se puede lanza
	 * una excepcion
	 * 
	 * @param onlyRead
	 *            indica si se abre la BD en modo solo lectura
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */
	public DbAdapter open(boolean onlyRead) throws SQLException {
		mDbHelper = new DbHelper(mCtx);
		// obtiene la base de datos en modo lectura o escritura en funcion del
		// parametro onlyRead
		mDb = onlyRead ? mDbHelper.getReadableDatabase() : mDbHelper
				.getWritableDatabase();
		open = true;
		return this;
	}

	/**
	 * Comprueba si la BD esta abierta
	 * 
	 * @return si esta abierta o no
	 */
	public boolean isOpen() {
		return open;

	}

	/**
	 * Cierra la base de datos
	 */
	public void close() {
		open = false;
		mDbHelper.close();
	}

	/**
	 * Inserta una nueva fila en la tabla users. Si se crea correctamente
	 * devuelve el rowId, en caso contrario, devuelve -1 para indicar que ha
	 * habido un error..
	 * 
	 * @param player
	 *            nombre del jugador
	 * @param pass
	 *            password del jugador
	 */
	public long createRowUsers(String player, String pass) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(USERS_KEY_PLAYER, player);
		initialValues.put(USERS_KEY_PASS, pass);

		return mDb.insert(TABLE_USERS, null, initialValues);
	}

	/**
	 * Inserta una nueva fila en la tabla parcade. Si se crea correctamente
	 * devuelve el rowId, en caso contrario, devuelve -1 para indicar que ha
	 * habido un error..
	 * 
	 * @param player
	 *            nombre del jugador
	 * @param score
	 *            puntuacion del jugador
	 * @return rowId o -1 si ha fallado
	 */
	public long createRowParcade(String player, int[] score) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(PARCADE_KEY_PLAYER, player);
		initialValues.put(PARCADE_KEY_SCORE1, score[0]);
		initialValues.put(PARCADE_KEY_SCORE2, score[1]);
		initialValues.put(PARCADE_KEY_SCORE3, score[2]);
		initialValues.put(PARCADE_KEY_SCORE4, score[3]);
		initialValues.put(PARCADE_KEY_SCORE5, score[4]);
		initialValues.put(PARCADE_KEY_SCORE6, score[5]);
		initialValues.put(PARCADE_KEY_SCORE7, score[6]);
		initialValues.put(PARCADE_KEY_SCORE8, score[7]);
		initialValues.put(PARCADE_KEY_SCORE9, score[8]);
		initialValues.put(PARCADE_KEY_SCORE10, score[9]);
		initialValues.put(PARCADE_KEY_SCORE11, score[10]);
		initialValues.put(PARCADE_KEY_SCORE12, score[11]);

		return mDb.insert(TABLE_PARCADE, null, initialValues);
	}

	/**
	 * Inserta una nueva fila en la tabla pquest. Si se crea correctamente
	 * devuelve el rowId, en caso contrario, devuelve -1 para indicar que ha
	 * habido un error.
	 * 
	 * @param player
	 *            nombre del jugador
	 * @param score
	 *            puntuacion del jugador
	 * @param actual
	 *            fase actual
	 * @param quest
	 *            nombre de la aventura
	 * @return rowId o -1 si ha fallado
	 */
	public long createRowPquest(String player, int[] score, int actual,
			String quest) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(PARCADE_KEY_PLAYER, player);
		initialValues.put(PQUEST_KEY_SCORE1, score[0]);
		initialValues.put(PQUEST_KEY_SCORE2, score[1]);
		initialValues.put(PQUEST_KEY_SCORE3, score[2]);
		initialValues.put(PQUEST_KEY_SCORE4, score[3]);
		initialValues.put(PQUEST_KEY_SCORE5, score[4]);
		initialValues.put(PQUEST_KEY_SCORE6, score[5]);
		initialValues.put(PQUEST_KEY_SCORE7, score[6]);
		initialValues.put(PQUEST_KEY_SCORE8, score[7]);
		initialValues.put(PQUEST_KEY_SCORE9, score[8]);
		initialValues.put(PQUEST_KEY_SCORE10, score[9]);
		initialValues.put(PQUEST_KEY_SCORE11, score[10]);
		initialValues.put(PQUEST_KEY_SCORE12, score[11]);
		initialValues.put(PQUEST_KEY_QUEST, quest);

		initialValues.put(PQUEST_KEY_ACTUAL, actual);

		return mDb.insert(TABLE_PQUEST, null, initialValues);
	}

	/**
	 * Inserta una nueva fila en la tabla quest. Si se crea correctamente
	 * devuelve el rowId, en caso contrario, devuelve -1 para indicar que ha
	 * habido un error..
	 * 
	 * @param name
	 *            nombre de la aventura
	 * @param pass
	 *            password de la aventura
	 * @param minijuegos
	 *            minijuegos de la aventura
	 * @param pistas
	 *            las pista asociadas a los mj
	 * @return rowId o -1 si ha fallado
	 */
	public long createRowQuest(String name, String pass, Integer[] minijuegos,
			String[] pista) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(QUEST_KEY_NAME, name);
		initialValues.put(QUEST_KEY_PASS, pass);

		initialValues.put(QUEST_KEY_MJ1, minijuegos[0]);
		initialValues.put(QUEST_KEY_MJ2, minijuegos[1]);
		initialValues.put(QUEST_KEY_MJ3, minijuegos[2]);
		initialValues.put(QUEST_KEY_MJ4, minijuegos[3]);
		initialValues.put(QUEST_KEY_MJ5, minijuegos[4]);
		initialValues.put(QUEST_KEY_MJ6, minijuegos[5]);
		initialValues.put(QUEST_KEY_MJ7, minijuegos[6]);
		initialValues.put(QUEST_KEY_MJ8, minijuegos[7]);
		initialValues.put(QUEST_KEY_MJ9, minijuegos[8]);
		initialValues.put(QUEST_KEY_MJ10, minijuegos[9]);
		initialValues.put(QUEST_KEY_MJ11, minijuegos[10]);
		initialValues.put(QUEST_KEY_MJ12, minijuegos[11]);

		initialValues.put(QUEST_KEY_PISTA1, pista[0]);
		initialValues.put(QUEST_KEY_PISTA2, pista[1]);
		initialValues.put(QUEST_KEY_PISTA3, pista[2]);
		initialValues.put(QUEST_KEY_PISTA4, pista[3]);
		initialValues.put(QUEST_KEY_PISTA5, pista[4]);
		initialValues.put(QUEST_KEY_PISTA6, pista[5]);
		initialValues.put(QUEST_KEY_PISTA7, pista[6]);
		initialValues.put(QUEST_KEY_PISTA8, pista[7]);
		initialValues.put(QUEST_KEY_PISTA9, pista[8]);
		initialValues.put(QUEST_KEY_PISTA10, pista[9]);
		initialValues.put(QUEST_KEY_PISTA11, pista[10]);
		initialValues.put(QUEST_KEY_PISTA12, pista[11]);

		return mDb.insert(TABLE_QUEST, null, initialValues);
	}

	/**
	 * Borra la fila con el rowId dado
	 * <p>
	 * 0-parcade 1-pquest 2-quest
	 * </p>
	 * 
	 * @param rowId
	 *            id de la fila a borrar
	 * @param indiceTabla
	 *            tabla a tratar
	 * @return true si se ha borrado, false en caso contrario
	 */
	public boolean deleteRow(String rowId, Tabla indiceTabla) {
		boolean del = false;
		switch (indiceTabla) {
		case users:
			del = mDb.delete(TABLE_USERS, USERS_KEY_PLAYER + "=" + "'" + rowId
					+ "'", null) > 0;
			break;
		case parcade:
			del = mDb.delete(TABLE_PARCADE, PARCADE_KEY_PLAYER + "=" + "'"
					+ rowId + "'", null) > 0;
			break;
		case pquest:
			del = mDb.delete(TABLE_PQUEST, PARCADE_KEY_PLAYER + "=" + "'"
					+ rowId + "'", null) > 0;
			break;
		case quest:
			del = mDb.delete(TABLE_QUEST, QUEST_KEY_NAME + "=" + "'" + rowId
					+ "'", null) > 0;
			break;
		}
		return del;
	}

	/**
	 * Devuelve un Cursor apuntando a la lista de todas las filas para una
	 * determinada tabla
	 * <p>
	 * 0-parcade 1-pquest 2-quest
	 * </p>
	 * 
	 * @param indiceTabla
	 *            tabla a tratar
	 * @return Cursor de todas las filas
	 */
	public Cursor fetchAllRows(Tabla indiceTabla) {

		Cursor mCursor = null;
		try {
			switch (indiceTabla) {
			case users:
				mCursor = mDb.query(TABLE_USERS, usersCampos, null, null, null,
						null, null);
				break;
			case parcade:
				mCursor = mDb.query(TABLE_PARCADE, parcadeCampos, null, null,
						null, null, null);
				break;
			case pquest:
				mCursor = mDb.query(TABLE_PQUEST, pquestCampos, null, null,
						null, null, null);
				break;
			case quest:

				mCursor = mDb.query(TABLE_QUEST, questCampos, null, null, null,
						null, null);
				break;
			}
		} catch (SQLException e) {
			// si pasa algo raro
			mCursor = null;
		}
		return mCursor;

	}

	/**
	 * Devuelve un Cursor apuntando a la fila con el rowId dado
	 * 
	 * @param rowId
	 *            id de la fila
	 * @param indiceTabla
	 *            tabla a tratar
	 * @return Cursor posicionado en la fila que queremos
	 */
	public Cursor fetchRow(String rowId, Tabla indiceTabla) {
		Cursor mCursor = null;
		try {
			switch (indiceTabla) {
			case users:
				mCursor = mDb.query(true, TABLE_USERS, usersCampos,
						USERS_KEY_PLAYER + "=" + "'" + rowId + "'", null, null,
						null, null, null);
				break;
			case parcade:
				mCursor = mDb.query(true, TABLE_PARCADE, parcadeCampos,
						PARCADE_KEY_PLAYER + "=" + "'" + rowId + "'", null,
						null, null, null, null);
				break;
			case pquest:
				mCursor = mDb.query(true, TABLE_PQUEST, pquestCampos,
						PARCADE_KEY_PLAYER + "=" + "'" + rowId + "'", null,
						null, null, null, null);
				break;
			case quest:
				mCursor = mDb.query(true, TABLE_QUEST, questCampos,
						QUEST_KEY_NAME + "=" + "'" + rowId + "'", null, null,
						null, null, null);
				break;

			}
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
		} catch (SQLException e) {
			// si no se encuentra la columna
			mCursor = null;
		}
		return mCursor;
	}

	/**
	 * Devuelve true o false en funcion de si existe la fila o no
	 * 
	 * @param rowId
	 *            id de la fila
	 * @param indiceTabla
	 *            tabla a tratar
	 * @return true o false
	 * @throws SQLException
	 *             si no se ha podido encontrar la fila
	 */
	public boolean existsRow(String rowId, Tabla indiceTabla) {
		Cursor mCursor = fetchRow(rowId, indiceTabla);
		boolean existe = false;

		if (mCursor != null) {
			if (mCursor.getCount() <= 0) {
				existe = false;
			} else {
				existe = true;
			}
			mCursor.close();
		} else {
			existe = false;
		}
		return existe;
	}

	/**
	 * Comprueba si la contraseña es valida tanto en arcade como en pquest
	 * 
	 * @param nombre
	 *            id de la fila
	 * @param pass
	 *            a comprobar en MD5
	 * @return si la pass es correcta o no y si ademas existe la fila
	 */
	public boolean passOk(String nombre, String pass) {
		boolean passOk = false;
		Cursor mCursor = fetchRow(nombre, Tabla.users);

		if (mCursor != null) {
			if (mCursor.getCount() <= 0) {
				passOk = false;
			} else {
				if (pass.equals(mCursor.getString(USERS_IDCOL_PASS)))
					passOk = true;
				else
					passOk = false;
			}
			mCursor.close();
		} else {
			passOk = false;
		}
		return passOk;
	}

	/**
	 * Actualiza una fila de parcade con los detalles dados, si el valor de
	 * cualquier parametro es -1 no se actualizará
	 * 
	 * @param rowId
	 *            id de la fila a actualizar
	 * @param pass
	 *            nueva password
	 * */
	public boolean updateRowUsers(String rowId, String pass) {
		ContentValues args = new ContentValues();
		if (pass != null)
			args.put(USERS_KEY_PASS, pass);

		return mDb.update(TABLE_USERS, args, USERS_KEY_PLAYER + "=" + "'"
				+ rowId + "'", null) > 0;
	}

	/**
	 * Actualiza una fila de parcade con los detalles dados, si el valor de
	 * cualquier parametro es -1 no se actualizará
	 * 
	 * @param rowId
	 *            id de la fila a actualizar
	 * @param pass
	 *            nueva password
	 * @param score
	 *            array de la puntuacion del jugador actualizado, toda pos que
	 *            no se quiera actualizar debe estar a -1 o null
	 * @return true si la fila se ha editado correctamente, false en caso
	 *         contrario
	 */
	public boolean updateRowParcade(String rowId, int[] score) {
		ContentValues args = new ContentValues();
		if (score[0] != -1)
			args.put(PARCADE_KEY_SCORE1, score[0]);
		if (score[1] != -1)
			args.put(PARCADE_KEY_SCORE2, score[1]);
		if (score[2] != -1)
			args.put(PARCADE_KEY_SCORE3, score[2]);
		if (score[3] != -1)
			args.put(PARCADE_KEY_SCORE4, score[3]);
		if (score[4] != -1)
			args.put(PARCADE_KEY_SCORE5, score[4]);
		if (score[5] != -1)
			args.put(PARCADE_KEY_SCORE6, score[5]);
		if (score[6] != -1)
			args.put(PARCADE_KEY_SCORE7, score[6]);
		if (score[7] != -1)
			args.put(PARCADE_KEY_SCORE8, score[7]);
		if (score[8] != -1)
			args.put(PARCADE_KEY_SCORE9, score[8]);
		if (score[9] != -1)
			args.put(PARCADE_KEY_SCORE10, score[9]);
		if (score[10] != -1)
			args.put(PARCADE_KEY_SCORE11, score[10]);
		if (score[11] != -1)
			args.put(PARCADE_KEY_SCORE12, score[11]);

		return mDb.update(TABLE_PARCADE, args, PARCADE_KEY_PLAYER + "=" + "'"
				+ rowId + "'", null) > 0;
	}

	/**
	 * Actualiza una fila de pquest con los detalles dados, si el valor de
	 * cualquier parametro es -1 no se actualizará
	 * 
	 * @param rowId
	 *            id de la fila a actualizar
	 * 
	 * @param score
	 *            array de la puntuacion del jugador actualizado, toda pos que
	 *            no se quiera actualizar debe estar a -1
	 * @param actual
	 *            la posicion actual del jugador
	 * @param quest
	 *            nombre de la aventura
	 * @return true si la fila se ha editado correctamente, false en caso
	 *         contrario
	 */
	public boolean updateRowPQuest(String rowId, int[] score, int actual,
			String quest) {
		ContentValues args = new ContentValues();
		if (score[0] != -1)
			args.put(PARCADE_KEY_SCORE1, score[0]);
		if (score[1] != -1)
			args.put(PARCADE_KEY_SCORE2, score[1]);
		if (score[2] != -1)
			args.put(PARCADE_KEY_SCORE3, score[2]);
		if (score[3] != -1)
			args.put(PARCADE_KEY_SCORE4, score[3]);
		if (score[4] != -1)
			args.put(PARCADE_KEY_SCORE5, score[4]);
		if (score[5] != -1)
			args.put(PARCADE_KEY_SCORE6, score[5]);
		if (score[6] != -1)
			args.put(PARCADE_KEY_SCORE7, score[6]);
		if (score[7] != -1)
			args.put(PARCADE_KEY_SCORE8, score[7]);
		if (score[8] != -1)
			args.put(PARCADE_KEY_SCORE9, score[8]);
		if (score[9] != -1)
			args.put(PARCADE_KEY_SCORE10, score[9]);
		if (score[10] != -1)
			args.put(PARCADE_KEY_SCORE11, score[10]);
		if (score[11] != -1)
			args.put(PARCADE_KEY_SCORE12, score[11]);
		if (actual != -1)
			args.put(PQUEST_KEY_ACTUAL, actual);
		if (quest != null)
			args.put(PQUEST_KEY_QUEST, quest);

		return mDb.update(TABLE_PQUEST, args, PARCADE_KEY_PLAYER + "=" + "'"
				+ rowId + "'", null) > 0;
	}

	/**
	 * Actualiza una fila de quest con los detalles dados, si el valor de
	 * cualquier parametro es -1 o null no se actualizará
	 * 
	 * @param rowId
	 *            id de la fila a actualizar
	 * @param pass
	 *            nueva pass
	 * @param minijuegos
	 *            array de los minijuegos de la aventura, si es null no debe
	 *            actualizarse
	 * @param pistas
	 *            array de las pistas de la aventura, si es null no debe
	 *            actualizarse
	 * @return true si la fila se ha editado correctamente, false en caso
	 *         contrario
	 */
	public boolean updateRowQuest(String rowId, String pass,
			Integer[] minijuegos, String[] pistas) {
		ContentValues args = new ContentValues();
		if (pass != null)
			args.put(QUEST_KEY_PASS, pass);
		if (minijuegos[0] != null)
			args.put(QUEST_KEY_MJ1, minijuegos[0]);
		if (minijuegos[1] != null)
			args.put(QUEST_KEY_MJ2, minijuegos[1]);
		if (minijuegos[2] != null)
			args.put(QUEST_KEY_MJ3, minijuegos[2]);
		if (minijuegos[3] != null)
			args.put(QUEST_KEY_MJ4, minijuegos[3]);
		if (minijuegos[4] != null)
			args.put(QUEST_KEY_MJ5, minijuegos[4]);
		if (minijuegos[5] != null)
			args.put(QUEST_KEY_MJ6, minijuegos[5]);
		if (minijuegos[6] != null)
			args.put(QUEST_KEY_MJ7, minijuegos[6]);
		if (minijuegos[7] != null)
			args.put(QUEST_KEY_MJ8, minijuegos[7]);
		if (minijuegos[8] != null)
			args.put(QUEST_KEY_MJ9, minijuegos[8]);
		if (minijuegos[9] != null)
			args.put(QUEST_KEY_MJ10, minijuegos[9]);
		if (minijuegos[10] != null)
			args.put(QUEST_KEY_MJ11, minijuegos[10]);
		if (minijuegos[11] != null)
			args.put(QUEST_KEY_MJ12, minijuegos[11]);
		if (pistas[0] != null)
			args.put(QUEST_KEY_PISTA1, pistas[0]);
		if (pistas[1] != null)
			args.put(QUEST_KEY_PISTA2, pistas[1]);
		if (pistas[2] != null)
			args.put(QUEST_KEY_PISTA3, pistas[2]);
		if (pistas[3] != null)
			args.put(QUEST_KEY_PISTA4, pistas[3]);
		if (pistas[4] != null)
			args.put(QUEST_KEY_PISTA5, pistas[4]);
		if (pistas[5] != null)
			args.put(QUEST_KEY_PISTA6, pistas[5]);
		if (pistas[6] != null)
			args.put(QUEST_KEY_PISTA7, pistas[6]);
		if (pistas[7] != null)
			args.put(QUEST_KEY_PISTA8, pistas[7]);
		if (pistas[8] != null)
			args.put(QUEST_KEY_PISTA9, pistas[8]);
		if (pistas[9] != null)
			args.put(QUEST_KEY_PISTA10, pistas[9]);
		if (pistas[10] != null)
			args.put(QUEST_KEY_PISTA11, pistas[10]);
		if (pistas[11] != null)
			args.put(QUEST_KEY_PISTA12, pistas[11]);

		return mDb.update(TABLE_QUEST, args, QUEST_KEY_NAME + "=" + "'" + rowId
				+ "'", null) > 0;
	}
}
