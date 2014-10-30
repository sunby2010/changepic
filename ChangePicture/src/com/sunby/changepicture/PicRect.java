package com.sunby.changepicture;

public class PicRect {
	private int initX=-1;
	private int initY=-1;
	private int width;
	private int height;
	public int getInitX() {
		return initX;
	}
	public void setInitX(int initX) {
		this.initX = initX;
	}
	public int getInitY() {
		return initY;
	}
	public void setInitY(int initY) {
		this.initY = initY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean inRect(int x,int y){
		if(x<initX || x>initX+width)
			return false;
		if(y<initY || y>initY+height)
			return false;
		return true;
	}
}
