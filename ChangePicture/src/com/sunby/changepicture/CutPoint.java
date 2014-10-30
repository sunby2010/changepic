package com.sunby.changepicture;

public class CutPoint {
	int bX=-1;
	int bY=-1;
	int eX=-1;
	int eY=-1;
	public int getWidth(){
		if(bX<0 || eX<0)
			return -1;
		return eX-bX;
	}
	public int getHeight(){
		if(bY<0 || eY<0)
			return -1;
		return eY-bY;
	}
}
