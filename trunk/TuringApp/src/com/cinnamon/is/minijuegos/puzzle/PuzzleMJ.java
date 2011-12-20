package com.cinnamon.is.minijuegos.puzzle;

 import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import com.cinnamon.is.R;
import com.cinnamon.is.comun.Intents;
import com.cinnamon.is.comun.Minijuego;
import com.cinnamon.is.game.Jugador;

public class PuzzleMJ extends Minijuego implements OnTouchListener{
	
	enum foto{img11,img12,img13,img21,img22,img23,img31,img32,blanco};
	
	private foto imagen;
	private foto pic[] = new foto[9] ;
	private  foto tabla [][];
	private static int ancho;
	private static int anchoBoton;
	
	
	Canvas canvas;
	puzzleSurface ourSurfaceView;
	float x,y;
	float celda;
	boolean touch =false;
	int cel1;
	int cel2;
	int celdaini,celdafin;
	

	@Override
	/*protected Dialog onCreateDialog(int id, Bundle bundle) {
		Dialog dialog = null;
		switch (id) {

		case DIALOG_MINIJUEGOS_RESULT:
			// obtiene datos
			String textoDialog = bundle.getString("textoDialog");
			int idIvDialog = bundle.getInt("idIvDialog");
			// crea dialog
			dialog = new Dialog(this);
			dialog.setContentView(R.layout.puzzleD);
			dialog.setTitle("Resultado Minijuego");
			// pone elementos
			TextView tvDialog = (TextView) dialog.findViewById(R.id.tvDialog);
			tvDialog.setText(textoDialog);
			ImageView ivDialog = (ImageView) dialog.findViewById(R.id.ivDialog);
			ivDialog.setImageResource(idIvDialog);
			// ivDialog.setImageResource(R.drawable.bonoff);
			Button bDialog = (Button) dialog.findViewById(R.id.bDialog);
			bDialog.setOnClickListener((OnClickListener) this);
			break;
		}

		return dialog;
	}*/
	/*
	public PuzzleMJ() {
		tabla = new foto[3][3];
		int[] comprobar= new int[9];
		for (int i=0; i<9; i++) comprobar[i]=-1;
		ponRandom(tabla, 9, comprobar);
		loadImagen();
		x=y=0;
		// TODO Auto-generated constructor stub
		
	}*/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ourSurfaceView = new puzzleSurface(this);
		ourSurfaceView.setOnTouchListener(this);
			
		tabla = new foto[3][3];
		pic[0]=foto.img11;
		pic[1]=foto.img12;
		pic[2]=foto.img13;
		pic[3]=foto.img21;
		pic[4]=foto.img22;
		pic[5]=foto.img23;
		pic[6]=foto.img31;
		pic[7]=foto.img32;
		pic[8]=foto.blanco;
		
		int[] comprobar= new int[9];
		for (int i=0; i<9; i++) comprobar[i]=-1;
		//ponRandom(tabla, 9, comprobar);
		ponRandomSol(tabla);
		//este comentario de debajo pone todas las piezas ordenadas sin el random
		//no borrar, puede ser útil
		/*int m=0;
		for (int i=0;i<3;i++){
			for (int j=0;j<3;j++){
					tabla[i][j]=pic[m];
						m++;
				}
		}*/
		
		
		x=y=0; 
			
		setContentView(ourSurfaceView);
		
		// Pone valores del minijuego
		startTime();
		setNombre(Intents.Comun.minijuegos[3]);
		setObjeto(3);
		setFase(4);
		setSuperado(false);

		jugador = (Jugador) getIntent().getSerializableExtra(
				Intents.Comun.JUGADOR);
		
		//Dialog
		
