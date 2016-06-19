package shaman.pdf.plugin;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import shaman.pdf.Converter;
import shaman.util.PdfMaker;
import shaman.util.Util;
import shaman.util.ZipEntrySorter;

import com.itextpdf.text.DocumentException;

public class ConverterFromZip extends Converter {
	final public static String EXT = "zip";
	private PdfMaker pdfMaker;
	private ZipFile archive;

	public ConverterFromZip(String filepath, boolean direction)
			throws DocumentException, ZipException, IOException {
		this(new File(filepath), direction);
	}

	public ConverterFromZip(File file, boolean direction)
			throws DocumentException, ZipException, IOException {
		String pdfPath = file.getAbsolutePath().replaceFirst(
				"(Z|z)(I|i)(P|p)$", "pdf");
		this.pdfMaker = new PdfMaker(pdfPath, direction);
		this.archive = new ZipFile(file);
	}

	public void toPdf() throws DocumentException, ZipException, IOException {
		ArrayList<ZipEntry> entries = new ArrayList<>();
		for (Enumeration<? extends ZipEntry> e = archive.entries(); e
				.hasMoreElements();) {
			ZipEntry entry = e.nextElement();
			if (entry.isDirectory())
				continue;
			if (Util.isSupportedFormats(entry.getName())
					&& !Util.isHiddenPath(entry.getName())) {
				entries.add(entry);
			}
		}

		Collections.sort(entries, new ZipEntrySorter());
		for (ZipEntry entry : entries) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			int ch;
			InputStream is = archive.getInputStream(entry);
			BufferedInputStream bis = new BufferedInputStream(is);
			while ((ch = bis.read()) != -1) {
				output.write(ch);
			}
			pdfMaker.addImage(output.toByteArray());
			output.close();
			bis.close();
		}
		archive.close();
		pdfMaker.close();
	}

}
