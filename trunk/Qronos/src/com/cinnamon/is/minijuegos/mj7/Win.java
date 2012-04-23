package com.cinnamon.is.minijuegos.mj7;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Minijuego;


public class Win extends Minijuego {

	private Button Comprueba;
	private EditText palabra;
	private String pal;
	private int tiempo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.finletqr);

		// pal="";
		palabra = (EditText) findViewById(R.id.entry);
		Comprueba = (Button) findViewById(R.id.Comprobar);
		Comprueba.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				//este texto es el que coge mal
				pal = palabra.getText().toString();

				// si la palabra es correcta lanzamos el Intent correcto
				if ((pal.equals("rapido")) || (pal.equals("Rapido"))
						|| (pal.equals("r�pido")) || (pal.equals("R�pido"))
						|| (pal.equals("RAPIDO")) || (pal.equals("R�PIDO"))) {

					Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        			vibr.vibrate(300);
        			//poner aqui un mensaje que pueda leer el usuario con el tiempo
        			//que ha tardado
        			//texto.setTextColor(Color.rgb(0,221,0));
        			//texto.setText("�BIEN!\nYa has conseguido el noveno c�digo, �corre al �ltimo!");
        			//TODO hay que acabar este m�todo	
					finalizar(true);
        			
				} 

			}
		});

	}

	protected int calcularPuntuacion() {
		int score = MAX_SCORE;
		tiempo = (int) (elapsed * tos);
		// tiempos de prueba para probar la aplicacion, habr�a que mirar cuando
		// se tarda en cada uno, o dejarlo para todos igual
		if (tiempo < 60)// 60segundos
			return score;
		else if (tiempo >= 60 && tiempo < 70)
			return score - 200;
		else if (tiempo >= 70 && tiempo < 80)
			return score - 400;
		else if (tiempo >= 80 && tiempo < 90)
			return score - 600;
		else if (tiempo >= 90 && tiempo < 100)
			return score - 800;
		else
			return 0;
	}

}