	//	String textoDialog = "Hola",
		/*"Enhorabuena has desbloqueado el minijuego de las 8 reinas." +
		"/nPara conseguir el siguiente objeto necesitaras completar el siguiente tablero" +
		"en el que se colocan ocho reinas sin que se amenacen. Una reina amenaza a otra si" +
		" se encuentren en su misma fila, columna o diagonal." +
		"/nSuerte.",*/
		/*title = "8 Reinas";
		int idIvDialog = R.drawable.tablero8reinas;

		Bundle dialogBundle = new Bundle();
		dialogBundle.putString("textoDialog", textoDialog);
		dialogBundle.putInt("idIvDialog", idIvDialog);
		//	dialogBundle.putBoolean(Intents.Comun.superado, superado);
		dialogBundle.putString("title", title);
		showDialog(DIALOG_MINIJUEGOS_INIT,dialogBundle);*/
	}
	
	public void ponRandomSol(foto[][] tab){		
		tab[0][0]=pic[3];
		tab[0][1]=pic[5];
		tab[0][2]=pic[7];
		tab[1][0]=pic[4];
		tab[1][1]=pic[8];
		tab[1][2]=pic[0];
		tab[2][0]=pic[1];
		tab[2][1]=pic[6];
		tab[2][2]=pic[2];
	}
	
	public void asignaPieza(foto[][] tab,int img, int pos){
		for (int i=0;i<3;i++){
			for (int j=0;j<3;j++){
					if(pos==dameCelda(i, j)){
						tabla[i][j]=pic[img];
					}
				}
			}
	}
	
	public boolean esAdyacente(int blanco, int img){
		//comprueba si la posición de la ficha img es adyacente al cuadrado blanco
		if(blanco<0 || blanco>8 || img<0 || img>8) 
			return false;
		if(img==blanco+1 || img==blanco-1|| img==blanco+3 || img==blanco-3){
			return true;
		}
		else return false;
	}
	
	public boolean moverPieza(foto[][] tab,int imgA, int imgB){
		//si imgB es blanco, intercambia las fichas imgA e imgB	
		if(tabla[dameCeldax(imgB)][dameCelday(imgB)]==foto.blanco && 0<=imgA && imgA<9 && 0<=imgB && imgB<9 && esAdyacente(imgB,imgA)){
				imagen=tabla[dameCeldax(imgA)][dameCelday(imgA)];
				int x1,x2,y1,y2;
				x1=dameCeldax(imgA);
				x2=dameCeldax(imgB);
				y1=dameCelday(imgA);
				y2=dameCelday(imgB);
				tabla[x1][y1]=tabla[dameCeldax(imgB)][dameCelday(imgB)];
				tabla[x2][y2]=imagen;
				return true;
		}
		else
			{return false;}
		
	}
	
	public int dameCelda(int fil,int col){
		if(0 <=fil && fil<3 && 0<=col && col<3){
			return (fil*3+col);
		}
		return -1;
	}
	
	public int dameCeldax(int celda){
		//tiene como entrada un número del 0 al 8 y como salida la fila que ocupa en el tablero
		if(0<=celda && celda<9){
			return(celda/3);
		}else{
		return -1;
		}
	}
	public int dameCelday(int celda){
		//tiene como entrada un número del 0 al 8 y como salida la columna que ocupa en el tablero
		if(0<=celda && celda<9){
			return(celda%3);
		}else{
			return -1;
		}
	}
	public String dameImagen (int i, int j){
		switch (tabla[i][j]){
		case img11: return "pieza11.png";
		case img12: return "pieza12.png";
		case img13: return "pieza13.png";
		case img21: return "pieza21.png";
		case img22: return "pieza22.png";
		case img23: return "pieza23.png";
		case img31: return "pieza31.png";
		case img32: return "pieza32.png";
		case blanco: return "pieza33-negra.png";
		}
		return null;
	}
	
	public void ponRandom(foto[][] tab, int aux, int[] comprobar){
		//si no usao random(1,9)
		//aux es el número de bloques que quedan por colocar
		//comprobar es para comprobar si ha sido usado el número. cada vez que se use un número éste se pondrá en comprobar[aux]
		int num=0;
		int i=0;
		Random r=new Random();
		boolean usado=false;
		boolean usadoaux=false;
		if (aux>0){
			while (!usado){
				num=r.nextInt(9);//genera un nº aleatorio entre 0 y 8	
				usadoaux=false;
				i=0;
				while ((i<9) && (!usadoaux)){
					if(comprobar[i]==num){usadoaux=true;}
					else {usadoaux=false;}
					i++;
				}
				if(usadoaux){
					usado=false;
				}
				else {
					usado=true;
					aux--;
					comprobar[aux]=num;
				}
			}
			asignaPieza(tab, num, aux);
			ponRandom(tab, aux, comprobar);
		}	
		
	}
	
	public boolean comprueba(){
			int m=0;
			for (int i=0;i<3;i++){
				for (int j=0;j<3;j++){
						if(pic[m]==tabla[i][j]){
							m++;
						}
						else
							{return false;}
				}
			}
			return true;
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSurfaceView.pause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ourSurfaceView.resume();
	}
	
