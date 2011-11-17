package com.cinnamon.is;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Este adaptador nos permite acceder a la base de datos y modificarla
 */
public class DbAdapter {

	// Nombre BD
	private static final String DATABASE_NAME = "data";
	// Nombres TABLAS
	private static final String DATABASE_TABLE_INFO = "info";
	private static final String DATABASE_TABLE_MAPA = "mapa";
	// Version database
	private static final int DATABASE_VERSION = 2;

	// Campos tablas info
	public static final String INFO_KEY_PLAYER = "player";
	public static final String INFO_KEY_SCORE = "score";
	public static final String INFO_KEY_HOJA = "hoja";
	//Codificacion mochila
	// 1-papel 1,2-papel 2,3-papel 3,4-makina,5-papel 1 y 2,6-papel 1 y 3,
	// 7-papel 2 y 3,8-papel 1,2,3 y makina
	public static final String INFO_KEY_MOCHILA = "mochila";

	// Campos tablas mapa
	public static final String MAPA_KEY_FASE1 = "fase1";
	public static final String MAPA_KEY_FASE2 = "fase2";
	public static final String MAPA_KEY_FASE3 = "fase3";
	public static final String MAPA_KEY_FASE4 = "fase4";

	// array con los campos de la tabla info
	private static final String[] infoCampos = new String[] { INFO_KEY_PLAYER,
			INFO_KEY_SCORE, INFO_KEY_HOJA, INFO_KEY_MOCHILA, MAPA_KEY_FASE1,
			MAPA_KEY_FASE2, MAPA_KEY_FASE3, MAPA_KEY_FASE4 };

	// array con los campos de la tabla mapa
	// private static final String[] mapaCampos = new String[] {
	// INFO_KEY_PLAYER,
	// MAPA_KEY_FASE1, MAPA_KEY_FASE2, MAPA_KEY_FASE3, MAPA_KEY_FASE4 };
	// TAG para el log
	private static final String TAG = "DbAdapter";

	// Clases para tratar la base de datos
	private DbHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;

	// Strings para clarificar SQL info
	public static final String SQLplayer = INFO_KEY_PLAYER
			+ " text primary key,";
	public static final String SQLscore = INFO_KEY_SCORE + " integer not null,";
	public static final String SQLhoja = INFO_KEY_HOJA + " integer not null,";
	public static final String SQLmochila = INFO_KEY_MOCHILA
			+ " integer not null,";

	// Strings para clarificar SQL mapa
	public static final String SQLfase1 = MAPA_KEY_FASE1 + " integer not null,";
	public static final String SQLfase2 = MAPA_KEY_FASE2 + " integer not null,";
	public static final String SQLfase3 = MAPA_KEY_FASE3 + " integer not null,";
	public static final String SQLfase4 = MAPA_KEY_FASE4
			+ " integer not null);";

	/**
	 * Sentencia SQL para crear la tabla info (con fases mapas)
	 */
	private static final String TABLE_INFO_CREATE = "create table "
			+ DATABASE_TABLE_INFO + "( " + SQLplayer + SQLscore + SQLhoja
			+ SQLmochila + SQLfase1 + SQLfase2 + SQLfase3 + SQLfase4;

	/**
	 * Sentencia SQL para crear la tabla mapa
	 */
	// private static final String TABLE_MAPA_CREATE = "create table "
	// + DATABASE_TABLE_MAPA + "( " + SQLplayer + SQLfase1 + SQLfase2
	// + SQLfase3 + SQLfase4;

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
	 * una excepci—n
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
	 * @param mapa
	 *            posicion del jugador en el mapa
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
	public boolean deleteNote(String rowId) {
		return mDb.delete(DATABASE_TABLE_INFO, INFO_KEY_PLAYER + "=" + rowId,
				null) > 0;
	}

	/**
	 * Devuelve un Cursor apuntando a la lista de todas las filas
	 * 
	 * @return Cursor de todas las filas
	 */
	public Cursor fetchAllRows() {

		return mDb.query(DATABASE_TABLE_INFO, infoCampos, null, null, null,
				null, null);
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

		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE_INFO, infoCampos, INFO_KEY_PLAYER + "="
				+ rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

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
	public boolean updateRow(String rowId, int score, int mapa, int hoja,
			int mochila, int fase1, int fase2, int fase3, int fase4) {
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

		return mDb.update(DATABASE_TABLE_INFO, args, rowId + "=" + rowId, null) > 0;
	}
}
