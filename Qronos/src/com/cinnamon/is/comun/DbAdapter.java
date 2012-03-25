//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: QRonos
// ASIGNATURA : Ingeniería del Software
//

// Paquete para clases comunes y de utilidad

package com.cinnamon.is.comun;

import com.cinnamon.is.comun.Props.Enum.Tabla;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Este adaptador nos permite acceder a la base de datos y modificarla
 * 
 * @author Cinnamon Team
 * @version 1.3 25.03.2012
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
	private static final int DATABASE_VERSION = 3;

	/**
	 * Nombre de las tablas de la BD
	 */
	private static final String TABLE_PARCADE = "parcade";
	private static final String TABLE_PQUEST = "pquest";
	private static final String TABLE_QUEST = "quest";

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
	 * Guarda la fase actual del jugador
	 **/
	public static final String PQUEST_KEY_ACTUAL = "actual";

	//
	// Campos de la tabla quest
	//

	/**
	 * Guarda nombre de la aventura y es la clave de la tabla
	 **/
	public static final String QUEST_KEY_NAME = "name";

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
			PQUEST_KEY_SCORE3, PQUEST_KEY_ACTUAL };

	/**
	 * Array con las variables de la tabla quest
	 */
	private static final String[] questCampos = new String[] { QUEST_KEY_NAME,
			QUEST_KEY_MJ1, QUEST_KEY_MJ2, QUEST_KEY_MJ3, QUEST_KEY_PISTA1,
			QUEST_KEY_PISTA2, QUEST_KEY_PISTA3 };

	/**
	 * Tag para identificar la base de datos en el log
	 */
	private static final String TAG = "DbAdapter";

	// Strings para clarificar SQL info
	/**
	 * Conjunto de Strings para clarificar la creacion de las tablas
	 */
	// tabla parcade-pquest
	public static final String SQLplayer = PARCADE_KEY_PLAYER
			+ " text primary key,";
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
	public static final String SQLactual = PQUEST_KEY_ACTUAL + " integer";
	// tabla quest
	public static final String SQLname = QUEST_KEY_NAME + " text primary key,";
	public static final String SQLmj1 = QUEST_KEY_MJ1 + " text,";
	public static final String SQLmj2 = QUEST_KEY_MJ2 + " text,";
	public static final String SQLmj3 = QUEST_KEY_MJ3 + " text,";
	public static final String SQLpista1 = QUEST_KEY_PISTA1 + " text,";
	public static final String SQLpista2 = QUEST_KEY_PISTA2 + " text,";
	public static final String SQLpista3 = QUEST_KEY_PISTA3 + " text";

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
	public static final int PAQUEST_IDCOL_ACTUAL = 4;
	/**
	 * Indice de las columnas de la tabla quest
	 */
	public static final int QUEST_IDCOL_NAME = 0;
	public static final int QUEST_IDCOL_MJ1 = 1;
	public static final int QUEST_IDCOL_MJ2 = 2;
	public static final int QUEST_IDCOL_MJ3 = 3;
	public static final int QUEST_IDCOL_PISTA1 = 4;
	public static final int QUEST_IDCOL_PISTA2 = 5;
	public static final int QUEST_IDCOL_PISTA3 = 6;

	/**
	 * Sentencia SQL para crear la tabla info (con fases mapas)
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

	private static final String TABLE_PQUEST_CREATE = "create table if not exists "
			+ TABLE_PQUEST
			+ "( "
			+ SQLplayer
			+ SQLscore1
			+ SQLscore2
			+ SQLscore3 + SQLactual + ")";

	private static final String TABLE_QUEST_CREATE = "create table if not exists "
			+ TABLE_QUEST
			+ "( "
			+ SQLname
			+ SQLmj1
			+ SQLmj2
			+ SQLmj3
			+ SQLpista1 + SQLpista2 + SQLpista3 + ")";

	// TAG para el log
	/**
	 * Clase interna de utilidad para creacion y gestion de la base de datos
	 */
	private static class DbHelper extends SQLiteOpenHelper {

		DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_PARCADE_CREATE);
			db.execSQL(TABLE_PQUEST_CREATE);
			db.execSQL(TABLE_QUEST_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
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

	public boolean isOpen() {
		return open;

	}

	public void close() {
		mDbHelper.close();
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
	 * habido un error..
	 * 
	 * @param player
	 *            nombre del jugador
	 * @param score
	 *            puntuacion del jugador
	 * @param actual
	 *            fase actual
	 * @return rowId o -1 si ha fallado
	 */
	public long createRowPquest(String player, int[] score, int actual) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(PARCADE_KEY_PLAYER, player);
		initialValues.put(PQUEST_KEY_SCORE1, score[0]);
		initialValues.put(PQUEST_KEY_SCORE2, score[1]);
		initialValues.put(PQUEST_KEY_SCORE3, score[2]);
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
	 * @param minijuegos
	 *            minijuegos de la aventura
	 * @param pistas
	 *            las pista asociadas a los mj
	 * @return rowId o -1 si ha fallado
	 */
	public long createRowQuest(String name, String[] minijuegos, String[] pista) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(QUEST_KEY_NAME, name);
		initialValues.put(QUEST_KEY_MJ1, minijuegos[0]);
		initialValues.put(QUEST_KEY_MJ2, minijuegos[1]);
		initialValues.put(QUEST_KEY_MJ3, minijuegos[2]);
		initialValues.put(QUEST_KEY_PISTA1, pista[0]);
		initialValues.put(QUEST_KEY_PISTA2, pista[1]);
		initialValues.put(QUEST_KEY_PISTA3, pista[2]);

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
	 * @throws SQLException
	 *             si no se ha podido encontrar la fila
	 */
	public Cursor fetchRow(String rowId, Tabla indiceTabla) throws SQLException {
		Cursor mCursor = null;
		try {
			switch (indiceTabla) {
			case parcade:
				mCursor = mDb.query(true, TABLE_PARCADE, parcadeCampos,
						PARCADE_KEY_PLAYER + "=" + "'" + rowId + "'", null,
						null, null, null, null);
				break;
			case pquest:
				mDb.query(true, TABLE_PQUEST, pquestCampos, PARCADE_KEY_PLAYER
						+ "=" + "'" + rowId + "'", null, null, null, null, null);
				break;
			case quest:
				mDb.query(true, TABLE_QUEST, questCampos, QUEST_KEY_NAME + "="
						+ "'" + rowId + "'", null, null, null, null, null);
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
	 * Actualiza una fila de parcade con los detalles dados, si el valor de
	 * cualquier parametro es -1 no se actualizará
	 * 
	 * @param rowId
	 *            id de la fila a actualizar
	 * @param score
	 *            array de la puntuacion del jugador actualizado, toda pos que
	 *            no se quiera actualizar debe estar a -1
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
	 * @param score
	 *            array de la puntuacion del jugador actualizado, toda pos que
	 *            no se quiera actualizar debe estar a -1
	 * @param actual
	 *            la posicion actual del jugador
	 * @return true si la fila se ha editado correctamente, false en caso
	 *         contrario
	 */
	public boolean updateRowPquest(String rowId, int[] score, int actual) {
		ContentValues args = new ContentValues();
		if (score[0] != -1)
			args.put(PARCADE_KEY_SCORE1, score[0]);
		if (score[1] != -1)
			args.put(PARCADE_KEY_SCORE2, score[1]);
		if (score[2] != -1)
			args.put(PARCADE_KEY_SCORE3, score[2]);
		if (actual != -1)
			args.put(PQUEST_KEY_ACTUAL, actual);

		return mDb.update(TABLE_PQUEST, args, PARCADE_KEY_PLAYER + "=" + "'"
				+ rowId + "'", null) > 0;
	}

	/**
	 * Actualiza una fila de quest con los detalles dados, si el valor de
	 * cualquier parametro es -1 o null no se actualizará
	 * 
	 * @param rowId
	 *            id de la fila a actualizar
	 * @param minijuegos
	 *            array de los minijuegos de la aventura, si es null no debe
	 *            actualizarse
	 * @param pistas
	 *            array de las pistas de la aventura, si es null no debe
	 *            actualizarse
	 * @return true si la fila se ha editado correctamente, false en caso
	 *         contrario
	 */
	public boolean updateRowQuest(String rowId, String[] minijuegos,
			String[] pistas) {
		ContentValues args = new ContentValues();
		if (minijuegos[0] != null)
			args.put(QUEST_KEY_MJ1, minijuegos[0]);
		if (minijuegos[1] != null)
			args.put(QUEST_KEY_MJ2, minijuegos[1]);
		if (minijuegos[2] != null)
			args.put(QUEST_KEY_MJ3, minijuegos[2]);
		if (pistas[0] != null)
			args.put(QUEST_KEY_PISTA1, pistas[0]);
		if (pistas[1] != null)
			args.put(QUEST_KEY_PISTA2, pistas[1]);
		if (pistas[2] != null)
			args.put(QUEST_KEY_PISTA3, pistas[2]);

		return mDb.update(TABLE_QUEST, args, QUEST_KEY_NAME + "=" + "'" + rowId
				+ "'", null) > 0;
	}
}
