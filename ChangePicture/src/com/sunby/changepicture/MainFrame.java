package com.sunby.changepicture;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MOVEUP=0;
	private static final int MOVEDOWN=1;
	private static final int MOVELEFT=2;
	private static final int MOVERIGHT=3;
	MyButton lockbn;
	MyButton spinbn;
	PicturePanel picPanel;
	private boolean isFix=false;
	private boolean isSpin=false;
	private FileMng filemng;
	private String outPath;
	public MainFrame(){
		setTitle("ChangePicture");
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MainFrame.class.getResource("/res/images/icon.png")));
		Container contentPane = getContentPane();
		int cy=dim.height-30;
		int cx=cy*3/4+100;
		setSize(cx,cy);
		setLocationRelativeTo(null);
		picPanel=new PicturePanel();
		contentPane.add(picPanel);
		
		contentPane.add(createToolsPanel(), BorderLayout.NORTH);
		
		setListener();
	}
	private JPanel createToolsPanel(){
		JPanel toolsPanel=new JPanel();
		toolsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
		MyButton openbn = new MyButton(0,MainFrame.class.getResource("/res/images/file_open.png"));
		openbn.addActionListener(new ActionClick("open"));
		MyButton prevbn = new MyButton(0,MainFrame.class.getResource("/res/images/go_prev.png"));
		prevbn.addActionListener(new ActionClick("prev"));
		MyButton nextbn = new MyButton(0,MainFrame.class.getResource("/res/images/go_next.png"));
		nextbn.addActionListener(new ActionClick("next"));
		MyButton jumpbn = new MyButton(0,MainFrame.class.getResource("/res/images/spin.png"));
		jumpbn.addActionListener(new ActionClick("jump"));
		lockbn = new MyButton(0,MainFrame.class.getResource("/res/images/lock.png"));
		lockbn.addActionListener(new ActionClick("lock"));
		spinbn = new MyButton(0,MainFrame.class.getResource("/res/images/spin.png"));
		spinbn.addActionListener(new ActionClick("spin"));
		MyButton execbn = new MyButton(0,MainFrame.class.getResource("/res/images/exec.png"));
		execbn.addActionListener(new ActionClick("exec"));
		toolsPanel.add(openbn);
		toolsPanel.add(prevbn);
		toolsPanel.add(nextbn);
		toolsPanel.add(jumpbn);
		toolsPanel.add(lockbn);
		toolsPanel.add(spinbn);
		toolsPanel.add(execbn);
		return toolsPanel;
	}
	class ActionClick implements ActionListener{
		private String type;
		public ActionClick(String type){
			this.type=type;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(type.equals("open")){
				openClick();
			}else if(type.equals("next")){
				nextClick();
			}else if(type.equals("prev")){
				prevClick();
			}else if(type.equals("jump")){
				jumpClick();
			}else if(type.equals("spin")){
				spinClick();
			}else if(type.equals("lock")){
				lockClick();
			}else if(type.equals("exec")){
				execClick();
			}
		}
		
	}
	private void setListener(){
		picPanel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) { 
				if(e.getButton()==MouseEvent.BUTTON1){
					if(isSpin){
						setSpinBegin(e.getX(),e.getY());
					}else{
						setCutBegin(e.getX(),e.getY());
					}
				}else if(e.getButton()==MouseEvent.BUTTON3){
					if(isSpin){
						setSpinEnd(e.getX(),e.getY());
					}else{
						setCutEnd(e.getX(),e.getY());
					}
					//System.out.println("clickRight");
				}
			}
		});
		Toolkit tk = Toolkit.getDefaultToolkit();  
        tk.addAWTEventListener(new AWTEventListener(){
            public void eventDispatched(AWTEvent event) {
                if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED) {
                	//放入自己的键盘监听事件
      			  int code=((KeyEvent) event).getKeyCode();
      			  switch(code){
      			  case KeyEvent.VK_E:
      				  setCutMove(MOVEUP,1);
      				  break;
      			  case KeyEvent.VK_D:
      				  setCutMove(MOVEDOWN,1);
      				  break;
      			  case KeyEvent.VK_S:
      				  setCutMove(MOVELEFT,1);
      				  break;
      			  case KeyEvent.VK_F:
      				  setCutMove(MOVERIGHT,1);
      				  break;
      			  case KeyEvent.VK_I:
    				  setCutMove(MOVEUP,2);
    				  break;
    			  case KeyEvent.VK_K:
    				  setCutMove(MOVEDOWN,2);
    				  break;
    			  case KeyEvent.VK_J:
    				  setCutMove(MOVELEFT,2);
    				  break;
    			  case KeyEvent.VK_L:
    				  setCutMove(MOVERIGHT,2);
    				  break;
      			  case KeyEvent.VK_ENTER:
      			  case KeyEvent.VK_R:
      				  execClick();
      				  break;
      			  case KeyEvent.VK_B:
      				  setCutRectClick();
      				  break;
      			  case KeyEvent.VK_U:
      				  lockClick();
      				  break;
       			  case KeyEvent.VK_0:
       			  case KeyEvent.VK_C:
       				  setSpinGA(1);
       				  break;
       			  case KeyEvent.VK_9:
       			  case KeyEvent.VK_X:
     				  setSpinGA(-1);
     				  break;
       			  case KeyEvent.VK_LEFT:
       				  prevClick();
       				  break;
       			  case KeyEvent.VK_RIGHT:
       				  nextClick();
       				  break;
     			  }
                }
                //((KeyEvent) event).getKeyCode();// 获取按键的code
                //((KeyEvent) event).getKeyChar();// 获取按键的字符
                }
            }, AWTEvent.KEY_EVENT_MASK);
		
	}
	protected void setCutRectClick() {
		// TODO Auto-generated method stub
		String str = JOptionPane.showInputDialog("Please input \"w,h\"");
		if(str!=null){
			int i=str.indexOf(',');
			int w=Integer.parseInt(str.substring(0, i));
			int h=Integer.parseInt(str.substring(i+1));
			setCutRect(w,h);
		}
	}
	public void execClick() {
		// TODO Auto-generated method stub
		changePic();
		nextClick();
	}
	public void lockClick() {
		// TODO Auto-generated method stub
		if(isFix){
			isFix=false;
			lockbn.setSelected(isFix);
		}else{
			isFix=true;
			lockbn.setSelected(isFix);
		}
	}
	public void spinClick() {
		// TODO Auto-generated method stub
		if(isSpin){
			isSpin=false;
			spinbn.setSelected(isSpin);
			picPanel.SpinPicture();
		}else{
			isSpin=true;
			spinbn.setSelected(isSpin);
		}
	}
	public void prevClick() {
		// TODO Auto-generated method stub
		FileItem item;
		if(filemng!=null){
			item=filemng.getPrev();
			dispPicture(item);
		}
	}
	public void nextClick() {
		// TODO Auto-generated method stub
		FileItem item;
		if(filemng!=null){
			item=filemng.getNext();
			dispPicture(item);
		}
	}
	private void jumpClick() {
		String str = JOptionPane.showInputDialog("Please input a value");
		if(str!=null){
			int pos=Integer.parseInt(str);
			if(pos<1){
				return;
			}
			pos--;
			FileItem item=filemng.set(pos);
			dispPicture(item);
		}
	}
	private void dispPicture(FileItem item){
		if(item != null){
			//String title=getTitleFix();
			setCutTitle();
			picPanel.ReadPicture(item.getPath());
		}
	}
	private String getTitleFix(){
		String title;
		title="change (";
		int pos=filemng.CurPos();
		FileItem item=filemng.get();
		if(item==null)
			return "";
		title+=Integer.toString(pos+1)+"/";
		title+=Integer.toString(filemng.size())+")";
		title+=item.getName();
		if(item.isFlag(outPath)){
			title+="*";
		}
		return title;
	}
	private void setCutTitle(){
		CutPoint cut=picPanel.getCutPoint();
		String cutbuf=getTitleFix()+" ";
		if(cut.bX<0 ||cut.eX<0){
			setTitle(cutbuf);
			return;
		}
		int imageH=picPanel.getImageHeight();
		PicRect rect = picPanel.getPicRect();
		int rectH=rect.getHeight();
		int initX=cut.bX*imageH/rectH;
		int initY=cut.bY*imageH/rectH;
		int w=cut.getWidth()*imageH/rectH;
		int h=cut.getHeight()*imageH/rectH;
		cutbuf+="(x:"+Integer.toString(initX);
		cutbuf+=" y:"+Integer.toString(initY);
		cutbuf+=" w:"+Integer.toString(w);
		cutbuf+=" h:"+Integer.toString(h)+")";
		setTitle(cutbuf);
	}
	protected void openClick() {
		// TODO Auto-generated method stub
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter ff = new FileNameExtensionFilter( null, "lst");
		chooser.setFileFilter(ff);
		int result=chooser.showOpenDialog(MainFrame.this);
		if(result==JFileChooser.APPROVE_OPTION) {
			String name = chooser.getSelectedFile().getPath();
			outPath=chooser.getSelectedFile().getParent()+"/out";
			System.out.println(outPath);
			filemng=new FileMng(name);
			if(filemng.size()>0){
				dispPicture(filemng.getNext());
				//picPanel.ReadPicture(filemng.getNext().getPath());
			}
		}
	}
	private void setSpinBegin(int x,int y){
		if(picPanel.getPicRect().inRect(x, y)){
			SpinPoint spin=picPanel.getSpinPoint();
			spin.setBegin(x, y);
			picPanel.setSpinPoint(spin);
		}
	}
	private void setSpinEnd(int x,int y){
		if(picPanel.getPicRect().inRect(x, y)){
			SpinPoint spin=picPanel.getSpinPoint();
			spin.setEnd(x, y);
			picPanel.setSpinPoint(spin);
		}
	}
	private void setSpinGA(int a){
		int ix=100,iy=100,dx=200;
		SpinPoint spin=picPanel.getSpinPoint();
		spin.setBegin(ix, iy);
		spin.setEnd(ix+dx, iy-a);
		picPanel.setSpinPoint(spin);
		picPanel.SpinPicture();
	}
	private void setCutBegin(int x,int y){
		if(picPanel.getPicRect().inRect(x, y)){
			CutPoint cut=picPanel.getCutPoint();
			PicRect rect = picPanel.getPicRect();
			x=x-rect.getInitX();
			y=y-rect.getInitY();
			if(isFix){
				int w=cut.getWidth();
				int h=cut.getHeight();
				cut.eX=x+w;
				cut.eY=y+h;
			}
			cut.bX=x;cut.bY=y;
			picPanel.setCutLine(cut);
			setCutTitle();
		}
	}
	private void setCutEnd(int x,int y){
		if(picPanel.getPicRect().inRect(x, y)){
			CutPoint cut=picPanel.getCutPoint();
			PicRect rect = picPanel.getPicRect();
			x=x-rect.getInitX();
			y=y-rect.getInitY();
			//cut.bX=x;cut.bY=y;
			cut.eX=x;cut.eY=y;
			picPanel.setCutLine(cut);
			setCutTitle();
		}
	}
	private void setCutMove(int type,int p){
		CutPoint cut=picPanel.getCutPoint();
		int dx=3;
		if(cut.bX<0 || cut.eX<0)
			return;
		if(p==1){
			if(type==MOVERIGHT){
				cut.bX+=dx;
				if(isFix)
					cut.eX+=dx;
			}else if(type==MOVELEFT){
				cut.bX-=dx;
				if(isFix)
					cut.eX-=dx;
			}else if(type==MOVEUP){
				cut.bY-=dx;
				if(isFix)
					cut.eY-=dx;
			}else if(type==MOVEDOWN){
				cut.bY+=dx;
				if(isFix)
					cut.eY+=dx;
			}
		}else{
			if(type==MOVERIGHT){
				cut.eX+=dx;
			}else if(type==MOVELEFT){
				cut.eX-=dx;
			}else if(type==MOVEUP){
				cut.eY-=dx;
			}else if(type==MOVEDOWN){
				cut.eY+=dx;
			}
		}
		picPanel.setCutLine(cut);
		setCutTitle();
	}
	private void setCutRect(int w,int h){
		int imageH=picPanel.getImageHeight();
		PicRect rect = picPanel.getPicRect();
		CutPoint cut = picPanel.getCutPoint();
		//int ww=w*rect.getHeight()/imageH;
		//int wh=h*rect.getHeight()/imageH;
		int ww = getH(w,rect.getHeight(),imageH);
		int wh = getH(h,rect.getHeight(),imageH);
		if(cut.bX<0){
			cut.bX=(rect.getWidth()-ww)/2;
			cut.bY=(rect.getHeight()-wh)/2;
		}
		int ex=cut.bX+ww;
		int ey=cut.bY+wh;
		cut.eX=ex;cut.eY=ey;
		picPanel.setCutLine(cut);
		setCutTitle();
	}
	private int getH(int d,int l,int f){
		int x=d*l/f;
		while((x*f/l)<d){
			x++;
			System.out.println("+");
		}
		return x;
	}
	private String genOutDir(String path){
		String out=path+"/out";
		File f = new File(out);
		if(!f.exists()){
			f.mkdir();
		}
		return out;
	}
	private void changePic(){
		FileItem item = filemng.get();
		if(item==null)
			return;
		ChangePicture pic = new ChangePicture();
		pic.setInputName(item.getPath());
		File f = new File(item.getPath());
		String outname=genOutDir(f.getParent())+"/"+f.getName();
		pic.setOutputName(outname);
		int imageH=picPanel.getImageHeight();
		PicRect rect = picPanel.getPicRect();
		CutPoint cut = picPanel.getCutPoint();
		if(cut.bX>=0){
			int rectH=rect.getHeight();
			int initX=cut.bX*imageH/rectH;
			int initY=cut.bY*imageH/rectH;
			int w=cut.getWidth()*imageH/rectH;
			int h=cut.getHeight()*imageH/rectH;
			pic.setP_x(initX);
			pic.setP_y(initY);
			pic.setWidth(w);
			pic.setHeight(h);
		}
		pic.setArc(picPanel.getArc());
		pic.change();
	}

}
