//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: TuringApp
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.cinnamon.is.comun.dialog.Dialogos;
import com.cinnamon.is.game.Jugador;

/**
 * Actividad abtracta que representa un minijuego
 * 
 * @author Cinnamon Team
 * @version 1.5 14.03.2012
 */
public abstract class Minijuego extends Activity {

	/**
	 * GameView del MJ
	 */
	protected SurfaceView game;
	/**
	 * GameLoop del MJ
	 */
	protected Thread run;
	/**
	 * El nombre del minijuego
	 */
	protected String nombre;

	/**
	 * Indica si el minijuego ha sido completado
	 */
	protected boolean superado;

	/**
	 * Modo de juego (Arcade o Aventura)
	 */
	protected int modo;
	/**
	 * Maxima puntuacion
	 */
	protected final int MAX_SCORE = 1000;

	/**
	 * Maximo tiempo de juego en segundos (5minutos)
	 */
	protected final long MAX_TIME = 300;

	/**
	 * Variables para controlar el tiempo
	 */
	protected long start, elapsed;

	/**
	 * Variable que indica la fase del minijuego
	 */
	protected int fase;

	/**
	 * Variables para pasar de nanosegundos a segundos y viceversa
	 */
	protected final static double tos = 0.000000001;
	protected final static double tons = 1000000000;

	/**
	 * Inicia el contador de tiempo del minijuego
	 */
	protected void startTime() {
		start = System.nanoTime();
	}

	/**
	 * Detiene el contador de tiempo del minijuego y obtiene el tiempo
	 */
	protected void finishTime() {
		elapsed = System.nanoTime() - start;
	}

	/**
	 * Calcula la puntuacion del jugador
	 * 
	 * @return la puntuacion del jugador en base al tiempo que ha tardado
	 */
	protected int calcularPuntuacion() {
		// pasa el tiempo a segundos
		double elapsedS = elapsed * tos;
		int score = MAX_SCORE;
		// tiempos de prueba para probar la aplicacion, habría que mirar cuando
		// se tarda en cada uno, o dejarlo para todos igual
		if (elapsedS < 60)// 60segundos
			return score;
		else if (elapsedS >= 60 && elapsedS < 120)
			return score - 200;
		else if (elapsedS >= 120 && elapsedS < 240)
			return score - 400;
		else if (elapsedS >= 240 && elapsedS < 300)
			return score - 600;
		else if (elapsedS >= 300 && elapsedS < 360)
			return score - 800;
		else
			return 0;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getExtras();
		Boolean bool = (Boolean) b.getBoolean(Props.Comun.MODO);
		if (bool)
			modo = Dialogos.DIALOG_ARCADE;
		else modo = Dialogos.DIALOG_AVENTURA;
	}


	/**
	 * <p>
	 * Metodo que finaliza el minijuego, la puntuacion se obtiene mediante el
	 * metodo calcularPuntuacion(). Por defecto hace el calculo de puntuacion en
	 * base al tiempo que se ha tardado en completar el minijuego.
	 * </p>
	 * <p>
	 * Debe calcular la puntuacion obtenida, obtener si se ha superado o no,
	 * empaquetando esta informacion en un bundle y enviandolo de vuelva a la
	 * actividad que lanzo el minijuego mediante el metodo setResult() de
	 * Activity.
	 * </p>
	 * <p>
	 * Se hace uso de la clase de utilidad Launch para terminar la actividad y
	 * devolver el resultado.
	 * </p>
	 * 
	 * @see #setResult(int, android.content.Intent)
	 * @see Launch#returnActivity(Activity, Bundle, int)
	 * @see Props.Comun
	 * @param s
	 *            si se ha superado completamente o no
	 */
	public void finalizar(boolean s) {
		// Para tiempo
		finishTime();
		// Establece valores de puntuacion y superado
		int puntuacion = calcularPuntuacion();
		superado = s;
		// Creo el bundle con la info usando strings genericos de clase
		// Props.Comun
		Bundle b = new Bundle();
		b.putInt(Props.Comun.SCORE, puntuacion);
		b.putBoolean(Props.Comun.SUPERADO, superado);
		// Devuelvo resultado a actividad padre
		Launch.returnActivity(this, b, RESULT_OK);
	}

	/**
	 * Metodo que reinicia el minijuego, a implementar en subclases
	 */
	public void reiniciar() {
		// Deberá llamarse cuando se pulse el boton de reiniciar del dialog
		// opciones del minijuego
	}

	/**
	 * Metodo que para el minijuego, a implementar en subclases
	 */
	public void parar() {
		// Para minijuegos que tengan la necesidad de pararse previo lanzamiento
		// del menu de opciones
	}

	/**
	 * Metodo que lanza el dialog de opciones
	 */
	protected void lanzaOpcionesDialog() {
		// TODO Ahora mismo muestra 3 opciones, continuar, reiniciar o salir
		// para el mj y luego lanza las opciones
		// Launch.lanzaConfirmacion("Salir del minijuego",
		// "¿Quieres salir del minijuego sin completarlo?", this);
		parar();
		Launch.lanzaOpciones(this, "Juego Pausado", modo, this);
	}

	// @Override
	// public void onClick(DialogInterface dialog, int boton) {
	// switch (boton) {
	// case 0:
	// dialog.cancel();
	// break;
	// case 1:
	// dialog.cancel();
	// reiniciar();
	// break;
	// case 2:
	// dialog.cancel();
	// finalizar(false);
	// break;
	// }
	// }


	@Override
	public void onBackPressed() {
		lanzaOpcionesDialog();
	}

	// getters & setters
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isSuperado() {
		return superado;
	}

	public void setSuperado(boolean superado) {
		this.superado = superado;
	}

	public int getFase() {
		return fase;
	}

	public void setFase(int fase) {
		this.fase = fase;
	}

	public void setSV(SurfaceView sv) {
		this.game = sv;
	}

	public void resumir() {
		this.onResume();
	}

}
