package com.cinnamon.is.minijuegos.mj5;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Minijuego;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Win extends Minijuego {

	private static final int MAX_SCORE = 1000;
	private long tiempo;
	private TextView texto;
	private int puntuacion;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aciertoqr);
        
        texto=(TextView) findViewById(R.id.textView);
       
        Bundle datos = this.getIntent().getExtras();
		tiempo = datos.getLong("tiempo");
		
		puntuacion=calcularPuntuacion();
		
		texto.setText("Has encontrado el c�digo en "+tiempo+" segundos, tu puntuaci�n es "+puntuacion+" puntos.");
		 
	}

	
	protected int calcularPuntuacion() {
		int score = MAX_SCORE;
		// tiempos de prueba para probar la aplicacion, habr�a que mirar cuando
		// se tarda en cada uno, o dejarlo para todos igual
		if (tiempo < 20)// 60segundos
			return score;
		else if (tiempo >= 20 && tiempo < 30)
			return score - 200;
		else if (tiempo >= 30 && tiempo < 40)
			return score - 400;
		else if (tiempo >= 40 && tiempo < 50)
			return score - 600;
		else if (tiempo >= 50 && tiempo < 60)
			return score - 800;
		else
			return 0;
	}
}
