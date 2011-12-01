//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//

/**
 * Paquete para clases comunes y de utilidad
 */
package com.cinnamon.is.comun;

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
 * @version 1.4 25.11.2011
 */
public class DbAdapter {

	/**
	 * Nombre de la base de datos
	 */
	private static final String DATABASE_NAME = "data";
	/**
	 * Nombre de las tablas de la BD
	 */
	private static final String DATABASE_TABLE_INFO = "info";
	/**
	 * Version de la base de datos
	 */
	private static final int DATABASE_VERSION = 2;

	// Campos de la tabla info
	/**
	 * Guarda el nombre del jugador y actua como clave de la tabla
	 */
	public static final String INFO_KEY_PLAYER = "player";
	/**
	 * Guarda la puntuacion del jugador
	 **/
	public static final String INFO_KEY_SCORE = "score";

	/**
	 * Guarda la pagina del libro en la que se encuentra el jugador actualmente
	 */
	public static final String INFO_KEY_HOJA = "hoja";

	/**
	 * Representa los objetos que tiene el jugador en la mochila siguiendo los
	 * siguientes criterios de codificacion <code><pre>
	 * Valor 0 - nada
	 * Valor 1 - papel 1
	 * Valor 2 - papel 2
	 * Valor 3 - papel 3
	 * Valor 4 - maquina
	 * Valor 5 - papel 1 y 2
	 * Valor 6 - papel 1 y 3
	 * Valor 7 - papel 2 y 3
	 * Valor 8 - papel 1,2,3
	 * Valor 9 - papel 1,2,3 y maquina
	 * </pre></code>
	 */
	public static final String INFO_KEY_MOCHILA = "mochila";

	/**
	 * Indica la posicion del jugador en el mapa, en funcion de la siguiente
	 * codificacion: <code><pre>
	 * Valor 0 - indica que jugador no ha visitado esa fase
	 * Valor 1 - indica que el jugador ha visitado esa fase
	 * Valor 2 - indica que el jugador se encuentra en esa fase
	 * </pre></code> Si todas las fases se encuentran a 1 el jugador se
	 * encontrara en la fase final
	 */
	public static final String MAPA_KEY_FASE1 = "fase1";
	public static final String MAPA_KEY_FASE2 = "fase2";
	public static final String MAPA_KEY_FASE3 = "fase3";
	public static final String MAPA_KEY_FASE4 = "fase4";

	/**
	 * Array con las variables de la tabla Info
	 */
	private static final String[] infoCampos = new String[] { INFO_KEY_PLAYER,
			INFO_KEY_SCORE, INFO_KEY_HOJA, INFO_KEY_MOCHILA, MAPA_KEY_FASE1,
			MAPA_KEY_FASE2, MAPA_KEY_FASE3, MAPA_KEY_FASE4 };

	/**
	 * Tag para identificar la base de datos en el log
	 */
	private static final String TAG = "DbAdapter";

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

	// Strings para clarificar SQL info
	/**
	 * Conjunto de Strings para clarificar la creacion de la tabla info
	 */
	public static final String SQLplayer = INFO_KEY_PLAYER
			+ " text primary key,";
	public static final String SQLscore = INFO_KEY_SCORE + " integer,";
	public static final String SQLhoja = INFO_KEY_HOJA + " integer,";
	public static final String SQLmochila = INFO_KEY_MOCHILA + " integer,";

	public static final String SQLfase1 = MAPA_KEY_FASE1 + " integer,";
	public static final String SQLfase2 = MAPA_KEY_FASE2 + " integer,";
	public static final String SQLfase3 = MAPA_KEY_FASE3 + " integer,";
	public static final String SQLfase4 = MAPA_KEY_FASE4 + " integer);";

	/**
	 * Indice de las columnas de la tabla info
	 */
	public static final int INFO_IDCOL_PLAYER = 0;
	public static final int INFO_IDCOL_SCORE = 1;
	public static final int INFO_IDCOL_HOJA = 2;
	public static final int INFO_IDCOL_MOCHILA = 3;
	public static final int MAPA_IDCOL_FASE1 = 4;
	public static final int MAPA_IDCOL_FASE2 = 5;
	public static final int MAPA_IDCOL_FASE3 = 6;
	public static final int MAPA_IDCOL_FASE4 = 7;

	/**
	 * Sentencia SQL para crear la tabla info (con fases mapas)
	 */
	private static final String TABLE_INFO_CREATE = "create table if not exists "
			+ DATABASE_TABLE_INFO
			+ "( "
			+ SQLplayer
			+ SQLscore
			+ SQLhoja
			+ SQLmochila + SQLfase1 + SQLfase2 + SQLfase3 + SQLfase4;

