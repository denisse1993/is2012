package com.cinnamon.is.minijuegos.mj6;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;

import com.cinnamon.is.R;


public class GameView extends View implements OnClickListener{
	//private GameLoop loop;
	private int estado;
	private Paint paint;
	private boolean explosion;
	private int alto,ancho;
	private float y,z;
	private Bitmap fondo,boom, bomba,camara;
	private int centroY,centroZ;
	//private Button boton;
	//private Button btnQR;
	private int numVibraciones;
	private int score;
	private int estadoAnterior;
	private int vibracionesEspera;
	private MinijuegoBomba a;
	private int vClicked;

	public GameView(Context context, int width, int height, MinijuegoBomba minijuegoBomba) {
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
		a = minijuegoBomba;
		crearFondo(R.drawable.fondobomba);
		crearBomba(R.drawable.bombared);
		crearFondoExplosion(R.drawable.estallido);
		crearBotonCamara(R.drawable.ic_menu_camara);
		numVibraciones = 0;
		score = 1000;
		vibracionesEspera = 0;
		estadoAnterior = 0;
		estado = 0;
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
	
	public void crearBotonCamara(int resource){
		/*ImageButton ibCamara = new ImageButton(a);
		ibCamara.setBackgroundResource(resource);
		ibCamara.setId(R.id.ib_camara_bomba);
		ibCamara.setPadding(centroZ, 5/6*alto, centroZ-32, 1/6*alto);
		ibCamara.setOnClickListener(this);
		ibCamara.setVisibility(View.VISIBLE);*/
		
		Bitmap camara = BitmapFactory.decodeResource(getResources(), resource);
		int width = 75;
		int height = 75;
		Bitmap tmp = Bitmap.createScaledBitmap(camara, width, height, true);
		this.camara = tmp; 
	}
	
	
	public void onDraw(Canvas canvas){
		
		//poner match parent, match parent keda mejor y no estirada
		//canvas.drawBitmap(fondo, 0, 0, paint);
		//boton = new Button(null);
		
		int y1,y2,y3,z1,z2,z3;
		int altobomba=this.bomba.getHeight();
		int anchobomba=this.bomba.getWidth();

		int altocamara = this.camara.getWidth();
		int anchocamara = this.camara.getHeight();

		if (!explosion){
		
			z1=(int) (centroZ-((centroZ/10)*z));
			z2=z1-(altobomba/2);
			z3=(int)(centroZ-anchocamara/2);
			y1=(int) (centroY-((centroY/10)*y));
			y2=y1-(anchobomba/2);
			y3=(int)(alto)-anchocamara+20;
				
			canvas.drawBitmap(fondo, 0, 0, paint);
			canvas.drawBitmap(bomba,z2,y2,paint);
			
			
			canvas.drawBitmap(camara,z3,y3,paint);
			canvas.drawText(Integer.toString(numVibraciones), ancho-50, 50, paint);
			
			
		
		}else {
			canvas.drawBitmap(boom, 0, 0, paint);
			a.finalizar(true);
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
			
			
		
		}
		
	}
	public void setExplosion(Boolean b){
		explosion = b;
	}
	public void crearRecursos() {
		//crearFondo(R.drawable.fondoprueba2);
		
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

	//TODO da error el pulsarCamara y el id
	@Override
	public void onClick(View v) {
		switch (vClicked = v.getId()) {
//		case R.id.ib_camara_bomba:
//			a.pulsarCamara();
//			break;
		}
		
	}
}
