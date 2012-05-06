package com.cinnamon.is.minijuegos.mj7;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Minijuego;


public class Win extends Minijuego {

	private Button Comprueba;
	private EditText palabra;
	private String pal;
	private int tiempo;
	private long total;
	private long inicio;
	private TextView texto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.finletqr);

		palabra = (EditText) findViewById(R.id.entry);
		Comprueba = (Button) findViewById(R.id.Comprobar);
		texto = (TextView) findViewById(R.id.textViewWin);
		final Launch l=new Launch(this);
		Comprueba.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				pal = palabra.getText().toString();
				texto.setText("");

				// si la palabra es correcta
				if ((pal.equals("rapido")) || (pal.equals("Rapido"))
						|| (pal.equals("rápido")) || (pal.equals("Rápido"))
						|| (pal.equals("RAPIDO")) || (pal.equals("RÁPIDO"))) {

					Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        			vibr.vibrate(300);
        			
        			Bundle bundle=new Bundle();
        			bundle=getIntent().getExtras();
        			inicio=bundle.getLong("inicio");
        			long fin=System.nanoTime();
        			total=fin-inicio;
        			int puntuacion=calcularPuntuacion();
        			Bundle b= new Bundle();
					b.putInt("puntuacion", puntuacion);
        			l.returnActivity(b, RESULT_OK);
				}
				else{
					texto.setTextColor(Color.RED);
					texto.setText("Esa no es la palabra que estamos buscando");
				}

			}
		});

	}

	protected int calcularPuntuacion() {
		int score = MAX_SCORE;
		tiempo = (int) (total * tos);
		// tiempos de prueba para probar la aplicacion, habría que mirar cuando
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

