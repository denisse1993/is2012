package com.cinnamon.is.minijuegos.end;

import java.util.ArrayList;
import java.util.List;

import com.cinnamon.is.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Mochila {
	
	private GameView game;
	private List<Objeto> objetosRecogidos;
	private Bitmap bmp;
	private int posX = 1;
	private int posX2 = 100;
	private int posY = 1;
	private int posY2 = 100;
	private boolean combinando = false;
	private Objeto primerCombinado;
	private Objeto segundoCombinado;
	private int itemActual;
	public Mochila(GameView gameView, Bitmap mochi) {
		this.game = gameView;
		this.bmp = mochi;
		objetosRecogidos = new ArrayList<Objeto>();
	}
	public void add (Objeto objeto){
		if (!objetosRecogidos.contains(objeto)){
		objetosRecogidos.add(objeto);
		}
	}
	public boolean isClick(float x2,float y2){
	
		if (x2 > posX && x2 < posX2 && y2 > posY && y2 < posY2){
			
			game.getHandler().post(new Runnable(){
			    public void run(){
			    	
			    	final String[] items = dameObjetosMochila();
			    	final String[] descripciones = dameDescripcionObjetos();

			    	AlertDialog.Builder builder = new AlertDialog.Builder(game.getContext());
			    	builder.setTitle("Mi mochila");
			    	builder.setIcon(R.drawable.maletin);
			    	builder.setItems(items, new DialogInterface.OnClickListener() {
			    	    public void onClick(DialogInterface dialog, int item) {
			    	        //Toast.makeText(game.getContext(), descripciones[item], Toast.LENGTH_SHORT).show();
			    	        /////////////////////
			    	    	itemActual = item;
			    	        AlertDialog.Builder builder = new AlertDialog.Builder(game.getContext());
			    	        builder.setMessage(descripciones[item])
			    	               .setCancelable(true)
			    	               .setPositiveButton("Combinar", new DialogInterface.OnClickListener() {
			    	                  
			    	            	   
									public void onClick(DialogInterface dialog, int id) {
			    	                        
			    	                        if (primerCombinado==null){
			    	                        	Toast.makeText(game.getContext(), "Combinar con?", Toast.LENGTH_LONG).show();
			    	                        	primerCombinado = getObjeto(itemActual);
			    	                        	
			    	                        }else if (segundoCombinado ==null){
			    	                        	segundoCombinado = getObjeto(itemActual);
			    	                        	
			    	                        	if(combinar()){
			    	                        		if (game.getFinalJuego()){
			    	                        			Toast.makeText(game.getContext(), "Has conseguido reventar " +
			    	                        					"la caja, te das cuenta que entre los pedazos " +
			    	                        					"hay una placa ensangrentada. FIIIIIIIN!!", Toast.LENGTH_LONG).show();
			    	                        		}
			    	                        		else{Toast.makeText(game.getContext(), "Has conseguido un nuevo objeto clave", Toast.LENGTH_LONG).show();}
			    	                        	}else{
			    	                        		if (!game.getUltimaCombinacion()){
			    	                        		Toast.makeText(game.getContext(), "No ha pasado nada", Toast.LENGTH_LONG).show();
			    	                        	
			    	                        		}else{
			    	                        			Toast.makeText(game.getContext(), "Deberias usar algo para protegerte" +
			    	                        					" antes de combinar esos 2 objetos", Toast.LENGTH_LONG).show();
			    	                        		}
			    	                        	}
			    	                        	segundoCombinado = null;
			    	                        	primerCombinado = null;
			    	                        }
			    	                   }

									
			    	               })
			    	               .setNegativeButton("Usar", new DialogInterface.OnClickListener() {
			    	                   public void onClick(DialogInterface dialog, int id) {
			    	                        getObjeto(itemActual).setUsado(true);
			    	                        if(getObjeto(itemActual).getId()==5){
			    	                        	Toast.makeText(game.getContext(), " Con el escudo en la mano pareces" +
			    	                        			" estar protegido", Toast.LENGTH_LONG).show();
			    	                        }else{
			    	                        	Toast.makeText(game.getContext(), "Parece no tener efecto", Toast.LENGTH_LONG).show();
			    	                        }
			    	                   }
			    	               });
			    	        AlertDialog alert = builder.create();
			    	        alert.show();
			    	        ///////////////////////////////////
			    	    }
			    	});
			    	AlertDialog alert = builder.create();
			    	alert.show();
			    }
			});
			
			return true;
		}else{
			return false;
		}
		
	}
	
	public String[] dameObjetosMochila (){
		String [] objetos = new String [objetosRecogidos.size()];
		for (int i = 0; i<objetosRecogidos.size();i++){
			objetos[i]=(objetosRecogidos.get(i).getIds() );
		}
		return objetos;
	}
	
	public String[] dameDescripcionObjetos (){
		String [] objetos = new String [objetosRecogidos.size()];
		for (int i = 0; i<objetosRecogidos.size();i++){
			objetos[i]=(objetosRecogidos.get(i).getDescripcion() );
		}
		return objetos;
	}
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(bmp, posX, posY,null);
		
	}
	
	public Objeto getObjeto(int id){
		return objetosRecogidos.get(id);
		
	}
	public boolean combinar(){
		if ( ((primerCombinado.getId()==7) && (segundoCombinado.getId() ==3) ) ||
				((primerCombinado.getId() ==3) && (segundoCombinado.getId() ==7))  ){
			objetosRecogidos.remove(primerCombinado);
			objetosRecogidos.remove(segundoCombinado);
			objetosRecogidos.add(new Objeto(game,0,0,0,0,8,"Botella con pólvora","Se podría hacer explotar algo con esto"));
			return true;
		}else if ( ((primerCombinado.getId()==6) && (segundoCombinado.getId() ==2) ) ||
				((primerCombinado.getId() ==2) && (segundoCombinado.getId() ==6))  ){
			objetosRecogidos.remove(primerCombinado);
			objetosRecogidos.remove(segundoCombinado);
			objetosRecogidos.add(new Objeto(game,0,0,0,0,9,"Papel ardiendo","Corre o te quemarás la mano"));
			return true;
		
		}else if ( ((primerCombinado.getId()==9) && (segundoCombinado.getId() ==4) ) ||
				((primerCombinado.getId() ==4) && (segundoCombinado.getId() ==9))  ){
			objetosRecogidos.remove(primerCombinado);
			objetosRecogidos.remove(segundoCombinado);
			objetosRecogidos.add(new Objeto(game,0,0,0,0,10,"Candelabro encendido","Ahora las velas están encendidas"));
			return true;
		
		}else if ( ((primerCombinado.getId()==8) && (segundoCombinado.getId() ==15) ) ||
				((primerCombinado.getId() ==15) && (segundoCombinado.getId() ==8))  ){
			objetosRecogidos.remove(primerCombinado);
			objetosRecogidos.remove(segundoCombinado);
			objetosRecogidos.add(new Objeto(game,0,0,0,0,11,"Botella con pólvora y mecha","Con esto podría hacer explotar algo"));
			return true;
		
		}else if ( ((primerCombinado.getId()==11) && (segundoCombinado.getId() ==1) ) ||
				((primerCombinado.getId() ==1) && (segundoCombinado.getId() ==11))  ){
			objetosRecogidos.remove(primerCombinado);
			objetosRecogidos.remove(segundoCombinado);
			objetosRecogidos.add(new Objeto(game,0,0,0,0,12,"Bomba casera junto a la caja","Pusiste la bomba casera al lado de la caja"));
			return true;
		
		
		}else if(( ((primerCombinado.getId()==12) && (segundoCombinado.getId() ==10) ) ||
				((primerCombinado.getId() ==10) && (segundoCombinado.getId() ==12))  )&&(objetosRecogidos.contains(game.getEscudo())) ){
				if (game.getEscudo().getUsado()){
					/*ACTIVO EL BOOLEANO FINDELJUEGOOOOOOO*/
					game.setFinalJuego(true);
				}else{
					game.setUltimaCombinacion(true);
					return false;
				}
			
			
			return true;
		}
		return false;
	}
	
}