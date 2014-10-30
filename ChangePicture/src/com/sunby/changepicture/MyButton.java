package com.sunby.changepicture;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1964154636195088075L;
	private Color color_line=new Color(125,125,155);
	private int bn_w=24,bn_h=24;

	public MyButton(int bntype,URL imageurl){
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setMargin(new Insets(0, 0, 0, 0));
		try {
			BufferedImage source = ImageIO.read(imageurl);
			setIcon(new ImageIcon(getImage0(source)));
			setRolloverIcon(new ImageIcon(getImage1(source)));
			setPressedIcon(new ImageIcon(getImage2(source)));
			setSelectedIcon(new ImageIcon(getImage3(source)));
			setRolloverSelectedIcon(new ImageIcon(getImage4(source)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//this.set
	}
	private Image getImage0(BufferedImage source){
		BufferedImage image=new BufferedImage(bn_w,bn_h,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d=image.createGraphics();
		image = g2d.getDeviceConfiguration().createCompatibleImage(bn_w,bn_h, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		int x=(bn_w-source.getWidth())/2;
		int y=(bn_h-source.getHeight())/2;
		g2d.drawImage(source, x, y, source.getWidth(), source.getHeight(), null);
		g2d.dispose();
		//g2d.setColor(Color.BLACK);
		//g2d.draw3DRect(0, 0, source.getWidth(), source.getHeight(), true);
		return image;
	}
	private Image getImage1(BufferedImage source){
		BufferedImage image=new BufferedImage(bn_w,bn_h,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d=image.createGraphics();
		image = g2d.getDeviceConfiguration().createCompatibleImage(bn_w,bn_h, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		g2d.setColor(new Color(255,255,255));
		g2d.fillRoundRect(0, 0, bn_w-1, bn_h-1, 6, 6);
		int x=(bn_w-source.getWidth())/2;
		int y=(bn_h-source.getHeight())/2;
		g2d.drawImage(source, x, y, source.getWidth(), source.getHeight(), null);
		g2d.setColor(color_line);
		g2d.drawRoundRect(0, 0, bn_w-1, bn_h-1, 6, 6);
		g2d.dispose();
		return image;
	}
	private Image getImage2(BufferedImage source){
		BufferedImage image=new BufferedImage(bn_w,bn_h,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d=image.createGraphics();
		image = g2d.getDeviceConfiguration().createCompatibleImage(bn_w,bn_h, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		int x=(bn_w-source.getWidth())/2;
		int y=(bn_h-source.getHeight())/2;
		g2d.drawImage(source, x+2, y+2, source.getWidth(), source.getHeight(), null);
		g2d.setColor(color_line);
		g2d.drawRoundRect(0, 0, bn_w-1, bn_h-1, 6, 6);
		g2d.dispose();
		return image;
	}
	private Image getImage3(BufferedImage source){
		BufferedImage image=new BufferedImage(bn_w,bn_h,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d=image.createGraphics();
		image = g2d.getDeviceConfiguration().createCompatibleImage(bn_w,bn_h, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		g2d.setColor(new Color(200,200,200));
		g2d.fillRoundRect(0, 0, bn_w-1, bn_h-1, 6, 6);
		int x=(bn_w-source.getWidth())/2;
		int y=(bn_h-source.getHeight())/2;
		g2d.drawImage(source, x+1, y+1, source.getWidth(), source.getHeight(), null);
		g2d.dispose();
		return image;
	}
	private Image getImage4(BufferedImage source){
		BufferedImage image=new BufferedImage(bn_w,bn_h,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d=image.createGraphics();
		image = g2d.getDeviceConfiguration().createCompatibleImage(bn_w,bn_h, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		g2d.setColor(new Color(200,200,200));
		g2d.fillRoundRect(0, 0, bn_w-1, bn_h-1, 6, 6);
		int x=(bn_w-source.getWidth())/2;
		int y=(bn_h-source.getHeight())/2;
		g2d.drawImage(source, x+1, y+1, source.getWidth(), source.getHeight(), null);
		g2d.setColor(color_line);
		g2d.drawRoundRect(0, 0, bn_w-1, bn_h-1, 6, 6);
		g2d.dispose();
		return image;
	}
}
