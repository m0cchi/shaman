package shaman.frame;

import java.io.File;

import shaman.util.Util;

public class FileObject {
	private File file;
	public FileObject(File file){
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}

	@Override
	public String toString(){
		return Util.getFileName(file.getAbsolutePath());
	}

}
