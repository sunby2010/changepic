package com.sunby.changepicture;

import java.io.File;

public class FileItem {
	private String path=null;
	private String name;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
		File f = new File(path);
		this.name=f.getName();
	}
	public boolean isFlag(String outPath) {
		String outname=outPath+"/"+this.name;
		File f = new File(outname);
		return f.exists();
	}
	public String getName() {
		return name;
	}
}