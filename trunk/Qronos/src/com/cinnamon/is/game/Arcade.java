//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: QRonos
// ASIGNATURA : Ingeniería del Software
//

package com.cinnamon.is.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Conexion;
import com.cinnamon.is.comun.DbAdapter;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Props;
import com.cinnamon.is.comun.dialog.Dialogos;

/**
 * Pantalla principal del arcade de la aplicacion
 * 
 * @author Cinnamon Team
 * @version 0.6 16.04.2012
 */
public class Arcade extends Activity implements View.OnClickListener,
		DialogInterface.OnClickListener {

	// interfaz
	private LinearLayout llArcade, llArcadeActionBar, llArcadeBottomBar;
	private ImageView iVinfo;
	private ImageView iVconexion;
	private ImageView iBseeSc, iBright, iBleft, iBupSc;
	private ImageButton[] iBmj;
	private ImageView[] iVsc;
	private TextView tVhello;
	private Menu menuArcade;
	// private ImageButton iBmj1,iBmj2,iBmj3,iBmj4,iBmj5,iBmj6;
	// private ImageView iBsc1,iBsc2,iBsc3,iBsc4,iBsc5,iBsc6;

	/**
	 * Indica grupo de mjs mostrado
	 */
	private int grupoMJ;

	/**
	 * Controla la habilitacion de transicion a izquierda y derecha
	 */
	private boolean der, izq;

	/**
	 * Variable auxiliar referencia a ultimo dialog usado
	 */
	private AlertDialog aDactual;

	/**
	 * Vista pulsada en onClick para uso en dialog onclick
	 */
	private int vClicked;
	/**
	 * DbAdapter para interaccionar con la base de datos
	 */
	private DbAdapter mDbHelper;

	/**
	 * Jugador actual en la aplicacion
	 */
	private Jugador jugador;
	/**
	 * Para saber que item del menu clickeado
	 */
	private int iClicked;
	/**
	 * Lanzador auxiliar
	 */
	public Launch l;
	/**
	 * Conexion
	 */
	public Conexion conexion;
	/**
	 * Usamos para indicar si el sonido se reproduce o no
	 */
	private boolean sonido;
	/**
	 * SharedPreferencias de la aplicacion
	 */
	private SharedPreferences getData;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arcade);
		Bundle b = getIntent().getExtras();
		jugador = (Jugador) b.getSerializable(Props.Comun.JUGADOR);
		getData = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		sonido = getData.getBoolean(Props.Comun.CB_SONIDO, true);
		grupoMJ = 0;
		conexion = new Conexion(this);
		l = new Launch(this);
		inicializar();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mDbHelper.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Props.Comun.ONLINE) {
			iVconexion.setImageResource(R.drawable.ic_conexion_on_24);
			iBseeSc.setOnClickListener(this);
		} else {
			iVconexion.setImageResource(R.drawable.ic_conexion_off_24);
			// deshabilitar,cambiar color, etc
			iBseeSc.setOnClickListener(null);
		}
		
		if (!mDbHelper.isOpen()) {
			mDbHelper.open(false);
		}
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		if (resultCode == RESULT_OK) {
			Bundle b = data.getExtras();
			if (!mDbHelper.isOpen())
				mDbHelper.open(false);
			int score = b.getInt(Props.Comun.SCORE);
			boolean superado = b.getBoolean(Props.Comun.SUPERADO);
			switch (requestCode) {
			case Props.Comun.cmj1:
				int indice = Props.Comun.cmj1 - 1;
				if (score > jugador.getScore(indice) && superado) {
					jugador.setScore(score, indice);
					mDbHelper.updateRowParcade(jugador.getNombre(),
							jugador.getScore());
					actualizarDatos();
				}
				break;
			case Props.Comun.cmj2:
				int indice2 = Props.Comun.cmj2 - 1;
				if (score > jugador.getScore(indice2) && superado) {
					jugador.setScore(score, indice2);
					mDbHelper.updateRowParcade(jugador.getNombre(),
							jugador.getScore());
					actualizarDatos();
				}
				break;
			case Props.Comun.cmj3:
				int indice3 = Props.Comun.cmj3 - 1;
				if (score > jugador.getScore(indice3) && superado) {
					jugador.setScore(score, indice3);
					mDbHelper.updateRowParcade(jugador.getNombre(),
							jugador.getScore());
					actualizarDatos();
				}
				break;
			case Props.Comun.cmj4:
				int indice4 = Props.Comun.cmj4 - 1;
				if (score > jugador.getScore(indice4) && superado) {
					jugador.setScore(score, indice4);
					mDbHelper.updateRowParcade(jugador.getNombre(),
							jugador.getScore());
					actualizarDatos();
				}
				break;
			case Props.Comun.cmj5:
				int indice5 = Props.Comun.cmj5 - 1;
				if (score > jugador.getScore(indice5) && superado) {
					jugador.setScore(score, indice5);
					mDbHelper.updateRowParcade(jugador.getNombre(),
							jugador.getScore());
					actualizarDatos();
				}
				break;
			case Props.Comun.cmj6:
				int indice6 = Props.Comun.cmj6 - 1;
				if (score > jugador.getScore(indice6) && superado) {
					jugador.setScore(score, indice6);
					mDbHelper.updateRowParcade(jugador.getNombre(),
							jugador.getScore());
					actualizarDatos();
				}
				break;
			case Props.Comun.cmj7:
				int indice7 = Props.Comun.cmj7 - 1;
				if (score > jugador.getScore(indice7) && superado) {
					jugador.setScore(score, indice7);
					mDbHelper.updateRowParcade(jugador.getNombre(),
							jugador.getScore());
					actualizarDatos();
				}
				break;
			case Props.Comun.cmj8:
				int indice8 = Props.Comun.cmj8 - 1;
				if (score > jugador.getScore(indice8) && superado) {
					jugador.setScore(score, indice8);
					mDbHelper.updateRowParcade(jugador.getNombre(),
							jugador.getScore());
					actualizarDatos();
				}	
				break;
			case Props.Comun.cmj9:
				break;
			case Props.Comun.cmj10:
				break;
			case Props.Comun.cmj11:
				break;
			case Props.Comun.cmj12:
				break;
			}
			if (mDbHelper.isOpen())
				mDbHelper.close();
			if (superado) {
				l.lanzaAviso(Props.Strings.RESULTADO_MJ_COMPLETO,
						"Puntuacion obtenida: " + score,
						R.drawable.ic_dialog_checkmark_24);
			} else {
				l.lanzaAviso(
						Props.Strings.RESULTADO_MJ_INCOMPLETO,
						"No has completado el MJ, no se guardara tu puntuacion.",
						R.drawable.ic_dialog_delete_24);
			}

		}

	}

	/**
	 * Metodo de utilidad para inicializar la Actividad
	 */
	private void inicializar() {
		// asigna el menu
		// menuArcade = findViewById(R.menu.menu_arcade);

		// abre base de datos
		mDbHelper = new DbAdapter(this);
		mDbHelper.open(false);

		// genericos
		llArcade = (LinearLayout) findViewById(R.id.ll_arcade);
		llArcadeActionBar = (LinearLayout) findViewById(R.id.ll_action_bar);
		llArcadeBottomBar = (LinearLayout) findViewById(R.id.ll_arcade_bottom_bar);
		iVinfo = (ImageView) findViewById(R.id.iBinfoArcade);
		iVconexion = (ImageView) findViewById(R.id.iBinfoConexion);
		iBupSc = (ImageView) findViewById(R.id.iBupSc);
		iBseeSc = (ImageView) findViewById(R.id.iBseeSc);
		iBleft = (ImageView) findViewById(R.id.iBleft);
		iBright = (ImageView) findViewById(R.id.iBright);
		tVhello = (TextView) findViewById(R.id.tVhello);

		// Opacidad
		llArcade.getBackground().setAlpha(75);
		llArcadeActionBar.getBackground().setAlpha(175);
		llArcadeBottomBar.getBackground().setAlpha(175);

		iVinfo.setAlpha(150);
		iBleft.setAlpha(150);
		iBright.setAlpha(150);
		iBseeSc.setAlpha(150);
		iBupSc.setAlpha(150);

		// establezco Listeners
		// arcade.setOnTouchListener(this);
		iVinfo.setOnClickListener(this);
		iBleft.setOnClickListener(this);
		iBright.setOnClickListener(this);
		iBupSc.setOnClickListener(this);
		iVconexion.setOnClickListener(this);
		
		// rellena texto de bienvenida
		tVhello.setText(tVhello.getText() + " " + jugador.getNombre() + "!"
				+ "\nPuntuacion total: " + jugador.getScoreTotal());

		// creo arrays para minijuegos
		iBmj = new ImageButton[Props.Comun.MAX_MJ_P];
		iVsc = new ImageView[Props.Comun.MAX_MJ_P];

		// inicializo arrays
		for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
			iBmj[i] = (ImageButton) findViewById(Props.Comun.iDiBmj[i]);
			iVsc[i] = (ImageView) findViewById(Props.Comun.iDiVsC[i]);

			// imagenes de mj
			iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[i]);
			if (Props.Comun.bHabilitado(Props.Comun.iDiVmj[i])) {
				iBmj[i].setOnClickListener(this);
				iVsc[i].setImageResource(Props.Comun.getStar(jugador
						.getScore(i)));
			} else {
				iVsc[i].setImageResource(Props.Comun.iDiVstar[4]);
			}

		}
		// habilito flechas
		iBleft.setEnabled(false);
		iBright.setEnabled(true);
		der = true;
		izq = false;

	}

	/**
	 * Establece el grupo de minijuegos a mostrar en la pantalla de arcade
	 * 
	 * @param actual
	 *            el grupo a activar
	 */
	private void habilitarGrupoMJ(final int actual) {
		grupoMJ = actual;
		switch (grupoMJ) {
		case 0:
			for (int i = 0; i < Props.Comun.MAX_MJ_P; i++) {
				// imagenes de mj
				iBmj[i].setBackgroundResource(Props.Comun.iDiVmj[i]);
				// iVsc[i].setImageResource(Props.Comun.getStar(jugador.getScore(i)));
				if (Props.Comun.bHabilitado(Props.Comun.iDiVmj[i])) {
					iBmj[i].setOnClickListener(this);
					iVsc[i].setImageResource(Props.Comun.getStar(jugador
							.getScore(i)));
				} else {
					iBmj[i].setOnClickListener(null);
					iVsc[i].setImageResource(Props.Comun.iDiVstar[4]);
				}
			}
			// habilito transicion derecha
			iBleft.setEnabled(false);
			izq = false;
			iBright.setEnabled(true);
			der = true;
			break;
		case 1:
			for (int j = 0, i = Props.Comun.MAX_MJ_P; i < Props.Comun.MAX_MJ_P * 2; i++) {
				// imagenes de mj
				iBmj[j].setBackgroundResource(Props.Comun.iDiVmj[i]);
				if (Props.Comun.bHabilitado(Props.Comun.iDiVmj[i])) {
					iBmj[j].setOnClickListener(this);
					iVsc[j].setImageResource(Props.Comun.getStar(jugador
							.getScore(i)));
				} else {
					iBmj[j].setOnClickListener(null);
					iVsc[j].setImageResource(Props.Comun.iDiVstar[4]);
				}
				if (j < 6) {
					j++;
				}
			}
			// habilito transicion izquierda
			iBleft.setEnabled(true);
			izq = true;
			iBright.setEnabled(false);
			der = false;
			break;
		}

	}

	/**
	 * Metodo para subir puntuaciones al servidor
	 */
	private void subirScores() {
		l.lanzaDialogoEsperaUpdateScoreArcade(jugador.getNombre(),
				jugador.getScore());
		
	}

	public void onClick(final DialogInterface dialog, final int boton) {
		if (aDactual == dialog) {
			aDactual = null;
			switch (boton) {
			case 0:
				tVhello.setText("pulsado continuar");
				break;
			case 1:
				tVhello.setText("pulsado reiniciar");
				break;
			case 2:
				tVhello.setText("pulsado salir");
				break;
			}
		} else {
			switch (boton) {
			case -1:// yes
				dialog.cancel();
				switch (vClicked) {
				case R.id.iBupSc:
					subirScores();
					break;
				}
				switch (iClicked) {
				case R.id.bMenuReset:
					jugador.resetArcade();
					mDbHelper.updateRowParcade(this.jugador.getNombre(),
							this.jugador.getScore());
					Launch.lanzaAviso("Arcade Reseteado!", this);
					actualizarDatos();
					break;
				case R.id.bMenuCambiaUser:
					Props.Comun.ACTIVIDAD.finish();// cierar mainMenu
					Props.Comun.ACTIVIDAD = null;
					SharedPreferences.Editor e = getData.edit();
					e.putBoolean(Props.Comun.CB_LOGIN_AUTO, false);
					e.commit();
					finish();
					Launch.lanzaActivity(this, Props.Action.LOGIN);
					break;
				case R.id.bMenuSalir:
					// Para que actue como salir usar estas 3 lineas
					// Props.Comun.ACTIVIDAD.finish();
					// Props.Comun.ACTIVIDAD=null;
					// finish();
					SharedPreferences.Editor editor = getData.edit();
					if (sonido) {
						sonido = false;
						editor.putBoolean(Props.Comun.CB_SONIDO, sonido);
						editor.commit();
					} else {
						sonido = true;
						editor.putBoolean(Props.Comun.CB_SONIDO, sonido);
						editor.commit();
					}
					break;
				}
			case -2:// no
				dialog.cancel();
				break;
			}
		}
	}

	public void onClick(final View v) {
		switch (vClicked = v.getId()) {
		// Botones
		case R.id.iBleft:
			habilitarGrupoMJ(grupoMJ - 1);
			break;
		case R.id.iBright:
			habilitarGrupoMJ(grupoMJ + 1);
			break;
		case R.id.iBinfoArcade:
			Launch.lanzaAviso("Información de arcade", Props.Strings.iArcade,
					this);
			break;
		case R.id.iBseeSc:
			if (Props.Comun.ONLINE) {
				l.lanzaDialogoEsperaVerRankingArcade(jugador);
			} else {
				Bundle b = new Bundle();
				b.putSerializable(Props.Comun.JUGADOR, jugador);
				b.putSerializable(Props.Comun.ARCADE_DATA, "Arcade");
				l.lanzaActivity(Props.Action.RANKING, b);
			}

			break;
		case R.id.iBupSc:
			Launch.lanzaConfirmacion("Confirmaci—n para subir puntuaciones de "
					+ jugador.getNombre(), Props.Strings.upSc, this);
			break;

		case R.id.iBinfoConexion:
			if (Props.Comun.ONLINE){
				Launch.lanzaToast(this, "Modo de juego ONLINE");
			}else{
				Launch.lanzaToast(this, "Modo de juego OFFLINE");
			}
			break;
		// Botones de mjs
		case R.id.iBmj1:
			if (grupoMJ == 0) {
				Launch.lanzaConfirmacion(this, 0, l, Dialogos.DIALOG_ARCADE);
			} else if (grupoMJ == 1) {
				Launch.lanzaConfirmacion(this, 6, l, Dialogos.DIALOG_ARCADE);
			}
			break;
		case R.id.iBmj2:
			if (grupoMJ == 0) {
				Launch.lanzaConfirmacion(this, 1, l, Dialogos.DIALOG_ARCADE);
			} else if (grupoMJ == 1) {
				Launch.lanzaConfirmacion(this, 7, l, Dialogos.DIALOG_ARCADE);
			}
			break;
		case R.id.iBmj3:
			if (grupoMJ == 0) {
				Launch.lanzaConfirmacion(this, 2, l, Dialogos.DIALOG_ARCADE);
			} else if (grupoMJ == 1) {
				Launch.lanzaConfirmacion(this, 8, l, Dialogos.DIALOG_ARCADE);
			}
			break;
		case R.id.iBmj4:
			if (grupoMJ == 0) {
				Launch.lanzaConfirmacion(this, 3, l, Dialogos.DIALOG_ARCADE);
			} else if (grupoMJ == 1) {
				Launch.lanzaConfirmacion(this, 9, l, Dialogos.DIALOG_ARCADE);
			}
			break;
		case R.id.iBmj5:
			if (grupoMJ == 0) {
				Launch.lanzaConfirmacion(this, 4, l, Dialogos.DIALOG_ARCADE);
			} else if (grupoMJ == 1) {
				Launch.lanzaConfirmacion(this, 10, l, Dialogos.DIALOG_ARCADE);
			}
			break;
		case R.id.iBmj6:
			if (grupoMJ == 0) {
				Launch.lanzaConfirmacion(this, 5, l, Dialogos.DIALOG_ARCADE);
			} else if (grupoMJ == 1) {
				Launch.lanzaConfirmacion(this, 11, l, Dialogos.DIALOG_ARCADE);
			}
			break;
		}
	}

	// TODO ontouch desactivado
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // funciona pero si lo haces por el centro de la pantalla, donde esta el
	// // layout arcade, y no siempre
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_MOVE:
	// float x1,
	// x2,
	// dif,
	// lim = llarcade.getWidth() / 4;
	// ;
	//
	// x1 = event.getHistoricalX(0);
	// x2 = event.getX();
	// dif = x1 - x2;
	// if (!izq && der && dif > 0 && dif > lim)
	// habilitarGrupoMJ(grupoMJ + 1);
	// else if (izq && !der && dif < 0 && Math.abs(dif) > lim)
	// habilitarGrupoMJ(grupoMJ - 1);
	// break;
	// }
	// return true;
	// }

	/**
	 * Metodo para que una vez se actualice un score del jugador recargue
	 * visualmente (estrellas) su nueva puntuacion lograda al pasarse el MJ
	 */
	private void actualizarDatos() {

		tVhello.setText("Hola!" + " " + jugador.getNombre() + "!"
				+ "\nPuntuacion total: " + jugador.getScoreTotal());

		habilitarGrupoMJ(grupoMJ);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_arcade, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// Handle item selection
		switch (iClicked = item.getItemId()) {
		case R.id.bMenuReset:
			Launch.lanzaConfirmacion("¿Estás seguro?",
					"Confirma que quieres resetear todas tus puntuaciones!!",
					this);
			return true;
		case R.id.bMenuCambiaUser:
			Launch.lanzaConfirmacion("¿Estás seguro?",
					"Volverás a la pantalla de login!", this);
			return true;
		case R.id.bMenuSalir:
			Launch.lanzaConfirmacion("¿Estás seguro?",
					sonido ? "¿Desactivar sonido?" : "¿Activar sonido?", this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
