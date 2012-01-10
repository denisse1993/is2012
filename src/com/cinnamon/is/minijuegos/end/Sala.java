package com.cinnamon.is.minijuegos.end;

import java.util.ArrayList;
import java.util.List;

import com.cinnamon.is.R;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class Sala {
//cada habitacion tendra un bmp y una lista de objetos clave
	private Bitmap fondo;
	private int id;
	private GameView game;
	private List<Objeto> listaObjetos = new ArrayList<Objeto>(); //objeto 1 papel, objeto 2 cajafuerte,3 ceniza
	
	
	public Sala (Bitmap fondo, int id,GameView game){
		this.fondo = fondo;
		this.id = id;
		this.game = game;
		creaObjetosClave();
	}
	
	public void onDraw(Canvas canvas){
	
        canvas.drawBitmap(fondo, 0, 0, null);
        if (listaObjetos.size()>0){
        	for (int i=0; i<2;i++){
        		listaObjetos.get(i).onDraw(canvas);
        	}
        }
		//Bitmap bmp =  BitmapFactory.decodeResource(getResources(), resource);
		
	}
	
	public void creaObjetosClave(){
		if (id ==1){
			int cajaX1 = ((fondo.getWidth()*47)/100);
			int cajaX2=((fondo.getWidth()*50)/100); /// todo esto para determinar la anchura de pixel
			int cajaY1=((fondo.getHeight()*80)/100);
			int cajaY2=((fondo.getHeight()*88)/100);
		
			int papelX1=((fondo.getWidth()*51)/100);
			int papelX2=((fondo.getWidth()*56)/100);
			int papelY1=((fondo.getHeight()*32)/100);
			int papelY2=((fondo.getHeight()*38)/100);
	
			int cenizaX1=((fondo.getWidth()*74)/100);
			int cenizaX2=(fondo.getWidth());
			int cenizaY1=((fondo.getHeight()*80)/100); //no me dice que x e y del canvas
			int cenizaY2=((fondo.getHeight()*96)/100);
		
			Bitmap cajaFuerte = BitmapFactory.decodeResource(game.getResources(), R.drawable.cajaf);
			Bitmap papel = BitmapFactory.decodeResource(game.getResources(), R.drawable.papel);
		
        
        /*int width = getWidth();        
        int height = getHeight();*/
		
		
			Bitmap tmpCaja = Bitmap.createScaledBitmap(cajaFuerte,cajaX2-cajaX1, cajaY2-cajaY1, true);
			Bitmap tmpPapel = Bitmap.createScaledBitmap(papel,papelX2-papelX1, papelY2-papelY1, true);
        
        
			Objeto caja = new Objeto(game,tmpCaja,cajaX1,cajaX2,cajaY1,cajaY2,1,"Caja","Parece ser que hay algo dentro");
			Objeto papel1 = new Objeto(game,tmpPapel,papelX1,papelX2,papelY1,papelY2,2,"Papel","Un papel arrugado y roto");
			Objeto ceniza = new Objeto(game,cenizaX1,cenizaX2,cenizaY1,cenizaY2,3,"Ceniza",
					"Parece ser una mezcla de ceniza y pólvora");
			listaObjetos.add(caja);
			listaObjetos.add(papel1);
			listaObjetos.add(ceniza);
		
		}else if (id==2){
			int candelabroX1 = ((fondo.getWidth()*36)/100);
			int candelabroX2=((fondo.getWidth()*43)/100); /// todo esto para determinar la anchura de pixel
			int candelabroY1=((fondo.getHeight()*36)/100);
			int candelabroY2=((fondo.getHeight()*54)/100);
		
			int escudoX1=((fondo.getWidth()*47)/100);
			int escudoX2=((fondo.getWidth()*54)/100);
			int escudoY1=((fondo.getHeight()*33)/100);
			int escudoY2=((fondo.getHeight()*45)/100);
			
			//Bitmap papel = BitmapFactory.decodeResource(game.getResources(), R.drawable.escudo);
	
			//Bitmap tmpEscudo = Bitmap.createScaledBitmap(papel,escudoX2-escudoX1, escudoY2-escudoY1, true);
			Objeto candelabro = new Objeto(game,candelabroX1,candelabroX2,candelabroY1,candelabroY2,4,"Candelabro",
					"Al cogerlo se apagaron las velas");
			Objeto escudo1 = new Objeto(game, escudoX1, escudoX2, escudoY1, escudoY2,5,"Escudo",
					"Un escudo que no podria ser destruido");
			game.setEscudo(escudo1);
			listaObjetos.add(candelabro);
			listaObjetos.add(escudo1);
			
		}
		else if (id==3){
			int gafasx1 =((fondo.getWidth()*15)/100);
			int gafasx2 = ((fondo.getWidth()*26)/100);
			int gafasy1=((fondo.getHeight()*65)/100);
			int gafasy2=((fondo.getHeight()*75)/100);
			
			int botellax1=((fondo.getWidth()*55)/100);
			int botellax2=((fondo.getWidth()*63)/100);
			int botellay1=1;
			int botellay2=((fondo.getHeight()*12)/100);
			
			int cuerdax1=((fondo.getWidth()*25)/100);
			int cuerdax2=((fondo.getWidth()*41)/100);
			int cuerday1= 1;
			int cuerday2=((fondo.getHeight()*15)/100);
			
			Objeto gafas = new Objeto(game, gafasx1, gafasx2, gafasy1, gafasy2,6,"Gafas","El cristal de estas gafas"
					+"podria ser muy util");
			Objeto botella = new Objeto(game, botellax1, botellax2, botellay1, botellay2, 7, "Botella","Me podria"
				+" servir para meter algo dentro");
			Objeto cuerda = new Objeto(game, cuerdax1, cuerdax2, cuerday1, cuerday2, 15, "Cuerda", "Una cuerda");
			
			listaObjetos.add(gafas);
			listaObjetos.add(botella);
			listaObjetos.add(cuerda);
		}
	}
	 public boolean isClick(float x2, float y2){
		
		for (int i=0;i<listaObjetos.size();i++){
			if(listaObjetos.get(i).isClick(x2, y2)){
			return true;
			}
		}
		return false;
	 }

	public Bitmap getBmp() {
		
		return this.fondo;
	}
}