public boolean onTouch(View v, MotionEvent event) {
		
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				y = event.getY();
				//canvas.getWidth();
				if(x<=ancho && y<=ancho){//si pulsamos dentro de los márgenes
					int f=queFilaEs();
					int c=queColEs();
					celdaini= dameCelda(f,c);
					cel1= celdaini;
					touch=true;
				}
				break;
		/*	case MotionEvent.ACTION_MOVE:
				
				break;*/
			case MotionEvent.ACTION_UP:
				x = event.getX();
				y = event.getY();
				//canvas.getWidth();
				if(x<=ancho && y<=ancho){
					int f2=queFilaEs();
					int c2=queColEs();
 					celdafin=dameCelda(f2,c2);
					cel2=(int) celdafin;
					if(touch==true){
						moverPieza(tabla, cel1, cel2);
						touch =false;
					}
				}
			    break;
		}					
		return true;
}

	
	int queColEs() {
		int coorX;
		celda=ancho/3;//canvas.getWidth()/3;
		coorX=(int)((x-(x % celda))/celda);
		return coorX;
	}
	int queFilaEs() {
		int coorY;
		celda=ancho/3;//canvas.getWidth()/3;
		coorY=(int)((y-(y % celda))/celda);
		return coorY;
		
	}

public class puzzleSurface extends SurfaceView implements Runnable{

	SurfaceHolder ourHolder;
	Thread ourThread = null;
	boolean isRunning = false;
	
	boolean ini =true;
	int aux;

	List<Bitmap> arrayFotos;
	
	Rect rectangulo;
	Rect rectPieza11;
	Rect rectPieza12;
	Rect rectPieza13;
	Rect rectPieza21;
	Rect rectPieza22;
	Rect rectPieza23;
	Rect rectPieza31;
	Rect rectPieza32;
	Rect rectPieza33;
	
	Rect rectPieza;

	
	Bitmap tab;
	Bitmap im11;
	Bitmap im12;
	Bitmap im13;
	Bitmap im21;
	Bitmap im22;
	Bitmap im23;
	Bitmap im31;
	Bitmap im32;
	Bitmap im33negra;
	Bitmap especial;
	
	Bitmap im;

	public puzzleSurface(Context context) {
		super(context);
		ourHolder = getHolder();
		ini=true;
		
	}
		
