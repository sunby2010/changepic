package com.sunby.changepicture;

import javax.swing.JFrame;

public class MainPG {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pam;
		int pamtype=0;
		MainFrame main = new MainFrame();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//main.setExtendedState(JFrame.MAXIMIZED_BOTH);
		main.setVisible(true);
		for(int i=0;i<args.length;i++){
			pam=args[i];
			if(isPam(pam)){
				if(pam.equals("-b")){
					pamtype=1;
				}else if(pam.equals("-c")){
					pamtype=2;
				}else{
					pamtype=0;
				}
			}else{
				switch(pamtype){
				case 1:
					main.setPicDir(pam);
					break;
				case 2:
					main.setCvtDir(pam);
				}
				pamtype=0;
			}
		}
	}
	private static boolean isPam(String buf){
		if(buf.length()<1)
			return false;
		if(buf.charAt(0)=='-')
			return true;
		return false;
	}

}
