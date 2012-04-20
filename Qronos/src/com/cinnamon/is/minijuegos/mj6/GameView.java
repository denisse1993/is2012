package com.cinnamon.is.minijuegos.mj6;

import android.R;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class GameView extends View{
	//private GameLoop loop;
	private int estado;
	private Paint paint;
	private boolean explosion;
	private int alto,ancho;
	private float y,z;
	private Bitmap fondo,boom, bomba;
	private SurfaceHolder holder;
	private MediaPlayer music;
	private int centroY,centroZ;
	//private Button boton;
	//private Button btnQR;
	private int numVibraciones;
	private int score;
	private int estadoAnterior;
	private int vibracionesEspera;
	

	public GameView(Context context, int width, int height) {
		super(context);		
		estado = 0;
		paint = new Paint();
		paint.setTextSize(50);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		ancho = width;
		alto=height;
		centroZ = ancho/2;
		centroY=alto/2;
		explosion = false;
		crearFondo(R.drawable.fondoprueba2);
		crearBomba(R.drawable.bombareducida);
		crearFondoExplosion(R.drawable.boom);
		numVibraciones = 0;
		score = 1000;
		vibracionesEspera = 0;
		estadoAnterior = 0;
	}
	
	public void setY(float Y){
		y=Y;
	}
	public void setZ(float Z){
		
		z=Z;
	}
	public boolean getExplosion(){
		return this.explosion;
	}
	
	public void setEstado(int estado){
		this.estado = estado;
		
	}
public void crearFondoExplosion(int resource) {
		
		Bitmap pantalla = BitmapFactory.decodeResource(getResources(), resource);
		int width = this.ancho;
		int height = this.alto;
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width, height, true);
		
		this.boom = tmp;
	}
	public void crearFondo(int resource) {
		
		Bitmap pantalla = BitmapFactory.decodeResource(getResources(), resource);
		int width = this.ancho;
		int height = this.alto;
		Bitmap tmp = Bitmap.createScaledBitmap(pantalla, width, height, true);
		
		this.fondo = tmp;
	}
	public void crearBomba(int resource) {
		
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		this.bomba = bmp;
	}
	private void temazo(int resource) {
		
		music = MediaPlayer.create(getContext(), resource);
		music.start();
	}
	
	public void onDraw(Canvas canvas){
		
		//poner match parent, match parent keda mejor y no estirada
		//canvas.drawBitmap(fondo, 0, 0, paint);
		//boton = new Button(null);
		
		int y1,y2,z1,z2;
		int altobomba=this.bomba.getHeight();
		int anchobomba=this.bomba.getWidth();
		if (!explosion){
		
			z1=(int) (centroZ-((centroZ/10)*z));
			z2=z1-(altobomba/2);
			y1=(int) (centroY-((centroY/10)*y));
			y2=y1-(anchobomba/2);
				
			canvas.drawBitmap(fondo, 0, 0, paint);
			canvas.drawBitmap(bomba,z2,y2,paint);
			canvas.drawText(Integer.toString(numVibraciones), ancho-50, 50, paint);
		
		}else {
			canvas.drawBitmap(boom, 0, 0, paint);
			/////
			/*
			btnQR=(Button) findViewById(R.id.leer);
	        //QR= new UtilQR(this);
	        btnQR.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					// TODO Auto-generated method stub
					try{
						//QR.lanzarQR();
						//lanza el scan del Barcode Scanner
						Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				        //startActivityForResult(intent, 0);
						}catch (ActivityNotFoundException e) {
							e.printStackTrace();
						}
				}
		 
		        });*/
	        //////
			
			//canvas.drawText("Te estalló la bomba!!",ancho/4 , alto/2, paint);
			//canvas.drawText("Toca la pantalla para volver a empezar",ancho/4 , alto/2 + 20, paint);
		
		}
		//canvas.drawColor(Color.RED);
	}
	public void setExplosion(Boolean b){
		explosion = b;
	}
	public void crearRecursos() {
		crearFondo(R.drawable.fondoprueba2);
		
	}

	public void restaPuntuacion() {
		if ( ((estadoAnterior == 2) && (vibracionesEspera > 20)) || (estadoAnterior != 2) ){
			score= score-100;
			numVibraciones= numVibraciones+1;
			vibracionesEspera = 0;
			
		}
		
		vibracionesEspera++;
		estadoAnterior = 2;
				
	}
	public int getScore(){
		return score;
	}
}