	public void pause(){
		isRunning = false;
		while(true){
			try {
				ourThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		ourThread = null;
	}
	
	public void resume(){
		isRunning = true;
		ourThread = new Thread(this);
		ourThread.start();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		//inicializa();
		while(isRunning){
			if(!ourHolder.getSurface().isValid())
				continue;
			
			canvas = ourHolder.lockCanvas();
			ancho=canvas.getWidth();
			anchoBoton=canvas.getWidth()/11;
			canvas.drawRGB(125, 125, 125);// fondo gris cambiar aquí el color
			
			if (ini){
			rectangulo=new Rect(0,0,canvas.getWidth(),canvas.getWidth());
			rectPieza11=new Rect(0,0,canvas.getWidth()/3,canvas.getWidth()/3);
			rectPieza12=new Rect(canvas.getWidth()/3,0,(canvas.getWidth()/3)*2,canvas.getWidth()/3);
			rectPieza13=new Rect((canvas.getWidth()/3)*2,0,(canvas.getWidth()/3)*3,canvas.getWidth()/3);
			rectPieza21=new Rect(0,(canvas.getWidth()/3),canvas.getWidth()/3,(canvas.getWidth()/3)*2);
			rectPieza22=new Rect((canvas.getWidth()/3),(canvas.getWidth()/3),(canvas.getWidth()/3)*2,(canvas.getWidth()/3)*2);
			rectPieza23=new Rect((canvas.getWidth()/3)*2,(canvas.getWidth()/3),(canvas.getWidth()/3)*3,(canvas.getWidth()/3)*2);
			rectPieza31=new Rect(0,(canvas.getWidth()/3)*2,canvas.getWidth()/3,(canvas.getWidth()/3)*3);
			rectPieza32=new Rect((canvas.getWidth()/3),(canvas.getWidth()/3)*2,(canvas.getWidth()/3)*2,(canvas.getWidth()/3)*3);
			rectPieza33=new Rect((canvas.getWidth()/3)*2,(canvas.getWidth()/3)*2,(canvas.getWidth()/3)*3,(canvas.getWidth()/3)*3);

			rectPieza=new Rect(anchoBoton*4+3,canvas.getWidth()+anchoBoton*3/2,anchoBoton*4+canvas.getWidth()/3,canvas.getWidth()+anchoBoton*3/2+canvas.getWidth()/3);

			
			tab= BitmapFactory.decodeResource(getResources(), R.drawable.pieza33negra);
			im11 = BitmapFactory.decodeResource(getResources(), R.drawable.pieza11);
			im12 = BitmapFactory.decodeResource(getResources(), R.drawable.pieza12);
			im13 = BitmapFactory.decodeResource(getResources(), R.drawable.pieza13);
			im21 = BitmapFactory.decodeResource(getResources(), R.drawable.pieza21);
			im22 = BitmapFactory.decodeResource(getResources(), R.drawable.pieza22);
			im23 = BitmapFactory.decodeResource(getResources(), R.drawable.pieza23);
			im31 = BitmapFactory.decodeResource(getResources(), R.drawable.pieza31);
			im32 = BitmapFactory.decodeResource(getResources(), R.drawable.pieza32);
			im33negra = BitmapFactory.decodeResource(getResources(), R.drawable.pieza33negra);
			//especial = BitmapFactory.decodeResource(getResources(), R.drawable.misterioletras);
			im = BitmapFactory.decodeResource(getResources(), R.drawable.puzzle_completo);
		
			arrayFotos= new ArrayList<Bitmap>();
			
			arrayFotos.add(im11);
			arrayFotos.add(im12);
			arrayFotos.add(im13);
			arrayFotos.add(im21);
			arrayFotos.add(im22);
			arrayFotos.add(im23);
			arrayFotos.add(im31);
			arrayFotos.add(im32);
			arrayFotos.add(im33negra);
			
			ini=false;
			}
			
			aux=0;
		for (int i =0;i<3;i++){
			for(int j=0;j<3;j++){
					foto auxi;
					auxi=tabla[dameCeldax(aux)][dameCelday(aux)];
			 switch(auxi)
					{
				case img11:
					arrayFotos.set(aux,im11);
					break;
				case img12:
					arrayFotos.set(aux,im12);
					break;
				case img13:
					arrayFotos.set(aux,im13);
					break;
				case img21:
					arrayFotos.set(aux,im21);
					break;
				case img22:
					arrayFotos.set(aux,im22);
					break;
				case img23:
					arrayFotos.set(aux,im23);
					break;
				case img31:
					arrayFotos.set(aux,im31);
					break;
				case img32:
					arrayFotos.set(aux,im32);
					break;
				case blanco:
					arrayFotos.set(aux,im33negra);
					break;
				 default:
					arrayFotos.set(aux,especial);
				}
				aux++;
				}
			}
		
			Bitmap boton = BitmapFactory.decodeResource(getResources(), R.drawable.boton_check_on);
			//Rect rect2=new Rect(50,canvas.getWidth()+50,200,canvas.getWidth()+150);
			Rect rect2 = new Rect(canvas.getWidth()/2-35, canvas.getHeight()*2/3 , 
					canvas.getWidth()/2+35,canvas.getHeight()*2/3 + 70);
			canvas.drawBitmap(boton, null, rect2, null);
			//-----------------------------boton comprobar--------------------------------------------------
			//if((x>=50 && x<=150) && (y>=canvas.getWidth()+50 && y<= canvas.getWidth()+150) ){
			if((x>=anchoBoton && x<=anchoBoton*4) && (y>=canvas.getWidth()+anchoBoton/2 && y<= canvas.getWidth()+anchoBoton*3/2) ){	
				if(comprueba()){
				/*Bitmap tick = BitmapFactory.decodeResource(getResources(), R.drawable.tick);
				//Rect rect3=new Rect(300,canvas.getWidth()+50,350,canvas.getWidth()+150);
				Rect rect3=new Rect(anchoBoton*5,canvas.getWidth()+anchoBoton/2,anchoBoton*6,canvas.getWidth()+anchoBoton*3/2);
				canvas.drawBitmap(tick, null, rect3, null);*/
					lanzarAvisoMJ("Enhorabuena has conseguido superar el juego del Puzzle Slide"
							, "Juego Superado");
				}else{
				Bitmap error = BitmapFactory.decodeResource(getResources(), R.drawable.error);
				//Rect rect4=new Rect(300,canvas.getWidth()+50,350,canvas.getWidth()+150);
				Rect rect4=new Rect(anchoBoton*5,canvas.getWidth()+anchoBoton/2,anchoBoton*6,canvas.getWidth()+anchoBoton*3/2);
				canvas.drawBitmap(error, null, rect4, null);
				}
				
			}
			
			//---------------------------------boton pieza---------------------------------------------------
			if((x>anchoBoton*4+3) && (y>canvas.getWidth()+anchoBoton*3/2) && (x<anchoBoton*4+canvas.getWidth()/3) && (y<canvas.getWidth()+anchoBoton*3/2+canvas.getWidth()/3)){
				
				//aqui va el showDialog
			}
		
			
			Iterator<Bitmap> it;
			it=arrayFotos.iterator();
			
			canvas.drawBitmap(tab, null,rectangulo,null);
			canvas.drawBitmap((Bitmap) it.next(), null, rectPieza11, null);
			canvas.drawBitmap((Bitmap) it.next(), null, rectPieza12, null);
			canvas.drawBitmap((Bitmap) it.next(), null, rectPieza13, null);
			canvas.drawBitmap((Bitmap) it.next(), null, rectPieza21, null);
			canvas.drawBitmap((Bitmap) it.next(), null, rectPieza22, null);
			canvas.drawBitmap((Bitmap) it.next(), null, rectPieza23, null);
			canvas.drawBitmap((Bitmap) it.next(), null, rectPieza31, null);
			canvas.drawBitmap((Bitmap) it.next(), null, rectPieza32, null);
			canvas.drawBitmap((Bitmap) it.next(), null, rectPieza33, null);
			canvas.drawBitmap(im, null, rectPieza, null);

			ourHolder.unlockCanvasAndPost(canvas);
		}
		
	}	
}
	
}