	// private static final String TABLE_MAPA_CREATE = "create table "
	// + DATABASE_TABLE_MAPA + "( " + SQLplayer + SQLfase1 + SQLfase2
	// + SQLfase3 + SQLfase4;
	// private static final String DATABASE_TABLE_MAPA = "mapa";
	// array con los campos de la tabla mapa
	// private static final String[] mapaCampos = new String[] {
	// INFO_KEY_PLAYER,
	// MAPA_KEY_FASE1, MAPA_KEY_FASE2, MAPA_KEY_FASE3, MAPA_KEY_FASE4 };
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
			db.execSQL(TABLE_INFO_CREATE);
			// db.execSQL(TABLE_MAPA_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS notes");
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
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/**
	 * Inserta una nueva fila. Si se crea correctamente devuelve el rowId, en
	 * caso contrario, devuelve -1 para indicar que ha habido un error..
	 * 
	 * @param player
	 *            nombre del jugador
	 * @param score
	 *            puntuacion del jugador
	 * @param hoja
	 *            hoja actual del libro del jugador
	 * @param mochila
	 *            elementos que ha conseguido el jugador
	 * @param fase1
	 *            jugador en fase 1
	 * @param fase2
	 *            jugador en fase 2
	 * @param fase3
	 *            jugador en fase 3
	 * @param fase4
	 *            jugador en fase 4
	 * @return rowId o -1 si ha fallado
	 */
	public long createRow(String player, int score, int hoja, int mochila,
			int fase1, int fase2, int fase3, int fase4) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(INFO_KEY_PLAYER, player);
		initialValues.put(INFO_KEY_SCORE, score);
		initialValues.put(INFO_KEY_HOJA, hoja);
		initialValues.put(INFO_KEY_MOCHILA, mochila);
		initialValues.put(MAPA_KEY_FASE1, fase1);
		initialValues.put(MAPA_KEY_FASE2, fase2);
		initialValues.put(MAPA_KEY_FASE3, fase3);
		initialValues.put(MAPA_KEY_FASE4, fase4);

		return mDb.insert(DATABASE_TABLE_INFO, null, initialValues);
	}

	/**
	 * Borra la fila con el rowId dado
	 * 
	 * @param rowId
	 *            id de la fila a borrar
	 * @return true si se ha borrado, false en caso contrario
	 */
	public boolean deleteRow(String rowId) {
		return mDb.delete(DATABASE_TABLE_INFO, INFO_KEY_PLAYER + "=" + rowId,
				null) > 0;
	}

	/**
	 * Devuelve un Cursor apuntando a la lista de todas las filas
	 * 
	 * @return Cursor de todas las filas
	 */
	public Cursor fetchAllRows() {
		try {
			return mDb.query(DATABASE_TABLE_INFO, infoCampos, null, null, null,
					null, null);
		} catch (SQLException e) {
			// si pasa algo raro
			return null;
		}

	}

	/**
	 * Devuelve un Cursor apuntando a la fila con el rowId dado
	 * 
	 * @param rowId
	 *            id de la fila
	 * @return Cursor posicionado en la fila que queremos
	 * @throws SQLException
	 *             si no se ha podido encontrar la fila
	 */
	public Cursor fetchRow(String rowId) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor =

			mDb.query(true, DATABASE_TABLE_INFO, infoCampos, INFO_KEY_PLAYER
					+ "=" + rowId, null, null, null, null, null);
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
	 * @return true o false
	 * @throws SQLException
	 *             si no se ha podido encontrar la fila
	 */
	public boolean existsRow(String rowId) {
		Cursor mCursor = null;
		try {
			mCursor = mDb
					.query(true, DATABASE_TABLE_INFO, infoCampos,
							INFO_KEY_PLAYER + "=" + rowId, null, null, null,
							null, null);
		} catch (SQLException e) {
			// si no se encuentra la columna
			return false;
		}
		if (mCursor != null)
			if (mCursor.getCount() <= 0) {
				mCursor.close();
				return false;
			} else {
				mCursor.close();
				return true;
			}
		else {
			return false;
		}

	}

	/**
	 * Actualiza la fila con los detalles dados, si el valor de cualquier
	 * parametro es -1 no se actualizará
	 * 
	 * @param rowId
	 *            id de la fila a actualizar
	 * @param score
	 *            puntuacion del jugador
	 * @param hoja
	 *            hoja actual del libro del jugador
	 * @param mochila
	 *            elementos que ha conseguido el jugador
	 * @param fase1
	 *            jugador en fase 1
	 * @param fase2
	 *            jugador en fase 2
	 * @param fase3
	 *            jugador en fase 3
	 * @param fase4
	 *            jugador en fase 4
	 * @return true si la fila se ha editado correctamente, false en caso
	 *         contrario
	 */
	public boolean updateRow(String rowId, int score, int hoja, int mochila,
			int fase1, int fase2, int fase3, int fase4) {
		ContentValues args = new ContentValues();
		if (score != -1)
			args.put(INFO_KEY_SCORE, score);
		if (hoja != -1)
			args.put(INFO_KEY_HOJA, hoja);
		if (mochila != -1)
			args.put(INFO_KEY_MOCHILA, mochila);
		if (fase1 != -1)
			args.put(MAPA_KEY_FASE1, fase1);
		if (fase2 != -1)
			args.put(MAPA_KEY_FASE2, fase2);
		if (fase3 != -1)
			args.put(MAPA_KEY_FASE3, fase3);
		if (fase4 != -1)
			args.put(MAPA_KEY_FASE4, fase4);

		return mDb.update(DATABASE_TABLE_INFO, args, INFO_KEY_PLAYER + "="
				+ rowId, null) > 0;
	}
}
