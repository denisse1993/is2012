package com.cinnamon.is.minijuegos.mj4;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Launch;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.comun.Props;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Win extends Minijuego {

	private static final int MAX_SCORE = 1000;
	private long tiempo;
	private TextView texto;
	private int puntuacion;
	private Button volver;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aciertoqr);
        
        texto=(TextView) findViewById(R.id.textView);
       
        Bundle datos = this.getIntent().getExtras();
		tiempo = datos.getLong("tiempo");
		puntuacion=datos.getInt(Props.Comun.SCORE);
		//puntuacion=calcularPuntuacion();
		
		texto.setText("Has encontrado el código en "+tiempo+" segundos, tu puntuación es "+puntuacion+" puntos.");
		
		volver=(Button) findViewById(R.id.volver);
		volver.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finalizar(true);
			}
		});
	}



	protected int calcularPuntuacion() {
		int score = MAX_SCORE;
		// tiempos de prueba para probar la aplicacion, habría que mirar cuando
		// se tarda en cada uno, o dejarlo para todos igual
		if (tiempo < 30)// 30segundos
			return score;
		else if (tiempo >= 30 && tiempo < 45)
			return score - 200;
		else if (tiempo >= 45 && tiempo < 60)
			return score - 400;
		else if (tiempo >= 60 && tiempo < 75)
			return score - 600;
		else if (tiempo >= 90 && tiempo < 105)
			return score - 800;
		else
			return 0;
	}
	
	protected void finalizar(boolean s) {
		// Para tiempo
		//finishTime();
		// Establece valores de puntuacion y superado
		//int puntuacion = calcularPuntuacion();
		superado = s;
		// Creo el bundle con la info usando strings genericos de clase
		// Props.Comun
		Bundle b = new Bundle();
		b.putInt(Props.Comun.SCORE, puntuacion);
		b.putBoolean(Props.Comun.SUPERADO, superado);
		// Devuelvo resultado a actividad padre
		//setResult(RESULT_OK, getIntent().putExtras(b));
		//finish();
		//finishActivity(Props.Comun.cmj4);
		Launch.returnActivity(this, b, RESULT_OK);
	}
	
}
