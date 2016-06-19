package shaman.pdf.plugin;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import shaman.pdf.Converter;
import shaman.util.NameSorter;
import shaman.util.PdfMaker;

import com.itextpdf.text.DocumentException;

public class ConverterFromFile extends Converter {
	final public static String EXT = "";
	private PdfMaker pdfMaker;
	private File file;

	public ConverterFromFile(String filepath, boolean direction)
			throws FileNotFoundException, DocumentException {
		this(new File(filepath), direction);
	}

	public ConverterFromFile(File file, boolean direction)
			throws FileNotFoundException, DocumentException {
		this.file = file;
		String pdfPath = file.getAbsolutePath() + ".pdf";
		this.pdfMaker = new PdfMaker(pdfPath, direction);
	}

	public ArrayList<String> searchFile(File file) {
		ArrayList<String> fileList = new ArrayList<>();
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				fileList.addAll(searchFile(f));
			} else {
				fileList.add(f.getAbsolutePath());
			}
		}
		return fileList;
	}

	@Override
	public void toPdf() throws Throwable {
		ArrayList<String> fileList = searchFile(this.file);
		Collections.sort(fileList, NameSorter.getInstance());
		for (String path : fileList) {
			int ch;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);
			while ((ch = bis.read()) != -1) {
				output.write(ch);
			}
			pdfMaker.addImage(output.toByteArray());
			output.close();
			bis.close();
		}
		pdfMaker.close();
	}

}
