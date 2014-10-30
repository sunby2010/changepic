package com.sunby.changepicture;

public class SpinPoint {
	int bX=-1,bY=-1;
	int eX=-1,eY=-1;
	public void setBegin(int x,int y){
		bX=x;
		bY=y;
	}
	public void setEnd(int x,int y){
		eX=x;
		eY=y;
	}
	public double getG(){
		if(bX>0 && eX>0){
			if(bX>eX)
				return 255L;
			else
				return Math.atan((double)(bY-eY)/(double)(eX-bX));
		}else
			return 255L;
	}
}
