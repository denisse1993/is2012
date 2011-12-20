package com.cinnamon.is.minijuegos.end;

import java.util.ArrayList;
import java.util.List;

import com.cinnamon.is.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView{
	private SurfaceHolder holder;
	private List<Sala> listaSalas; //lista de las 3 habs
	private List<Objeto> listaObjetos; //todos los objetos clave del juego
		//tip. objetos clave 
	private Mapa map;
	private int salaActual;
	private Mochila mochila;
	Handler handler;
	Context contexto;
	private Objeto primerCombinado;
	private Objeto segundoCombinado;
	private Objeto escudo;
	private boolean ultimaCombinacion;
	private boolean finalJuego;
	
	public GameView(Context context) {
		super(context);
		initMe();
		contexto = context;
		holder = getHolder();
		salaActual = 1;
		holder.addCallback(new Callback(){

			@Override
			public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				Canvas c = holder.lockCanvas();
				onDraw(c);
				holder.unlockCanvasAndPost(c);
				
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				
				//initMe();
				listaSalas = new ArrayList<Sala>(3);
				
				creaSala(R.drawable.sala1,1);
				creaSala(R.drawable.sala2v2,2);
				creaSala(R.drawable.sala3v2,3);
				creaMap(R.drawable.minimap,R.drawable.flecha1);
				crearMochila(R.drawable.maletin);
				
				
				
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
	}
	
	
	protected void creaMap(int recurso,int rscflecha) {
		
		/*Bitmap fondo = BitmapFactory.decodeResource(getResources(), recurso);*/
		Bitmap flecha = BitmapFactory.decodeResource(getResources(), rscflecha);
        
        int width = getWidth();        
        int height = getHeight();
		
        Bitmap tmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), recurso),width/4, height/3, true);
       // Bitmap tmpf = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), rscflecha),.getWidth()/3, flecha.getHeight()/3, true);
		map = new Mapa(tmp,this);
		map.setFlecha(flecha);
		
		
	}


	protected void creaSala(int recurso, int i) {
		
		
		
        Bitmap tmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), recurso),getWidth(), getHeight(), true);
        
		Sala sala = new Sala(tmp,i,this);
		listaSalas.add(sala);
	}


	protected void onDraw (Canvas canvas){
	
	if (listaSalas.size()>0){
		listaSalas.get(map.getMiniMap()-1).onDraw(canvas);
		/*for (int i = 1; i<listaSalas.size();i++){
			if (i!=map.getMiniMap()){
				listaSalas.get(i).getBmp().recycle();
			}
		}*/
	}
	map.onDraw(canvas);
	mochila.onDraw(canvas);
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
		if (map.isClick(event.getX(), event.getY())){
			Canvas c = holder.lockCanvas();
			onDraw(c);
			holder.unlockCanvasAndPost(c);
		}else if(listaSalas.get(map.getMiniMap()-1).isClick(event.getX(), event.getY())){
			
			
		}else if (mochila.isClick(event.getX(), event.getY()) ){
			
		}
		 
		return super.onTouchEvent(event);
		
	}
	
	private void initMe()
	{
	    handler = new Handler();
	}
	public Handler getHandler(){
		return this.handler;
	}
	
	
	public void crearMochila(int recurso){
		
		 //Bitmap mochilaBmp = BitmapFactory.decodeResource(getResources(), recurso);
        
       /* int width = getWidth();        
        int height = getHeight();*/
		
		
        //Bitmap mochi = Bitmap.createScaledBitmap(mochilaBmp,20, 20, true);
       
		mochila = new Mochila (this,BitmapFactory.decodeResource(getResources(), recurso));
	}
	public void addMochila (Objeto objeto){
		this.mochila.add(objeto);
	}
	
	public void combina (){
		
	}


	public void setEscudo(Objeto escudo1) {
		this.escudo = escudo1;
		
	}
	public Objeto getEscudo(){
		return this.escudo;
	}
	public void setUltimaCombinacion(boolean b){
		ultimaCombinacion = b;
	}
	public boolean getUltimaCombinacion(){
		return ultimaCombinacion;
	}
	
	public boolean getFinalJuego(){
		return this.finalJuego;
	}
	
	public void setFinalJuego(boolean b){
		this.finalJuego = b;
	}
}
