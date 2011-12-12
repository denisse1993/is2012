package com.cinnamon.is.minijuegos.cuadrado;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Intents;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CuadradoMagicoMJIntro extends Activity {

	Button Continuar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.iniciocuadradomj);
		
		Continuar=(Button)findViewById(R.id.bContinuar);
		Continuar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
				 Intent openGame=new Intent(Intents.Action.CUADRADOMJ);
				 startActivity(openGame);
				}catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Me falta definir el método onClick para dentro ejecutar
	//la actividad que me lleve a CuadradomagicoActivity
	
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
}
