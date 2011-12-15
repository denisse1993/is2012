package com.cinnamon.is.minijuegos.reinas;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Tablero {

	private Set<Reina> setReinas;
	private Iterator<Reina> itReinas;
	private int numReinas;
	private boolean llenado[][];
	
	public Tablero(){
		setReinas= new HashSet<Reina>();
		numReinas=0;
		llenado=new boolean [8][8];
		for(int i=0; i<8;i++){
			for(int j=0; j<8;j++){
				llenado[i][j]=false;
			}
		}
	}
	
	public int getnumReinas(){
		return this.numReinas;
	}
	
	public void setnumReinas(int nR){
		this.numReinas=nR;
	}
	
	public boolean addReina(Reina r, int fila, int columna){
		if(numReinas < 8 && !llenado[fila][columna]){
			setReinas.add(r);
			numReinas++;
			llenado[fila][columna]=true;
			return true;			
		}else return false;
	}
	
	public Set<Reina> getSetReinas(){
		return this.setReinas;
	} 
	public boolean hayReina(int fila, int columna){
		return this.llenado[fila][columna];
	}

	public void removeReina(Reina r, int f, int c) {
		if(numReinas > 0){
			setReinas.remove(r);
			llenado[f][c]=false;
			numReinas--;
		}
		
	}

	public Reina dameReina(int f, int c) {
		Reina r=null;
		if(hayReina(f, c)){
		    itReinas=setReinas.iterator();
			boolean enc=false;
			while(itReinas.hasNext() && !enc){
				r=itReinas.next();
				enc=(f==r.dameFila())&&(c==r.dameCol());
			}	
		}
		return r;
	}
	public boolean dameCelda(int f, int c) {
		return this.llenado[f][c];
	}
}
