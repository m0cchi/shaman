package shaman.pdf;

import java.io.IOException;
import java.util.zip.ZipException;

import shaman.exception.UnSupportedExtensionException;
import shaman.pdf.plugin.ConverterFromFile;
import shaman.pdf.plugin.ConverterFromZip;
import shaman.util.Util;

import com.itextpdf.text.DocumentException;

public abstract class Converter {
	public static Converter instance(String filepath, boolean direction)
			throws ZipException, DocumentException, IOException,
			UnSupportedExtensionException {
		Converter converter = null;
		System.out.println(Util.getExt(filepath));
		switch (Util.getExt(filepath)) {
		case ConverterFromZip.EXT:
			converter = new ConverterFromZip(filepath, direction);
			break;
		case ConverterFromFile.EXT:
			converter = new ConverterFromFile(filepath, direction);
			break;
		default:
			throw new UnSupportedExtensionException();
		}

		return converter;
	}

	public abstract void toPdf() throws Throwable;

}
