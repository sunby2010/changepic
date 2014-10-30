package com.sunby.changepicture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileMng {

	private ArrayList<FileItem> filelist = new ArrayList<FileItem>();
	private int pos=-1;
	public FileMng(String path){
		readList(path);
	}
	public int CurPos(){
		return pos;
	}
	public int size(){
		return filelist.size();
	}
	public FileItem getNext(){
		pos++;
		if(pos>=filelist.size())
			pos=0;
		return filelist.get(pos);
	}
	public FileItem getPrev(){
		pos--;
		if(pos<=0)
			pos=size()-1;
		return filelist.get(pos);
	}
	public FileItem get(){
		if(pos<0)
			return null;
		return filelist.get(pos);
	}
	public FileItem set(int pos){
		if(pos<0 || pos>=size())
			return null;
		this.pos=pos;
		return filelist.get(pos);
	}
	private void readList(String path){
		String linebuf;
		File f = new File(path);
		FileItem item;
		//ArrayList<String> book = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while((linebuf=br.readLine())!=null){
				if(!linebuf.equals("")) {
					item = new FileItem();
					item.setPath(f.getParent()+"/"+linebuf);
					filelist.add(item);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
