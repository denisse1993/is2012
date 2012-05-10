package com.cinnamon.is.minijuegos.mj1;

import android.graphics.Bitmap;

public class Muertes {
	private int num;
	private Bitmap bmp;
	
	
	
	
	public Muertes(int num,Bitmap bmp){
		this.setNum(num);
		this.setBmp(bmp);
	}




	/**
	 * @return the bmp
	 */
	public Bitmap getBmp() {
		return bmp;
	}




	/**
	 * @param bmp the bmp to set
	 */
	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}




	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}




	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
}
