package com.sunby.changepicture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PicturePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int scrW,scrH;
	private int picW,picH;
	private int initX,initY;
	private BufferedImage image=null;
	private CutPoint cut=new CutPoint();
	private SpinPoint spin = new SpinPoint();
	private PicRect picrect=new PicRect();
	private double arc=255;
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D) g;
		drawHelpMsg(g2);
		if(image!=null){
			g2.drawRect(initX-1, initY-1, picW+1, picH+1);
			g2.drawImage(image,initX,initY,picW,picH,null);
			drawCutLine(g2);
			drawSpinLine(g2);
		}
	}
	public void ReadPicture(String name){
		scrH=getHeight()-5;
		scrW=getWidth()-5;
		try {
			image=ImageIO.read(new File(name));
			picW=image.getWidth(this);
			picH=image.getHeight(this);
			if(picW>scrW){
				picH=picH*scrW/picW;
				picW=scrW;
				if(picH>scrH){
					picW=picW*scrH/picH;
					picH=scrH;
				}
			}else{
				if(picH>scrH){
					picW=picW*scrH/picH;
					picH=scrH;
				}
			}
			initX=(getWidth()-picW)/2;
			initY=(getHeight()-picH)/2;
			picrect.setInitX(initX);
			picrect.setInitY(initY);
			picrect.setWidth(picW);
			picrect.setHeight(picH);
			arc=255;
			spin.bX=-1;spin.eX=-1;
			repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setCutLine(CutPoint cut){
		this.cut=cut;
		repaint();
	}
	public PicRect getPicRect(){
		return picrect;
	}
	public CutPoint getCutPoint(){
		return cut;
	}
	public SpinPoint getSpinPoint(){
		return spin;
	}
	public void setSpinPoint(SpinPoint spin){
		this.spin=spin;
		repaint();
	}
	public double getArc(){
		return arc;
	}
	public int getImageHeight(){
		if(image!=null){
			return image.getHeight();
		}else
			return 0;
	}
	public int getImageWidth(){
		if(image!=null){
			return image.getWidth();
		}else
			return 0;
	}
	public void SpinPicture(){
		double g=spin.getG();
		System.out.println(g);
		if(g>10){
	        spin.setBegin(-1, -1);
	        spin.setEnd(-1, -1);
	        repaint();
			return;
		}
		int w=image.getWidth();
		int h=image.getHeight();
		BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
		//BufferedImage rotatedImage = new BufferedImage(w+20, h+20, BufferedImage.TYPE_INT_RGB);
		Graphics2D gs = (Graphics2D)rotatedImage.getGraphics();
        rotatedImage  = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);  
		AffineTransform at = new AffineTransform();  
        at.rotate(g, w/2, h/2);//旋转图象  
        at.translate(0, 0);  
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);  
        op.filter(image, rotatedImage);
        image = rotatedImage;
        if(arc>10)
        	arc=g;
        else
        	arc+=g;
        spin.setBegin(-1, -1);
        spin.setEnd(-1, -1);
        repaint();
	}
	private void drawCutLine(Graphics2D g2){
		if(cut.bX<0)
			return;
		int bX = picrect.getInitX()+cut.bX;
		int bY = picrect.getInitY()+cut.bY;
		g2.setColor(Color.RED);
		if(cut.eX<0){
			int l=5;
			g2.drawLine(bX-l, bY, bX+l, bY);
			g2.drawLine(bX, bY-l, bX, bY+l);
		}else{
			g2.drawRect(bX, bY, cut.getWidth(), cut.getHeight());
		}
	}
	private void drawSpinLine(Graphics2D g2){
		if(spin.bX<0)
			return;
		if(spin.eX<0){
			int l=5;
			g2.drawLine(spin.bX-l, spin.bY, spin.bX+l, spin.bY);
			g2.drawLine(spin.bX, spin.bY-l, spin.bX, spin.bY+l);
		}else{
			g2.drawLine(spin.bX, spin.bY, spin.eX, spin.eY);
		}
	}
	private void drawHelpMsg(Graphics2D g2){
		String [] msg={"e:左上","d:左下","s:左左","f:左右"
				,"i:右上","k:右下","j:右左","l:右右"
				,"r,En:执行","b:设置矩形","u:锁矩形"
				,"0,v:顺旋","9,c:逆旋","lf:上页","rg:下页"};
		int x=10,y=20;
		int dy=30;
		for(int i=0;i<msg.length;i++){
			g2.drawString(msg[i], x, y);
			y+=dy;
		}
	}
}
