package com.sunby.changepicture;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ChangePicture {

	private String inputName;
	private String outputName;
	private int p_x=-1;
	private int p_y=-1;
	private int width=-1;
	private int height=-1;
	private double arc=255;
	private BufferedImage image;
	public double getArc() {
		return arc;
	}
	public void setArc(double arc) {
		this.arc = arc;
		System.out.println(arc);
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
		System.out.println(inputName);
	}
	public String getOutputName() {
		return outputName;
	}
	public void setOutputName(String outputName) {
		this.outputName = outputName;
		System.out.println(outputName);
	}
	public int getP_x() {
		return p_x;
	}
	public void setP_x(int p_x) {
		this.p_x = p_x;
	}
	public int getP_y() {
		return p_y;
	}
	public void setP_y(int p_y) {
		this.p_y = p_y;
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
	public void change(){
		try {
			if(isSpin()){
				if(isCut()){
					String tempName=getTempName();
					spinImage(inputName,tempName);
					cutImage(tempName,outputName);
				}else{
					spinImage(inputName,outputName);
				}
			}else{
				cutImage(inputName,outputName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean isSpin(){
		if(arc>10)
			return false;
		else
			return true;
	}
	private boolean isCut(){
		if(p_x<0)
			return false;
		else
			return true;
	}
	private String getTempName(){
		File f = new File(outputName);
		String name=f.getParent()+"/temp.png";
		return name;
	}
	private void spinImage(String in,String out) throws IOException{
			image=ImageIO.read(new File(in));
			int w=image.getWidth();
			int h=image.getHeight();
			BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
			Graphics2D gs = (Graphics2D)rotatedImage.getGraphics();
	        rotatedImage  = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);  
			AffineTransform at = new AffineTransform();  
	        at.rotate(arc, w/2, h/2);//旋转图象  
	        at.translate(0, 0);  
	        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);  
	        op.filter(image, rotatedImage);
	        image = rotatedImage;
	        FileOutputStream fos=new FileOutputStream(out);  
	        ImageIO.write(image, "png", fos);
	}
	private void cutImage(String in,String out) throws IOException{
		if(p_x>0 || p_y>0 || width>0 || height>0){
	        FileInputStream is = null ;
	        ImageInputStream iis =null ;
	     
	        try{   
	            //读取图片文件
	            is = new FileInputStream(in); 
	            
	            /*
	             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 
	             * 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 .
	             *（例如 "jpeg" 或 "tiff"）等 。 
	             */
	            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("png");  
	            ImageReader reader = it.next(); 
	            //获取图片流 
	            iis = ImageIO.createImageInputStream(is);
	               
	            /* 
	             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
	             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
	             *  避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
	             */
	            reader.setInput(iis,true) ;
	            
	            /* 
	             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O 
	             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件
	             * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回 
	             * ImageReadParam 的实例。  
	             */
	            ImageReadParam param = reader.getDefaultReadParam(); 
	             
	            /*
	             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
	             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。 
	             */ 
	            Rectangle rect = new Rectangle(p_x, p_y, width, height);
	            System.out.println(String.format("x:%d,y:%d", p_x,p_y));
	            System.out.println(String.format("w:%d,h:%d", width, height));
	            
	              
	            //提供一个 BufferedImage，将其用作解码像素数据的目标。 
	            param.setSourceRegion(rect); 

	            /*
	             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将
	             * 它作为一个完整的 BufferedImage 返回。
	             */
	            BufferedImage bi = reader.read(0,param);                
	      
	            //保存新图片 
	            ImageIO.write(bi, "png", new File(out));     
	        }
	        
	        finally{
	            if(is!=null)
	               is.close() ;       
	            if(iis!=null)
	               iis.close();  
	        } 
	        
	         			
		}
	}
}
