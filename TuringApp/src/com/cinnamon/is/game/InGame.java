//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//

/**
 * Paquete para clases del juego
 */
package com.cinnamon.is.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Intents;

//NOTAS
//En cada minijuego se obtiene el jugador, y se modifica su puntuacion/inventario/mapa a nivel de clase,
//volviendo a enviar el jugador en el setResult al ingame.
//Esta modificacion se hace en base a los parametros del mj, el objeto k sea, la fase y la puntuacion 
//k se haya obtenido y solo si el minijuego se ha completado
/**
 * Actividad del juego principal, se mantiene en ejecución durante el desarrollo
 * del juego, llamando al resto de Actividades
 * 
 * @author Cinnamon Team
 * @version 1.6 13.12.2011
 */
public class InGame extends Activity implements OnClickListener, OnCompletionListener {

	/**
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	/**
	 * String para guardar el raw del codigo QR leido
	 */
	private String qrLeido;

	/**
	 * Constantes para controlar que actividad tratar en onActivityResult() en
	 * función de la actividad lanzada.
	 */
	private static final int cMAPA = 0;
	private static final int cCAMARA = 1;
	private static final int cMOCHILA = 2;
	private static final int cMINIJUEGO = 3;

	/**
	 * Contanstes para manjeas dialogos del ingame
	 */
	private static final int DIALOG_MINIJUEGOS_RESULT = 0;

	/**
	 * Jugador actual del juego
	 */
	private Jugador jugador;

	// private Button bOpciones;
	 private Button bReinas;
	 /**
	  * VideoView para reproducir el video 
	  */
	private VideoView vd;
	//private MediaController mc;
	/**
	 * URI con la dirección del Video
	 */
	private Uri uriVideo;

	// texview de prueba para comprobar que funciona lo de la puntuacion
	// private TextView tvScoreActual;

