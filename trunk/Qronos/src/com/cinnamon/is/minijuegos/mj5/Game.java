package com.cinnamon.is.minijuegos.mj5;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Minijuego;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Game extends Minijuego {

	private Button btnQR;
	private TextView texto;
	//private UtilQR QR;
	private int tiempo;
	
	

	/**
	 * @param args
	 */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.inicioencqr);
        
        startTime();

        texto=(TextView) findViewById(R.id.textView);
        btnQR=(Button) findViewById(R.id.leer);
        //QR= new UtilQR(this);
        btnQR.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					//QR.lanzarQR();
					//lanza el scan del Barcode Scanner
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			        startActivityForResult(intent, 0);
					}catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
			}
	 
	        });
	    }
	 
	

	    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	 	   if (requestCode == 0) {
	 	      if (resultCode == RESULT_OK) {
	 	        String contents = intent.getStringExtra("SCAN_RESULT");
	 	         // Handle successful scan
	 	         //Si la lectura se realiza correctamente se llega aqui�
	 	         //Si el c�digo no es el c�digo objetivo, seguir intentandolo
	 	         if(!contents.equals("objetivo")){
	 	        	 texto.setText("�MAL!\nEse no es el c�digo que tienes que encontrar\n�SIGUE BUSCANDO!");
	 	        	Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
     			    vibr.vibrate(500);
	 	         }
	 	         if(contents.equals("objetivo")){
	 	        		Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	        			vibr.vibrate(300);
	        			//poner aqui un mensaje que pueda leer el usuario con el tiempo
	        			//que ha tardado
	        			texto.setTextColor(Color.rgb(0,221,0));
	        			texto.setText("�BIEN!\nYa has conseguido el noveno c�digo, �corre al �ltimo!");
	        				
	        			finalizar(true);
	 	         }
	 	        // TODO Comprobar que el c�digo QR sea de este juego
	 	         }
	 	       else if (resultCode == RESULT_CANCELED) {
	 	         // Handle cancel
	 	    	 //Si se cancela la lectura se llega aqu�
	 	    	texto.setText("Lee de nuevo el c�digo QR");
	 	      }
	 	    } 
	 	   }

	   
		protected int calcularPuntuacion() {
			int score = MAX_SCORE;
			tiempo = (int) (elapsed * tos);
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

		
	    

            
   
   

