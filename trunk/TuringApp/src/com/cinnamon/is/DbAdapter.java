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
	// Version database
	private static final int DATABASE_VERSION = 2;

	// Campos tablas
	public static final String INFO_KEY_PLAYER = "player";
	public static final String INFO_KEY_SCORE = "score";
	public static final String INFO_KEY_MAPA = "mapa";
	public static final String INFO_KEY_HOJA = "hoja";
	public static final String INFO_KEY_MOCHILA = "mochila";

	// array con los campos de la tabla info
	private static final String[] infoCampos = new String[] { INFO_KEY_PLAYER,
			INFO_KEY_SCORE, INFO_KEY_MAPA, INFO_KEY_HOJA, INFO_KEY_MOCHILA };
	// TAG para el log
	private static final String TAG = "DbAdapter";

	// Clases para tratar la base de datos
	private DbHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;

	// Strings para clarificar SQL
	public static final String SQLplayer = INFO_KEY_PLAYER
			+ " text primary key,";
	public static final String SQLscore = INFO_KEY_SCORE + " integer not null,";
	public static final String SQLmapa = INFO_KEY_MAPA + " integer not null,";
	public static final String SQLhoja = INFO_KEY_HOJA + " integer not null,";
	public static final String SQLmochila = INFO_KEY_MOCHILA
			+ " integer not null);";

	/**
	 * Sentencia SQL para crear la base de datos (crear las tablas)
	 */
	private static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE_INFO + "( " + SQLplayer + SQLscore + SQLmapa
			+ SQLhoja + SQLmochila;

	private static class DbHelper extends SQLiteOpenHelper {

		DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
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
	 * @return rowId o -1 si ha fallado
	 */
	public long createRow(String player, int score, int mapa, int hoja,
			int mochila) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(INFO_KEY_PLAYER, player);
		initialValues.put(INFO_KEY_SCORE, score);
		initialValues.put(INFO_KEY_MAPA, mapa);
		initialValues.put(INFO_KEY_HOJA, hoja);
		initialValues.put(INFO_KEY_MOCHILA, mochila);

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
	 * @param mapa
	 *            posicion del jugador en el mapa
	 * @param hoja
	 *            hoja actual del libro del jugador
	 * @param mochila
	 *            elementos que ha conseguido el jugador
	 * @return true si la fila se ha editado correctamente, false en caso
	 *         contrario
	 */
	public boolean updateRow(String rowId, int score, int mapa, int hoja,
			int mochila) {
		ContentValues args = new ContentValues();
		if (score != -1)
			args.put(INFO_KEY_SCORE, score);
		if (mapa != -1)
			args.put(INFO_KEY_MAPA, mapa);
		if (hoja != -1)
			args.put(INFO_KEY_HOJA, hoja);
		if (mochila != -1)
			args.put(INFO_KEY_MOCHILA, mochila);

		return mDb.update(DATABASE_TABLE_INFO, args, rowId + "=" + rowId, null) > 0;
	}
}