	/**
	 * Fase del juego en la que nos encontramos
	 */
	public int fase = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videosurface);
		inicializar();

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		
		fase = getFase();
		
		uriVideo = escogerVideo();
		inicializarVideo();
		

	}

	/**
	 * Inicializa el video con su correspondiente URI
	 * le añadimos visibilidad y lo traemos al frente
	 */
	private void inicializarVideo() {

		//La barra del play necesaria para depurar
		/*mc = new MediaController(this);
		vd.setMediaController(mc);*/
		 
		vd.setVideoURI(uriVideo);
		vd.bringToFront();
		vd.setVisibility(View.VISIBLE);
		vd.start();

	}
	/**
	 * Descodificamos la fase de jugador en la que nos encontramos
	 * @return devolvemos la fase en la que nos encontramos (0..5)
	 */
	private int getFase() {
		if ((jugador.getFase1() == 0) || (jugador.getFase1() == 0)
				|| (jugador.getFase1() == 0) || (jugador.getFase1() == 0))
			return 0;
		if (jugador.getFase1() == 2)
			return 1;
		if (jugador.getFase2() == 2)
			return 2;
		if (jugador.getFase3() == 2)
			return 3;
		if (jugador.getFase4() == 2)
			return 4;
		else
			return 5;

	}
	

	/**
	 * Seleccionamos una dirección del video según la fase en la que
	 * nos encontremos
	 * @return URI con la dirección del video a reproducir
	 */
	private Uri escogerVideo() {
		switch (fase){
		case 0 : return Uri.parse("android.resource://com.cinnamon.is/"
				+ R.raw.open_book);
				
		case 1 : return Uri.parse("android.resource://com.cinnamon.is/"
				+ R.raw.open_book);
				
		case 2 : return Uri.parse("android.resource://com.cinnamon.is/"
				+ R.raw.open_book);
				
		case 3 : return Uri.parse("android.resource://com.cinnamon.is/"
				+ R.raw.open_book);
				
		case 4 : return Uri.parse("android.resource://com.cinnamon.is/"
				+ R.raw.open_book);
				
		case 5 : return Uri.parse("android.resource://com.cinnamon.is/"
				+ R.raw.open_book);
								
		}
		return null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDbHelper.close();
	}

	/**
	 * Se ejecuta al volver de la aplicacion lanzada con forresult
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		boolean scanDirecto, mochilaDirecto, mapaDirecto;
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case cMAPA:
				scanDirecto = data.getBooleanExtra(Intents.Comun.INGAME_SCAN,
						false);
				mochilaDirecto = data.getBooleanExtra(Intents.Action.MOCHILA,
						false);
				jugador = (Jugador) data
						.getSerializableExtra(Intents.Comun.JUGADOR);
				updateJugador();
				if (scanDirecto)
					lanzaScan();
				else if (mochilaDirecto)
					lanzaMochila();
				else {
					// hacer nada
				}
				break;

			case cCAMARA:
				qrLeido = data.getStringExtra("SCAN_RESULT");
				lanzaMinijuego(qrLeido);
				break;
			case cMOCHILA:
				scanDirecto = data.getBooleanExtra(Intents.Comun.INGAME_SCAN,
						false);
				mapaDirecto = data.getBooleanExtra(Intents.Action.MAPA, false);
				jugador = (Jugador) data
						.getSerializableExtra(Intents.Comun.JUGADOR);
				updateJugador();
				if (scanDirecto)
					lanzaScan();
				else if (mapaDirecto)
					lanzaMapa();
				else {
					// hacer nada
				}
				break;
			case cMINIJUEGO:
				jugador = (Jugador) data
						.getSerializableExtra(Intents.Comun.JUGADOR);
				updateJugador();
				// Tratamiento resultado minijuego
				boolean superado = data.getBooleanExtra(Intents.Comun.superado,
						false);
				int objeto = data.getIntExtra(Intents.Comun.objeto, 0);

				// obtener resultados del minijuego
				String objStr = "",
					faseStr = "",
					textoDialog = "",
					title = "";
				int idIvDialog = 0;
				// en funcion del objeto actualizo las variables a pasar al
				// dialog
				switch (objeto) {
				case 1:
					objStr = "Papel 1";
					idIvDialog = R.drawable.papel1;
					faseStr = "Ascensor";
					break;
				case 2:
					objStr = "Papel 2";
					idIvDialog = R.drawable.papel2;
					faseStr = "Reinas";
					break;
				case 3:
					objStr = "Papel 3";
					idIvDialog = R.drawable.papel3;
					faseStr = "Puzzle";
					break;
				case 4:
					objStr = "Enigma";
					idIvDialog = R.drawable.enigma;
					faseStr = "Cuadrado";
					break;
				}

				if (superado) {
					textoDialog = "Minijuego " + faseStr
							+ " completado\nHas conseguido el objeto: "
							+ objStr + "\nTu puntuacion ahora es: "
							+ jugador.getScore();
					title = "Resultado Minijuego";
				} else {
					title = "Minijuego no superado";
					textoDialog = "Minijuego "
							+ faseStr
							+ " no completado\nTendrás que volver a scanear el QR para lanzar de nuevo el minijuego!";
				}
				Bundle dialogBundle = new Bundle();
				dialogBundle.putString("textoDialog", textoDialog);
				dialogBundle.putInt("idIvDialog", idIvDialog);
				dialogBundle.putBoolean(Intents.Comun.superado, superado);
				dialogBundle.putString("title", title);
				// lanzarAvisoMJ(textoDialog);
				showDialog(DIALOG_MINIJUEGOS_RESULT, dialogBundle);
				uriVideo = escogerVideo();
				inicializarVideo();
				break;
			}

		} else if (resultCode == RESULT_CANCELED) {
			// si no ha hecho nada
		}
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		Dialog dialog = null;
		switch (id) {

		case DIALOG_MINIJUEGOS_RESULT:
			// obtiene datos
			String textoDialog = bundle.getString("textoDialog");
			int idIvDialog = bundle.getInt("idIvDialog");
			boolean superado = bundle.getBoolean(Intents.Comun.superado);
			String title = bundle.getString("title");
			// crea dialog
			dialog = new Dialog(this);
			dialog.setContentView(R.layout.dialogimg);
			dialog.setTitle(title);
			// pone elementos
			TextView tvDialog = (TextView) dialog.findViewById(R.id.tvDialog);
			tvDialog.setText(textoDialog);
			ImageView ivDialog = (ImageView) dialog.findViewById(R.id.ivDialog);
			ivDialog.setImageResource(idIvDialog);
			if (!superado)
				ivDialog.setVisibility(View.GONE);
			// ivDialog.setImageResource(R.drawable.bonoff);
			Button bDialog = (Button) dialog.findViewById(R.id.bDialog);
			bDialog.setOnClickListener(this);
			break;
		}

		return dialog;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menuingame, menu);
		return true;// debe devolver true para que el menu se muestre
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.bCamara:
			lanzaScan();
			break;
		case R.id.bMapa:
			lanzaMapa();
			break;
		case R.id.bMochila:
			lanzaMochila();
			break;
		}
		return false;// devuelve falso para proceso normal
	}

	@Override
	public void onBackPressed() {
		lanzaExitDialog();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bOpciones:
			// El boton bOpciones se comporta como el boton fisico MENU del
			// dispositivo
			this.getWindow().openPanel(Window.FEATURE_OPTIONS_PANEL,
					new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MENU));
			break;
		case R.id.bReinas:
			Intent iReinas = new Intent(Intents.Action.REINASMJ);
			iReinas.putExtra(Intents.Comun.JUGADOR, jugador);
			startActivityForResult(iReinas, cMINIJUEGO);
			break;
		/*
		 * case R.id.bOpciones: // El boton bOpciones se comporta como el boton
		 * fisico MENU del // dispositivo
		 * this.getWindow().openPanel(Window.FEATURE_OPTIONS_PANEL, new
		 * KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MENU)); break; case
		 */ 
		case R.id.bDialog:
			dismissDialog(DIALOG_MINIJUEGOS_RESULT);
			break;
		}
	}

	/**
	 * Metodo auxiliar para inicializar la actividad
	 * de la base de datos y el VideoView
	 */
	private void inicializar() {
		// tvScoreActual = (TextView) findViewById(R.id.tVscoreActual);

		vd = (VideoView) findViewById(R.id.svVideo);
		vd.setOnCompletionListener(this);
		/*
		 * bOpciones = (Button) findViewById(R.id.bOpciones);
		 * bOpciones.setOnClickListener(this);
		 */

		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		// prueba lanzar juego
		
		  bReinas = (Button) findViewById(R.id.bReinas);
		  bReinas.setOnClickListener(this);
		 
	}

	/**
	 * Actualiza al jugador accediendo a la base de datos
	 */
	private void updateJugador() {
		mDbHelper.updateRow("'" + jugador.getNombre() + "'",
				jugador.getScore(), jugador.getHoja(), jugador.mochilaToInt(),
				jugador.getFase1(), jugador.getFase2(), jugador.getFase3(),
				jugador.getFase4());
		// pone el textview la puntuacion del jugador
		// tvScoreActual.setText(String.valueOf(jugador.getScore()));
	}

	/**
	 * Lanza la mochila
	 */
	private void lanzaMochila() {
		Intent iMochila = new Intent(Intents.Action.MOCHILA);
		iMochila.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivityForResult(iMochila, cMOCHILA);
	}

	/**
	 * Lanza el mapa
	 */
	private void lanzaMapa() {
		Intent iMapa = new Intent(Intents.Action.MAPA);
		iMapa.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivityForResult(iMapa, cMAPA);
	}

	/**
	 * Lanza el scaneo de codigos QR
	 */
	private void lanzaScan() {
		Intent iScan = new Intent(Intents.Action.SCAN);
		iScan.putExtra("SCAN_MODE", "QR_CODE_MODE");
		iScan.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivityForResult(iScan, cCAMARA);
	}

	/**
	 * Lanza el menu principal
	 */
	private void lanzaMainMenu() {
		Intent iMainMenu = new Intent(Intents.Action.MAINMENU);
		iMainMenu.putExtra(Intents.Comun.JUGADOR, jugador);
		startActivity(iMainMenu);
	}

	/**
	 * Lanza el minijuego del "qrMinijuego", comprobando que el
	 * codigoQR leido es correcto para lanzar la actividad asociada
	 * 
	 * @param qrMiniJuego
	 *            el nombre del minijuego a lanzar
	 */
	private void lanzaMinijuego(String qrMiniJuego) {

		for (int i = 0; i < Intents.Comun.minijuegos.length; i++) {
			// comprobacion de que existe el minijuego leido
			if (Intents.Comun.minijuegos[i].equals(qrMiniJuego)) {
				Intent iMinijuego = new Intent(Intents.Comun.BASE_MINIJUEGOS
						+ qrMiniJuego);
				iMinijuego.putExtra(Intents.Comun.JUGADOR, jugador);
				startActivityForResult(iMinijuego, cMINIJUEGO);
			}
		}
		// no se lanza el minijuego porque no se corresponde con uno que exista
	}

	/**
	 * Lanza el dialog para escoger si quieres salir al menu
	 * principal o no
	 */
	private void lanzaExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("¿Quieres salir al menu principal?")
				.setCancelable(false)
				.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						InGame.this.finish();
						lanzaMainMenu();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show();
	}

	// Metodo antiguo para lanzar el aviso de minijuego
	/**
	 * Lanza el Dialog para salida del minijuego
	 */
	private void lanzarAvisoMJ(String texto) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(texto).setNegativeButton("Cerrar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show();
	}
	/**
	 * Lanza un Dialog con un string dado
	 * @param texto string que queremos que se visualize en el dialog
	 */
	public void lanzarJerogrifico(String texto) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(texto).setNegativeButton("Cerrar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show();
	}
	/**
	 * Método heredado para saber que hacer al acabar el video
	 * Lanza un dialog segun la fase de la que provengamos
	 */
	@Override
	public void onCompletion(MediaPlayer mediaPlayer) {
		
		String texto="";
		switch (fase) {
		case 0:
			texto = "waaaaaaaaaaaa";
			lanzarJerogrifico(texto);
			break;
		case 1:
			texto = getString(R.string.Jerogrifico2);
			lanzarJerogrifico(texto);
			break;
		case 2:
			texto = getString(R.string.Jerogrifico3);
			lanzarJerogrifico(texto);
			break;
		case 3:
			texto = getString(R.string.Jerogrifico4);
			lanzarJerogrifico(texto);
			break;
		case 4:texto = getString(R.string.Jerogrifico4);
			lanzarJerogrifico(texto);
			break;
		case 5:
			String title ="Última fase",
			textoDialog = "Ha superado todas las pruebas ya sólo queda resolver el misterio";
			//falta poner bien la imagen
			int idIvDialog= R.drawable.papel3;
			Bundle dialogBundle = new Bundle();
			dialogBundle.putString("textoDialog", textoDialog);
			dialogBundle.putInt("idIvDialog", idIvDialog);
			dialogBundle.putString("title", title);
			showDialog(DIALOG_MINIJUEGOS_RESULT, dialogBundle);
			break;
		}

	}
	

}
