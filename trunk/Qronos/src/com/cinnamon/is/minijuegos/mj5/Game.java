package com.cinnamon.is.minijuegos.mj5;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.Minijuego;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Game extends Minijuego {

	private Button btnQR;
	private TextView texto;
	//private UtilQR QR;
	private long elapsed;
	private long start;
	protected final static double tos = 0.000000001;
	protected final static double tons = 1000000000;

	

	/**
	 * @param args
	 */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.encqr);
        Activity.getInstanceCount();

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
	 	         //Si la lectura se realiza correctamente se llega aquií
	 	         //Si el código no es el código objetivo, seguir intentandolo
	 	         if(!contents.equals("objetivo")){
	 	        	 texto.setText("¡MAL!\nEse no es el código que tienes que encontrar\n¡SIGUE BUSCANDO!"); 
	 	         }
	 	         //Si el código es el código objetivo detener el tiempo y lanzar otra actividad para 
	 	         //decirle al usuario que ha acabado el juego y mostrarle su puntuación
	 	         if(contents.equals("objetivo")){
	 	        	try{
	 	        		//para recoger el dato inicio que pasabamos al intent
	 	        		 Bundle datos = this.getIntent().getExtras();
	 	        		 start = datos.getLong("inicio");
	 	        		 finishTime(); 
	 	        		 elapsed=(long) (elapsed*tos);
	 	        		// texto.setText(""+elapsed);
	 	        		 Intent openWin=new Intent("com.cinnamon.qrgames.WIN");
	 	        		 openWin.putExtra("tiempo", elapsed);
						 startActivity(openWin);
						 finish();
						}catch (ActivityNotFoundException e) {
							e.printStackTrace();
						}
	 	         }
	 	        // TODO Comprobar que el código QR sea de este juego
	 	         }
	 	       else if (resultCode == RESULT_CANCELED) {
	 	         // Handle cancel
	 	    	 //Si se cancela la lectura se llega aquí
	 	    	texto.setText("Lee de nuevo el código QR");
	 	      }
	 	    } 
	 	   }

	   
		protected void finishTime() {
			elapsed = System.nanoTime() - start;
		}
	}

		
	    

            
   
   


